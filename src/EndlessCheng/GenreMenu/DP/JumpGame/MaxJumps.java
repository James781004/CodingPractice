package EndlessCheng.GenreMenu.DP.JumpGame;

public class MaxJumps {

    // https://leetcode.cn/problems/jump-game-v/solutions/3963787/liang-chong-fang-fa-zheng-zhao-tiao-dao-pl5ep/
    public int maxJumps(int[] arr, int d) {
        int n = arr.length;
        int[] memo = new int[n];

        // 枚舉起點
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dfs(i, arr, d, memo));
        }
        return ans;
    }

    private int dfs(int i, int[] arr, int d, int[] memo) {
        if (memo[i] > 0) { // 之前計算過
            return memo[i];
        }

        int res = 1;

        // 往左跳
        for (int j = i - 1; j >= Math.max(i - d, 0) && arr[j] < arr[i]; j--) {
            res = Math.max(res, dfs(j, arr, d, memo) + 1);
        }

        // 往右跳
        for (int j = i + 1; j <= Math.min(i + d, arr.length - 1) && arr[j] < arr[i]; j++) {
            res = Math.max(res, dfs(j, arr, d, memo) + 1);
        }

        memo[i] = res; // 記憶化
        return res;
    }

}
