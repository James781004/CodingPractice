package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

public class MostPoints {

    // http://leetcode.cn/problems/solving-questions-with-brainpower/solutions/1213919/dao-xu-dp-by-endlesscheng-2qkc/
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        long[] f = new long[n + 1]; // f[i] 表示在能解決問題 i 時，解決區間 [0,i−1] 內的問題可以獲得的最高分數
        for (int i = 0; i < n; i++) {
            // 如果不解決（不選），那麼問題 i+1 是能解決的，用 f[i] 更新 f[i+1] 的最大值
            f[i + 1] = Math.max(f[i + 1], f[i]);

            // 如果解決（選），設 j=min(i+brainpoweri +1,n)，
            // 問題 j 是能解決的，用 f[i]+pointi 更新 f[j] 的最大值
            int[] q = questions[i];
            int j = Math.min(i + q[1] + 1, n);
            f[j] = Math.max(f[j], f[i] + q[0]);
        }
        return f[n];
    }


}
