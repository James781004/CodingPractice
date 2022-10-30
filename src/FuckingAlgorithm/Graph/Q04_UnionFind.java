package FuckingAlgorithm.Graph;

import java.util.Stack;

public class Q04_UnionFind {

    class UF {
        // 連通分量個數
        private int count;
        // 存儲每個節點的父節點
        private int[] parent;

        // n 為圖中節點的個數
        public UF(int n) {
            this.count = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }


        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

//        public int find(int x) {
//            while (x != parent[x]) {
//                parent[x] = parent[parent[x]];
//                x = parent[x];
//            }
//            return x;
//        }

        // 返回圖中的連通分量個數
        public int count() {
            return count;
        }

        // 將節點 p 和節點 q 連通
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);

            if (rootP == rootQ)
                return;

            parent[rootQ] = rootP;
            // 兩個連通分量合並成一個連通分量
            count--;
        }

        // 判斷節點 p 和節點 q 是否連通
        public boolean connected(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            return rootP == rootQ;
        }
    }


//    https://zhuanlan.zhihu.com/p/90907170
//    323. 無向圖中連通分量的數目
//    給定編號從 0 到 n-1 的 n 個節點和一個無向邊列表（每條邊都是一對節點），請編寫一個函數來計算無向圖中連通分量的數目。

    public int countComponents(int n, int[][] edges) {
        UF uf = new UF(n);
        for (int[] e : edges) {
            uf.union(e[0], e[1]);
        }
        return uf.count();
    }


    //    https://leetcode.cn/problems/surrounded-regions/
//    130. 被圍繞的區域
//    給你一個 m x n 的矩陣 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 圍繞的區域，並將這些區域裡所有的 'O' 用 'X' 填充。

    class surroundedRegions {

        // Union-Find 算法
        // 把那些不需要被替換的 O (也就是與邊界相連的 O)看成一個擁有獨門絕技的門派，
        // 它們有一個共同「祖師爺」叫 dummy，
        // 這些 O 和 dummy 互相連通，而那些需要被替換的 O 與 dummy 不連通。
        public void solve(char[][] board) {
            if (board.length == 0) return;

            int m = board.length;
            int n = board[0].length;

            // 給 dummy 留一個額外位置
            UF uf = new UF(m * n + 1);
            int dummy = m * n;

            // 將首列和末列的 O 與 dummy 連通
            // 二維坐標 (x,y) 可以轉換成 x * n + y 這個數（m 是棋盤的行數，n 是棋盤的列數），
            // 這是將二維坐標映射到一維的常用技巧。
            for (int i = 0; i < m; i++) {
                if (board[i][0] == 'O') {
                    uf.union(i * n, dummy);
                }
                if (board[i][n - 1] == 'O') {
                    uf.union(i * n + n - 1, dummy);
                }
            }

            // 將首行和末行的 O 與 dummy 連通
            for (int j = 0; j < n; j++) {
                if (board[0][j] == 'O') {
                    uf.union(j, dummy);
                }
                if (board[m - 1][j] == 'O') {
                    uf.union(n * (m - 1) + j, dummy);
                }
            }

            // 方向數組 d 是上下左右搜索的常用手法
            int[][] d = new int[][]{{1, 0}, {0, 1}, {0, -1}, {-1, 0}};
            for (int i = 1; i < m - 1; i++)
                for (int j = 1; j < n - 1; j++)
                    if (board[i][j] == 'O')
                        // 將此 O 與上下左右的 O 連通
                        for (int k = 0; k < 4; k++) {
                            int x = i + d[k][0];
                            int y = j + d[k][1];
                            if (board[x][y] == 'O')
                                uf.union(x * n + y, i * n + j);
                        }
            // 所有不和 dummy 連通的 O，都要被替換
            for (int i = 1; i < m - 1; i++)
                for (int j = 1; j < n - 1; j++)
                    if (!uf.connected(dummy, i * n + j))
                        board[i][j] = 'X';

        }

        // DFS遞歸
        // 先用 for 循環遍歷棋盤的四邊，
        // 用 DFS 算法把那些與邊界相連的 O 換成一個特殊字符，比如 #；
        // 然後再遍歷整個棋盤，把剩下的 O 換成 X，把 # 恢復成 O。
        // 這樣就能完成題目的要求，時間復雜度 O(MN)。
        public void solveDFS(char[][] board) {
            if (board == null || board.length == 0) return;
            int m = board.length;
            int n = board[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // 從邊緣o開始搜索
                    boolean isEdge = i == 0 || j == 0 || i == m - 1 || j == n - 1;
                    if (isEdge && board[i][j] == 'O') {
                        dfs(board, i, j);
                    }
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == 'O') {
                        board[i][j] = 'X';
                    }
                    if (board[i][j] == '#') {
                        board[i][j] = 'O';
                    }
                }
            }
        }

        public void dfs(char[][] board, int i, int j) {
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] == 'X' || board[i][j] == '#') {
                // board[i][j] == '#' 說明已經搜索過了.
                return;
            }
            board[i][j] = '#';
            dfs(board, i - 1, j); // 上
            dfs(board, i + 1, j); // 下
            dfs(board, i, j - 1); // 左
            dfs(board, i, j + 1); // 右
        }


        // DFS 非遞歸:
        public class Pos {
            int i;
            int j;

            Pos(int i, int j) {
                this.i = i;
                this.j = j;
            }
        }

        public void solveDFS2(char[][] board) {
            if (board == null || board.length == 0) return;
            int m = board.length;
            int n = board[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // 從邊緣第一個是o的開始搜索
                    boolean isEdge = i == 0 || j == 0 || i == m - 1 || j == n - 1;
                    if (isEdge && board[i][j] == 'O') {
                        dfs2(board, i, j);
                    }
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == 'O') {
                        board[i][j] = 'X';
                    }
                    if (board[i][j] == '#') {
                        board[i][j] = 'O';
                    }
                }
            }
        }

        public void dfs2(char[][] board, int i, int j) {
            Stack<Pos> stack = new Stack<>();
            stack.push(new Pos(i, j));
            board[i][j] = '#';
            while (!stack.isEmpty()) {
                // 取出當前stack 頂, 不彈出.
                Pos current = stack.peek();
                // 上
                if (current.i - 1 >= 0
                        && board[current.i - 1][current.j] == 'O') {
                    stack.push(new Pos(current.i - 1, current.j));
                    board[current.i - 1][current.j] = '#';
                    continue;
                }
                // 下
                if (current.i + 1 <= board.length - 1
                        && board[current.i + 1][current.j] == 'O') {
                    stack.push(new Pos(current.i + 1, current.j));
                    board[current.i + 1][current.j] = '#';
                    continue;
                }
                // 左
                if (current.j - 1 >= 0
                        && board[current.i][current.j - 1] == 'O') {
                    stack.push(new Pos(current.i, current.j - 1));
                    board[current.i][current.j - 1] = '#';
                    continue;
                }
                // 右
                if (current.j + 1 <= board[0].length - 1
                        && board[current.i][current.j + 1] == 'O') {
                    stack.push(new Pos(current.i, current.j + 1));
                    board[current.i][current.j + 1] = '#';
                    continue;
                }
                // 如果上下左右都搜索不到,本次搜索結束，彈出stack
                stack.pop();
            }
        }

    }


//    https://leetcode.cn/problems/satisfiability-of-equality-equations/
//    990. 等式方程的可滿足性
//    給定一個由表示變量之間關系的字符串方程組成的數組，每個字符串方程 equations[i] 的長度為 4，
//    並采用兩種不同的形式之一："a==b" 或 "a!=b"。在這裡，a 和 b 是小寫字母（不一定不同），表示單字母變量名。
//
//    只有當可以將整數分配給變量名，以便滿足所有給定的方程時才返回 true，否則返回 false。


    // 將 equations 中的算式根據 == 和 != 分成兩部分，先處理 == 算式，
    // 使得他們通過相等關系各自勾結成門派（連通分量）；
    // 然後處理 != 算式，檢查不等關系是否破壞了相等關系的連通性。
    public boolean equationsPossible(String[] equations) {
        // 26 個英文字母
        UF uf = new UF(26);

        // 先讓相等的字母形成連通分量
        for (String eq : equations) {
            if (eq.charAt(1) == '=') {
                char x = eq.charAt(0);
                char y = eq.charAt(3);
                uf.union(x - 'a', y - 'a');
            }
        }

        // 檢查不等關系是否打破相等關系的連通性
        for (String eq : equations) {
            if (eq.charAt(1) == '!') {
                char x = eq.charAt(0);
                char y = eq.charAt(3);
                // 如果相等關系成立，就是邏輯沖突
                if (uf.connected(x - 'a', y - 'a'))
                    return false;
            }
        }
        return true;
    }
}
