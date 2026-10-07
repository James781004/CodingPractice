package EndlessCheng.GenreMenu.DP.JumpGame;

public class CanCross {

    // https://leetcode.cn/problems/frog-jump/solutions/603278/shou-hua-tu-jie-ji-hao-de-di-gui-ti-man-8kk2z/
    public boolean canCross(int[] stones) {
        /*
        青蛙在過河的的過程中我們一共需要關注兩個變量:
        一個是青蛙此時所處的石頭索引idx,另一個是通過k步跳到該石頭上去
        1.狀態定義:dp[i][k]為經過跳k步到達索引為i的石頭的方案能否到達終點
        2.狀態轉移:要dp[i][k]就要知道當前dp[i][k]可以由哪些狀態轉移過來的
            很顯然k有3種轉移途徑:k-1,k,k+1; i只能從前面[0,i-1]某個石頭跳過來的
            因此遍歷j∈[1,i-1],記錄stones[j]->stones[i]距離為k
            那麼此時可以有3種轉移途徑:
            2.1 dp[i][k]可以由dp[j][k-1]轉移過來:stones[j]->stones[i] 跳了比之前多1步
            2.2 dp[i][k]可以由dp[j][k]轉移過來:stones[j]->stones[i] 跳了與之前一樣的步數
            2.3 dp[i][k]可以由dp[j][k+1]轉移過來:stones[j]->stones[i] 跳了比之前少1步
            任意一種為true就說明可以轉移過來
        3.初始化:dp[1][1]=true 因為默認true才能不影響後面的轉移
        4.遍歷順序:先i後j,i與j均為正序
        5.返回形式:遍歷dp[n-1][j],j∈[1,n-1]只要有一個為true就返回true(跳到stones[n-1]的步數可以從1->n-1)
        */
        int n = stones.length;
        if (stones[1] != 1) return false;   // stones[0]->stones[1]只能跳1步,因此stones[1]只能為1
        boolean[][] dp = new boolean[n + 1][n + 1];
        dp[1][1] = true;
        // 遍歷石頭索引 i∈[2,n-1]
        for (int i = 2; i < n; i++) {
            // 遍歷i之前的石頭 j∈[1,i-1]
            for (int j = 1; j < i; j++) {
                int k = stones[i] - stones[j];  // 從stones[j]->stones[i]要跳的距離為k
                if (k <= j + 1) { // k小於步數理論的最大值
                    // 現在跳k步,之前只能跳k-1,k,k+1步,索引為j
                    dp[i][k] = dp[j][k - 1] || dp[j][k] || dp[j][k + 1];
                }
            }
        }
        // 遍歷dp[n - 1][j]只要有一個為true就返回true
        for (int j = 1; j < n; j++) {
            if (dp[n - 1][j]) return true;
        }
        // 沒有一條路徑可以轉移
        return false;
    }
}
