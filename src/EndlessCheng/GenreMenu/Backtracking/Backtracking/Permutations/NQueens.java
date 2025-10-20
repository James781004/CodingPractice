package EndlessCheng.GenreMenu.Backtracking.Backtracking.Permutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueens {

    // https://leetcode.cn/problems/n-queens/solutions/2079586/hui-su-tao-lu-miao-sha-nhuang-hou-shi-pi-mljv/
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        int[] queens = new int[n]; // 皇後放在 (r,queens[r])
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[n * 2 - 1];
        boolean[] diag2 = new boolean[n * 2 - 1];
        dfs(0, queens, col, diag1, diag2, ans);
        return ans;
    }

    private void dfs(int r, int[] queens, boolean[] col, boolean[] diag1, boolean[] diag2, List<List<String>> ans) {
        int n = col.length;
        if (r == n) {
            List<String> board = new ArrayList<>(n); // 預分配空間
            for (int c : queens) {
                char[] row = new char[n];
                Arrays.fill(row, '.');
                row[c] = 'Q';
                board.add(new String(row));
            }
            ans.add(board);
            return;
        }
        // 在 (r,c) 放皇後
        for (int c = 0; c < n; c++) { // c代表列的下標（寬度）
            int rc = r - c + n - 1;
            // 3個限制（1）列是否被選過（2）'/'右對角線是否有選過（3）'\'左對角線是否有選過
            if (!col[c] && !diag1[r + c] && !diag2[rc]) { // 判斷能否放皇後
                queens[r] = c; // 直接覆蓋，無需恢復現場
                col[c] = diag1[r + c] = diag2[rc] = true; // 皇後佔用了 c 列和兩條斜線
                dfs(r + 1, queens, col, diag1, diag2, ans);
                col[c] = diag1[r + c] = diag2[rc] = false; // 恢復現場
            }
        }
    }


}
