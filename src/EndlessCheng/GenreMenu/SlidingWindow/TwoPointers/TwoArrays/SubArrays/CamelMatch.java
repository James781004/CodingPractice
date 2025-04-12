package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

import java.util.ArrayList;
import java.util.List;

public class CamelMatch {

    // https://leetcode.cn/problems/camelcase-matching/solutions/2225856/python3javacgotypescript-yi-ti-yi-jie-sh-vr5x/
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> ans = new ArrayList<>();
        for (var q : queries) {
            ans.add(check(q, pattern));
        }
        return ans;
    }

    private boolean check(String s, String t) {
        int m = s.length(), n = t.length();
        int i = 0, j = 0; // 指向兩個字符串的首字符
        for (; j < n; ++i, ++j) {

            // 如果指針 i 和 j 指向的字符不同，並且 s[i] 為小寫字母，則指針 i 循環向後移動一位
            while (i < m && s.charAt(i) != t.charAt(j) && Character.isLowerCase(s.charAt(i))) {
                ++i;
            }

            // 如果指針 i 已經到達字符串 s 的末尾，或者指針 i 和 j 指向的字符不同，則返回 false
            if (i == m || s.charAt(i) != t.charAt(j)) {
                return false;
            }
        }

        // 當指針 j 到達字符串 t 的末尾時，我們需要判斷字符串 s 中剩余的字符是否都為小寫字母，
        // 若是則返回 true，否則返回 false
        while (i < m && Character.isLowerCase(s.charAt(i))) {
            ++i;
        }
        return i == m;
    }


}
