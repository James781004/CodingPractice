package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.*;

public class Q04_Traversal {
    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_0_121
    class levelOrder {
        public List<List<Integer>> levelOrderBFS(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            Queue<TreeNode> q = new LinkedList<>();
            if (root != null) q.offer(root);
            while (!q.isEmpty()) {
                int size = q.size();
                List<Integer> level = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    TreeNode cur = q.poll();
                    level.add(cur.val);
                    if (cur.left != null) q.offer(cur.left);
                    if (cur.right != null) q.offer(cur.right);
                }
                res.add(level);
            }
            return res;
        }

        public List<List<Integer>> levelOrderDFS(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            dfs(root, res, 0);
            return res;
        }

        private void dfs(TreeNode root, List<List<Integer>> res, int height) {
            if (root == null) return;
            if (height == res.size()) res.add(new ArrayList<>());
            res.get(height).add(root.val);
            if (root.left != null) dfs(root.left, res, height + 1);
            if (root.right != null) dfs(root.right, res, height + 1);
        }
    }


    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_0_127
    class NumberOfIslands {
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        public int numIslands(char[][] grid) {
            if (grid.length == 0) return 0;
            int M = grid.length, N = grid[0].length, res = 0;
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] == '1') {
                        res++;
                        bfs(grid, i, j);
//                        dfs(grid, i, j);
                    }
                }
            }
            return res;
        }

        private void bfs(char[][] grid, int row, int col) {
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{row, col});
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int x = cur[0], y = cur[1];
                if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == '0') continue;
                grid[x][y] = '0';
                for (int[] dir : dirs) q.add(new int[]{x + dir[0], y + dir[1]});
            }
        }

        private void dfs(char[][] grid, int row, int col) {
            if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == '0') return;
            grid[row][col] = '0';
            dfs(grid, row - 1, col);
            dfs(grid, row + 1, col);
            dfs(grid, row, col - 1);
            dfs(grid, row, col + 1);
        }


        public int numIslandsDSU(char[][] grid) {
            if (grid.length == 0) return 0;
            int M = grid.length, N = grid[0].length, count = 0;
            DSU dsu = new DSU(M * N);
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] == '1') {
                        count++;
                        for (int[] dir : dirs) {
                            int x = i + dir[0], y = j + dir[1];
                            if (x >= 0 && x < M && y >= 0 && y < N && grid[x][y] == '1') {
                                // 相鄰本應是同一個union，但是這邊發現不同，表示尚未連接
                                // 這邊進行union然後count--
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
                if (parent[x] != x) parent[x] = find(parent[x]);
                return parent[x];
            }

            public void union(int x, int y) {
                parent[find(x)] = find(y);
            }
        }
    }


    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_0_135
    class Maze {
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        public boolean hasPathBFS(int[][] maze, int[] start, int[] destination) {
            int M = maze.length, N = maze[0].length;
            boolean[][] visited = new boolean[M][N];
            Queue<int[]> q = new LinkedList<>();
            q.add(start);
            visited[start[0]][start[1]] = true;
            return bfs(maze, visited, q, destination);
        }

        private boolean bfs(int[][] maze, boolean[][] visited, Queue<int[]> q, int[] destination) {
            int M = maze.length, N = maze[0].length;
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                if (cur[0] == destination[0] && cur[1] == destination[1]) return true;
                for (int[] dir : dirs) {
                    int x = cur[0], y = cur[1];
                    while (x + dir[0] >= 0 && x + dir[0] < M &&
                            y + dir[1] >= 0 && y + dir[1] < N &&
                            maze[x + dir[0]][y + dir[1]] == 0) {  // 同一個方向先走到底，直到不能走為止
                        x += dir[0];
                        y += dir[1];
                    }
                    if (!visited[x][y]) {  // 前面走到底的點(x, y)如果之前沒有走過，就加進queue作為下一個起點
                        q.add(new int[]{x, y});
                        visited[x][y] = true;
                    }
                }
            }
            return false;
        }


        public boolean hasPathDFS(int[][] maze, int[] start, int[] destination) {
            int M = maze.length, N = maze[0].length;
            boolean[][] visited = new boolean[M][N];
            return dfs(maze, visited, start, destination);
        }

        private boolean dfs(int[][] maze, boolean[][] visited, int[] cur, int[] destination) {
            int M = maze.length, N = maze[0].length;
            if (visited[cur[0]][cur[1]]) return false;
            if (cur[0] == destination[0] && cur[1] == destination[1]) return true;
            visited[cur[0]][cur[1]] = true;
            for (int[] dir : dirs) {
                int x = cur[0], y = cur[1];
                while (x + dir[0] >= 0 && x + dir[0] < M &&
                        y + dir[1] >= 0 && y + dir[1] < N &&
                        maze[x + dir[0]][y + dir[1]] == 0) {  // 同一個方向先走到底，直到不能走為止
                    x += dir[0];
                    y += dir[1];
                }

                // 前面走到底的點(x, y)如果之前沒有走過，就可以作為下一個遞歸起點
                if (dfs(maze, visited, new int[]{x, y}, destination)) return true;
            }
            return false;
        }
    }


    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_0_141
    class SerializeDeserializeBinaryTree {
        public String serialize(TreeNode root) {
            if (root == null) return "#";
            return root.val + "," + serialize(root.left) + "," + serialize(root.right);
        }

        public TreeNode deserialize(String data) {
            Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
            return helper(q);
        }

        private TreeNode helper(Queue<String> q) {
            String s = q.poll();
            if (s.equals("#")) return null;
            TreeNode root = new TreeNode(Integer.valueOf(s));
            root.left = helper(q);
            root.right = helper(q);
            return root;
        }
    }


    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_0_150
    class BinaryTreeMaximumPathSum {
        int maxvalue = Integer.MIN_VALUE;

        public int maxPathSum(TreeNode root) {
            helper(root);
            return maxvalue;
        }

        // 樹形dp: 子樹傳遞資料給上層使用
        private int helper(TreeNode node) {
            if (node == null) return 0;
            int left = Math.max(0, helper(node.left));
            int right = Math.max(0, helper(node.right));
            maxvalue = Math.max(maxvalue, left + right + node.val);
            return Math.max(left, right) + node.val;
        }
    }


    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_0_159
    class NestedListWeightSum {
        public int depthSumBFS(List<NestedInteger> nestedList) {
            Queue<NestedInteger> q = new LinkedList<>(nestedList);
            int res = 0, level = 1;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    NestedInteger temp = q.poll();
                    if (temp.isInteger()) res += temp.getInteger() * level;  // 如果當前是數字，就直接乘上level加進res
                    else q.addAll(temp.getList());  // 否則把getList()的元素全部加進queue，下一層繼續處理
                }
                level++;
            }
            return res;
        }


        public int depthSumDFS(List<NestedInteger> nestedList) {
            return dfs(nestedList, 1);
        }

        private int dfs(List<NestedInteger> nestedList, int level) {
            int res = 0;
            for (NestedInteger unit : nestedList) {
                if (unit.isInteger()) res += unit.getInteger() * level;  // 如果當前是數字，就直接乘上level加進res
                else res += dfs(unit.getList(), level + 1);  // 否則把getList()的元素當作nestedList，下一層遞歸繼續處理
            }
            return res;
        }
    }


    class NestedListWeightSumII {
        public int depthSumDFS(List<NestedInteger> nestedList) {
            int maxDepth = findMaxDepth(nestedList);
            return weightedSum(nestedList, 1, maxDepth);
        }

        private int findMaxDepth(List<NestedInteger> nestedList) {
            int max = 1;
            for (NestedInteger unit : nestedList) {
                if (!unit.isInteger()) {
                    max = Math.max(max, 1 + findMaxDepth(unit.getList()));
                }
            }
            return max;
        }

        private int weightedSum(List<NestedInteger> nestedList, int depth, int maxDepth) {
            int res = 0;
            for (NestedInteger unit : nestedList) {
                if (unit.isInteger()) res += unit.getInteger() * (maxDepth - depth + 1);
                else res += weightedSum(unit.getList(), depth + 1, maxDepth);
//                if (unit.isInteger()) res += unit.getInteger() * maxDepth;
//                else res += weightedSum(unit.getList(), maxDepth - 1);  // 其實maxDepth-1就好，不用多帶depth參數
            }
            return res;
        }

        public int depthSumBFS(List<NestedInteger> nestedList) {
            int sum = 0, levelSum = 0;
            Queue<NestedInteger> q = new LinkedList<>();
            q.addAll(nestedList);
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    NestedInteger ni = q.poll();
                    if (ni.isInteger()) levelSum += ni.getInteger();
                    else q.addAll(ni.getList());
                }
                sum += levelSum; // 最外層多次加進sum，相當於上面DFS的unit.getInteger() * (maxDepth - depth + 1)
            }
            return sum;
        }
    }

    class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int v) {
            val = v;
        }
    }


    class NestedInteger {
        private List<NestedInteger> list;
        private Integer integer;

        public NestedInteger(List<NestedInteger> list) {
            this.list = list;
        }

        public void add(NestedInteger nestedInteger) {
            if (this.list != null) {
                this.list.add(nestedInteger);
            } else {
                this.list = new ArrayList();
                this.list.add(nestedInteger);
            }
        }

        public void setInteger(int num) {
            this.integer = num;
        }

        public NestedInteger(Integer integer) {
            this.integer = integer;
        }

        public NestedInteger() {
            this.list = new ArrayList();
        }

        public boolean isInteger() {
            return integer != null;
        }

        public Integer getInteger() {
            return integer;
        }

        public List<NestedInteger> getList() {
            return list;
        }

        public String printNi(NestedInteger thisNi, StringBuilder sb) {
            if (thisNi.isInteger()) {
                sb.append(thisNi.integer);
                sb.append(",");
            }
            sb.append("[");
            for (NestedInteger ni : thisNi.list) {
                if (ni.isInteger()) {
                    sb.append(ni.integer);
                    sb.append(",");
                } else {
                    printNi(ni, sb);
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

}
