package EndlessCheng.Basic.DifferenceArray;

public class possibleToStamp {

    // https://leetcode.cn/problems/stamping-the-grid/solutions/1199642/wu-nao-zuo-fa-er-wei-qian-zhui-he-er-wei-zwiu/
    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int m = grid.length, n = grid[0].length;
        int[][] sum = new int[m + 1][n + 1], diff = new int[m + 2][n + 2];
        // 計算佔據位置的前綴和
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                sum[i + 1][j + 1] = grid[i][j] + sum[i + 1][j] + sum[i][j + 1] - sum[i][j];
            }
        }

        // 依次檢查每個位置是否可以作為左上角，計算差分數組
        for (int i = 0; i + stampHeight - 1 < m; ++i) {
            for (int j = 0; j + stampWidth - 1 < n; ++j) {
                int x = i + stampHeight; // 計算郵票左上角的行索引
                int y = j + stampWidth; // 計算郵票左上角的列索引
                int s = sum[x][y] - sum[i][y] - sum[x][j] + sum[i][j];
                if (s == 0) { // 判斷是否可以放置郵票
                    diff[i + 1][j + 1]++; // 左上角坐標加一
                    diff[i + 1][y + 1]--; // 右上角坐標減一
                    diff[x + 1][j + 1]--; // 左下角坐標減一
                    diff[x + 1][y + 1]++; // 右下角坐標加一
                }
            }
        }
        int[][] a = new int[m + 1][n + 1];
        // 還原差分數組
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] += a[i - 1][j] + a[i][j - 1] - a[i - 1][j - 1] + diff[i][j];
                if (a[i][j] == 0 && grid[i - 1][j - 1] == 0) {
                    return false; // 還原時如果某個空格子未被覆蓋，返回false
                }
            }
        }
        return true; // 所有空格子均被覆蓋，返回true
    }


}
