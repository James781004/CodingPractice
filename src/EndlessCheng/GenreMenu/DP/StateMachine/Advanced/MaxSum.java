package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

import java.util.HashMap;
import java.util.Map;

public class MaxSum {

    // https://leetcode.cn/problems/get-the-maximum-score/solutions/2849260/zhuang-tai-ji-dpde-si-lu-ji-yi-hua-sou-s-okzi/
    public int maxSum(int[] nums0, int[] nums1) {
        final int mod = (int) 1e9 + 7;
        int n = nums0.length, m = nums1.length;
        int l = Math.max(n, m);

        // 初始化數組和映射
        long[][] nums = new long[2][l];
        Map<Integer, Integer> mp0 = new HashMap<>();
        Map<Integer, Integer> mp1 = new HashMap<>();

        // 填充nums[0]和mp0
        for (int i = 0; i < n; i++) {
            nums[0][i] = nums0[i];
            mp0.put(nums0[i], i);
        }
        // 填充nums[1]和mp1
        for (int i = 0; i < m; i++) {
            nums[1][i] = nums1[i];
            mp1.put(nums1[i], i);
        }

        // 初始化備忘錄（-1表示未計算）
        long[][] memo = new long[l][2];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < 2; j++) {
                memo[i][j] = -1;
            }
        }

        // 遞歸計算最大和
        long res = Math.max(dfs(nums, mp0, mp1, memo, l - 1, 0),
                dfs(nums, mp0, mp1, memo, l - 1, 1));
        return (int) (res % mod);
    }

    // numsj 選到第i個數的最大得分
    private long dfs(long[][] nums, Map<Integer, Integer> mp0,
                     Map<Integer, Integer> mp1, long[][] memo,
                     int i, int j) {
        if (i < 0) return 0;
        if (memo[i][j] != -1) return memo[i][j];

        long current = nums[j][i];
        long res;

        if (j == 0) { // 當前在nums0
            long res1 = dfs(nums, mp0, mp1, memo, i - 1, 0); // 不切換
            long res2 = 0;
            if (mp1.containsKey((int) current)) { // 檢查nums1中是否存在相同值
                int idx = mp1.get((int) current);
                res2 = dfs(nums, mp0, mp1, memo, idx - 1, 1); // 切換到nums1
            }
            res = Math.max(res1, res2) + current;
        } else { // 當前在nums1
            long res1 = dfs(nums, mp0, mp1, memo, i - 1, 1); // 不切換
            long res2 = 0;
            if (mp0.containsKey((int) current)) { // 檢查nums0中是否存在相同值
                int idx = mp0.get((int) current);
                res2 = dfs(nums, mp0, mp1, memo, idx - 1, 0); // 切換到nums0
            }
            res = Math.max(res1, res2) + current;
        }

        memo[i][j] = res;
        return res;
    }

}
