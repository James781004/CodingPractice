package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

public class MinimumLengthDelete {

    // https://leetcode.cn/problems/minimum-length-of-string-after-deleting-similar-ends/solutions/2034827/by-tizzi-dp7f/
    public int minimumLength(String S) {
        int l = 0, r = S.length() - 1;
        char[] s = S.toCharArray();
        while (l < r && s[l] == s[r]) {
            char c = s[l];
            while (l <= r && s[l] == c) l++;
            while (l <= r && s[r] == c) r--;
        }
        return r - l + 1;
    }


}
