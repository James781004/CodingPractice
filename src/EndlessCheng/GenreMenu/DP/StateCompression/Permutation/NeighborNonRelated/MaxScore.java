package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

public class MaxScore {

    // https://leetcode.cn/problems/maximize-score-after-n-operations/solutions/2028835/by-lcbin-8uxm/
    public int maxScore(int[] nums) {
        int m = nums.length;
        int[][] g = new int[m][m]; //  g[i][j] 表示 nums[i] 和 nums[j] 的最大公約數
        for (int i = 0; i < m; ++i) {
            for (int j = i + 1; j < m; ++j) {
                g[i][j] = gcd(nums[i], nums[j]);
            }
        }
        int[] f = new int[1 << m]; //  f[k] 表示當前操作後的狀態為 k 時，可以獲得的最大分數和
        for (int k = 0; k < 1 << m; ++k) { // 從小到大枚舉所有狀態，
            int cnt = Integer.bitCount(k);
            if (cnt % 2 == 0) { // 對於每個狀態 k，先判斷此狀態的二進制位中 1 的個數 cnt 是否為偶數
                for (int i = 0; i < m; ++i) { // 枚舉 k 中二進制位為 1 的位置，假設為 i 和 j，則 i 和 j 兩個位置的元素可以進行一次操作
                    if (((k >> i) & 1) == 1) {
                        for (int j = i + 1; j < m; ++j) {
                            if (((k >> j) & 1) == 1) {
                                f[k] = Math.max(f[k], f[k ^ (1 << i) ^ (1 << j)] + cnt / 2 * g[i][j]);
                            }
                        }
                    }
                }
            }
        }
        return f[(1 << m) - 1];
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


}
