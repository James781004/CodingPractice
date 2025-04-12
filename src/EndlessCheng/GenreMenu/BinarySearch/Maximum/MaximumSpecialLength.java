package EndlessCheng.GenreMenu.BinarySearch.Maximum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaximumSpecialLength {

    // https://leetcode.cn/problems/find-longest-special-substring-that-occurs-thrice-ii/solutions/2585801/fen-lei-tao-lun-jian-ji-xie-fa-pythonjav-671l/
    public int maximumLength(String S) {
        char[] s = S.toCharArray();
        List<Integer>[] groups = new ArrayList[26];
        Arrays.setAll(groups, i -> new ArrayList<>());
        int cnt = 0;
        for (int i = 0; i < s.length; i++) {
            cnt++;
            if (i + 1 == s.length || s[i] != s[i + 1]) {
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

            // 1. 從最長的特殊子串（a[0]）中取三個長度均為 a[0]−2 的特殊子串。例如示例 1 的 aaaa 可以取三個 aa。
            // 2. 或者，從最長和次長的特殊子串（a[0],a[1]）中取三個長度一樣的特殊子串：
            // 如果 a[0]=a[1]，那麼可以取三個長度均為 a[0]−1 的特殊子串。
            // 如果 a[0]>a[1]，那麼可以取三個長度均為 a[1] 的特殊子串：從最長中取兩個，從次長中取一個。
            // 這兩種情況可以合並成 min(a[0]−1,a[1])，如果 a[0]−1<a[1]，這只能是第一種情況，因為 a[0]≥a[1]，取二者較小值 a[0]−1；
            // 如果 a[0]−1≥a[1]，即 a[0]>a[1]，這是第二種情況，我們也取的是二者較小值 a[1]。
            // 3. 又或者，從最長、次長、第三長的的特殊子串（a[0],a[1],a[2]）中各取一個長為 a[2] 的特殊子串。
            ans = Math.max(ans, Math.max(a.get(0) - 2, Math.max(Math.min(a.get(0) - 1, a.get(1)), a.get(2))));
        }

        return ans > 0 ? ans : -1;
    }


    // 二分 + 雙指針 + 剪枝
    int n;
    String s;

    public int maximumLength2(String _s) {
        s = _s;
        n = s.length();
        int l = 0, r = n;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (check(mid)) l = mid;
            else r = mid - 1;
        }
        return l == 0 ? -1 : l;
    }

    public boolean check(int mid) {
        int[] cnt = new int[26];
        for (int i = 0; i + mid <= n; i++) {
            char c = s.charAt(i);
            int j = i;
            while (j < n && s.charAt(j) == c) {
                if (j - i + 1 >= mid) {
                    cnt[c - 'a']++;
                }
                j++;
            }
            if (cnt[c - 'a'] >= 3) {
                return true;
            }
            i = j - 1;
        }
        return false;
    }


}
