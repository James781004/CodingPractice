package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class StrangePrinterII {

    // https://leetcode.cn/problems/strange-printer-ii/solutions/420361/cyi-chong-qi-guai-de-si-lu-jian-tu-tuo-bu-pai-xu-b/
    public boolean isPrintable(int[][] grid) {

        // 總共有不超過 60 種顏色，且顏色編號從 0 開始，
        // colorArea[c] 代表顏色 c 的范圍，按照上下左右的順序給出
        int m = grid.length, n = grid[0].length, maxv = 60;
        int[][] colorArea = new int[maxv + 1][];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int c = grid[i][j];
                if (colorArea[c] == null) colorArea[c] = new int[]{i, i, j, j}; // 上下左右
                else {
                    int[] a = colorArea[c];
                    a[1] = i;
                    a[2] = Math.min(j, a[2]);
                    a[3] = Math.max(j, a[3]);
                }
            }
        }

        // 建立鄰接表、入度表，節點 v 出現在 colorArea[u] 范圍裡，則記錄一條 u 到 v 的有向邊。注意，邊可能是雙向的。
        List<Integer>[] adt = new List[maxv + 1];
        int[] inDegree = new int[maxv + 1];
        boolean[][] handled = new boolean[maxv + 1][maxv + 1];
        for (int i = 0; i <= maxv; i++) adt[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = grid[i][j];
                for (int u = 1; u <= maxv; u++) {
                    int[] a = colorArea[u];
                    if (u == v || a == null || handled[u][v]) continue;
                    if (i >= a[0] && i <= a[1] && j >= a[2] && j <= a[3]) {
                        adt[u].add(v);
                        inDegree[v]++;
                        handled[u][v] = true;
                    }
                }
            }
        }

        // 拓撲排序
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= maxv; i++) {
            if (inDegree[i] == 0) queue.offer(i);
        }
        int left = maxv;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            left--;
            for (int v : adt[u]) {
                if (--inDegree[v] == 0) queue.offer(v);
            }
        }

        return left == 0;

    }


}
