package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

public class MaximumRows {

    // https://leetcode.cn/problems/maximum-rows-covered-by-columns/solutions/1798794/by-endlesscheng-dvxe/
    private int ans;
    private int[] masks;

    public int maximumRows(int[][] matrix, int numSelect) {
        int m = matrix.length, n = matrix[0].length;

        // 初始化
        ans = 0;
        // 將每行的 1 壓縮為一個位掩碼
        // mask[i] 表示第 i 行中 1 所在的列，用二進制表示
        // 例如，mat[i] = [0, 1, 1, 0]，則 mask[i] = 0110（二進制）= 6
        masks = new int[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 將 1 左移 j 位並與 mask[i] 進行或運算
                // 表示將 j 添加到集合中
                masks[i] |= matrix[i][j] << j;
            }
        }

        // DFS 遍歷所有可能
        dfs(0, matrix, numSelect, 0);

        return ans;
    }

    // 子集型回溯，選或不選第 col 列，subset 記錄已選的列
    private void dfs(int col, int[][] matrix, int numSelect, int subset) {
        if (col == matrix[0].length || numSelect == 0) {
            // 計算被覆蓋的行數
            int cnt = 0;
            // 判斷一行是否被覆蓋：該行所有 1 都在選中的列中
            // 如果 row & subset == row，說明 row 的所有 1 都在 subset 中
            // 例如，row = 0110（第 1、2 列有 1），subset = 0111（選擇了第 0、1、2 列），
            // 則 row & subset = 0110 == row，該行被覆蓋。
            for (int row : masks) {
                if ((row & subset) == row) {
                    cnt++;
                }
            }
            // 更新最大覆蓋行數
            ans = Math.max(ans, cnt);
            return;
        }
        // 不選
        dfs(col + 1, matrix, numSelect, subset);
        // 選
        dfs(col + 1, matrix, numSelect - 1, (subset | 1 << col));
    }

}
