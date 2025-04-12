package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayDeque;
import java.util.Deque;

public class ShortestBridge {

    // https://leetcode.cn/problems/shortest-bridge/solutions/1922327/-by-muse-77-j7w5/
    int[][] grid, coordinates = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}}; // 上、下、右、左四個方向
    Deque<int[]> edges; // 用戶存儲邊緣格子

    public int shortestBridge(int[][] grid) {
        int result = 0;
        boolean findIsland = false; // 只要找到2個島嶼中其中的1個島嶼，就將其設置為true，並結束步驟1中的兩層for循環
        edges = new ArrayDeque();
        this.grid = grid;

        /** 步驟1：為其中一個島嶼打標記（val=2），並保存”邊界格子“到edges中 */
        for (int i = 0; !findIsland && i < grid.length; i++)
            for (int j = 0; !findIsland && j < grid[0].length; j++)
                if (findIsland = (grid[i][j] == 1)) markIsland(i, j);

        /** 步驟2：利用邊界edges，一層一層擴展島嶼（val=2），直到遇到另一個島嶼（val=1）*/
        while (!edges.isEmpty()) {
            result++; // 擴展層數
            int num = edges.size();
            for (int i = 0; i < num; i++) {
                int[] edge = edges.removeFirst();
                for (int[] c : coordinates) { // 向edge的四個方向查看格子編號，並擴展島嶼邊界
                    int nex = edge[0] + c[0], ney = edge[1] + c[1];
                    if (isLegal(nex, ney) && grid[nex][ney] == 0) {
                        edges.addLast(new int[]{nex, ney}); // 添加新的邊界
                        grid[nex][ney] = 2;
                    } else if (isLegal(nex, ney) && grid[nex][ney] == 1) return result; // 與另一個島嶼相遇，則直接返回result
                }
            }
        }
        return result;
    }

    public void markIsland(int row, int column) {
        if (!isLegal(row, column) || grid[row][column] == 2) return;
        if (grid[row][column] == 0) {
            grid[row][column] = 2; // 將邊界向外擴展1層島嶼（val=2)
            edges.addLast(new int[]{row, column});
            return;
        }
        grid[row][column] = 2; // 為島嶼打標記（val=2）
        for (int[] c : coordinates) markIsland(row + c[0], column + c[1]); // 深度遍歷某個格子的四個方向
    }

    public boolean isLegal(int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }


}
