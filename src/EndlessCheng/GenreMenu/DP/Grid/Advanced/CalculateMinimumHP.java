package EndlessCheng.GenreMenu.DP.Grid.Advanced;

public class CalculateMinimumHP {

    // https://leetcode.cn/problems/dungeon-game/solutions/24493/cong-hui-su-dao-ji-yi-hua-sou-suo-dao-dong-tai-gui/
    private int rowSize = 0;
    private int colSize = 0;
    private int[][] globalDun = null;

    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 0;
        }
        rowSize = dungeon.length;
        colSize = dungeon[0].length;
        globalDun = dungeon;
        int[][] memory = new int[rowSize][colSize];
        // 初始化為-1，便於區別是否計算過結果了。
        for (int i = 0; i < rowSize; ++i) {
            for (int j = 0; j < colSize; ++j) {
                memory[i][j] = -1;
            }
        }
        return dfs(0, 0, memory) + 1;
    }

    public int dfs(int rowIndex, int colIndex, int[][] memory) {
        if (rowIndex >= rowSize || colIndex >= colSize) {
            return Integer.MAX_VALUE;
        }
        // 不為-1就是計算過了，直接返回結果。
        if (memory[rowIndex][colIndex] != -1) {
            return memory[rowIndex][colIndex];
        }
        if (rowIndex == rowSize - 1 && colIndex == colSize - 1) {
            if (globalDun[rowIndex][colIndex] >= 0) {
                return 0;
            } else {
                return -globalDun[rowIndex][colIndex];
            }
        }
        int right = dfs(rowIndex, colIndex + 1, memory);
        int down = dfs(rowIndex + 1, colIndex, memory);
        int needMin = Math.min(right, down) - globalDun[rowIndex][colIndex];
        int res = 0;
        if (needMin < 0) {
            res = 0;
        } else {
            res = needMin;
        }
        memory[rowIndex][colIndex] = res;
        System.out.println(rowIndex + " " + colIndex + " " + res);
        return res;
    }


    public int calculateMinimumHPBest(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 0;
        }
        int rowSize = dungeon.length;
        int colSize = dungeon[0].length;
        int[][] dp = new int[rowSize][colSize];
        // 設置最後一個值。
        dp[rowSize - 1][colSize - 1] = Math.max(0, -dungeon[rowSize - 1][colSize - 1]);

        // 設置最後一列的值
        for (int i = rowSize - 2; i >= 0; --i) {
            int needMin = dp[i + 1][colSize - 1] - dungeon[i][colSize - 1];
            dp[i][colSize - 1] = Math.max(0, needMin);
        }

        // 設置最後一行的值
        for (int i = colSize - 2; i >= 0; --i) {
            int needMin = dp[rowSize - 1][i + 1] - dungeon[rowSize - 1][i];
            dp[rowSize - 1][i] = Math.max(0, needMin);
        }

        for (int i = rowSize - 2; i >= 0; --i) {
            for (int j = colSize - 2; j >= 0; --j) {
                // 從右邊和下邊選擇一個最小值，然後減去當前的 dungeon 值
                int needMin = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = Math.max(0, needMin);
            }
        }
        return dp[0][0] + 1;
    }

}
