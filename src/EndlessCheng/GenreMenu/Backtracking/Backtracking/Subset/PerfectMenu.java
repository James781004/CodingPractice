package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

public class PerfectMenu {

    // https://leetcode.cn/problems/UEcfPD/solutions/3059542/ling-shen-hui-su-ti-dan-hui-su-shu-ru-de-sh3i/
    int[] materials;
    int[][] cookbooks;
    int[][] attribute;
    int limit;
    int ans = -1;
    int n;

    public int perfectMenu(int[] materials, int[][] cookbooks, int[][] attribute, int limit) {
        this.materials = materials;
        this.cookbooks = cookbooks;
        this.attribute = attribute;
        this.limit = limit;
        n = cookbooks.length;
        dfs(0, 0, 0);
        return ans;
    }

    private void dfs(int i, int x, int y) {
        // 邊界條件
        if (i == n) {
            if (y >= limit) {
                ans = Math.max(ans, x);
            }
            return;
        }

        // 非邊界條件
        dfs(i + 1, x, y); // 不選

        int[] cookbook = cookbooks[i];

        // 判斷當前的料理能否完成
        boolean loop = true;

        // 計算美味度 x 和飽腹感 y的增量
        int curX = 0;
        int curY = 0;
        for (int j = 0; j < cookbook.length; j++) {
            if (materials[j] - cookbook[j] < 0) {
                loop = false;
            }
            materials[j] -= cookbook[j];
        }

        curX += attribute[i][0];
        curY += attribute[i][1];

        // 能完成就帶著這個料理進入下一階段
        if (loop) {
            dfs(i + 1, x + curX, y + curY);
        }

        // 恢復現場
        for (int j = 0; j < cookbook.length; j++) {
            materials[j] += cookbook[j];
        }
    }


}
