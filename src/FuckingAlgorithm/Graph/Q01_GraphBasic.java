package FuckingAlgorithm.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Q01_GraphBasic {
    class Graph {
        int val;
        int[][] neighbors;

    }

    /* 圖節點的邏輯結構 */
    class Vertex {
        int id;
        Vertex[] neighbors;
    }

    /* 基本的 N 叉樹節點 */
    class TreeNode {
        int val;
        TreeNode[] children;
    }


    // 記錄被遍歷過的節點
    boolean[] visited;
    // 記錄從起點到當前節點的路徑
    boolean[] onPath;

    /* 圖遍歷框架 */
    void traverse(Graph graph, int s) {
        if (visited[s]) return;
        // 經過節點 s，標記為已遍歷
        visited[s] = true;
        // 做選擇：標記節點 s 在路徑上
        onPath[s] = true;
        for (int neighbor : graph.neighbors[s]) {
            traverse(graph, neighbor);
        }
        // 撤銷選擇：節點 s 離開路徑
        onPath[s] = false;
    }


    // DFS 算法，關注點在節點
    void traverse(TreeNode root) {
        if (root == null) return;
//        System.out.println("進入節點 %s", root);
        for (TreeNode child : root.children) {
            traverse(child);
        }
//        System.out.println("離開節點 %s", root);
    }

    // 回溯算法，關注點在樹枝
    void backtrack(TreeNode root) {
        if (root == null) return;
        for (TreeNode child : root.children) {
            // 做選擇
//            System.out.println("從 %s 到 %s", root, child);
            backtrack(child);
            // 撤銷選擇
//            System.out.println("從 %s 到 %s", child, root);
        }
    }

//    https://leetcode.cn/problems/all-paths-from-source-to-target/
//    797. 所有可能的路徑
//    給你一個有 n 個節點的 有向無環圖（DAG），請你找出所有從節點 0 到節點 n-1 的路徑並輸出（不要求按特定順序）
//
//    graph[i] 是一個從節點 i 可以訪問的所有節點的列表（即從節點 i 到節點 graph[i][j]存在一條有向邊）。

    // 記錄所有路徑
    List<List<Integer>> res = new LinkedList<>();

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        // 維護遞歸過程中經過的路徑
        LinkedList<Integer> path = new LinkedList<>();
        traverse(graph, 0, path);
        return res;
    }

    /* 圖的遍歷框架 */
    void traverse(int[][] graph, int s, LinkedList<Integer> path) {
        // 添加節點 s 到路徑
        path.addLast(s);

        int n = graph.length;
        if (s == n - 1) {
            // 到達終點
            res.add(new LinkedList<>(path));
            // 可以在這直接 return，但要 removeLast 正確維護 path
            // path.removeLast();
            // return;
            // 不 return 也可以，因為圖中不包含環，不會出現無限遞歸
        }

        // 遞歸每個相鄰節點
        for (int v : graph[s]) {
            traverse(graph, v, path);
        }

        // 從路徑移出節點 s
        path.removeLast();
    }


//    https://leetcode.cn/problems/clone-graph/?show=1
//    133. 克隆圖
//    給你無向 連通 圖中一個節點的引用，請你返回該圖的 深拷貝（克隆）。
//
//    圖中的每個節點都包含它的值 val（int） 和其鄰居的列表（list[Node]）。
//
//    class Node {
//        public int val;
//        public List<Node> neighbors;
//    }
//
//
//    測試用例格式：
//
//    簡單起見，每個節點的值都和它的索引相同。例如，第一個節點值為 1（val = 1），第二個節點值為 2（val = 2），
//    以此類推。該圖在測試用例中使用鄰接列表表示。
//
//    鄰接列表 是用於表示有限圖的無序列表的集合。每個列表都描述了圖中節點的鄰居集。
//
//    給定節點將始終是圖中的第一個節點（值為 1）。你必須將 給定節點的拷貝 作為對克隆圖的引用返回。

    class cloneGraph {
        class Node {
            public int val;
            public List<Node> neighbors;

            public Node(int val) {
                this.val = val;
            }
        }

        // 記錄 DFS 遍歷過的節點，防止走回頭路
        HashSet<Node> visited = new HashSet<>();
        // 記錄原節點到克隆節點的映射
        HashMap<Node, Node> originToClone = new HashMap<>();

        public Node cloneGraph(Node node) {
            // DFS 遍歷圖，順便構建克隆圖
            traverse(node);
            // 從 map 裡找到克隆圖的對應節點
            return originToClone.get(node);
        }

        // DFS 圖遍歷框架
        void traverse(Node node) {
            if (node == null || visited.contains(node)) return;

            // 前序位置，標記為已訪問
            visited.add(node);
            // 前序位置，克隆節點
            if (!originToClone.containsKey(node)) {
                originToClone.put(node, new Node(node.val));
            }

            Node cloneNode = originToClone.get(node);

            // 遞歸遍歷鄰居節點，並構建克隆圖
            for (Node neighbor : node.neighbors) {
                traverse(neighbor);
                // 遞歸之後，鄰居節點一定存在 originToClone 中
                Node cloneNeighbor = originToClone.get(neighbor);
                cloneNode.neighbors.add(cloneNeighbor);
            }

        }
    }

    //    https://leetcode.cn/problems/number-of-islands/?show=1
//    200. 島嶼數量
//    給你一個由 '1'（陸地）和 '0'（水）組成的的二維網格，請你計算網格中島嶼的數量。
//
//    島嶼總是被水包圍，並且每座島嶼只能由水平方向和/或豎直方向上相鄰的陸地連接形成。
//
//    此外，你可以假設該網格的四條邊均被水包圍。


    // 主函數，計算島嶼數量
    public int numIslands(char[][] grid) {
        int res = 0;
        int m = grid.length, n = grid[0].length;
        // 遍歷 grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    // 每發現一個島嶼，島嶼數量加一
                    res++;
                    // 然後使用 DFS 將島嶼淹了(上下左右方向)
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }

    private void dfs(char[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引邊界
            return;
        }
        if (grid[i][j] == '0') {
            // 已經是海水了
            return;
        }

        // 將 (i, j) 變成海水
        // 因為 dfs 函數遍歷到值為 0 的位置會直接返回，
        // 所以只要把經過的位置都設置為 0，就可以起到不走回頭路的作用。
        grid[i][j] = '0';

        // 淹沒上下左右的陸地
        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);
    }


//    https://leetcode.cn/problems/count-nodes-with-the-highest-score/?show=1
//    2049. 統計最高分的節點數目
//    給你一棵根節點為 0 的 二叉樹 ，它總共有 n 個節點，節點編號為 0 到 n - 1 。
//    同時給你一個下標從 0 開始的整數數組 parents 表示這棵樹，其中 parents[i] 是節點 i 的父節點。
//    由於節點 0 是根，所以 parents[0] == -1 。
//
//    一個子樹的 大小 為這個子樹內節點的數目。每個節點都有一個與之關聯的 分數 。
//    求出某個節點分數的方法是，將這個節點和與它相連的邊全部 刪除 ，
//    剩余部分是若干個 非空 子樹，這個節點的 分數 為所有這些子樹 大小的乘積 。
//
//    請你返回有 最高得分 節點的 數目 。


    // 用鄰接表表示的一棵二叉樹
    int[][] tree;
    HashMap<Long, Integer> scoreToCount = new HashMap<>();

    public int countHighestScoreNodes(int[] parents) {
        tree = buildTree(parents);
        countNode(0);
        // 計算最大分數出現的次數
        long maxScore = 0;
        for (long score : scoreToCount.keySet()) {
            maxScore = Math.max(maxScore, score);
        }
        return scoreToCount.get(maxScore);
    }

    // 將 parents 數組轉化成常規二叉樹（鄰接表形式）
    int[][] buildTree(int[] parents) {
        int n = parents.length;

        // 表節點 x 的左子節點為 tree[x][0]，節點 x 的右子節點為 tree[x][1]
        // 若 tree[x][0] 或 tree[x][1] 等於 -1 則代表空指針
        int[][] tree = new int[n][2];
        for (int i = 0; i < n; i++) {
            // 先都初始化成空指針
            tree[i][0] = tree[i][1] = -1;
        }

        // 根據 parents 數組構建二叉樹（跳過 parents[0] 根節點）
        for (int i = 1; i < n; i++) {
            int p = parents[i];
            if (tree[p][0] == -1) {
                tree[p][0] = i;
            } else {
                tree[p][1] = i;
            }
        }
        return tree;
    }

    // 計算二叉樹中的節點個數
    int countNode(int root) {
        if (root == -1) {
            return 0;
        }

        // 二叉樹中節點總數
        int n = tree.length;
        int leftCount = countNode(tree[root][0]);
        int rightCount = countNode(tree[root][1]);

        // 後序位置，計算每個節點的「分數」
        int otherCount = n - leftCount - rightCount - 1;
        // 注意，這裡要把 int 轉化成 long，否則會產生溢出！！！
        long score = (long) Math.max(leftCount, 1)
                * Math.max(rightCount, 1) * Math.max(otherCount, 1);
        // 給分數 score 計數
        scoreToCount.put(score, scoreToCount.getOrDefault(score, 0) + 1);

        return leftCount + rightCount + 1;
    }


}
