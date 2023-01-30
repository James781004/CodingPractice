package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.*;

public class Q02_NumberOfIslands {

    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.g786776e5f7_0_0
    class NumberOfIslands {
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        public int numIslands(char[][] grid) {
            int M = grid.length, N = grid[0].length, res = 0;
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] == '1') {
                        res++;
                        dfs(grid, i, j);
//                        bfs(grid, i, j);
                    }
                }
            }
            return res;
        }


        private void bfs(char[][] grid, int row, int col) {
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{row, col});
            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == '0') continue;
                grid[x][y] = '0';
                for (int[] dir : dirs) queue.add(new int[]{x + dir[0], y + dir[1]});
            }
        }

        private void dfs(char[][] grid, int row, int col) {
            if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == '0') return;
            grid[row][col] = '0';
//            dfs(grid, row + 1, col);
//            dfs(grid, row - 1, col);
//            dfs(grid, row, col + 1);
//            dfs(grid, row, col - 1);
            for (int[] dir : dirs) dfs(grid, row + dir[0], col + dir[1]);
        }


        private void dfs2(char[][] grid, int row, int col) {
            Stack<int[]> stack = new Stack<>();
            stack.push(new int[]{row, col});
            while (!stack.isEmpty()) {
                int[] cur = stack.pop();
                int x = cur[0], y = cur[1];
                if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == '0') continue;
                grid[x][y] = '0';
                for (int[] dir : dirs) stack.push(new int[]{x + dir[0], y + dir[1]});
            }
        }


        public int numIslandsDSU(char[][] grid) {
            int M = grid.length, N = grid[0].length, count = 0;
            DSU dsu = new DSU(M * N);
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] == '1') {
                        count++;
                        for (int[] dir : dirs) {
                            int x = i + dir[0], y = j + dir[1];
                            if (x >= 0 && x < M && y >= 0 && y < N && grid[x][y] == '1') {
                                if (dsu.find(x * N + y) != dsu.find(i * N + j)) count--;
                                dsu.union(x * N + y, i * N + j);
                            }
                        }
                    }
                }
            }
            return count;
        }

        class DSU {
            int[] parent;

            public DSU(int N) {
                parent = new int[N];
                for (int i = 0; i < N; i++) {
                    parent[i] = i;
                }
            }

            public int find(int x) {
                if (parent[x] != x) find(parent[x]);
                return parent[x];
            }

            public void union(int x, int y) {
                parent[find(x)] = find(y);
            }
        }
    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.g786776e5f7_0_89
    class MaxAreaOfIsland {

        // 套娃题，直接暴力找哪一个group的1最多
        public int maxAreaOfIsland(int[][] grid) {
            int res = 0;
            int[] area = new int[1];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        area[0] = 0;
                        dfs(grid, i, j, area);
                        res = Math.max(area[0], res);
                    }
                }
            }
            return res;
        }

        private void dfs(int[][] grid, int x, int y, int[] area) {
            if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 1) {
                grid[x][y] = 0;  // 起點設為0，下面上下左右繼續找1
                area[0]++;  // 面積加上1
                dfs(grid, x - 1, y, area);
                dfs(grid, x + 1, y, area);
                dfs(grid, x, y - 1, area);
                dfs(grid, x, y + 1, area);
            }
        }
    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.gb0e0a254a1_0_26
    class IslandPerimeter {
        public int islandPerimeter(int[][] grid) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        return dfs(grid, i, j);
                    }
                }
            }
            return 0;
        }

        private int dfs(int[][] grid, int i, int j) {
            // 碰到邊界或水域，就算是一條邊，碰到相鄰的島就繼續走
            if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0) return 1;
            if (grid[i][j] == -1) return 0;  // 已經碰過的島，不重複計算
            grid[i][j] = -1;  // 也可以用 visited set

            // 上下左右暴力搜索其他方向
            return dfs(grid, i + 1, j) + dfs(grid, i - 1, j) + dfs(grid, i, j + 1) + dfs(grid, i, j - 1);
        }

        public int islandPerimeter2(int[][] grid) {
            int res = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {

                    // 每个单独岛可以提供4个边界，有相邻的岛屿每一个少提供一个边界
                    if (grid[i][j] == 1) {
                        res += 4;  // count border
                        if (i < grid.length - 1 && grid[i + 1][j] == 1) res -= 2;  // count down
                        if (j < grid[i].length - 1 && grid[i][j + 1] == 1) res -= 2;  // count right
                    }
                }
            }
            return res;
        }
    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.g786776e5f7_0_77
    class NumberOfIslandsII {
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        public List<Integer> numIslands2(int m, int n, int[][] positions) {
            DSU dsu = new DSU(m * n);
            boolean[][] island = new boolean[m][n];
            List<Integer> res = new ArrayList<>();
            int count = 0;
            for (int[] cur : positions) {
                if (island[cur[0]][cur[1]]) {
                    res.add(count);
                    continue;
                }
                island[cur[0]][cur[1]] = true;
                count++;

                //  [cur[0]][cur[1]]作為起點，上下左右暴力搜索其他方向
                for (int[] dir : dirs) {
                    int x = cur[0] + dir[0], y = cur[1] + dir[1];  // 上下左右方向的目標
                    if (x < 0 || x >= m || y < 0 || y >= n || island[x][y] == false) continue;  // 出界的或者沒碰過的位置跳過
                    int component1 = dsu.find(cur[0] * n + cur[1]);
                    int component2 = dsu.find(x * n + y);
                    if (component1 != component2) {
                        dsu.union(component1, component2);  // 加進同一個union
                        count--;  // 如果加進同一個union，那就不是一個新的島，之前加上的count--
                    }
                }
                res.add(count);
            }
            return res;
        }

        class DSU {
            int[] parent;

            public DSU(int N) {
                parent = new int[N];
                for (int i = 0; i < N; i++) {
                    parent[i] = i;
                }
            }

            public int find(int x) {
                if (parent[x] != x) parent[x] = find(parent[x]);
                return parent[x];
            }

            public void union(int x, int y) {
                parent[find(x)] = find(y);
            }
        }
    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.g786776e5f7_0_97
    class NumberOfDistinctIslands {

        // 使用字串來標記，判斷shape是否相同
        public int NumberOfDistinctIslands(int[][] grid) {
            Set<String> set = new HashSet<>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] != 0) {
                        StringBuilder sb = new StringBuilder();
                        dfs(grid, i, j, sb, "start#");  // 起點
                        set.add(sb.toString());
                    }
                }
            }
            return set.size();
        }

        private void dfs(int[][] grid, int i, int j, StringBuilder sb, String s) {
            if (i < 0 || i == grid.length || j < 0 || j == grid[i].length || grid[i][j] == 0) return;
            sb.append(s);
            grid[i][j] = 0;
            dfs(grid, i - 1, j, sb, "u");  // up
            dfs(grid, i + 1, j, sb, "d");  // down
            dfs(grid, i, j - 1, sb, "l");  // left
            dfs(grid, i, j + 1, sb, "r");  // right
            sb.append("#end");  // 終點
        }


        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        public int NumberOfDistinctIslands2(int[][] grid) {
            Set<List<List<Integer>>> islands = new HashSet<>();
            int count = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        List<List<Integer>> island = new ArrayList<>();
                        helper(grid, i, j, i, j, island);
                        islands.add(island);
                    }
                }
            }
            return islands.size();
        }

        // 必须用相对位置，因为起点i0, j0已经变化了
        private void helper(int[][] grid, int i, int j, int i0, int j0, List<List<Integer>> island) {
            if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] != 1) return;
            grid[i][j] = -1;
            island.add(Arrays.asList(i - i0, j - j0));  // 把相對位置和起點的座標差值組合放進list，判斷shape是否相同
            for (int[] dir : dirs) helper(grid, i + dir[0], j + dir[1], i0, j0, island);
        }

    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.gb0ac0ba254_0_12
    class NumberOfClosedIslands {
        public int numberOfClosedIslands(int[][] grid) {
            int res = 0, m = grid.length, n = grid[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0 && dfs(grid, i, j, m, n)) res++;
                }
            }
            return res;
        }

        private boolean dfs(int[][] grid, int i, int j, int m, int n) {
            if (i < 0 || j < 0 || i >= m || j >= n) return false;  // 出界狀況false
            if (grid[i][j] == 1) return true;  // 這邊1是水域，0是陸地
            grid[i][j] = 1; // 遍歷過的陸地直接翻成水域，避免重複計算

            // &運算：只要有一個false，最後結果就return false
            return dfs(grid, i - 1, j, m, n) &
                    dfs(grid, i + 1, j, m, n) &
                    dfs(grid, i, j - 1, m, n) &
                    dfs(grid, i, j + 1, m, n);
        }
    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.gb0ac0ba254_0_2
    class MakingIslands {
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        public int largestIsland(int[][] grid) {
            int m = grid.length, n = grid[0].length, res = 1;
            DSU dsu = new DSU(m * n);
            for (int i = 0; i < m; i++) {  // build up DSU, record the largest union
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        for (int[] dir : dirs) {
                            int x = i + dir[0], y = j + dir[1];  // connect<i, j>  <x, y>
                            if (x < 0 || y < 0 || x >= m || y >= n || grid[i][j] != 1) continue;
                            dsu.union(i * n + j, x * n + y);  // 相鄰陸地union成同一島嶼
                            res = Math.max(res, dsu.size[dsu.find(i * n + j)]);  // 記錄最大size
                        }
                    }
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) {  // 找到一個0，把上下左右4個1連接起來
                        Map<Integer, Integer> map = new HashMap<>();
                        for (int[] dir : dirs) {
                            int x = i + dir[0], y = j + dir[1];
                            if (x < 0 || y < 0 || x >= m || y >= n || grid[i][j] != 1) continue;
                            int parent = dsu.find(i * n + j);
                            map.put(parent, dsu.size[parent]);
                        }
                        // 以當前0做起點，把周圍一格的union size相加，再加上當前0轉換成1
                        res = Math.max(res, map.values().stream().mapToInt(x -> x).sum() + 1);
                    }
                }
            }
            return res;
        }

        class DSU {
            int[] parent;
            int[] size;

            public DSU(int N) {
                this.parent = new int[N];
                this.size = new int[N];
                Arrays.fill(size, 1);
                for (int i = 0; i < N; i++) {
                    parent[i] = i;
                }
            }

            public int find(int x) {
                if (parent[x] != x) parent[x] = find(parent[x]);
                return parent[x];
            }

            public void union(int x, int y) {
                int rootX = find(x), rootY = find(y);
                if (rootX == rootY) return;
                if (size[rootX] < size[rootY]) {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                } else {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                }
            }
        }
    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.gb0ac0ba254_0_23
    class ShortestBridge {
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Queue<int[]> q = new LinkedList<>();

        public int shortestBridge(int[][] A) {
            int m = A.length, n = A[0].length;
            boolean[][] visited = new boolean[m][n];
            boolean found = false;

            // 1. 第一座島標為visited並放入queue
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (!found && A[i][j] == 1) {
                        dfs(A, visited, i, j);
                        found = true;
                        break;
                    }
                }
            }

            // 2. 從起點q中BFS，一層一層擴展自己，直到找到下一座島嶼1，擴展層數就是步數
            int step = 0;
            while (!q.isEmpty()) {
                int size = q.size();  // 前一輪加進queue的位置，下面暴力搜尋上下左右位置
                for (int x = 0; x < size; x++) {
                    int[] cur = q.poll();
                    for (int[] dir : dirs) {
                        int i = cur[0] + dir[0], j = cur[1] + dir[1];

                        // 如果範圍內碰到合法的島嶼
                        if (i >= 0 && i < m && j >= 0 && j < n && !visited[i][j]) {
                            if (A[i][j] == 1) return step;  // 找到下一座島嶼1，step就是步數
                            q.offer(new int[]{i, j});  // 否則當前位置標記為visited，並且放進queue
                            visited[i][j] = true;
                        }
                    }
                }
                step++;  // 每一輪loop結束，步數+1
            }
            return -1;
        }

        private void dfs(int[][] A, boolean[][] visited, int i, int j) {
            if (i < 0 || j < 0 || i >= A.length || j >= A[0].length
                    || visited[i][j] || A[i][j] == 0) {
                return;
            }
            visited[i][j] = true;
            q.offer(new int[]{i, j});
            for (int[] dir : dirs) dfs(A, visited, i + dir[0], j + dir[1]);
        }
    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.gb0e0a254a1_0_0
    class Tarjan {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> timeMap = new HashMap<>();
        boolean foundCriticalEdge;
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int root = -1, time = 0, count = 0;

        public int minDays(int[][] grid) {
            if (numOfIsland(grid) != 1) return 0;
            if (count == 1) return 1;
            if (count == 2) return 2;
            buildGraph(grid);
            tarjan(-1, root, 0, new HashSet<>());  // (int parent, int cur, int time, HashSet<Integer> visited)
            return foundCriticalEdge ? 1 : 2;
        }

        private void tarjan(int parent, int cur, int time, HashSet<Integer> visited) {
            visited.add(cur);
            timeMap.put(cur, time);
            for (int nei : graph.get(cur)) {
                if (nei == parent) continue;
                if (!visited.contains(nei)) tarjan(cur, nei, time + 1, visited);
                if (time < timeMap.get(nei)) foundCriticalEdge = true;
                timeMap.put(cur, Math.min(timeMap.get(cur), timeMap.get(nei)));
            }
        }

        private void buildGraph(int[][] grid) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        for (int[] dir : dirs) {
                            mark(grid, i, j, i + dir[0], j + dir[1]);
                        }
                    }
                }
            }
        }

        private void mark(int[][] grid, int preX, int preY, int x, int y) {
            if (x < 0 || y < 0 || x == grid.length || y == grid[0].length || grid[x][y] == 0) return;
            int n1 = preX * grid[0].length + preY;
            int n2 = x * grid[0].length + y;
            graph.computeIfAbsent(n1, v -> new ArrayList<>()).add(n2);
            graph.computeIfAbsent(n2, v -> new ArrayList<>()).add(n1);
        }

        private int numOfIsland(int[][] grid) {
            int res = 0;
            boolean[][] visited = new boolean[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (!visited[i][j] && grid[i][j] == 1) {
                        if (root == -1) root = i * grid[0].length + j;
                        res++;
                        dfs(visited, grid, i, j);
                    }
                }
            }
            return res;
        }

        private void dfs(boolean[][] visited, int[][] grid, int i, int j) {
            if (i < 0 || j < 0 || i == grid.length || j == grid[0].length || visited[i][j] || grid[i][j] == 0) return;
            count++;
            visited[i][j] = true;
            for (int[] dir : dirs) dfs(visited, grid, i + dir[0], j + dir[1]);
        }
    }


    // https://docs.google.com/presentation/d/1Lq97BhyTndEplyYuTQobiqdDKqGKAcgJlrymSnHuBVQ/edit#slide=id.gb0e0a254a1_0_49
    class NumberOfDistinctIslandsII {
        int[][] grid;
        boolean[][] visited;
        List<Integer> shape;
        int M, N;

        public int numberOfDistinctIslandsII(int[][] grid) {
            this.grid = grid;
            M = grid.length;
            N = grid[0].length;
            visited = new boolean[M][N];
            Set<String> shapes = new HashSet<>();

            for (int r = 0; r < M; r++) {
                for (int c = 0; c < N; c++) {
                    shape = new ArrayList<>();
                    explore(r, c);
                    if (!shape.isEmpty()) shapes.add(canonical(shape));
                }
            }
            return shapes.size();
        }


        public String canonical(List<Integer> shape) {
            String res = "";
            int[] out = new int[shape.size()];
            int[] xs = new int[shape.size()];
            int[] ys = new int[shape.size()];

            for (int c = 0; c < 8; c++) {
                int t = 0;
                for (int z : shape) {
                    int x = z / N;
                    int y = z % N;
                    xs[t] = c <= 1 ? x : c <= 3 ? -x : c <= 5 ? y : -y;
                    ys[t++] = c <= 3 ? (c % 2 == 0 ? y : -y) : (c % 2 == 0 ? x : -x);
                }
                int mx = xs[0], my = ys[0];
                for (int x : xs) mx = Math.max(mx, x);
                for (int y : ys) my = Math.max(my, y);

                for (int j = 0; j < shape.size(); j++) {
                    out[j] = (xs[j] - mx) * N + (ys[j] - my);
                }

                Arrays.sort(out);
                String candidate = Arrays.toString(out);
                if (res.compareTo(candidate) < 0) res = candidate;
            }

            return res;
        }

        private void explore(int r, int c) {
            if (r < 0 || c < 0 || r >= M || c >= N) return;
            if (grid[r][c] != 1 || visited[r][c]) return;
            visited[r][c] = true;
            shape.add(r * N + c);
            explore(r + 1, c);
            explore(r - 1, c);
            explore(r, c + 1);
            explore(r, c - 1);
        }

    }
}
