package EndlessCheng.GenreMenu.Backtracking.Backtracking.Permutations;

public class NQueensII {

    // https://leetcode.cn/problems/n-queens-ii/solutions/2381883/hui-su-miao-sha-nhuang-hou-yi-ge-shi-pin-l41l/
    private int ans;

    public int totalNQueens(int n) {
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[n * 2 - 1];
        boolean[] diag2 = new boolean[n * 2 - 1];
        dfs(0, col, diag1, diag2);
        return ans;
    }

    private void dfs(int r, boolean[] col, boolean[] diag1, boolean[] diag2) {
        int n = col.length;
        if (r == n) {
            ans++; // 找到一個合法方案
            return;
        }
        for (int c = 0; c < n; c++) { // c代表列的下標（寬度）
            int rc = r - c + n - 1;
            // 3個限制（1）列是否被選過（2）'/'右對角線是否有選過（3）'\'左對角線是否有選過
            if (!col[c] && !diag1[r + c] && !diag2[rc]) {
                col[c] = diag1[r + c] = diag2[rc] = true;
                dfs(r + 1, col, diag1, diag2);
                col[c] = diag1[r + c] = diag2[rc] = false; // 恢復現場
            }
        }
    }


}
