package LeetcodeMaster.Graph;

public class Q01_IslandPerimeter {
//    0463.島嶼的周長
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0463.%E5%B2%9B%E5%B1%BF%E7%9A%84%E5%91%A8%E9%95%BF.md


    // 解法一
    // 遍歷每一個空格，遇到島嶼，計算其上下左右的情況，遇到水域或者出界的情況，就可以計算邊了。
    // 上下左右 4 個方向
    int[] dirx = {-1, 1, 0, 0};
    int[] diry = {0, 0, -1, 1};

    public int islandPerimeter(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int res = 0; // 島嶼周長
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < 4; k++) { // 上下左右四個方向
                        int x = i + dirx[k];
                        int y = j + diry[k]; // 計算周邊坐標x,y
                        // 當前位置是陸地，並且從當前位置4個方向擴展的“新位置”是“水域”或“新位置“越界，則會為周長貢獻一條邊
                        if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] == 0) {
                            res++;
                            continue;
                        }
                    }
                }
            }
        }

        return res;
    }


    // 解法二
    // 計算出總的島嶼數量，因為有一對相鄰兩個陸地，邊的總數就減2，那麽在計算出相鄰島嶼的數量就可以了。
    public int islandPerimeter2(int[][] grid) {
        // 計算島嶼的周長
        // 方法二 : 遇到相鄰的陸地總周長就-2
        int landSum = 0; // 陸地數量
        int cover = 0; // 相鄰陸地數量
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    landSum++;
                    // 統計上面和左邊的相鄰陸地
                    if (i - 1 >= 0 && grid[i - 1][j] == 1) cover++;
                    if (j - 1 >= 0 && grid[i][j - 1] == 1) cover++;
                }
            }
        }
        return landSum * 4 - cover * 2;
    }
}
