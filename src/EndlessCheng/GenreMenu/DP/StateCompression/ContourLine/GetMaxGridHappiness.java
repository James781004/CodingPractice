package EndlessCheng.GenreMenu.DP.StateCompression.ContourLine;

public class GetMaxGridHappiness {

    // https://leetcode.cn/problems/maximize-grid-happiness/solutions/2318399/python3javacgotypescript-yi-ti-shuang-ji-fssp/
    private int m;
    private int n;
    private int p;
    private final int[][] h = {{0, 0, 0}, {0, -60, -10}, {0, -10, 40}};
    private Integer[][][][] memo;

    public int getMaxGridHappiness(int m, int n, int introvertsCount, int extrovertsCount) {
        this.m = m;
        this.n = n;
        p = (int) Math.pow(3, n - 1);
        memo = new Integer[m * n][p * 3][introvertsCount + 1][extrovertsCount + 1];
        return dfs(0, 0, introvertsCount, extrovertsCount);
    }

    // 當前搜索到位置 pos，且此前的 n 個網格單元的狀態為 pre，
    // 內向的人還剩 ic 個，外向的人還剩 ec 個時，網格的最大幸福感
    private int dfs(int pos, int pre, int ic, int ec) {
        // 如果 pos=m×n，表示已經處理完了所有的網格單元，那麼返回 0
        // 如果 ic=0 且 ec=0，表示所有的人都已經分配完了，那麼返回 0
        if (pos == m * n || (ic == 0 && ec == 0)) {
            return 0;
        }
        if (memo[pos][pre][ic][ec] != null) {
            return memo[pos][pre][ic][ec];
        }
        int ans = 0;
        int up = pre / p; // 出當前網格單元的上邊相鄰網格單元的狀態
        int left = pos % n == 0 ? 0 : pre % 3; // 左邊相鄰網格單元的狀態
        for (int i = 0; i < 3; i++) { // 枚舉當前網格單元的狀態
            if (i == 1 && (ic == 0) || (i == 2 && ec == 0)) {
                continue;
            }
            int cur = pre % p * 3 + i; // 當前的 n 個網格單元的狀態
            int a = h[up][i] + h[left][i]; // 當前網格單元以及左邊和上邊的相鄰網格單元的幸福感貢獻
            int b = dfs(pos + 1, cur, ic - (i == 1 ? 1 : 0), ec - (i == 2 ? 1 : 0));
            int c = i == 1 ? 120 : (i == 2 ? 40 : 0); // 當前網格單元本身的幸福感取決於該位置是否分配人員，以及分配的人員是內向的還是外向的
            ans = Math.max(ans, a + b + c);
        }
        return memo[pos][pre][ic][ec] = ans;
    }


}
