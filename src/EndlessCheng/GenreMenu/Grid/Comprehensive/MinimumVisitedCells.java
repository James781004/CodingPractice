package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumVisitedCells {

    // https://leetcode.cn/problems/minimum-number-of-visited-cells-in-a-grid/solutions/2216329/dan-diao-zhan-you-hua-dp-by-endlesscheng-mc50/
    public int minimumVisitedCells(int[][] grid) {
        // 獲取網格的行數和列數
        int m = grid.length, n = grid[0].length, mn = 0;
        // 為每一列創建一個棧,用於存儲訪問該列所需的最小步數和對應的行號
        List<int[]>[] colStack = new ArrayList[n];
        // 創建一個棧,用於存儲訪問每一行所需的最小步數和對應的列號
        List<int[]> rowStack = new ArrayList<>();
        // 初始化每一個列棧
        Arrays.setAll(colStack, i -> new ArrayList<int[]>());
        // 從最後一行開始倒敘遍歷
        for (int i = m - 1; i >= 0; i--) {
            // 清空行棧,因為每一行都是從新開始計算
            rowStack.clear();
            // 從最後一列開始倒序遍歷
            for (int j = n - 1; j >= 0; j--) {
                int g = grid[i][j];
                List<int[]> colSt = colStack[j];
                // 如果是第一行第一列,則只需訪問一個單元格
                mn = i < m - 1 || j < n - 1 ? Integer.MAX_VALUE : 1;
                // 如果當前單元格的值大於0,則需要從相鄰的單元格移動過來
                if (g > 0) {
                    // 在行棧中查找距離當前單元格最近的合法單元格
                    int k = bs(rowStack, j + g);
                    if (k < rowStack.size()) mn = rowStack.get(k)[0] + 1;

                    // 在列棧中查找距離當前單元格最近的合法單元格
                    k = bs(colSt, i + g);
                    if (k < colSt.size()) mn = Math.min(mn, colSt.get(k)[0] + 1);
                }
                // 如果找到了合法的相鄰單元格,則更新行棧和列棧
                if (mn < Integer.MAX_VALUE) {
                    // 從行棧中移除所有步數大於等於mn的元素
                    while (!rowStack.isEmpty() && mn <= rowStack.get(rowStack.size() - 1)[0]) {
                        rowStack.remove(rowStack.size() - 1);
                    }
                    // 將當前單元格的信息添加到行棧
                    rowStack.add(new int[]{mn, j});
                    // 從列棧中移除所有步數大於等於mn的元素
                    while (!colSt.isEmpty() && mn <= colSt.get(colSt.size() - 1)[0]) {
                        colSt.remove(colSt.size() - 1);
                    }
                    // 將當前單元格的信息添加到列棧
                    colSt.add(new int[]{mn, i});
                }
            }
        }
        // 如果找到了合法的路徑,則返回最小步數,否則返回-1
        return mn < Integer.MAX_VALUE ? mn : -1;
    }

    // 二分查找,返回第一個大於等於target的下標
    private int bs(List<int[]> rowStack, int target) {
        int left = -1, right = rowStack.size();
        while (left + 1 < right) {
            int middle = left + (right - left) / 2;
            if (rowStack.get(middle)[1] <= target) right = middle;
            else left = middle;
        }
        return right;
    }


}
