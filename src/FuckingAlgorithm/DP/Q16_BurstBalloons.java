package FuckingAlgorithm.DP;

public class Q16_BurstBalloons {
//    https://www.cnblogs.com/labuladong/p/13940264.html

//    https://leetcode-solution-leetcode-pp.gitbook.io/leetcode-solution/hard/312.burst-balloons
//    有 n 個氣球，編號為0 到 n-1，每個氣球上都標有一個數字，這些數字存在數組 nums 中。
//
//    現在要求你戳破所有的氣球。每當你戳破一個氣球 i 時，你可以獲得 nums[left] * nums[i] * nums[right] 個硬幣。
//    這里的 left 和 right 代表和 i 相鄰的兩個氣球的序號。注意當你戳破了氣球 i 後，氣球 left 和氣球 right 就變成了相鄰的氣球。
//
//    求所能獲得硬幣的最大數量。

    public int burstBalloonsMemo(int[] nums) {
        int size = nums.length + 2;
        int[] points = new int[size];
        points[0] = 1;
        points[size - 1] = 1;
        for (int i = 0; i < nums.length; i++) {
            points[i + 1] = nums[i];
        }

        int[][] memo = new int[size][size];

        int result = process(points, memo, 0, size - 1);

        // Arrays.stream(memo).map(Arrays::toString).forEach(System.out::println);

        return result;
    }

    private int process(int[] points, int[][] memo, int left, int right) {
        if (left + 1 == right) return 0;
        if (memo[left][right] > 0) return memo[left][right];

        int max = 0;
        int pointsBase = points[left] * points[right];
        for (int i = left + 1; i < right; i++) {
            max = Math.max(max, points[i] * pointsBase
                    + process(points, memo, left, i)
                    + process(points, memo, i, right));
        }

        memo[left][right] = max;
        return max;
    }

    public int burstBalloonsDP(int[] nums) {
        int n = nums.length;

        // 添加兩側的虛擬氣球
        int[] points = new int[n + 2];
        points[0] = points[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            points[i] = nums[i - 1];
        }

        // base case 已經都被初始化為 0
        // dp[i][j] = x 表示，戳破氣球 i 和氣球 j 之間（開區間，不包括 i 和 j）的所有氣球，可以獲得的最高分數為 x
        // 根據這個定義，題目要求的結果就是 dp[0][n+1] 的值，而 base case 就是 dp[i][j] = 0
        int[][] dp = new int[n + 2][n + 2];

        // 開始狀態轉移
        // i 應該從下往上
        for (int i = n; i >= 0; i--) {
            // j 應該從左往右
            for (int j = i + 1; j < n + 2; j++) {
                // 最後戳破的氣球是哪個？
                for (int k = i + 1; k < j; k++) {
                    // 擇優做選擇
                    dp[i][j] = Math.max(
                            dp[i][j],
                            dp[i][k] + dp[k][j] + points[i] * points[j] * points[k]
                    );
                }
            }
        }
        return dp[0][n + 1];
    }

}
