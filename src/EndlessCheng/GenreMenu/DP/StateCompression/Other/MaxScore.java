package EndlessCheng.GenreMenu.DP.StateCompression.Other;

import java.util.*;

public class MaxScore {

    // https://leetcode.cn/problems/select-cells-in-grid-with-maximum-score/solutions/2899994/zhi-yu-zhuang-ya-dppythonjavacgo-by-endl-x27y/
    public int maxScore(List<List<Integer>> grid) {
        Map<Integer, Integer> pos = new HashMap<>();
        int m = grid.size();
        for (int i = 0; i < m; i++) {
            for (int x : grid.get(i)) {
                // 保存所有包含 x 的行號（壓縮成二進制數）
                pos.merge(x, 1 << i, (a, b) -> a | b);
            }
        }

        List<Integer> allNums = new ArrayList<>(pos.keySet());
        Collections.sort(allNums); // 下面從大到小遞歸
        int n = allNums.size();
        int[][] memo = new int[n][1 << m];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, 0, pos, allNums, memo);
    }

    private int dfs(int i, int j, Map<Integer, Integer> pos, List<Integer> allNums, int[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        // 枚舉選第 k 行的 x
        // 如果循環結束後 res > 0，就不再遞歸不選的情況
        int res = 0;
        int x = allNums.get(i);
        for (int t = pos.get(x), lb; t > 0; t ^= lb) {
            lb = t & -t; // lb = 1<<k，其中 k 是行號
            if ((j & lb) == 0) { // 沒選過第 k 行的數
                res = Math.max(res, dfs(i - 1, j | lb, pos, allNums, memo) + x);
            }
        }
        if (res == 0) {
            // 不選 x
            res = dfs(i - 1, j, pos, allNums, memo);
        }
        return memo[i][j] = res; // 記憶化
    }


    public int maxScoreDP(List<List<Integer>> grid) {
        Map<Integer, Integer> pos = new HashMap<>();
        int m = grid.size();
        for (int i = 0; i < m; i++) {
            for (int x : grid.get(i)) {
                // 保存所有包含 x 的行號（壓縮成二進制數）
                pos.merge(x, 1 << i, (a, b) -> a | b);
            }
        }

        List<Integer> allNums = new ArrayList<>(pos.keySet());
        int u = 1 << m;
        int[][] f = new int[allNums.size() + 1][u];
        for (int i = 0; i < allNums.size(); i++) {
            int x = allNums.get(i);
            int posMask = pos.get(x);
            for (int j = 0; j < u; j++) {
                f[i + 1][j] = f[i][j]; // 不選 x
                for (int t = posMask, lb; t > 0; t ^= lb) {
                    lb = t & -t; // lb = 1<<k，其中 k 是行號
                    if ((j & lb) == 0) { // 沒選過第 k 行的數
                        f[i + 1][j] = Math.max(f[i + 1][j], f[i][j | lb] + x); // 選第 k 行的 x
                    }
                }
            }
        }
        return f[allNums.size()][0];
    }


    public int maxScoreDP2(List<List<Integer>> grid) {
        Map<Integer, Integer> pos = new HashMap<>();
        int m = grid.size();
        for (int i = 0; i < m; i++) {
            for (int x : grid.get(i)) {
                // 保存所有包含 x 的行號（壓縮成二進制數）
                pos.merge(x, 1 << i, (a, b) -> a | b);
            }
        }

        int u = 1 << m;
        int[] f = new int[u];
        for (Map.Entry<Integer, Integer> e : pos.entrySet()) {
            int x = e.getKey();
            int posMask = e.getValue();
            for (int j = 0; j < u; j++) {
                for (int t = posMask, lb; t > 0; t ^= lb) {
                    lb = t & -t; // lb = 1<<k，其中 k 是行號
                    if ((j & lb) == 0) { // 沒選過第 k 行的數
                        f[j] = Math.max(f[j], f[j | lb] + x);
                    }
                }
            }
        }
        return f[0];
    }


}
