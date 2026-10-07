package EndlessCheng.GenreMenu.DP.Interval.Interval;

import java.util.HashSet;
import java.util.Set;

public class ScoreOfStudents {

    // https://leetcode.cn/problems/the-score-of-students-solving-math-expression/solutions/1018504/qu-jian-dpha-xi-biao-by-endlesscheng-62fj/
    public int scoreOfStudents(String s, int[] ans) {
        int n = s.length();
        char[] sa = s.toCharArray();
        int ret = 0;
        for (int i = 0; i < n; ) {
            int mul = sa[i] - '0';
            i += 2;
            while (i < n && sa[i - 1] == '*') {
                mul *= (sa[i] - '0');
                i += 2;

            }
            ret += mul;
        }

        // 表示 s[l..r] 內的表達式在不同計算順序下的不超過 1000 的所有可能值
        Set<Integer>[][] f = new HashSet[n][n];
        Set<Integer> possRes = calcAns(0, n - 1, s, f);
        int totSc = 0;
        for (int an : ans) {
            if (an == ret) {
                totSc += 5;
            } else if (possRes.contains(an)) totSc += 2;
        }
        return totSc;
    }

    private Set<Integer> calcAns(int l, int r, String s, Set<Integer>[][] f) {
        if (f[l][r] != null) return f[l][r];
        Set<Integer> st = new HashSet<>();
        if (l == r) {
            st.add(s.charAt(l) - '0');
        } else {
            for (int i = l + 1; i < r; i += 2) {
                char op = s.charAt(i);
                Set<Integer> leSet = calcAns(l, i - 1, s, f);
                Set<Integer> riSet = calcAns(i + 1, r, s, f);

                for (int v : leSet) {
                    for (int w : riSet) {
                        int x = (op == '+') ? v + w : v * w;
                        if (x <= 1000) st.add(x);

                    }
                }
            }
        }
        f[l][r] = st;
        return st;
    }


}
