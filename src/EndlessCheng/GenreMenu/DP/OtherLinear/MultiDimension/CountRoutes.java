package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class CountRoutes {

    // https://leetcode.cn/problems/count-all-possible-routes/solutions/665208/dong-tai-gui-hua-lu-jing-wen-ti-ru-he-hu-hg5d/
    int mod = 1000000007;
    int[][] cache;

    public int countRoutes(int[] ls, int start, int end, int fuel) {
        int n = ls.length;
        cache = new int[n][fuel + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(cache[i], -1);
        }
        return dfs(ls, start, end, fuel);
    }

    /**
     * 計算「路徑數量」
     *
     * @param ls   入參 locations
     * @param u    當前所在位置（ls 的下標）
     * @param end  目標哦位置（ls 的下標）
     * @param fuel 剩余油量
     * @return 在位置 u 出發，油量為 fuel 的前提下，到達 end 的「路徑數量」
     */
    int dfs(int[] ls, int u, int end, int fuel) {
        if (cache[u][fuel] != -1) {
            return cache[u][fuel];
        }

        int need = Math.abs(ls[u] - ls[end]);
        if (need > fuel) {
            cache[u][fuel] = 0;
            return 0;
        }

        int n = ls.length;
        int sum = u == end ? 1 : 0;
        for (int i = 0; i < n; i++) {
            if (i != u) {
                need = Math.abs(ls[i] - ls[u]);
                if (fuel >= need) {
                    sum += dfs(ls, i, end, fuel - need);
                    sum %= mod;
                }
            }
        }
        cache[u][fuel] = sum;
        return sum;
    }


    public int countRoutesDP(int[] ls, int start, int end, int fuel) {
        int n = ls.length;

        // f[i][j] 代表從位置 i 出發，當前油量為 j 時，到達目的地的路徑數
        int[][] f = new int[n][fuel + 1];

        // 對於本身位置就在目的地的狀態，路徑數為 1
        for (int i = 0; i <= fuel; i++) f[end][i] = 1;

        // 從狀態轉移方程可以發現 f[i][fuel]=f[i][fuel]+f[k][fuel-need]
        // 在計算 f[i][fuel] 的時候依賴於 f[k][fuel-need]
        // 其中 i 和 k 並無嚴格的大小關系
        // 而 fuel 和 fuel-need 具有嚴格大小關系：fuel >= fuel-need
        // 因此需要先從小到大枚舉油量
        for (int cur = 0; cur <= fuel; cur++) {
            for (int i = 0; i < n; i++) {
                for (int k = 0; k < n; k++) {
                    if (i != k) {
                        int need = Math.abs(ls[i] - ls[k]);
                        if (cur >= need) {
                            f[i][cur] += f[k][cur - need];
                            f[i][cur] %= mod;
                        }
                    }
                }
            }
        }
        return f[start][fuel];
    }


}
