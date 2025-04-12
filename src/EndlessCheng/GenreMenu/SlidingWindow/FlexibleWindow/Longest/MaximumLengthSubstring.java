package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class MaximumLengthSubstring {

    // https://leetcode.cn/problems/maximum-length-substring-with-two-occurrences/solutions/2704776/on-hua-dong-chuang-kou-pythonjavacgo-by-hl44d/
    // 相當於把 3. 無重復字符的最長子串 中的「出現次數不超過 1」改成「出現次數不超過 2」
    public int maximumLengthSubstring(String S) {
        char[] s = S.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[26];
        for (int i = 0; i < s.length; i++) {
            int b = s[i] - 'a';
            cnt[b]++;
            while (cnt[b] > 2) {
                cnt[s[left++] - 'a']--;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }


}
