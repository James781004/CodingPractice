package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_414 {
    // https://leetcode.cn/problems/convert-date-to-binary/solutions/2909000/ku-han-shu-jian-ji-xie-fa-pythonjavacgo-ohojk/
    public String convertDateToBinary(String date) {
        String[] a = date.split("-");
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.toBinaryString(Integer.parseInt(a[i]));
        }
        return String.join("-", a);
    }


    // https://leetcode.cn/problems/maximize-score-of-numbers-in-ranges/solutions/2908931/er-fen-da-an-zui-da-hua-zui-xiao-zhi-pyt-twe2/
    public int maxPossibleScore(int[] start, int d) {
        Arrays.sort(start);
        int n = start.length;
        int left = 0;
        int right = (start[n - 1] + d - start[0]) / (n - 1) + 1; // 右區間開始預設為「平均值+1」
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(start, d, mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean check(int[] start, int d, int score) {
        long preX = Long.MIN_VALUE; // 也可以只用一個 x 變量
        for (int s : start) {
            long x = preX + score;
            if (x > s + d) {
                return false;  // 區間外，不能選
            }
            preX = Math.max(x, s);
        }
        return true; // 當前 score 可以選 n 個數
    }


    // https://leetcode.cn/problems/reach-end-of-array-with-max-score/solutions/2908950/yi-tu-miao-dong-tan-xin-pythonjavacgo-by-tfua/
    public long findMaximumScore(List<Integer> nums) {
        long ans = 0;
        int mx = 0;
        for (int i = 0; i < nums.size() - 1; i++) {
            mx = Math.max(mx, nums.get(i));
            ans += mx;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-number-of-moves-to-kill-all-pawns/solutions/2909069/pai-lie-xing-zhuang-ya-dpxiang-lin-xiang-q49q/
    private static final int[][] DIRS = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

    public int maxMoves(int kx, int ky, int[][] positions) {
        int n = positions.length;
        int[][][] dis = new int[n][50][50];
        for (int i = 0; i < n; i++) {
            int[][] d = dis[i];
            for (int j = 0; j < 50; j++) {
                Arrays.fill(d[j], -1);
            }
            int px = positions[i][0];
            int py = positions[i][1];
            d[px][py] = 0;
            List<int[]> q = List.of(new int[]{px, py});
            for (int step = 1; !q.isEmpty(); step++) {
                List<int[]> tmp = q;
                q = new ArrayList<>();
                for (int[] p : tmp) {
                    for (int[] dir : DIRS) {
                        int x = p[0] + dir[0], y = p[1] + dir[1];
                        if (0 <= x && x < 50 && 0 <= y && y < 50 && d[x][y] < 0) {
                            d[x][y] = step;
                            q.add(new int[]{x, y});
                        }
                    }
                }
            }
        }

        int[][] memo = new int[n + 1][1 << n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n, (1 << n) - 1, kx, ky, positions, dis, memo);
    }

    private int dfs(int i, int mask, int kx, int ky, int[][] positions, int[][][] dis, int[][] memo) {
        if (mask == 0) {
            return 0;
        }
        if (memo[i][mask] != -1) { // 之前計算過
            return memo[i][mask];
        }
        int n = positions.length;
        int x = i < n ? positions[i][0] : kx;
        int y = i < n ? positions[i][1] : ky;

        int res = 0;
        int u = (1 << n) - 1;
        if (Integer.bitCount(u ^ mask) % 2 == 0) { // Alice 操作
            for (int j = 0; j < n; j++) {
                if ((mask >> j & 1) > 0) {
                    res = Math.max(res, dfs(j, mask ^ (1 << j), kx, ky, positions, dis, memo) + dis[j][x][y]);
                }
            }
        } else { // Bob 操作
            res = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if ((mask >> j & 1) > 0) {
                    res = Math.min(res, dfs(j, mask ^ (1 << j), kx, ky, positions, dis, memo) + dis[j][x][y]);
                }
            }
        }
        memo[i][mask] = res; // 記憶化
        return res;
    }


}


