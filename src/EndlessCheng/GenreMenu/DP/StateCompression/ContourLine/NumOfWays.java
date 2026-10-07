package EndlessCheng.GenreMenu.DP.StateCompression.ContourLine;

import java.util.HashMap;
import java.util.Map;

public class NumOfWays {

    // https://leetcode.cn/problems/number-of-ways-to-paint-n-3-grid/solutions/3869310/san-chong-fang-fa-ji-yi-hua-sou-suo-di-t-tell/
    private static final int MOD = 1_000_000_007;

    // 全局 memo，記憶化的內容可以在不同測試數據間共享
    private static Map<Integer, Integer> memo = new HashMap<>();

    public int numOfWays(int n) {
        return dfs(n, 0, 0, 0);
    }

    // (i, j)：當前位置
    // preRow：上一行（i+1 行）的顏色
    // curRow：當前這一行已填入的顏色
    private int dfs(int i, int j, int preRow, int curRow) {
        if (i == 0) { // 所有格子都已涂色
            return 1; // 找到一個合法方案
        }

        if (j == 3) { // i 行已涂色
            // 開始對 i-1 行涂色，curRow 變成 preRow
            return dfs(i - 1, 0, curRow, 0);
        }

        // 參數壓縮到一個 int 中
        int key = (i << 14) | (j << 12) | (preRow << 6) | curRow;
        if (memo.containsKey(key)) { // 之前計算過
            return memo.get(key);
        }

        int res = 0;
        for (int color = 0; color < 3; color++) { // 枚舉 (i, j) 的顏色 color
            if (preRow > 0 && color == (preRow >> (j * 2) & 3) || // 不能和下面相鄰格子 (i+1, j) 顏色相同
                    j > 0 && color == (curRow >> ((j - 1) * 2) & 3)) { // 不能和左側相鄰格子 (i, j-1) 顏色相同
                continue;
            }
            res = (res + dfs(i, j + 1, preRow, curRow | (color << (j * 2)))) % MOD;
        }

        memo.put(key, res); // 記憶化
        return res;
    }


    public int numOfWaysDP(int n) {
        final int MOD = 1_000_000_007;
        int[] f = new int[n + 1];
        f[0] = 3;
        f[1] = 12;
        for (int i = 2; i <= n; i++) {
            f[i] = (int) ((f[i - 1] * 5L - f[i - 2] * 2L) % MOD); // 注意這裡有減法，結果可能是負數
        }
        return (f[n] + MOD) % MOD; // 保證結果非負
    }

}
