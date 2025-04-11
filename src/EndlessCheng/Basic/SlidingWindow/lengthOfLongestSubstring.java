package EndlessCheng.Basic.SlidingWindow;

public class lengthOfLongestSubstring {
    // https://leetcode.cn/problems/longest-substring-without-repeating-characters/solutions/1959540/xia-biao-zong-suan-cuo-qing-kan-zhe-by-e-iaks/
    public int lengthOfLongestSubstring(String S) {
        char[] s = S.toCharArray(); // 轉換成 char[] 加快效率（忽略帶來的空間消耗）
        int n = s.length, ans = 0, left = 0;
        boolean[] has = new boolean[128]; // 也可以用 HashSet<Character>，這裡為了效率用的數組
        for (int right = 0; right < n; right++) {
            char c = s[right];
            // 如果窗口內已經包含 c，那麼再加入一個 c 會導致窗口內有重復元素
            // 所以要在加入 c 之前，先移出窗口內的 c
            while (has[c]) { // 窗口內有 c
                has[s[left++]] = false; // 縮小窗口
            }
            has[c] = true; // 加入 c
            ans = Math.max(ans, right - left + 1); // 更新窗口長度最大值
        }
        return ans;
    }
}
