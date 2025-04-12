package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Shortest;

public class BalancedString {

    // https://leetcode.cn/problems/replace-the-substring-for-balanced-string/solutions/2108358/tong-xiang-shuang-zhi-zhen-hua-dong-chua-z7tu/
    // 如果在待替換子串之外的任意字符的出現次數都不超過 m，那麼可以通過替換，使 s 為平衡字符串，即每個字符的出現次數均為 m
    public int balancedString(String S) {
        char[] s = S.toCharArray();
        int[] cnt = new int['X']; // 也可以用哈希表，不過數組更快一些
        for (char c : s) {
            cnt[c]++;
        }
        int n = s.length;
        int m = n / 4;
        if (cnt['Q'] == m && cnt['W'] == m && cnt['E'] == m && cnt['R'] == m) {
            return 0; // 已經符合要求
        }
        int ans = n;
        int left = 0;
        for (int right = 0; right < n; right++) { // 枚舉子串右端點
            cnt[s[right]]--;
            while (cnt['Q'] <= m && cnt['W'] <= m && cnt['E'] <= m && cnt['R'] <= m) {
                ans = Math.min(ans, right - left + 1); // 更新答案的最小值
                cnt[s[left++]]++; // 縮小子串
            }
        }
        return ans;
    }


}
