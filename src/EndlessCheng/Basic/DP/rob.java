package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class rob {

    // https://leetcode.cn/problems/house-robber/solutions/2102725/ru-he-xiang-chu-zhuang-tai-ding-yi-he-zh-1wt1/
    public int rob(int[] nums) {
        int n = nums.length;
        int[] f = new int[n + 2];
        for (int i = 0; i < n; i++) {
            f[i + 2] = Math.max(f[i + 1], f[i] + nums[i]);
        }
        return f[n + 1];
    }

    public int rob2(int[] nums) {
        int f0 = 0;
        int f1 = 0;
        for (int x : nums) {
            int newF = Math.max(f1, f0 + x);
            f0 = f1;
            f1 = newF;
        }
        return f1;
    }


    private int[] nums, memo;

    public int robMemo(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        memo = new int[n];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        return dfs(n - 1); // 從最後一個房子開始思考
    }

    // dfs(i) 表示從 nums[0] 到 nums[i] 最多能偷多少
    private int dfs(int i) {
        if (i < 0) { // 遞歸邊界（沒有房子）
            return 0;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }
        int res = Math.max(dfs(i - 1), dfs(i - 2) + nums[i]);
        memo[i] = res; // 記憶化：保存計算結果
        return res;
    }

}
