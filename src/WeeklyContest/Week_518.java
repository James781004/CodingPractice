package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;

public class Week_518 {

    // https://leetcode.cn/problems/count-rotations-with-exactly-k-equal-adjacent-pairs/solutions/4023619/on-ding-chang-hua-dong-chuang-kou-python-1d6t/
    public int countRotations(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int same = 0;
        int ans = 0;
        for (int i = 0; i < n * 2 - 2; i++) {
            // 1. 入
            if (s[i % n] == s[(i + 1) % n]) {
                same++;
            }

            // 注意窗口長度為 n-1
            int left = i - n + 2;
            if (left < 0) {
                continue;
            }

            // 2. 更新答案
            if (same == k) {
                ans++;
            }

            // 3. 出
            if (s[left] == s[(left + 1) % n]) {
                same--;
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/count-good-cyclic-rotations/solutions/4023626/liang-chong-fang-fa-qian-zhui-he-ding-ch-wh1y/
    public int countGoodRotations(int[] nums) {
        int n = nums.length;
        long sum1 = 0;
        long sum2 = 0;
        int ans = 0;
        for (int i = n / 2; i < n * 2 - 1; i++) {
            // 1. 入
            sum1 += nums[(i - n / 2) % n];
            sum2 += nums[i % n];

            int left = i - n + 1;
            if (left < 0) { // 尚未形成第一個窗口
                continue;
            }

            // 2. 更新答案
            if (sum1 > sum2) {
                ans++;
            }

            // 3. 出
            sum1 -= nums[left];
            sum2 -= nums[(left + n / 2) % n];
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-robot-groups/solutions/4023607/dan-diao-zhan-pythonjavacgo-by-endlessch-nqiu/
    public int countGroups(int[] position, int[] speed, int distance) {
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 哨兵
        for (int i = 0; i < position.length; i++) {
            // 找到這一組的最右側機器人
            if (i == position.length - 1 || position[i + 1] - position[i] > distance) {
                int v = speed[i];
                while (!st.isEmpty() && st.peek() > v) {
                    st.pop(); // 棧頂機器人速度更快，一段時間後與機器人 i 合並
                }
                st.push(v);
            }
        }
        return st.size() - 1; // 減去哨兵
    }

    // https://leetcode.cn/problems/minimum-cost-path-with-at-most-k-turns/solutions/4023616/liang-chong-fang-fa-dijkstra-dong-tai-gu-ozqt/
    private final static int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // 左右上下

    public int minCost(int[][] grid, int k0) {
        int m = grid.length;
        int n = grid[0].length;
        int[][][][] dis = new int[k0 + 1][m][n][4];
        for (int[][][] a : dis) {
            for (int[][] b : a) {
                for (int[] c : b) {
                    Arrays.fill(c, Integer.MAX_VALUE / 2);
                }
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // 初始方向可以向右（1）或向下（3）
        pq.offer(new int[]{grid[0][0], k0, 0, 0, 1});
        pq.offer(new int[]{grid[0][0], k0, 0, 0, 3});
        dis[k0][0][0][1] = dis[k0][0][0][3] = grid[0][0];

        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int d = top[0];
            int k = top[1];
            int i = top[2];
            int j = top[3];
            int idx = top[4];
            if (i == m - 1 && j == n - 1) {
                return d;
            }
            if (d > dis[k][i][j][idx]) {
                continue;
            }
            for (int newIdx = 0; newIdx < 4; newIdx++) {
                int x = i + DIRS[newIdx][0];
                int y = j + DIRS[newIdx][1];
                if (0 <= x && x < m && 0 <= y && y < n) {
                    int newK = k;
                    if (newIdx != idx) {
                        if (k == 0) {
                            continue;
                        }
                        newK--;
                    }
                    int newD = d + grid[x][y];
                    if (newD < dis[newK][x][y][newIdx]) {
                        dis[newK][x][y][newIdx] = newD;
                        pq.offer(new int[]{newD, newK, x, y, newIdx});
                    }
                }
            }
        }
        return -1;
    }

}










