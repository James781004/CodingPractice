package GuChengAlgorithm.Ch05_DP;

import java.util.Arrays;

public class Q04_Knapsack {
    // 0-1 背包：物品每種只有一個
    //
    // 給你一個可裝載重量為W的背包和N個物品，每個物品有重量和價值兩個屬性。其中第i個物品的重量為wt[i]，價值為val[i]，現在讓你用這個背包裝物品，最多能裝的價值是多少？
    // N = 3, W = 4
    // wt = [2, 1, 3]
    // val = [4, 2, 3]   should return value 6 (pick index0, 1)
    // 一個典型的動態規劃問題。這個題目中的物品不可以分割，要麼裝進包里，要麼不裝，不能說切成兩塊裝一半
    //
    // 狀態：  1. 目前數到第i個物品，目前背包里的剩余空間w
    // 選擇： 第i個物品裝入背包增加錢，減少空間；第i個物品不裝入背包，維持不變
    //
    // dp[i][w]的定義如下：對於前i個物品，當前背包的容量為w，這種情況下可以裝的最大價值是dp[i][w]

    // https://docs.google.com/presentation/d/11JEhihv0rBCSZuyHxf_p-OwJZQ7jGxFunYdDPOx53D4/edit#slide=id.g91ca74dc59_0_14
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) return false;
        sum /= 2;
        int n = nums.length;

        // 2D 01 Knapsack
        boolean[][] dp = new boolean[n + 1][sum + 1];  // dp[i][j]: nums[0...i-1]之間物品能否組成當前總和j
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;  // 目標和為0，這邊不取任何數字即可
        }

        for (int i = 0; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];  // 當前物品不放入背包，直接複製前一部狀態
                if (j - nums[i - 1] > 0) dp[i][j] |= dp[i - 1][j - nums[i - 1]];  // 當前物品放入背包，背包空間減少，狀態疊加
            }
        }

        return dp[n][sum];
    }


    public boolean canPartition2(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) return false;
        sum /= 2;
        int n = nums.length;

        // 1D 01 Knapsack
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int num : nums) {  // 01背包，同種物品只能用一次
            for (int i = sum; i > 0; i--) {  //  一維01背包，背包容量倒序處理，以免重複加入同種物品
                if (i > num) dp[i] |= dp[i] || dp[i - num];
            }
        }
        return dp[sum];
    }


    // https://docs.google.com/presentation/d/11JEhihv0rBCSZuyHxf_p-OwJZQ7jGxFunYdDPOx53D4/edit#slide=id.g91ca74dc59_0_37
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];  // dp[i][j]: 剩餘空間為i, j的時候可以裝多少個物品
        for (String s : strs) {
            int one = 0, zero = 0;
            for (char c : s.toCharArray()) {  // 統計物品數：one個1和zero個0
                if (c == '1') one++;
                else zero++;
            }
            for (int i = m; i >= zero; i--) {  // 只要空間夠就可以拿物品，犧牲空間換取count + 1
                for (int j = n; j >= one; j--) {  // 這邊i, j實際上意義都是背包容量，倒序處理以免重複加入同種物品
                    dp[i][j] = Math.max(dp[i][j], dp[i - zero][j - one] + 1);
                }
            }
        }
        return dp[m][n];
    }


    // https://docs.google.com/presentation/d/11JEhihv0rBCSZuyHxf_p-OwJZQ7jGxFunYdDPOx53D4/edit#slide=id.g91ca74dc59_0_90
    public int findTargetSumWays(int[] nums, int S) {
//        int sum = Arrays.stream(nums).sum();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if (S > sum || (S + sum) % 2 == 1) return 0;
        return subsetSum(nums, (S + sum) / 2);
    }

    // a - b = S
    // a + b = sum
    // a = (S + sum) / 2
    // find sub sequence sum equals to (S + sum) / 2
    // because a, b are two part construct original array
    private int subsetSum(int[] nums, int S) {
        int[] dp = new int[S + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {  // 物品
            for (int j = S; j >= 0; j--) {  // 容量(一維倒序)
                if (j - nums[i] >= 0) dp[j] += dp[j - nums[i]];  // 算組合總數就把前面狀態加起來
            }
        }
        return dp[S];
    }

    private int subsetSum2(int[] nums, int S) {
        int[][] dp = new int[nums.length + 1][S + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= S; j++) {  // 特殊情況S可能等於0
                dp[i][j] = dp[i - 1][j];
                if (j - nums[i - 1] >= 0) dp[i][j] += dp[i - 1][j - nums[i]];
            }
        }
        return dp[nums.length][S];
    }


    // https://docs.google.com/presentation/d/11JEhihv0rBCSZuyHxf_p-OwJZQ7jGxFunYdDPOx53D4/edit#slide=id.g923f310211_5_2
    public int lastStoneWeight(int[] stones) {
        int sum = Arrays.stream(stones).sum();
        int target = sum / 2;
        int[][] dp = new int[stones.length + 1][target + 1];  // [石頭種類, 剩餘空間]
        for (int i = 1; i <= stones.length; i++) {
            for (int j = 1; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - stones[i - 1] >= 0) {
                    dp[i][j] = Math.max(dp[i - 1][j], stones[i - 1] + dp[i - 1][j - stones[i - 1]]);
                }
            }
        }
        return sum - 2 * dp[stones.length][target];
    }


    // 完全背包 --物品數量無限
    //
    // 有一個背包，最大容量為amount，有一系列物品coins，每個物品的重量為coins[i]，每個物品的數量無限。請問有多少種方法，能夠把背包恰好裝滿？
    // dp[i][j]的定義如下：
    // 若只使用前i個物品，當背包容量為j時，有dp[i][j]種方法可以裝滿背包。
    // 換句話說，翻譯回我們題目的意思就是：
    // 若只使用coins中的前i個硬幣的面值，若想湊出金額j，有dp[i][j]種湊法。
    // 我們最後目標要求的是 dp[N][amount]

    // https://docs.google.com/presentation/d/11JEhihv0rBCSZuyHxf_p-OwJZQ7jGxFunYdDPOx53D4/edit#slide=id.g91ca74dc59_0_0
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];  // 前i個物品,剩餘錢數w
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 0;
        }

        // bottom-up
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];  // 選擇不拿當前物品，二維DP初始狀態為前面累計的dp[i - 1][j]狀態
                if (j >= coins[i - 1] && dp[i][j - coins[i - 1]] != Integer.MAX_VALUE) {
                    // 完全背包同種物品可以使用無限次，所以選擇拿下當前物品時，取i的狀態而不是參考i-1的狀態
                    // 最後面的+1，如果物品有value，就改成加上value
                    dp[i][j] = Math.min(dp[i][j], coins[i - 1] + dp[i][j - coins[i - 1]] + 1);
                }
            }
        }
        return dp[coins.length][amount] == Integer.MAX_VALUE ? -1 : dp[coins.length][amount];
    }


    public int coinChange2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 0; i <= amount; i++) {  // 注意容量loop變成順向，因為同種物品可以重複取
            for (int j = 0; j < coins.length; j++) {  // 完全背包內外層loop互換也沒問題，
                // 選擇不拿當前物品，一維DP初始狀態為前面累計的dp[i]狀態
                // 完全背包同種物品可以使用無限次，所以選擇拿下當前物品時，取i的狀態而不是參考i-1的狀態
                // 最後面的+1，如果物品有value，就改成加上value
                if (coins[i] <= i) dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }


    public int coinChangeMemo(int[] coins, int amount) {
        int[] memo = new int[amount + 1];
        return coinHelper(coins, amount, memo);
    }

    private int coinHelper(int[] coins, int amount, int[] memo) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (memo[amount] != 0) return memo[amount];
        int min = Integer.MIN_VALUE;
        for (int coin : coins) {
            int res = coinHelper(coins, amount - coin, memo);
            if (res >= 0) min = Math.min(min, res + 1);
        }
        return memo[amount] = (min == Integer.MIN_VALUE) ? -1 : min;
    }


    // https://docs.google.com/presentation/d/11JEhihv0rBCSZuyHxf_p-OwJZQ7jGxFunYdDPOx53D4/edit#slide=id.g91ca74dc59_0_48
    // 完全背包，每一種硬幣數量無限，問有多少種組合方法
    // 直接上模板，i是前i個物品，j是剩余背包的容量。value是能夠填滿當前容量為j背包的組合方法數
    public int coinChangeII(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - coins[i - 1] >= 0) dp[i][j] += dp[i][j - coins[i - 1]];
            }
        }
        return dp[coins.length][amount];
    }


    // https://docs.google.com/presentation/d/11JEhihv0rBCSZuyHxf_p-OwJZQ7jGxFunYdDPOx53D4/edit#slide=id.g91ca74dc59_0_63
    // 多重背包： 每種物品本身數量有限
    // 直接模板，需要考慮每一種物品本身的數量有限
    public int canPartition(int n, int[] prices, int[] weight, int[] amounts) {
        int[][] dp = new int[prices.length + 1][n + 1];
        for (int i = 1; i <= prices.length; i++) {  // 前i類物品
            for (int j = 1; j <= n; j++) {  // 背包空間j
                dp[i][j] = dp[i - 1][j];
                for (int k = 0; k <= amounts[i - 1]; k++) {  // 前i類物品取k件
                    if (j - k * prices[i - 1] >= 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * prices[i - 1]] + k * weight[i - 1]);
                    }
                }
            }
        }
        return dp[prices.length][n];
    }


    // https://docs.google.com/presentation/d/1p4jKDFa1niyzBRHEexI_yO9k7LRR0AiZvSEDDHXyPDQ/edit#slide=id.g97f61e8b45_0_10
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for (int i = 2; i < nums.length + 1; i++) {
            dp[i] = Math.max(dp[i - 1], nums[i - 1] + dp[i - 2]);
        }
        return dp[nums.length];
    }


    public int robMemo(int[] nums) {
        Integer[] memo = new Integer[nums.length + 1];
        return robHelper(nums, nums.length - 1, memo);
    }

    private int robHelper(int[] nums, int i, Integer[] memo) {
        if (i < 0) return 0;
        if (memo[i] != null) return memo[i];
        int res = Math.max(robHelper(nums, i - 1, memo), robHelper(nums, i - 2, memo) + nums[i]);
        return nums[i] = res;
    }


    // https://docs.google.com/presentation/d/1p4jKDFa1niyzBRHEexI_yO9k7LRR0AiZvSEDDHXyPDQ/edit#slide=id.g97f61e8b45_0_40
    public int robII(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] n1 = Arrays.copyOfRange(nums, 0, nums.length - 1);
        int[] n2 = Arrays.copyOfRange(nums, 1, nums.length);
        return Math.max(rob(n1), rob(n2));
    }


    // https://docs.google.com/presentation/d/1p4jKDFa1niyzBRHEexI_yO9k7LRR0AiZvSEDDHXyPDQ/edit#slide=id.g97f61e8b45_0_61
    public int robIII(TreeNode root) {
        int[] res = robSub(root);  // {rob, not rob}
        return Math.max(res[0], res[1]);
    }

    private int[] robSub(TreeNode root) {
        if (root == null) return new int[2];
        int[] left = robSub(root.left);
        int[] right = robSub(root.right);
        int rob = root.val + left[0] + right[0];  // 當前拿，下家不拿
        int not_rob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);  // 當前不拿下家可拿可不拿，取較大值
        return new int[]{rob, not_rob};
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

}
