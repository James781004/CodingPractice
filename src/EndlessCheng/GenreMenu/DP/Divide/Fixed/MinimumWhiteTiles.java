package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Arrays;

public class MinimumWhiteTiles {

    // https://leetcode.cn/problems/minimum-white-tiles-after-covering-with-carpets/solutions/1352146/by-endlesscheng-pa3v/
    public int minimumWhiteTiles(String floor, int numCarpets, int carpetLen) {
        int m = floor.length();
        int[][] memo = new int[numCarpets + 1][m];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(numCarpets, m - 1, floor.toCharArray(), memo, carpetLen);
    }

    // 用 i 條地毯覆蓋下標在 [0,j] 中的磚，沒被覆蓋的白色磚塊的最少數目
    private int dfs(int i, int j, char[] floor, int[][] memo, int carpetLen) {
        if (j < carpetLen * i) { // 剩余磚塊可以全部覆蓋
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        // 不選
        int res = dfs(i, j - 1, floor, memo, carpetLen) + (floor[j] - '0');

        // 選
        if (i > 0) {
            res = Math.min(res, dfs(i - 1, j - carpetLen, floor, memo, carpetLen));
        }
        
        return memo[i][j] = res; // 記憶化
    }


    public int minimumWhiteTilesDP(String floor, int numCarpets, int carpetLen) {
        char[] s = floor.toCharArray();
        int m = s.length;
        int[][] f = new int[numCarpets + 1][m];
        // 單獨計算 i=0 的情況，本質是 s 的前綴和
        f[0][0] = s[0] - '0';
        for (int j = 1; j < m; j++) {
            f[0][j] = f[0][j - 1] + (s[j] - '0');
        }
        for (int i = 1; i <= numCarpets; i++) {
            for (int j = carpetLen * i; j < m; j++) {
                f[i][j] = Math.min(f[i][j - 1] + (s[j] - '0'), f[i - 1][j - carpetLen]);
            }
        }
        return f[numCarpets][m - 1];
    }


    public int minimumWhiteTilesDP2(String floor, int numCarpets, int carpetLen) {
        int m = floor.length();
        if (numCarpets * carpetLen >= m) {
            return 0;
        }

        char[] s = floor.toCharArray();
        int[] f = new int[m];
        f[0] = s[0] - '0';
        for (int j = 1; j < m; j++) {
            f[j] = f[j - 1] + (s[j] - '0');
        }
        for (int i = 1; i <= numCarpets; i++) {
            int[] nf = new int[m];
            for (int j = carpetLen * i; j < m; j++) {
                nf[j] = Math.min(nf[j - 1] + (s[j] - '0'), f[j - carpetLen]);
            }
            f = nf;
        }
        return f[m - 1];
    }


}
