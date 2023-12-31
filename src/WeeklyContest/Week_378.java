package WeeklyContest;

import java.util.*;

public class Week_378 {
    // https://leetcode.cn/problems/check-if-bitwise-or-has-trailing-zeros/solutions/2585847/pan-duan-shi-fou-you-zhi-shao-liang-ge-o-aggh/
    // nums 中的奇數肯定不能參與或運算，這會導致或運算結果的最低位必然是 1。
    // 所以只有偶數能參與或運算，這樣最低位必然是 0。
    // 所以判斷 nums 中是否至少有兩個偶數即可。
    public boolean hasTrailingZeros(int[] nums) {
        int even = nums.length;
        for (int x : nums) {
            even -= x % 2;
        }
        return even >= 2;
    }


    // https://leetcode.cn/problems/find-longest-special-substring-that-occurs-thrice-i/solutions/2585886/shuang-zhi-zhen-by-legendqi-0crk/
    public int maximumLength(String s) {
        Map<String, Integer> map = new HashMap<>();
        int l = 0;
        while (l < s.length()) {
            int r = l;
            while (r < s.length() && s.charAt(r) == s.charAt(l)) {
                String key = s.substring(l, r + 1);
                int times = map.getOrDefault(key, 0) + 1;
                map.put(key, times);
                r++;
            }
            l++;
        }
        int max = -1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 2) {
                max = Math.max(entry.getKey().length(), max);
            }
        }
        return max;
    }


    // https://leetcode.cn/problems/find-longest-special-substring-that-occurs-thrice-ii/solutions/2585801/fen-lei-tao-lun-jian-ji-xie-fa-pythonjav-671l/
    public int maximumLength2(String S) {
        char[] s = S.toCharArray();
        List<Integer>[] groups = new ArrayList[26];
        Arrays.setAll(groups, i -> new ArrayList<>());
        int cnt = 0;
        for (int i = 0; i < s.length; i++) {
            cnt++;
            if (i == s.length - 1 || s[i] != s[i + 1]) {
                groups[s[i] - 'a'].add(cnt); // 統計連續字符長度
                cnt = 0;
            }
        }

        int ans = 0;
        for (List<Integer> a : groups) {
            if (a.isEmpty()) continue;
            a.sort(Collections.reverseOrder());
            a.add(0);
            a.add(0); // 假設還有兩個空串
            ans = Math.max(ans, Math.max(a.get(0) - 2, Math.max(Math.min(a.get(0) - 1, a.get(1)), a.get(2))));
        }
        return ans > 0 ? ans : -1;
    }


    // https://leetcode.cn/problems/palindrome-rearrangement-queries/solutions/2585862/fen-lei-tao-lun-by-endlesscheng-jxg0/
    public boolean[] canMakePalindromeQueries(String S, int[][] queries) {
        char[] s = S.toCharArray();
        int m = s.length;
        int n = m / 2;

        // 預處理三種前綴和
        int[][] sumS = new int[n + 1][26];
        for (int i = 0; i < n; i++) {
            sumS[i + 1] = sumS[i].clone();
            sumS[i + 1][s[i] - 'a']++;
        }

        int[][] sumT = new int[n + 1][26];
        for (int i = 0; i < n; i++) {
            sumT[i + 1] = sumT[i].clone();
            sumT[i + 1][s[m - 1 - i] - 'a']++;
        }

        int[] sumNe = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sumNe[i + 1] = sumNe[i] + (s[i] != s[m - 1 - i] ? 1 : 0);
        }

        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int l1 = q[0], r1 = q[1], l2 = m - 1 - q[3], r2 = m - 1 - q[2];
            ans[i] = l1 <= l2 ? check(l1, r1, l2, r2, sumS, sumT, sumNe) :
                    check(l2, r2, l1, r1, sumT, sumS, sumNe);
        }
        return ans;
    }

    private boolean check(int l1, int r1, int l2, int r2, int[][] sumS, int[][] sumT, int[] sumNe) {
        if (sumNe[l1] > 0 || // [0,l1-1] 有 s[i] != t[i]
                sumNe[sumNe.length - 1] - sumNe[Math.max(r1, r2) + 1] > 0) { // [max(r1,r2)+1,n-1] 有 s[i] != t[i]
            return false;
        }
        if (r2 <= r1) { // 區間包含
            return Arrays.equals(count(sumS, l1, r1), count(sumT, l1, r1));
        }
        if (r1 < l2) { // 區間不相交
            return sumNe[l2] - sumNe[r1 + 1] <= 0 && // [r1+1,l2-1] 都滿足 s[i] == t[i]
                    Arrays.equals(count(sumS, l1, r1), count(sumT, l1, r1)) &&
                    Arrays.equals(count(sumS, l2, r2), count(sumT, l2, r2));
        }
        // 區間相交但不包含
        int[] s1 = subtract(count(sumS, l1, r1), count(sumT, l1, l2 - 1));
        int[] s2 = subtract(count(sumT, l2, r2), count(sumS, r1 + 1, r2));
        return s1 != null && s2 != null && Arrays.equals(s1, s2);
    }

    // 計算子串中各個字符的出現次數，閉區間 [l,r]
    private int[] count(int[][] sum, int l, int r) {
        int[] res = sum[r + 1].clone();
        for (int i = 0; i < 26; i++) {
            res[i] -= sum[l][i];
        }
        return res;
    }

    private int[] subtract(int[] s1, int[] s2) {
        for (int i = 0; i < 26; i++) {
            s1[i] -= s2[i];
            if (s1[i] < 0) {
                return null;
            }
        }
        return s1;
    }
}
