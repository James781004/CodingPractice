package WeeklyContest;

import java.util.*;

public class Week_409 {
    // https://leetcode.cn/problems/design-neighbor-sum-service/solutions/2868638/3242-she-ji-xiang-lin-yuan-su-qiu-he-fu-5h9s7/
    class neighborSum {
        private static int[][] adjacentDirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        private static int[][] diagonalDirs = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        private int n;
        private int[][] grid;
        private int[][] positions;

        public neighborSum(int[][] grid) {
            this.n = grid.length;
            this.grid = grid;
            this.positions = new int[n * n][];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    positions[grid[i][j]] = new int[]{i, j};
                }
            }
        }

        public int adjacentSum(int value) {
            int sum = 0;
            int[] position = positions[value];
            for (int[] dir : adjacentDirs) {
                int newRow = position[0] + dir[0], newCol = position[1] + dir[1];
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                    sum += grid[newRow][newCol];
                }
            }
            return sum;
        }

        public int diagonalSum(int value) {
            int sum = 0;
            int[] position = positions[value];
            for (int[] dir : diagonalDirs) {
                int newRow = position[0] + dir[0], newCol = position[1] + dir[1];
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                    sum += grid[newRow][newCol];
                }
            }
            return sum;
        }
    }


    // https://leetcode.cn/problems/shortest-distance-after-road-addition-queries-i/solutions/2868640/3243-xin-zeng-dao-lu-cha-xun-hou-de-zui-mh3vs/
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        List<Integer>[] adjacentArr = new List[n];
        for (int i = 0; i < n; i++) {
            adjacentArr[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n - 1; i++) {
            adjacentArr[i].add(i + 1);
        }
        int queriesCount = queries.length;
        int[] answer = new int[queriesCount];
        for (int i = 0; i < queriesCount; i++) {
            int u = queries[i][0], v = queries[i][1];
            adjacentArr[u].add(v);
            answer[i] = bfs(adjacentArr, n);
        }
        return answer;
    }

    public int bfs(List<Integer>[] adjacentArr, int n) {
        boolean[] visited = new boolean[n];
        visited[0] = true;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        int steps = -1;
        int distance = -1;
        while (!queue.isEmpty() && distance < 0) {
            steps++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int city = queue.poll();
                if (city == n - 1) {
                    distance = steps;
                }
                List<Integer> adjacent = adjacentArr[city];
                for (int next : adjacent) {
                    if (!visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
        }
        return steps;
    }


    // https://leetcode.cn/problems/shortest-distance-after-road-addition-queries-ii/solutions/2868558/qu-jian-bing-cha-ji-pythonjavacgo-by-end-a9k7/
    public int[] shortestDistanceAfterQueriesII(int n, int[][] queries) {
        UnionFind uf = new UnionFind(n - 1);
        int[] ans = new int[queries.length];
        int cnt = n - 1; // 並查集連通塊個數
        for (int qi = 0; qi < queries.length; qi++) {
            int l = queries[qi][0], r = queries[qi][1] - 1;
            int fr = uf.find(r);
            for (int i = uf.find(l); i < r; i = uf.find(i + 1)) {
                uf.fa[i] = fr;
                cnt--;
            }
            ans[qi] = cnt;
        }
        return ans;
    }

    class UnionFind {
        public final int[] fa;

        public UnionFind(int size) {
            fa = new int[size];
            for (int i = 1; i < size; i++) {
                fa[i] = i;
            }
        }

        public int find(int x) {
            if (fa[x] != x) {
                fa[x] = find(fa[x]);
            }
            return fa[x];
        }
    }


    // https://leetcode.cn/problems/alternating-groups-iii/solutions/2869147/cong-yi-dao-nan-yi-bu-bu-tui-dao-pythonj-jho9/
    public List<Integer> numberOfAlternatingGroups(int[] a, int[][] queries) {
        int n = a.length;
        TreeSet<Integer> set = new TreeSet<>();
        FenwickTree t = new FenwickTree(n);

        for (int i = 0; i < n; i++) {
            if (a[i] == a[(i + 1) % n]) {
                add(set, t, n, i); // i 是一個結束位置
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int[] q : queries) {
            if (q[0] == 1) {
                if (set.isEmpty()) {
                    ans.add(n); // 每個長為 size 的子數組都符合要求
                } else {
                    int[] res = t.query(q[1]);
                    ans.add(res[1] - res[0] * (q[1] - 1));
                }
            } else {
                int i = q[1];
                if (a[i] == q[2]) { // 無影響
                    continue;
                }
                int pre = (i - 1 + n) % n;
                int nxt = (i + 1) % n;
                // 修改前，先去掉結束位置
                if (a[pre] == a[i]) {
                    del(set, t, n, pre);
                }
                if (a[i] == a[nxt]) {
                    del(set, t, n, i);
                }
                a[i] ^= 1;
                // 修改後，添加新的結束位置
                if (a[pre] == a[i]) {
                    add(set, t, n, pre);
                }
                if (a[i] == a[nxt]) {
                    add(set, t, n, i);
                }
            }
        }
        return ans;
    }

    // 添加一個結束位置 i
    private void add(TreeSet<Integer> set, FenwickTree t, int n, int i) {
        if (set.isEmpty()) {
            t.update(n, 1);
        } else {
            update(set, t, n, i, 1);
        }
        set.add(i);
    }

    // 移除一個結束位置 i
    private void del(TreeSet<Integer> set, FenwickTree t, int n, int i) {
        set.remove(i);
        if (set.isEmpty()) {
            t.update(n, -1);
        } else {
            update(set, t, n, i, -1);
        }
    }

    // op=1，添加一個結束位置 i
    // op=-1，移除一個結束位置 i
    private void update(TreeSet<Integer> set, FenwickTree t, int n, int i, int op) {
        Integer pre = set.floor(i);
        if (pre == null) {
            pre = set.last();
        }

        Integer nxt = set.ceiling(i);
        if (nxt == null) {
            nxt = set.first();
        }

        t.update((nxt - pre + n - 1) % n + 1, -op); // 移除/添加舊長度
        t.update((i - pre + n) % n, op);
        t.update((nxt - i + n) % n, op); // 添加/移除新長度
    }

    class FenwickTree {
        private final int[][] t;

        public FenwickTree(int n) {
            t = new int[n + 1][2];
        }

        // op=1，添加一個 size
        // op=-1，移除一個 size
        public void update(int size, int op) {
            for (int i = t.length - size; i < t.length; i += i & -i) {
                t[i][0] += op;
                t[i][1] += op * size;
            }
        }

        // 返回 >= size 的元素個數，元素和
        public int[] query(int size) {
            int cnt = 0, sum = 0;
            for (int i = t.length - size; i > 0; i &= i - 1) {
                cnt += t[i][0];
                sum += t[i][1];
            }
            return new int[]{cnt, sum};
        }
    }

}


