package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class CountSpecialSubsequences {

    // https://leetcode.cn/problems/count-number-of-special-subsequences/solutions/908427/dong-tai-gui-hua-by-endlesscheng-4onu/
    static int mod = 1000000007;

    public int countSpecialSubsequences(int[] nums) {
        // f[i][0] 表示前 i 項得到的全 0 子序列個數
        // f[i][1] 表示前 i 項得到的先 0 後 1 的子序列個數
        // f[i][2] 表示前 i 項得到的特殊子序列個數
        // 這邊使用滾動數組，因為 i 位置可以直接繼承 i-1 的累積狀態
        int f0 = 0, f1 = 0, f2 = 0;

        // 遍歷數組 nums，對於 f[i][j]，若 j != nums[i]，則直接從前一項轉移過來，即 f[i][j]=f[i−1][j]。
        for (int num : nums) {
            // 若 j=nums[i] 則需要分類計算：
            if (num == 0) {
                // 對於 f[i][0]，當遇到 0 時，有選或不選兩種方案，
                // 不選 0 時有 f[i][0]=f[i−1][0]，
                // 選 0 時，可以單獨組成一個子序列，也可以與前面的 0 組合，
                // 因此有 f[i][0]=f[i−1][0]+1，兩者相加得 f[i][0]=2⋅f[i−1][0]+1。
                f0 = (f0 * 2 + 1) % mod;
            } else if (num == 1) {
                // 對於 f[i][1]，當遇到 1 時，有選或不選兩種方案，
                // 不選 1 時有 f[i][1]=f[i−1][1]，
                // 選 1 時，可以單獨與前面的 0 組成一個子序列，也可以與前面的 1 組合，
                // 因此有 f[i][1]=f[i−1][1]+f[i−1][0]，兩者相加得 f[i][1]=2⋅f[i−1][1]+f[i−1][0]。
                f1 = (f1 * 2 % mod + f0) % mod;
            } else {
                // f[i][2] 和 f[i][1] 類似，有 f[i][2]=2⋅f[i−1][2]+f[i−1][1]。
                f2 = (f2 * 2 % mod + f1) % mod;
            }
        }

        // 最後答案為 f[n−1][2]。
        return f2;
    }

}
