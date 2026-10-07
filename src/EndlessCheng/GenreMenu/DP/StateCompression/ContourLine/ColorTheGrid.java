package EndlessCheng.GenreMenu.DP.StateCompression.ContourLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorTheGrid {

    // https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/solutions/869703/zhuang-ya-dp-yu-chu-li-he-fa-zhuang-tai-l927s/
    private static final int MOD = 1_000_000_007;

    public int colorTheGrid(int m, int n) {
        int[] pow3 = new int[m];
        pow3[0] = 1;
        for (int i = 1; i < m; i++) {
            pow3[i] = pow3[i - 1] * 3;
        }

        // 預處理所有合法的（沒有相鄰相同顏色的）三進制數，記在數組 valid 中
        List<Integer> valid = new ArrayList<>();
        next:
        for (int color = 0; color < pow3[m - 1] * 3; color++) {
            for (int i = 1; i < m; i++) {
                if (color / pow3[i] % 3 == color / pow3[i - 1] % 3) { // 相鄰顏色相同
                    continue next;
                }
            }
            valid.add(color);
        }

        // 對於每個 valid[i]，預處理它的下一列顏色，要求左右相鄰顏色不同。把 valid 的下標記在數組 nxt[i] 中
        int nv = valid.size();
        List<Integer>[] nxt = new ArrayList[nv];
        Arrays.setAll(nxt, i -> new ArrayList<>());
        for (int i = 0; i < nv; i++) {
            next2:
            for (int j = 0; j < nv; j++) {
                for (int p3 : pow3)
                    if (valid.get(i) / p3 % 3 == valid.get(j) / p3 % 3) { // 相鄰顏色相同
                        continue next2;
                    }
                nxt[i].add(j);
            }
        }

        int[][] memo = new int[n][nv];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        long ans = 0;
        for (int j = 0; j < nv; j++) {
            ans += dfs(n - 1, j, nxt, memo);
        }
        return (int) (ans % MOD);
    }

    // dfs(i,j) 表示對於 m×i 的網格，右邊第 i+1 列填的是三進制數 valid[j] 的情況下的涂色方案數
    private int dfs(int i, int j, List<Integer>[] nxt, int[][] memo) {
        if (i == 0) {
            return 1; // 找到了一個合法涂色方案
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        // 枚舉第 i 列填顏色 valid[k]（其中 k 是 nxt[j] 中的元素）
        long res = 0;
        for (int k : nxt[j]) {
            // 問題變成對於 m×(i−1) 的網格，右邊第 i 列填的是三進制數 valid[k] 的情況下的涂色方案數
            res += dfs(i - 1, k, nxt, memo);
        }
        return memo[i][j] = (int) (res % MOD); // 記憶化
    }


    public int colorTheGridDP(int m, int n) {
        int[] pow3 = new int[m];
        pow3[0] = 1;
        for (int i = 1; i < m; i++) {
            pow3[i] = pow3[i - 1] * 3;
        }

        // 預處理所有合法的（沒有相鄰相同顏色的）三進制數，記在數組 valid 中
        List<Integer> valid = new ArrayList<>();
        next:
        for (int color = 0; color < pow3[m - 1] * 3; color++) {
            for (int i = 1; i < m; i++) {
                if (color / pow3[i] % 3 == color / pow3[i - 1] % 3) { // 相鄰顏色相同
                    continue next;
                }
            }
            valid.add(color);
        }

        // 對於每個 valid[i]，預處理它的下一列顏色，要求左右相鄰顏色不同。把 valid 的下標記在數組 nxt[i] 中
        int nv = valid.size();
        List<Integer>[] nxt = new ArrayList[nv];
        Arrays.setAll(nxt, i -> new ArrayList<>());
        for (int i = 0; i < nv; i++) {
            next2:
            for (int j = 0; j < nv; j++) {
                for (int p3 : pow3)
                    if (valid.get(i) / p3 % 3 == valid.get(j) / p3 % 3) { // 相鄰顏色相同
                        continue next2;
                    }
                nxt[i].add(j);
            }
        }

        int[][] f = new int[n][nv];
        Arrays.fill(f[0], 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < nv; j++) {
                for (int k : nxt[j]) {
                    f[i][j] = (f[i][j] + f[i - 1][k]) % MOD;
                }
            }
        }

        long ans = 0;
        for (int j = 0; j < nv; j++) {
            ans += f[n - 1][j];
        }
        return (int) (ans % MOD);
    }


}
