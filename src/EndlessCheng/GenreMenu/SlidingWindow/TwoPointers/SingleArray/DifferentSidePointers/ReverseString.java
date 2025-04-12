package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

public class ReverseString {

    // https://leetcode.cn/problems/reverse-string/submissions/203592049/
    public void reverseString(char[] s) {
        if (s == null || s.length == 0) return;
        int left = 0, right = s.length - 1;
        while (left < right) {
            swap(s, left++, right--);
        }
    }

    private void swap(char[] s, int a, int b) {
        char tmp = s[a];
        s[a] = s[b];
        s[b] = tmp;
    }

}
