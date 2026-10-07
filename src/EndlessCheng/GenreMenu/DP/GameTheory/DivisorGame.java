package EndlessCheng.GenreMenu.DP.GameTheory;

public class DivisorGame {

    // https://leetcode.cn/problems/divisor-game/solutions/369921/ji-yi-hua-di-gui-dong-tai-gui-hua-shu-xue-fang-fa-/
    public boolean divisorGame(int N) {
        if (N == 1) {
            return false;
        }

        // 為了不處理下標偏移，多設置 1 格
        // dp[i]：黑板上的數字為 i 時，當前做出選擇的人是否會贏
        boolean[] dp = new boolean[N + 1];
        // 最基本情況（如圖所示）
        dp[1] = false;
        dp[2] = true;

        // 以線性的方式逐步遞推得到結果
        for (int i = 3; i <= N; i++) {
            for (int j = 1; j <= i / 2; j++) {
                // 只要做出的選擇的其中之一，能讓對方輸，在當前這一步我們就可以贏
                if ((i % j == 0) && !dp[i - j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        // 根據題意：輸出是 dp[N]
        return dp[N];
    }

}
