package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

public class CanMakeSubsequence {

    // https://leetcode.cn/problems/make-string-a-subsequence-using-cyclic-increments/solutions/2396214/shuang-zhi-zhen-by-endlesscheng-h553/
    public boolean canMakeSubsequence(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int i = 0;
        int j = 0;
        while (i < s1.length && j < s2.length) {
            char a = s1[i];
            char b = s2[j];
            if (a == b || (a - 'a' + 1) % 26 == (b - 'a')) {
                j++;
            }
            i++;
        }
        return j == s2.length;
    }

}
