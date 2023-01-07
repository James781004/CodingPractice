package GuChengAlgorithm.Ch05_DP;

public class Q01_Gambling {
    // dfs+memo 或者dp iterative寫法都可以解答，dfs方式比較適合這類型
    // prefixSum或者minimax都可以作為得到當前位置res的方式
    // 時間覆雜度在memo或者iterative下一般為O(n) 因為每次的選擇都是有限的，常見的有，從一頭拿，從兩頭拿，從一頭按各種規則拿.
    // 這類型題目比較好分辨，一般為2人遊戲，要注意不要陷入greedy的陷阱，每一次的操作都需要minimal對手下一步的max收益
    // Dfs+memo 模板again


    // https://docs.google.com/presentation/d/18czsiBAEk08ChqQPkg7uJ8ER8jmATmipASlzc8Ofp6A/edit#slide=id.g8c688b9ba8_0_0
    // If dp[i] want to win the game, it must guarantee at least one of dp[i -1] dp[i -2] dp[i - 3] fail, 
    // 也就是說在我的選擇的環節，我有3個選擇，只要我有一個選擇能讓對手輸，我就一定贏
    public boolean canWinNim(int n) {
        boolean[] dp = new boolean[Math.max(n + 1, 4)];
        dp[1] = dp[2] = dp[3] = true;
        for (int i = 4; i <= n; i++) {
            dp[i] = !dp[i - 1] || !dp[i - 2] || !dp[i - 3];
        }
        return dp[n];
    }

    public boolean canWinNim2(int n) {
        return n % 4 != 0;  // 數學公式解：4的倍數情況下，無論拿123對手都能接著拿完
    }

    public boolean canWinNimMemo(int n) {
        Boolean[] memo = new Boolean[n + 1];
        return nimHelper(n, memo);
    }

    private boolean nimHelper(int n, Boolean[] memo) {
        if (n < 0) return false;
        if (memo[n] != null) return memo[n];
        boolean res = false;
        for (int i = 1; i < 4; i++) {
            if (n >= 1) res |= !nimHelper(n - i, memo);
        }
        return memo[n] = res;
    }


    // https://docs.google.com/presentation/d/18czsiBAEk08ChqQPkg7uJ8ER8jmATmipASlzc8Ofp6A/edit#slide=id.g8c688b9ba8_0_14
    public boolean predictTheWinner(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];  // 定義：dp[左邊界下標][右邊界下標]，輪到我的時候決策結果
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }

        // 決策：選頭或選尾，選完後剩下的部份給對手決策
        // 因為這邊只問勝負，所以判兩個玩家分數差：拿的分數 - F(剩下部份)
        for (int len = 1; len < n; len++) {
            for (int i = 0; i < n - len; i++) {
                int j = i + len;
                dp[i][j] = Math.max(piles[i] - dp[i + 1][j],
                        piles[j] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] >= 0;
    }

    public boolean predictTheWinnerMemo(int[] piles) {
        Integer[][] memo = new Integer[piles.length][piles.length];
        return winHelper(piles, 0, piles.length - 1, memo) >= 0;
    }

    // You have two choice, choose front or choose back.
    // dfs(i + 1) or dfs(j - 1) is for rest of the cards your opponent max score.
    // You can use your score - your opponent’s score >= 0 to see if you can win
    // 決策：選頭或選尾，選完後剩下的部份給對手決策
    // 因為這邊只問勝負，所以判兩個玩家分數差：拿的分數 - F(剩下部份)
    private int winHelper(int[] piles, int i, int j, Integer[][] memo) {
        if (i > j) return 0;
        if (memo[i][j] != null) return memo[i][j];
        memo[i][j] = Math.max(piles[i] - winHelper(piles, i + 1, j, memo),
                piles[j] - winHelper(piles, i, j - 1, memo));
        return memo[i][j];
    }


    // https://docs.google.com/presentation/d/18czsiBAEk08ChqQPkg7uJ8ER8jmATmipASlzc8Ofp6A/edit#slide=id.g8cb2d0e32e_0_0
    // 兩人輪流減去一個數字，這個減去的數字必須能被原來的N整除，直到拿完。拿最後一個數字的玩家獲勝（相當於你的對手無路可走）
    public boolean divisorGame(int N) {
        Boolean[] memo = new Boolean[N + 1];
        return divisorHelper(N, memo);
    }

    private boolean divisorHelper(int n, Boolean[] memo) {
        if (n == 1) return false;
        if (memo[n] != null) return memo[n];
        boolean canWin = false;
        for (int i = 1; i <= n / 2; i++) {  // 因為我們取的數字i必須能被n整除，所以這邊取到n一半即可，例如：4 % 2 == 0
            if (n % i == 0 && !divisorHelper(n - 1, memo)) {  // 取對手的下一個狀態
                canWin = true;  // 對手的下一個狀態false，表示我贏了，剩下就不用繼續算
                break;
            }
        }
        return memo[n] = canWin;
    }

    public boolean divisorGame2(int N) {
        return N % 2 == 0;  // 公式：最先得到偶數2勝利，奇數後手必敗
    }


    // https://docs.google.com/presentation/d/18czsiBAEk08ChqQPkg7uJ8ER8jmATmipASlzc8Ofp6A/edit#slide=id.g8c7bd04cee_0_20
    // Difference:
    // Total number of stones are odd
    // 兩人輪流頭尾取一個石頭，分數最高者獲勝
    public boolean stoneGame(int[] piles) {
        Integer[][] memo = new Integer[piles.length][piles.length];
        int max = stoneHelper(piles, 0, piles.length - 1, memo);
        return memo[0][piles.length - 1] > 0;
    }

    private int stoneHelper(int[] piles, int i, int j, Integer[][] memo) {
        if (i > j) return 0;
        if (memo[i][j] != null) return memo[i][j];
        memo[i][j] = Math.max(piles[i] - stoneHelper(piles, i + 1, j, memo),
                piles[j] - stoneHelper(piles, i, j - 1, memo));
        return memo[i][j];
    }

    public boolean stoneGame2(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = piles[i];
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = Math.max(piles[i] = dp[i + 1][j], piles[j] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] >= 0;
    }


    // https://docs.google.com/presentation/d/18czsiBAEk08ChqQPkg7uJ8ER8jmATmipASlzc8Ofp6A/edit#slide=id.g8cb2d0e32e_0_13
    // 兩人輪流取石頭，取的數量X有限制在[1,2*M] M是上一次的max(M, X)
    // 石頭分數多的人獲勝
    // 每次取的數量都可以是前一次的兩半，所以最優策略是限制下次對手取最小值，自己這次取的值就是最大了
    // 如果剩下石頭不足2M，表示下次對手可以全拿，計算就可以停止
    public int stoneGameII(int[] piles) {
        int N = piles.length;
        int[] sum = new int[N];
        for (int i = N - 1; i >= 0; i--) {  // 後綴和
            if (i == N - 1) sum[i] = piles[i];
            else sum[i] = sum[i + 1] + piles[i];
        }
        int[][] memo = new int[N][2 * N];
        return stoneGameIIHelper(piles, 0, 1, memo, sum); // 0->index, 1->M，題目規定M從1開始
    }

    private int stoneGameIIHelper(int[] p, int index, int M, int[][] memo, int[] sum) {
        if (index == p.length) return 0;    // 邊界限制
        if (p.length - index <= 2 * M) return sum[index];   // 後面都是對手的，剩餘數量不足2M，對手通吃
        if (memo[index][M] != 0) return memo[index][M]; // memo限制條件
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= 2 * M; i++) {  // 選擇限制對手得分最小的情況
            min = Math.min(min, stoneGameIIHelper(p, index + 1, Math.max(M, i), memo, sum));
        }
        return memo[index][M] = sum[index] - min;   // 減去對手得分就是自己這次在index位置的得分
    }


    // https://docs.google.com/presentation/d/18czsiBAEk08ChqQPkg7uJ8ER8jmATmipASlzc8Ofp6A/edit#slide=id.g8cb2d0e32e_0_26
    // 兩人輪流拿石頭，只能拿最左邊的1個，2個或者3個。分數最高者獲勝
    // Alice's score - Bob's score結果存入memo，然後看最後分數正負值
    public String stoneGameIII(int[] stoneValue) {
        Integer[] memo = new Integer[stoneValue.length];
        int score = minimax(stoneValue, 0, memo);  // Alice's score - Bob's score
        if (score == 0) return "Tie";
        else if (score > 0) return "Alice";
        else return "Bob";
    }

    private int minimax(int[] stoneValue, int cur, Integer[] memo) {
        if (cur == stoneValue.length) return 0;
        if (memo[cur] != null) return memo[cur];
        int res = Integer.MIN_VALUE;
        int score = 0;
        for (int i = cur; i < cur + 3 && i < stoneValue.length; i++) {  // 只能拿最左邊的1個，2個或者3個
            score += stoneValue[i];  // 然後看最後分數正負值
            res = Math.max(res, score - minimax(stoneValue, i + 1, memo));
        }
        return memo[cur] = res;
    }


    // https://docs.google.com/presentation/d/18czsiBAEk08ChqQPkg7uJ8ER8jmATmipASlzc8Ofp6A/edit#slide=id.g8c7bd04cee_0_1
    // 兩人輪流取石頭，只能取走平方數的石頭，拿最後一個石頭的玩家獲勝
    // You win == make sure other player remaining numbers (n - i * i) can not win
    public boolean winnerSquareGame(int n) {
        Boolean[] memo = new Boolean[n + 1];
        return squareHelper(n, memo);
    }

    private boolean squareHelper(int n, Boolean[] memo) {
        if (memo[n] != null) return memo[n];
        for (int i = (int) Math.sqrt(n); i > 0; i--) {  // 只能取走平方數的石頭
            if (!squareHelper(n - (i * i), memo)) return memo[n] = true;  // 只要有win case就直接return
        }
        return memo[n] = false; // no win case found in the loop
    }

    public boolean winnerSquareGameDP(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {  // 下一個玩家的dp[i - k * k]  只要是lose case就直接存進dp[i] = true
            for (int k = 1; k * k <= i; k++) {
                if (!dp[i - k * k]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

}
