package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_524 {

    // https://leetcode.cn/problems/minimum-deletions-to-make-array-sharp/solutions/4045150/xiang-lin-xiang-tong-de-ge-shu-pythonjav-cwp4/
    public int minDeletions(int[] nums) {
        int ans = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == nums[i]) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/last-visited-cell-in-a-directional-grid/solutions/4045146/mo-ni-pythonjavacgo-by-endlesscheng-hcfr/
    public int[] lastVisitedCell(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int x = 0, y = 0;
        while (true) {
            int nx = x, ny = y;
            switch (grid[x][y]) {
                case 'U' -> nx--;
                case 'D' -> nx++;
                case 'L' -> ny--;
                default -> ny++;
            }
            if (nx < 0 || nx == m || ny < 0 || ny == n || grid[nx][ny] == 0) {
                return new int[]{x, y};
            }
            grid[x][y] = 0; // 訪問標記
            x = nx;
            y = ny;
        }
    }


    // https://leetcode.cn/problems/find-root-of-a-colored-tree/solutions/4045139/zhi-you-yi-ge-dian-neng-zuo-wei-gen-by-e-d0s5/
    static class FindColoredRoot {
        private static final int[] mp = new int[128];

        static {
            mp['W'] = 0;
            mp['R'] = 1;
            mp['B'] = 2;
        }

        public int findColoredRoot(int[][] edges, String colors) {
            char[] s = colors.toCharArray();
            int n = s.length;
            ArrayList[] g = new ArrayList[n];
            Arrays.setAll(g, v -> new ArrayList<>());
            for (int[] e : edges) {
                int x = e[0];
                int y = e[1];
                g[x].add(y);
                g[y].add(x);
            }

            int ans = -1;
            for (int x = 0; x < g.length; x++) {
                List<Integer> to = g[x];
                // 一个点至多三个邻居
                if (to.size() > 3) {
                    return -1;
                }

                int[] cnt = new int[3];
                for (int y : to) {
                    cnt[mp[s[y]]]++;
                }

                int colorX = mp[s[x]];
                // 不能有邻居和 x 颜色相同，或者 x 的父节点的颜色个数超过 1
                if (cnt[colorX] > 0 || cnt[(colorX + 2) % 3] > 1) {
                    return -1;
                }

                if (to.size() < 3 && cnt[(colorX + 2) % 3] == 0) {
                    // 不能有多个点能是根，那样当一个点是根的时候，另一个点不满足要求
                    if (ans >= 0) {
                        return -1;
                    }
                    ans = x;
                }
            }

            return ans;
        }
    }


    // https://leetcode.cn/problems/minimum-interval-selection-to-cover-target-intervals/solutions/4045141/qu-jian-he-bing-qu-jian-fu-gai-pythonjav-05u0/
    public int minSelection(int[][] availableIntervals, int[][] targetIntervals) {
        Arrays.sort(availableIntervals, (p, q) -> p[0] - q[0]); // 按照左端點從小到大排序

        int curEnd = 0; // 已建造的橋的右端點
        int nextEnd = 0; // 下一座橋的右端點的最大值
        int i = 0;
        int ans = 0;
        for (int[] target : merge(targetIntervals)) {
            int tarL = target[0], tarR = target[1];
            while (curEnd < tarR) {
                // 當前最靠左的未覆蓋位置
                int uncover = Math.max(tarL, curEnd);

                // 尋找下一座橋的最遠點
                while (i < availableIntervals.length && availableIntervals[i][0] <= uncover) {
                    nextEnd = Math.max(nextEnd, availableIntervals[i][1]);
                    i++;
                }

                // 無法覆蓋 uncover，或者無法繼續向右延伸
                if (nextEnd < uncover || nextEnd == uncover && uncover < tarR) {
                    return -1;
                }

                curEnd = nextEnd; // 建橋後，最遠可以到達 nextEnd
                ans++;
            }
        }
        return ans;
    }

    // 56. 合並區間
    private List<int[]> merge(int[][] intervals) {
        Arrays.sort(intervals, (p, q) -> p[0] - q[0]); // 按照左端點從小到大排序

        List<int[]> ans = new ArrayList<>();
        for (int[] p : intervals) {
            int m = ans.size();
            if (m > 0 && p[0] <= ans.get(m - 1)[1]) { // 左端點在合並區間內，可以合並
                ans.get(m - 1)[1] = Math.max(ans.get(m - 1)[1], p[1]); // 更新合並區間的右端點
            } else { // 不相交，無法合並
                ans.add(p); // 新的合並區間
            }
        }
        return ans;
    }


}










