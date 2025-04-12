package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Shortest;

public class CountKConstraintSubstrings {

    // https://leetcode.cn/problems/count-substrings-that-satisfy-k-constraint-i/solutions/2884495/on-hua-dong-chuang-kou-pythonjavacgo-by-keubv/
    public int countKConstraintSubstrings(String S, int k) {
        char[] s = S.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[2];
        for (int i = 0; i < s.length; i++) {
            cnt[s[i] & 1]++;
            while (cnt[0] > k && cnt[1] > k) { // 等價於 !(cnt[0] <= k || cnt[1] <= k)
                // 字符 0 的 ASCII 值是偶數，字符 1 的 ASCII 值是奇數，
                // 所以可以用 ASCII 值 c mod 2 得到對應的數字。
                // 這也等價於和 1 計算 AND。
                cnt[s[left] & 1]--;
                left++;
            }
            ans += i - left + 1;
        }
        return ans;
    }


}
