package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Week_334 {
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2574.Left%20and%20Right%20Sum%20Differences/README.md
    public int[] leftRightDifference(int[] nums) {
        int left = 0, right = Arrays.stream(nums).sum();
        int n = nums.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            right -= nums[i];  // right 每次減去當前數字，就是當前位置的右側prefix
            res[i] = Math.abs(left - right);
            left += nums[i];  // left 每次加上當前數字，就是下一個位置的左側prefix
        }
        return res;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2575.Find%20the%20Divisibility%20Array%20of%20a%20String/README.md
    public int[] divisibilityArray(String word, int m) {
        int n = word.length();
        int[] res = new int[n];
        long x = 0;
        for (int i = 0; i < n; i++) {
            x = (x * 10 + word.charAt(i) - '0') % m;  // 遍歷字符串 word，用x記錄當前前綴與m的取模結果
            if (x == 0) res[i] = 1;  // 如果x為0 ，則當前位置的可整除數組值為1，否則為0
        }
        return res;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2576.Find%20the%20Maximum%20Number%20of%20Marked%20Indices/README.md
    class MaxNumOfMarkedIndices {
        public int maxNumOfMarkedIndices(int[] nums) {
            Arrays.sort(nums);  // 排序後使用貪心解法
            int n = nums.length;
            int res = 0;
            for (int i = 0, j = (n + 1) / 2; j < n; i++, j++) {  // j指針從nums中間開始走
                while (j < n && nums[i] * 2 > nums[j]) {  // 跳過不合條件的部份
                    j++;
                }
                if (j < n) res += 2;  // 標記頭尾下標
            }
            return res;
        }

        // https://leetcode.cn/problems/find-the-maximum-number-of-marked-indices/solution/er-fen-da-an-pythonjavacgo-by-endlessche-t9f5/
        // 二分答案
        public int maxNumOfMarkedIndicesBS(int[] nums) {
            Arrays.sort(nums);  // 排序
            int n = nums.length;
            int left = 0, right = (n + 1) / 2;
            while (left + 1 < right) {
                int mid = (left + right) >>> 1;
                if (check(nums, mid)) left = mid;
                else right = mid;
            }
            return left * 2;
        }

        private boolean check(int[] nums, int k) {
            for (int i = 0; i < k; i++) {
                if (nums[i] * 2 > nums[nums.length - k + i]) return false;
            }
            return true;
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2577.Minimum%20Time%20to%20Visit%20a%20Cell%20In%20a%20Grid/README.md
    // Dijkstra 算法 : https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/solution/er-fen-da-an-bfspythonjavacgo-by-endless-j10w/
    // video: https://www.bilibili.com/video/BV1wj411G7sH/
    class MinimumTime {
        private int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        public int minimumTime(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            if (grid[0][1] > 1 && grid[1][0] > 1) // 起點旁邊需時大於1，就不可能走下去
                return -1;

            int[][] dis = new int[m][n];
            for (int i = 0; i < m; ++i) Arrays.fill(dis[i], Integer.MAX_VALUE);  // 初始化
            dis[0][0] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
            pq.add(new int[]{0, 0, 0}); // {距離, 座標x, 座標y}
            while (true) {
                int[] p = pq.poll();
                int d = p[0], i = p[1], j = p[2];
                if (i == m - 1 && j == n - 1) return d;  // 抵達終點

                for (int[] q : dirs) { // 沒抵達終點，枚舉周圍四個格子
                    int x = i + q[0], y = j + q[1];
                    if (0 <= x && x < m && 0 <= y && y < n) {
                        // 根據網格圖的性質，在可以反復橫跳的情況下，到達一個格子的時間的奇偶性是不變的，
                        // 那麼 dis[i][j] 應當與  i+j 的奇偶性相同。
                        // 如果奇偶性不同，dis[i][j] += 1 調整
                        int nd = Math.max(d + 1, grid[x][y]);  // 更新當前距離
                        nd += (nd - x - y) % 2; // nd 必須和 x+y 同奇偶(考慮反覆橫跳的狀況)，奇偶性不同就+1，這邊這樣寫可以自動處理
                        if (nd < dis[x][y]) {
                            dis[x][y] = nd; // 更新最短路
                            pq.add(new int[]{nd, x, y});
                        }
                    }
                }
            }
        }


        private int[][] grid, vis;

        public int minimumTimeBFS(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            if (grid[0][1] > 1 && grid[1][0] > 1) // 無法「等待」
                return -1;

            this.grid = grid;
            vis = new int[m][n];
            int left = Math.max(grid[m - 1][n - 1], m + n - 2) - 1;  // 曼哈頓距離
            int right = (int) 1e5 + m + n; // 開區間
            while (left + 1 < right) {
                int mid = (left + right) >>> 1;
                if (check(mid)) right = mid;
                else left = mid;
            }
            return right + (right + m + n) % 2;
        }

        private boolean check(int endTime) {
            int m = grid.length, n = grid[0].length;
            vis[m - 1][n - 1] = endTime;
            List<int[]> q = new ArrayList<>();
            q.add(new int[]{m - 1, n - 1});
            for (int t = endTime - 1; !q.isEmpty(); --t) {
                List<int[]> tmp = q;
                q = new ArrayList<>();
                for (int[] p : tmp) {
                    int i = p[0], j = p[1];
                    for (int[] d : dirs) { // 枚舉周圍四個格子
                        int x = i + d[0], y = j + d[1];
                        if (0 <= x && x < m && 0 <= y && y < n && vis[x][y] != endTime && grid[x][y] <= t) {
                            if (x == 0 && y == 0) return true;
                            vis[x][y] = endTime; // 用二分的值endTime來標記，避免重復創建 vis 數組，也可以用boolean
                            q.add(new int[]{x, y});
                        }
                    }
                }
            }
            return false;
        }
    }
}
