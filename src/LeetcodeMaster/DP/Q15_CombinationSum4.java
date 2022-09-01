package LeetcodeMaster.DP;

public class Q15_CombinationSum4 {
//    377. 組合總和 Ⅳ
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0377.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8C%E2%85%A3.md
//
//    難度：中等
//
//    給定一個由正整數組成且不存在重覆數字的數組，找出和為給定目標正整數的組合的個數。
//
//    示例:
//
//    nums = [1, 2, 3] target = 4
//
//    所有可能的組合為： (1, 1, 1, 1) (1, 1, 2) (1, 2, 1) (1, 3) (2, 1, 1) (2, 2) (3, 1)
//
//    請注意，順序不同的序列被視作不同的組合。
//
//    因此輸出為 7。


    // 如果求組合數就是外層for循環遍歷物品，內層for遍歷背包。
    // 如果求排列數就是外層for遍歷背包，內層for循環遍歷物品。
    // 如果把遍歷nums（物品）放在外循環，遍歷target的作為內循環的話，
    // 舉一個例子：計算dp[4]的時候，結果集只有 {1,3} 這樣的集合，
    // 不會有{3,1}這樣的集合，因為nums遍歷放在外層，3只能出現在1後面！
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i <= target; i++) { // 遍歷背包
            for (int j = 0; j < nums.length; j++) { // 遍歷物品
                if (i >= nums[j]) dp[i] += dp[i - nums[j]];
            }
        }
        return dp[target];
    }
}
