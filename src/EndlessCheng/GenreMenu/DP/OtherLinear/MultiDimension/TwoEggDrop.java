package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class TwoEggDrop {

    // https://leetcode.cn/problems/egg-drop-with-2-eggs-and-n-floors/solutions/2945577/liang-chong-fang-fa-dong-tai-gui-hua-shu-hd4i/
    private static final int[] memo = new int[1001];

    public int twoEggDrop(int n) {
        if (n == 0) {
            return 0;
        }
        if (memo[n] > 0) { // 之前計算過
            return memo[n];
        }
        int res = Integer.MAX_VALUE;

        // 枚舉第一枚雞蛋在 j=1,2,3,⋯,n 樓扔下
        for (int j = 1; j <= n; j++) {
            // 如果雞蛋碎了，那麼接下來只能依次在 1,2,3,j−1 樓扔第二枚雞蛋，
            // 最壞情況下，總共要操作 1+(j−1)=j 次
            // 如果雞蛋沒碎，那麼接下來可以在 j+1 到 n 樓中繼續扔第一枚雞蛋，
            // 這等價於在一棟有 n−j 層樓的建築中扔雞蛋的子問題，即 dfs(n−j)，將其加一即為總操作次數。
            res = Math.min(res, Math.max(j, twoEggDrop(n - j) + 1));
        }
        return memo[n] = res; // 記憶化
    }


    // DP
    private static final int[] f = new int[1001];

    static {
        for (int i = 1; i < f.length; i++) {
            f[i] = Integer.MAX_VALUE;
            for (int j = 1; j <= i; j++) {
                f[i] = Math.min(f[i], Math.max(j, f[i - j] + 1));
            }
        }
    }

    public int twoEggDropDP(int n) {
        return f[n];
    }


}
