package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class SuperEggDrop {

    // https://leetcode.cn/problems/super-egg-drop/solutions/2949779/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-xkbx/
    public int superEggDrop(int k, int n) {
        int[][] memo = new int[n + 1][];
        for (int i = 1; ; i++) {
            memo[i] = new int[k + 1]; // 動態創建 memo
            if (dfs(i, k, memo) >= n) {
                return i;
            }
        }
    }

    // 在有 i 次操作機會和 j 枚雞蛋的情況下，可以讓我們能確定 f 值的最大建築層數
    private int dfs(int i, int j, int[][] memo) {
        if (i == 0 || j == 0) {
            return 0;
        }
        if (memo[i][j] != 0) { // 之前計算過
            return memo[i][j];
        }
        return memo[i][j] = dfs(i - 1, j, memo) + dfs(i - 1, j - 1, memo) + 1;
    }


    public int superEggDropDP(int k, int n) {
        int[][] f = new int[n + 1][k + 1];
        for (int i = 1; ; i++) {
            for (int j = 1; j <= k; j++) {
                f[i][j] = f[i - 1][j] + f[i - 1][j - 1] + 1;
            }
            if (f[i][k] >= n) {
                return i;
            }
        }
    }


    public int superEggDropDP2(int k, int n) {
        int[] f = new int[k + 1];
        for (int i = 1; ; i++) {
            for (int j = k; j > 0; j--) {
                f[j] += f[j - 1] + 1;
            }
            if (f[k] >= n) {
                return i;
            }
        }
    }


}
