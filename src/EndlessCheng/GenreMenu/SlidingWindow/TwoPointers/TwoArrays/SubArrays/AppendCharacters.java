package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

public class AppendCharacters {

    // https://leetcode.cn/problems/append-characters-to-string-to-make-subsequence/solutions/1993448/tan-xin-pi-pei-by-endlesscheng-d6eq/
    public int appendCharacters(String s, String t) {
        char[] cp = t.toCharArray();
        char[] cs = s.toCharArray();
        int k = 0, n = cp.length;
        for (char c : cs) {
            if (k < n && cp[k] == c) k++;
        }

        return n - k;
    }


}
