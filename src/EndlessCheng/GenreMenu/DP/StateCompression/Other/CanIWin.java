package EndlessCheng.GenreMenu.DP.StateCompression.Other;

public class CanIWin {

    // https://leetcode.cn/problems/can-i-win/solutions/685543/464-wo-neng-ying-ma-dai-bei-wang-lu-de-d-qu1t/
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (maxChoosableInteger >= desiredTotal) return true;
        // 所有數被選完都沒達到目標值，肯定贏不了
        if ((1 + maxChoosableInteger) * maxChoosableInteger / 2 < desiredTotal) return false;

        return dfs(0, desiredTotal, new Boolean[1 << maxChoosableInteger], maxChoosableInteger);
    }

    private boolean dfs(int state, int desiredTotal, Boolean[] dp, int maxChoosableInteger) {
        // 先判斷一下當前狀態state是否已經出現過了
        if (dp[state] != null) {
            return dp[state];
        }

        // 用一個二進制數options代表本輪可以選的數值，第i位為1,時，代表數字i+1可選
        // 先讓甲從可選數值中選一個數
        for (int i = 1; i <= maxChoosableInteger; i++) {
            // 用二進制位來標記某個數是否已被選擇
            // 比如01表示1已被選擇，10表示2已被選擇，11表示1和2都被選擇，100表示3被選擇
            // 1<<(n−1) 表示n已被選擇
            int cur = 1 << (i - 1);

            // 如果當前數i已經被選擇cur & state != 0，直接continue進入下一個數的選擇，
            // 因為一個數只能選一次
            if ((cur & state) != 0) {
                continue;
            }

            // 如果當前數i比剩余要達到的目標desiredTotal要大，說明選了這個數以後游戲就結束了，先手已贏，
            // 說明當前狀態state可以讓先手贏，dp[state] = True，並返回True
            // 如果當前先手選了i之後還不能馬上贏，但是下一步後手選數字的時候選輸了
            // （也就是dfs(cur | state, desiredTotal - i, dp)返回了False），說明先手能贏，
            // dp[state] = True，並返回True
            if (i >= desiredTotal || !dfs(cur | state, desiredTotal - i, dp, maxChoosableInteger)) {
                return dp[state] = true;
            }
        }

        // 如果遍歷完了所有的整數，當前state還沒有返回過True，說明這種狀態就不能讓先手贏
        return dp[state] = false;
    }


}
