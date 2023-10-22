package Grind.Ch15_DP;

import java.util.HashMap;

public class Q12_CombinationSumIV {
    // https://leetcode.cn/problems/combination-sum-iv/solutions/740675/dai-ma-sui-xiang-lu-377-zu-he-zong-he-iv-pj9s/
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        // 如果求組合數就是外層for循環遍歷物品，內層for遍歷背包。
        // 如果求排列數就是外層for遍歷背包，內層for循環遍歷物品。
        // 如果把遍歷nums（物品）放在外循環，遍歷target的作為內循環的話，
        // 舉一個例子：計算dp[4]的時候，結果集只有 {1,3} 這樣的集合，不會有{3,1}這樣的集合，
        // 因為nums遍歷放在外層，3只能出現在1後面！
        for (int i = 0; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i >= nums[j]) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }


    // dfs方式
    // https://leetcode.cn/problems/combination-sum-iv/solutions/740678/shu-ju-jie-gou-he-suan-fa-dong-tai-gui-h-jain/
    public int combinationSum4Memo(int[] nums, int target) {
        return helper(nums, target, new HashMap<>());
    }

    private int helper(int[] nums, int target, HashMap<Integer, Integer> map) {
        if (target == 0) return 1;
        if (target < 0) return 0;
        if (map.containsKey(target)) return map.get(target);
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res += helper(nums, target - nums[i], map);
        }
        map.put(target, res);
        return res;
    }
}
