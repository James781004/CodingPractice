package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

public class TilingRectangle {

    // https://leetcode.cn/problems/tiling-a-rectangle-with-the-fewest-squares/solutions/2291886/java-dfsbao-li-di-gui-by-arglone-alau/
    private int res; // 答案。會在DFS過程中更新。

    public int tilingRectangle(int n, int m) {
        boolean[][] tiled = new boolean[n][m];
        res = m * n;
        process(tiled, 0);
        return res;
    }

    // DFS：當前地上瓷磚狀態為tiled，已經鋪了count個瓷磚，繼續鋪下去把地面鋪滿
    private void process(boolean[][] tiled, int count) {
        if (count >= res) { // 剪枝
            return;
        }
        int[] emptyPos = checkEmpty(tiled); // 地上沒瓷磚的第一個位置
        if (emptyPos[0] == -1 && emptyPos[1] == -1) { // 已經鋪完了所有地方，收集答案
            res = Math.min(res, count);
            return;
        }
        // 從大到小，開始嘗試鋪瓷磚
        int maxLen = Math.min(tiled.length - emptyPos[0], tiled[0].length - emptyPos[1]);
        for (int len = maxLen; len >= 1; len--) {
            // 嘗試用len*len的瓷磚的左上角去對齊地上沒瓷磚的這個位置
            if (setStatus(tiled, emptyPos[0], emptyPos[1], emptyPos[0] + len - 1, emptyPos[1] + len - 1, false)) {
                process(tiled, count + 1);
                setStatus(tiled, emptyPos[0], emptyPos[1], emptyPos[0] + len - 1, emptyPos[1] + len - 1, true);
            }
        }
    }

    // 嘗試反轉 (row1, col1) 和 (row2, col2) 之間的鋪瓷磚狀態
    // 必須確保這個區域內初始都是hasTiled狀態，否則不會反轉
    private boolean setStatus(boolean[][] tiled, int row1, int col1, int row2, int col2, boolean hasTiled) {
        for (int i = row1; i <= row2; i++) {
            for (int j = col1; j <= col2; j++) {
                if (tiled[i][j] != hasTiled) {
                    return false;
                }
            }
        }
        for (int i = row1; i <= row2; i++) {
            for (int j = col1; j <= col2; j++) {
                tiled[i][j] = !tiled[i][j];
            }
        }
        return true;
    }

    // 順序遍歷尋找第一個沒鋪瓷磚的位置（從上到下，從左到右）
    private int[] checkEmpty(boolean[][] tiled) {
        int i = 0, j = 0;
        for (; i < tiled.length; i++) {
            for (j = 0; j < tiled[0].length; j++) {
                if (!tiled[i][j]) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }


}
