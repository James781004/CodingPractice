package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q17_StoneGame {
//    https://leetcode.cn/problems/predict-the-winner/
//    486. 預測贏家
//    給你一個整數數組 nums 。玩家 1 和玩家 2 基於這個數組設計了一個游戲。
//
//    玩家 1 和玩家 2 輪流進行自己的回合，玩家 1 先手。開始時，兩個玩家的初始分值都是 0 。
//    每一回合，玩家從數組的任意一端取一個數字（即，nums[0] 或 nums[nums.length - 1]），取到的數字將會從數組中移除（數組長度減 1 ）。
//    玩家選中的數字將會加到他的得分上。當數組中沒有剩余數字可取時，游戲結束。
//
//    如果玩家 1 能成為贏家，返回 true 。如果兩個玩家得分相等，同樣認為玩家 1 是游戲的贏家，也返回 true 。
//    你可以假設每個玩家的玩法都會使他的分數最大化。


    //    https://leetcode.cn/problems/predict-the-winner/solution/ji-yi-hua-di-gui-dong-tai-gui-hua-java-by-liweiwei/
    public boolean PredictTheWinner(int[] nums) {
        int len = nums.length;
        int[][] memo = new int[len][len];

        for (int i = 0; i < len; i++) {
            Arrays.fill(memo[i], Integer.MIN_VALUE);
        }
        return process(nums, 0, len - 1, memo) >= 0;
    }

    private int process(int[] nums, int i, int j, int[][] memo) {
        if (i > j) {
            return 0;
        }

        if (memo[i][j] != Integer.MIN_VALUE) {
            return memo[i][j];
        }

        // 當前自己做出選擇的時候，得分為正，留給對方做選擇的時候，相對於自己而言，得分為負。
        int chooseLeft = nums[i] - process(nums, i + 1, j, memo);
        int chooseRight = nums[j] - process(nums, i, j - 1, memo);
        memo[i][j] = Math.max(chooseLeft, chooseRight);
        return memo[i][j];
    }


    public boolean PredictTheWinnerDP(int[] nums) {
        int len = nums.length;

        // 狀態定義：dp[i][j] 表示作為先手，在區間 nums[i..j] 裡進行選擇可以獲得的 相對分數。
        int[][] dp = new int[len][len];

        // dp[i][j]：作為先手，在區間 nums[i..j] 裡進行選擇可以獲得的相對分數(當前玩家與另一個玩家的分數之差)
        for (int i = 0; i < len; i++) {
            dp[i][i] = nums[i];
        }

        // 從尾開始遍歷
        for (int i = len - 2; i > 0; i++) {
            for (int j = i + 1; j < len; j++) {
                // 當前自己做出選擇的時候，得分為正，留給對方做選擇的時候，相對於自己而言，得分為負。
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }

        return dp[0][len - 1] >= 0;
    }


    public boolean PredictTheWinnerDP2(int[] nums) {
        int len = nums.length;
        int[][] dp = new int[len][len];

        // dp[i][j]：作為先手，在區間 nums[i..j] 裡進行選擇可以獲得的相對分數(當前玩家與另一個玩家的分數之差)
        for (int i = 0; i < len; i++) {
            dp[i][i] = nums[i];
        }

        // 從頭開始遍歷
        for (int j = 1; j < len; j++) {
            for (int i = j - 1; i >= 0; i--) {
                // 當前自己做出選擇的時候，得分為正，留給對方做選擇的時候，相對於自己而言，得分為負。
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][len - 1] >= 0;
    }


//    https://leetcode.cn/problems/stone-game/
//    877. 石子游戲
//    Alice 和 Bob 用幾堆石子在做游戲。一共有偶數堆石子，排成一行；每堆都有 正 整數顆石子，數目為 piles[i] 。
//
//    游戲以誰手中的石子最多來決出勝負。石子的 總數 是 奇數 ，所以沒有平局。
//
//    Alice 和 Bob 輪流進行，Alice 先開始 。 每回合，玩家從行的 開始 或 結束 處取走整堆石頭。
//    這種情況一直持續到沒有更多的石子堆為止，此時手中 石子最多 的玩家 獲勝 。
//
//    假設 Alice 和 Bob 都發揮出最佳水平，當 Alice 贏得比賽時返回 true ，當 Bob 贏得比賽時返回 false 。


    // https://leetcode.cn/problems/stone-game/solution/ji-yi-hua-di-gui-dong-tai-gui-hua-shu-xue-jie-java/
    public boolean stoneGameMemo(int[] piles) {
        int len = piles.length;
        int[][] memo = new int[len][len];
        for (int i = 0; i < len; i++) {
            // 由於是相對分數，有可能是在負值裡面選較大者，因此初始化的時候不能為 0
            Arrays.fill(memo[i], Integer.MIN_VALUE);
            memo[i][i] = piles[i];
        }
        return stoneGameHelper(piles, 0, len - 1, memo) > 0;
    }

    /**
     * 計算子區間 [left, right] 裡先手能夠得到的分數
     *
     * @param piles
     * @param left
     * @param right
     * @return
     */
    private int stoneGameHelper(int[] piles, int left, int right, int[][] memo) {
        if (left == right) {
            return piles[left];
        }

        if (memo[left][right] != Integer.MIN_VALUE) {
            return memo[left][right];
        }

        // 當前自己做出選擇的時候，得分為正，留給對方做選擇的時候，相對於自己而言，得分為負。
        int chooseLeft = piles[left] - stoneGameHelper(piles, left + 1, right, memo);
        int chooseRight = piles[right] - stoneGameHelper(piles, left, right - 1, memo);
        int res = Math.max(chooseLeft, chooseRight);
        memo[left][right] = res;
        return res;
    }


    public boolean stoneGameDp1(int[] piles) {
        int len = piles.length;

        // dp[i][j] 定義：區間 piles[i..j] 內先手可以獲得的相對分數(當前玩家與另一個玩家的石子數量之差)
        int[][] dp = new int[len][len];

        for (int i = 0; i < len; i++) {
            dp[i][i] = piles[i];
        }

        // 從尾開始遍歷
        for (int i = len - 2; i >= 0; i--) {
            for (int j = i + 1; j < len; j++) {
                // 當前自己做出選擇的時候，得分為正，留給對方做選擇的時候，相對於自己而言，得分為負。
                dp[i][j] = Math.max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1]);
            }
        }
        return dp[0][len - 1] > 0;
    }


    public boolean stoneGameDp2(int[] piles) {
        int len = piles.length;

        // dp[i][j] 定義：區間 piles[i..j] 內先手可以獲得的相對分數(當前玩家與另一個玩家的石子數量之差)
        int[][] dp = new int[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = piles[i];
        }

        // 從頭開始遍歷
        for (int j = 1; j < len; j++) {
            for (int i = j - 1; i >= 0; i--) {
                // 當前自己做出選擇的時候，得分為正，留給對方做選擇的時候，相對於自己而言，得分為負。
                dp[i][j] = Math.max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1]);
            }
        }
        return dp[0][len - 1] > 0;
    }


    // https://labuladong.github.io/algo/3/28/93/
    // 直接進行計算
    class Pair {
        int first, second;

        Pair(int fir, int sec) {
            this.first = fir;
            this.second = sec;
        }
    }


    /* 返回游戲最後先手和後手的得分之差 */
    public int stoneGame(int[] piles) {
        int n = piles.length;

        // 初始化 dp 數組
        Pair[][] dp = new Pair[n][n];
        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++)
                dp[i][j] = new Pair(0, 0);

        // 填入 base case
        for (int i = 0; i < n; i++) {
            dp[i][i].first = piles[i];
            dp[i][i].second = 0;
        }

        // 倒著遍歷數組
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                // 先手選擇最左邊或最右邊的分數
                int left = piles[i] + dp[i + 1][j].second;
                int right = piles[j] + dp[i][j - 1].second;
                // 套用狀態轉移方程
                // 先手肯定會選擇更大的結果，後手的選擇隨之改變
                if (left > right) {
                    dp[i][j].first = left;
                    dp[i][j].second = dp[i + 1][j].first;
                } else {
                    dp[i][j].first = right;
                    dp[i][j].second = dp[i][j - 1].first;
                }
            }
        }
        Pair res = dp[0][n - 1];
        return res.first - res.second;
    }
}



