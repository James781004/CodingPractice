package EndlessCheng.Basic.DP;

public class climbStairs {

    // https://leetcode.cn/problems/climbing-stairs/solutions/2560716/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-7zm1/
    public int climbStairsMemo(int n) {
        int[] memo = new int[n + 1];
        return dfs(n, memo);
    }

    private int dfs(int i, int[] memo) {
        if (i <= 1) { // 遞歸邊界
            return 1;
        }
        if (memo[i] != 0) { // 之前計算過
            return memo[i];
        }
        return memo[i] = dfs(i - 1, memo) + dfs(i - 2, memo); // 記憶化
    }


    public int climbStairs(int n) {
        int[] f = new int[n + 1];
        f[0] = f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }

    public int climbStairs2(int n) {
        int f0 = 1;
        int f1 = 1;
        for (int i = 2; i <= n; i++) {
            int newF = f1 + f0;
            f0 = f1;
            f1 = newF;
        }
        return f1;
    }


}
