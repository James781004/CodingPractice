package EndlessCheng.GenreMenu.DP.Basic.Rob;

import java.util.Arrays;

public class Rob {

    // https://leetcode.cn/problems/house-robber/solutions/2102725/ru-he-xiang-chu-zhuang-tai-ding-yi-he-zh-1wt1/
    public int rob(int[] nums) {
        int n = nums.length;
        int[] memo = new int[n];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        return dfs(n - 1, nums, memo); // 從最後一個房子開始思考
    }

    // dfs(i) 表示從 nums[0] 到 nums[i] 最多能偷多少
    private int dfs(int i, int[] nums, int[] memo) {
        if (i < 0) { // 遞歸邊界（沒有房子）
            return 0;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }
        int notChoose = dfs(i - 1, nums, memo); // 不選當前房子偷
        int choose = dfs(i - 2, nums, memo) + nums[i]; // 選當前房子偷
        return memo[i] = Math.max(notChoose, choose); // 返回答案並記憶化
    }

    public int robDP(int[] nums) {
        int n = nums.length;
        int[] f = new int[n + 2];
        for (int i = 0; i < n; i++) {
            f[i + 2] = Math.max(f[i + 1], f[i] + nums[i]);
        }
        return f[n + 1];
    }

    public int robDP2(int[] nums) {
        int f0 = 0;
        int f1 = 0;
        for (int x : nums) {
            int newF = Math.max(f1, f0 + x);
            f0 = f1;
            f1 = newF;
        }
        return f1;
    }


}
