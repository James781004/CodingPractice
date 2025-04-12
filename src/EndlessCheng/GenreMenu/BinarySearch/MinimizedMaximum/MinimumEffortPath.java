package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

import java.util.LinkedList;
import java.util.Queue;

public class MinimumEffortPath {

    // https://leetcode.cn/problems/path-with-minimum-effort/solutions/2563063/javapython3cer-fen-cha-zhao-dfs-bing-cha-ynce/
    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};    // 方向集合，包含上下左右四個轉移方向

    public int minimumEffortPath(int[][] heights) {
        // 二分查找
        int left = 0;        // 最小結果為0，路徑上所有元素都相同
        int right = 1000000;    // 最大結果為1e6-1，這裡搜索區間為[left, right), right取取不到的值1e6
        int mid;            // 二分查找中間值
        int ans = 0;        // 結果，初始為0（對大小為1*1的矩陣的特殊處理）
        while (left < right) {
            mid = left + ((right - left) >> 1);    // 以mid為潛在消耗體力值進行搜索
            if (check(heights, mid)) {
                // 如果存在消耗體力小於等於mid的路徑，mid為一個潛在結果
                // ans暫存結果，縮小right在左半區間嘗試搜索更小的消耗值
                ans = right = mid;
            } else {
                // 不存在消耗體力小於等於mid的路徑，那麼就要擴大left在右半區間去搜索更大的消耗值以匹配對應的路徑
                left = mid + 1;
            }
        }
        return ans;
    }

    /**
     * 通過BFS搜索是否存在消耗體力小於等於target的路徑
     *
     * @param heights：矩陣
     * @param target:    目標值
     */
    private boolean check(int[][] heights, int target) {
        int m = heights.length;
        int n = heights[0].length;
        boolean[][] visited = new boolean[m][n];    // (r,c)單元格是否已處理
        Queue<Integer> queue = new LinkedList<>();    // BFS需要的隊列，存儲每個單元格(r,c)的位置編號id=r*n+c
        queue.offer(0);        // 初始起點(0,0)入隊
        visited[0][0] = true;    // 標記起點已處理
        while (!queue.isEmpty()) {
            int id = queue.poll();
            int r = id / n, c = id % n;    // 從隊首獲取當前處理的位置
            for (int[] d : directions) {
                // 枚舉四個方向，得到下一個處理的位置（nr，nc）
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && !visited[nr][nc] && Math.abs(heights[r][c] - heights[nr][nc]) <= target) {
                    // 僅有位置合法且未訪問過且高度差小於等於目標值的位置可以進行轉移
                    visited[nr][nc] = true;        // 標記位置已訪問
                    if (nr == m - 1 && nc == n - 1)
                        return true;            // 到達終點，返回true
                    queue.offer(nr * n + nc);    // 下個位置入隊
                }
            }
        }
        return false;    // 退出循環說明沒有滿足條件的路徑，即隊列為空沒有可轉移的位置
    }


}
