package EndlessCheng.GenreMenu.Graph.NetworkFlow;

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

        // 只考慮在 grid 中的元素
        List<Integer> allNums = new ArrayList<>(pos.keySet());
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
        // 不選 x
        int res = dfs(i - 1, j, pos, allNums, memo);
        // 枚舉選第 k 行的 x
        int x = allNums.get(i);
        for (int t = pos.get(x), lb; t > 0; t ^= lb) {
            lb = t & -t; // lb = 1<<k，其中 k 是行號
            if ((j & lb) == 0) { // 沒選過第 k 行的數
                res = Math.max(res, dfs(i - 1, j | lb, pos, allNums, memo) + x);
            }
        }
        return memo[i][j] = res; // 記憶化
    }


}
