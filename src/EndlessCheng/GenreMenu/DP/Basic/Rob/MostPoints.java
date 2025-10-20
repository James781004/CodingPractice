package EndlessCheng.GenreMenu.DP.Basic.Rob;

public class MostPoints {

    // https://leetcode.cn/problems/solving-questions-with-brainpower/solutions/1213919/dao-xu-dp-by-endlesscheng-2qkc/
    public long mostPoints(int[][] questions) {
        long[] memo = new long[questions.length];
        return dfs(0, questions, memo);
    }

    private long dfs(int i, int[][] questions, long[] memo) {
        if (i >= memo.length) {
            return 0;
        }
        if (memo[i] > 0) { // 之前計算過
            return memo[i];
        }
        long notChoose = dfs(i + 1, questions, memo);
        long choose = dfs(i + questions[i][1] + 1, questions, memo) + questions[i][0];
        return memo[i] = Math.max(notChoose, choose); // 記憶化
    }


    public long mostPointsDP(int[][] questions) {
        int n = questions.length;
        long[] f = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            int j = Math.min(i + questions[i][1] + 1, n);
            f[i] = Math.max(f[i + 1], f[j] + questions[i][0]);
        }
        return f[0];
    }

}
