package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

public class New21Game {

    // https://leetcode.cn/problems/new-21-game/solutions/3755107/hua-dong-chuang-kou-you-hua-dpjian-ji-xi-lybl/
    public double new21Game(int n, int k, int maxPts) {
        double[] f = new double[n + 1]; // f[i] 表示從 i 分開始游戲，最終到達閉區間 [k,n] 的概率
        double s = 0;
        for (int i = n; i >= 0; i--) {
            f[i] = i >= k ? 1 : s / maxPts;
            // 當前循環計算的是 f[i+1] + ... + f[i+maxPts]
            // 下個循環計算的是 f[i] + ... + f[i+maxPts-1]，多了 f[i]，少了 f[i+maxPts]
            s += f[i];
            if (i + maxPts <= n) {
                s -= f[i + maxPts];
            }
        }
        return f[0];
    }


}
