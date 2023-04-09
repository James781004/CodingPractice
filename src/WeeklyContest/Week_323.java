package WeeklyContest;

import java.util.*;
import java.util.stream.IntStream;

public class Week_323 {
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2500.Delete%20Greatest%20Value%20in%20Each%20Row/README.md
    // 由於每一次操作都是從每一行中刪除最大值，然後取最大值加到答案中，因此可以先對每一行進行排序。
    // 然後遍歷每一列，取每一列的最大值，然後將其加到答案中即可。
    public int deleteGreatestValue(int[][] grid) {
        for (int[] row : grid) {
            Arrays.sort(row);
        }
        int ans = 0;
        for (int j = 0; j < grid[0].length; j++) {
            int t = 0;
            for (int i = 0; i < grid.length; i++) {
                t = Math.max(t, grid[i][j]);
            }
            ans += t;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2501.Longest%20Square%20Streak%20in%20an%20Array/README.md
    class LongestSquareStreak {
        public int longestSquareStreak(int[] nums) {
            Set<Integer> s = new HashSet<>();
            for (int v : nums) {
                s.add(v);
            }
            int ans = -1;

            // 先用哈希表記錄數組中的所有元素，然後枚舉數組中的每個元素作為子序列的第一個元素
            for (int v : nums) {
                int t = 0;

                // 將該元素不斷平方，並判斷平方後的結果是否在哈希表中
                while (s.contains(v)) {
                    v *= v;
                    t++;
                }

                // 判斷子序列的長度
                if (t > 1) {
                    ans = Math.max(ans, t);
                }
            }
            return ans;
        }

        // DFS做法
        private final Map<Integer, Integer> f = new HashMap<>();
        private final Set<Integer> s = new HashSet<>();

        public int longestSquareStreakDFS(int[] nums) {
            for (int v : nums) {
                s.add(v);
            }
            int ans = 0;
            for (int v : nums) {
                ans = Math.max(ans, dfs(v));
            }
            return ans < 2 ? -1 : ans;
        }

        // 表示以 x 為第一個元素的方波的長度
        private int dfs(int x) {
            if (!s.contains(x)) return 0;
            if (f.containsKey(x)) return f.get(x);
            int ans = 1 + dfs(x * x);
            f.put(x, ans);
            return ans;
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2502.Design%20Memory%20Allocator/README.md
    class Allocator {
        private int[] m;

        public Allocator(int n) {
            m = new int[n];
        }

        public int allocate(int size, int mID) {
            int cnt = 0;
            for (int i = 0; i < m.length; i++) {
                if (m[i] > 0) {
                    cnt = 0;
                } else if (++cnt == size) {
                    // 遍歷數組，找到連續的 size 個空閒內存單元，將其置為 mID，並返回第一個下標
                    Arrays.fill(m, i - size + 1, i + 1, mID);
                    return i - size + 1;
                }
            }
            return -1;
        }

        public int free(int mID) {
            int ans = 0;
            for (int i = 0; i < m.length; i++) {
                if (m[i] == mID) {
                    m[i] = 0;
                    ans++;
                }
            }
            return ans;
        }
    }

    class Allocator2 {
        // 用有序集合維護所有已分配的內存單元的起始下標和結束下標，其中起始下標為鍵，結束下標為值；
        // 另外用哈希表維護 mID 和其對應的內存單元的起始下標
        private TreeMap<Integer, Integer> tm = new TreeMap<>();
        private Map<Integer, List<Integer>> d = new HashMap<>();

        public Allocator2(int n) {
            tm.put(-1, -1);
            tm.put(n, n);
        }

        public int allocate(int size, int mID) {
            int s = -1;

            // 遍歷有序集合，找到第一個長度大於等於 size 的空閒區間，將其分配給 mID，並更新有序集合
            for (Map.Entry<Integer, Integer> entry : tm.entrySet()) {
                int v = entry.getKey();
                if (s != -1) {
                    int e = v - 1;

                    // 將 mID 和其對應的內存單元的起始下標加入哈希表
                    if (e - s + 1 >= size) {
                        tm.put(s, s + size - 1);
                        d.computeIfAbsent(mID, k -> new ArrayList<>()).add(s);
                        return s;
                    }
                }
                s = entry.getValue() + 1;
            }
            return -1;
        }

        public int free(int mID) {
            int ans = 0;

            // 從哈希表中找到 mID 對應的內存單元的起始下標，然後將其從有序集合中刪除，再將 mID 從哈希表中刪除
            for (int s : d.getOrDefault(mID, Collections.emptyList())) {
                int e = tm.remove(s);
                ans += e - s + 1;
            }
            d.remove(mID);
            return ans;
        }
    }


    // https://leetcode.cn/problems/maximum-number-of-points-from-grid-queries/solution/by-endlesscheng-qeei/
    class MaxPoints {

        // 離線詢問 + 並查集
        private final int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        private int[] fa, size;

        public int[] maxPoints(int[][] grid, int[] queries) {
            int m = grid.length, n = grid[0].length, mn = m * n;

            // 並查集
            fa = new int[mn];
            for (int i = 0; i < mn; i++) fa[i] = i;
            size = new int[mn];
            Arrays.fill(size, 1);

            // 矩陣元素從小到大排序，方便離線
            int[][] a = new int[mn][3];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    a[i * n + j] = new int[]{grid[i][j], i, j};
                }
            }
            Arrays.sort(a, (p, q) -> p[0] - q[0]);

            // 查詢的下標按照查詢值從小到大排序，方便離線
            int k = queries.length;
            Integer[] id = IntStream.range(0, k).boxed().toArray(Integer[]::new);
            Arrays.sort(id, (i, j) -> queries[i] - queries[j]);

            int[] ans = new int[k];
            int j = 0;
            for (int i : id) {
                int q = queries[i];
                for (; j < mn && a[j][0] < q; j++) {
                    int x = a[j][1], y = a[j][2];
                    // 枚舉周圍四個格子，值小於 q 才可以合並
                    for (int[] d : dirs) {
                        int x2 = x + d[0], y2 = y + d[1];
                        if (0 <= x2 && x2 < m && 0 <= y2 && y2 < n && grid[x2][y2] < q)
                            merge(x * n + y, x2 * n + y2); // 把坐標壓縮成一維的編號
                    }
                }
                if (grid[0][0] < q)
                    ans[i] = size[find(0)]; // 左上角的連通塊的大小
            }
            return ans;
        }

        // 並查集模板
        private int find(int x) {
            if (fa[x] != x) find(fa[x]);
            return fa[x];
        }

        private void merge(int from, int to) {
            from = find(from);
            to = find(to);
            if (from != to) {
                fa[from] = to;
                size[to] += size[from];
            }
        }


        // 離線詢問 + 最小堆
        public int[] maxPoints2(int[][] grid, int[] queries) {
            // 查詢的下標按照查詢值從小到大排序，方便離線
            int k = queries.length;
            Integer[] id = IntStream.range(0, k).boxed().toArray(Integer[]::new);
            Arrays.sort(id, (i, j) -> queries[i] - queries[j]);

            int[] ans = new int[k];
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
            pq.add(new int[]{grid[0][0], 0, 0});
            grid[0][0] = 0; // 充當 vis 數組的作用
            int m = grid.length, n = grid[0].length, cnt = 0;
            for (int i : id) {
                int q = queries[i];
                while (!pq.isEmpty() && pq.peek()[0] < q) {
                    ++cnt;
                    int[] p = pq.poll();
                    for (int[] d : dirs) { // 枚舉周圍四個格子
                        int x = p[1] + d[0], y = p[2] + d[1];
                        if (0 <= x && x < m && 0 <= y && y < n && grid[x][y] > 0) {
                            pq.add(new int[]{grid[x][y], x, y});
                            grid[x][y] = 0; // 充當 vis 數組的作用
                        }
                    }
                }
                ans[i] = cnt;
            }
            return ans;
        }
    }
}
