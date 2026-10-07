package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class MaxSizeSlices {

    // https://leetcode.cn/problems/pizza-with-3n-slices/solutions/2393650/python3javacgotypescript-yi-ti-yi-jie-do-mhhj/
    private int n;

    // 求解的問題可以轉化為：在一個長度為 3n 的環形數組中，選擇其中 n 個不相鄰的數，使得這 n 個數的和最大。
    public int maxSizeSlices(int[] slices) {
        n = slices.length / 3;
        int[] nums = new int[slices.length - 1];

        // 如果選擇了最後一個數，那麼第一個數就不能選擇
        System.arraycopy(slices, 1, nums, 0, nums.length);
        int a = g(nums);

        // 選擇了第一個數，那麼最後一個數就不能選擇
        System.arraycopy(slices, 0, nums, 0, nums.length);
        int b = g(nums);
        return Math.max(a, b);
    }

    // 數組 nums 中選擇 n 個不相鄰的數，使得這 n 個數的和最大
    private int g(int[] nums) {
        int m = nums.length;
        int[][] f = new int[m + 1][n + 1]; // f[i][j] 表示在數組 nums 的前 i 個數中選擇 j 個不相鄰的數的最大和
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                // 考慮 f[i][j]，如果不選擇第 i 個數，那麼 f[i][j]=f[i−1][j]，
                // 如果選擇第 i 個數，那麼 f[i][j]=f[i−2][j−1]+nums[i−1]
                f[i][j] = Math.max(f[i - 1][j], (i >= 2 ? f[i - 2][j - 1] : 0) + nums[i - 1]);
            }
        }
        return f[m][n];
    }


}
