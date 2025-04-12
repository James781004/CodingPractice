package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class TakeCharacters {

    // https://leetcode.cn/problems/take-k-of-each-character-from-left-and-right/solutions/2031995/on-shuang-zhi-zhen-by-endlesscheng-4g9p/
    public int takeCharacters(String S, int k) {
        char[] s = S.toCharArray();
        int[] cnt = new int[3];
        for (char c : s) {
            cnt[c - 'a']++; // 一開始，把所有字母都取走
        }
        if (cnt[0] < k || cnt[1] < k || cnt[2] < k) {
            return -1; // 字母個數不足 k
        }

        int mx = 0; // 子串最大長度
        int left = 0;
        for (int right = 0; right < s.length; right++) {
            int c = s[right] - 'a';
            cnt[c]--; // 移入窗口，相當於不取走 c
            while (cnt[c] < k) { // 窗口之外的 c 不足 k，說明子串太長了，或者取走的字母個數太少了
                cnt[s[left] - 'a']++; // 移出窗口，相當於取走 s[left]，直到該字母個數等於 k
                left++;
            }
            mx = Math.max(mx, right - left + 1); // 更新子串長度的最大值
        }
        return s.length - mx;
    }

}
