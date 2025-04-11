package EndlessCheng.Basic.BackTracking;

public class totalNQueens {

    // https://leetcode.cn/problems/n-queens-ii/solutions/2381883/hui-su-miao-sha-nhuang-hou-yi-ge-shi-pin-l41l/
    private int n, ans;
    private boolean[] onPath, diag1, diag2;

    public int totalNQueens(int n) {
        this.n = n;
        onPath = new boolean[n];
        diag1 = new boolean[n * 2 - 1];
        diag2 = new boolean[n * 2 - 1];
        dfs(0);
        return ans;
    }

    private void dfs(int r) {
        if (r == n) {
            ans++;
            return;
        }
        for (int c = 0; c < n; ++c) {
            int rc = r - c + n - 1;
            if (!onPath[c] && !diag1[r + c] && !diag2[rc]) {
                onPath[c] = diag1[r + c] = diag2[rc] = true;
                dfs(r + 1);
                onPath[c] = diag1[r + c] = diag2[rc] = false; // 恢復現場
            }
        }
    }

}
