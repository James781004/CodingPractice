package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Longest;

public class CountSubstrings {

    // https://leetcode.cn/problems/count-substrings-with-k-frequency-characters-i/solutions/2957691/on-hua-dong-chuang-kou-pythonjavacgo-by-1xgqm/
    public int numberOfSubstrings(String S, int k) {
        char[] s = S.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[26];
        for (char c : s) { // 從小到大枚舉子串右端點 right，如果子串符合要求，則右移左端點 left
            cnt[c - 'a']++;

            // 右端點固定在 right，左端點在 0,1,2,⋯,left−1 的所有子串都是合法的，這一共有 left 個，加入答案。
            while (cnt[c - 'a'] >= k) {
                cnt[s[left] - 'a']--;
                left++;
            }
            ans += left;
        }
        return ans;
    }


}
