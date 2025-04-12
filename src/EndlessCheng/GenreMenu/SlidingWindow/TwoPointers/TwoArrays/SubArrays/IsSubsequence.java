package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

public class IsSubsequence {

    // https://leetcode.cn/problems/is-subsequence/solutions/2813031/jian-ji-xie-fa-pythonjavaccgojsrust-by-e-mz22/
    public boolean isSubsequence(String s, String t) {
        if (s.isEmpty()) {
            return true;
        }
        int i = 0;
        for (char c : t.toCharArray()) {
            if (s.charAt(i) == c && ++i == s.length()) { // 所有字符匹配完畢
                return true; // s 是 t 的子序列
            }
        }
        return false;
    }


}
