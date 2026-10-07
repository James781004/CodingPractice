package EndlessCheng.GenreMenu.DP.StateMachine.Basic;

public class MaxAlternatingSum {

    // https://leetcode.cn/problems/maximum-alternating-subsequence-sum/solutions/846375/dong-tai-gui-hua-by-endlesscheng-d92a/
    int[] nums;
    long[][] cache;

    public long maxAlternatingSum(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        cache = new long[n][2];
        for (int i = 0; i < n; i++)
            Arrays.fill(cache[i], -1);
        return Math.max(dfs(n - 1, 0), dfs(n - 1, 1));
    }

    // 定義 dfs(i, p) 表示 nums[:i] 以 i 結尾，為子序列奇數/偶數(p = 0/1) 的最大交替和
    // 轉移
    //   如果i接在子序列上
    //      如果 i 為子序列奇數尾  dfs(i, 0) = dfs(i-1, 1) - nums[i]
    //      如果 i 為子序列偶數尾  dfs(i, 1) = dfs(i-1, 0) + nums[i]
    //   如果i不接在子序列上
    //      如果 i 為子序列奇數尾  dfs(i, 0) = dfs(i-1, 0)
    //      如果 i 為子序列偶數尾  dfs(i, 1) = dfs(i-1, 1)
    //  取最大值，即
    //      dfs(i, 0) = max(dfs(i-1, 0), dfs(i-1, 1) - nums[i])
    //      dfs(i, 1) = max(dfs(i-1, 1), dfs(i-1, 0) + nums[i])
    // 遞歸邊界：dfs(i < 0, p) = 0
    // 遞歸入口：dfs(n-1, p) for p in range(2)
    public long dfs(int i, int p) {
        if (i < 0) return 0;
        if (cache[i][p] >= 0) return cache[i][p];
        long res = 0;
        if (p == 0)
            res = Math.max(dfs(i - 1, 0), dfs(i - 1, 1) - nums[i]);
        else
            res = Math.max(dfs(i - 1, 1), dfs(i - 1, 0) + nums[i]);
        return cache[i][p] = res;
    }


    public long maxAlternatingSumDP(int[] nums) {
        int n = nums.length;
        long[][] f = new long[n + 1][2];
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = Math.max(f[i][0], f[i][1] - nums[i]);
            f[i + 1][1] = Math.max(f[i][1], f[i][0] + nums[i]);
        }
        return Math.max(f[n][0], f[n][1]);
    }

    public long maxAlternatingSumDp2(int[] nums) {
        int n = nums.length;
        long[] f = new long[2];
        for (int i = 0; i < n; i++) {
            long[] tmp = new long[]{f[0], f[1]};
            f[0] = Math.max(tmp[0], tmp[1] - nums[i]);
            f[1] = Math.max(tmp[1], tmp[0] + nums[i]);
        }
        return Math.max(f[0], f[1]);
    }
}
