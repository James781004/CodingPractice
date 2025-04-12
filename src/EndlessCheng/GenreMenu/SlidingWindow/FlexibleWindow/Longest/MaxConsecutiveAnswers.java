package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class MaxConsecutiveAnswers {

    // https://leetcode.cn/problems/maximize-the-confusion-of-an-exam/solutions/1028668/zhi-jie-zhao-ban-1004-de-dai-ma-by-endle-42x3/
    public int maxConsecutiveAnswers(String answerKey, int k) {
        char[] s = answerKey.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[2];

        // 遍歷 answerKey，枚舉子串右端點 right，同時維護最小左端點 left 以及子串中的字符個數 cnt
        // 由於 T 和 F 的 ASCII 值除以 2 後的奇偶性不同，也就是它們二進制的次低位不同，可以改為統計二進制次低位
        for (int right = 0; right < s.length; right++) {
            cnt[s[right] >> 1 & 1]++; // 把 answerKey[right] 的出現次數加一

            // 如果 T 和 F 的出現次數都超過 k，那麼必須不斷移動左端點 left，
            // 同時減少 answerKey[left] 的出現次數，直到 T 和 F 的出現次數至少有一個 ≤k
            while (cnt[0] > k && cnt[1] > k) {
                cnt[s[left++] >> 1 & 1]--;
            }

            // 循環結束後，說明子串右端點在 right 時，對應的最小左端點為 left，
            // 用子串長度 right−left+1 更新答案的最大值
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


}
