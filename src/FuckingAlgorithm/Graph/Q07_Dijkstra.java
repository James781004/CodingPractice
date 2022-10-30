package FuckingAlgorithm.Graph;

import java.util.*;

public class Q07_Dijkstra {
    // https://labuladong.github.io/algo/2/22/56/

    // Dijkstra 算法框架
    class State {
        // 圖節點的 id
        int id;
        // 從 start 節點到當前節點的距離
        int distFromStart;

        State(int id, int distFromStart) {
            this.id = id;
            this.distFromStart = distFromStart;
        }
    }

    class Dijkstra {
        // 返回節點 from 到節點 to 之間的邊的權重
//        int weight(int from, int to);

        // 輸入節點 s 返回 s 的相鄰節點
//        List<Integer> adj(int s);

        // 輸入一幅圖和一個起點 start，計算 start 到其他節點的最短距離
        public int[] dijkstra(int start, List<int[]>[] graph) {
            // 定義：distTo[i] 的值就是起點 start 到達節點 i 的最短路徑權重
            int[] distTo = new int[graph.length];
            Arrays.fill(distTo, Integer.MAX_VALUE);
            // base case，start 到 start 的最短距離就是 0
            distTo[start] = 0;

            // 優先級隊列，distFromStart 較小的排在前面
            Queue<State> pq = new PriorityQueue<>((a, b) -> {
                return a.distFromStart - b.distFromStart;
            });
            // 從起點 start 開始進行 BFS
            pq.offer(new State(start, 0));

            while (!pq.isEmpty()) {
                State curState = pq.poll();
                int curNodeID = curState.id;
                int curDistFromStart = curState.distFromStart;

                if (curDistFromStart > distTo[curNodeID]) {
                    continue;
                }

                // 將 curNode 的相鄰節點裝入隊列
                for (int[] neighbor : graph[curNodeID]) {
                    int nextNodeID = neighbor[0];
                    int distToNextNode = distTo[curNodeID] + neighbor[1];
                    // 更新 dp table
                    if (distTo[nextNodeID] > distToNextNode) {
                        distTo[nextNodeID] = distToNextNode;
                        pq.offer(new State(nextNodeID, distToNextNode));
                    }
                }
            }
            return distTo;
        }
    }


//    https://leetcode.cn/problems/network-delay-time/
//    743. 網絡延遲時間
//    有 n 個網絡節點，標記為 1 到 n。
//
//    給你一個列表 times，表示信號經過 有向 邊的傳遞時間。 times[i] = (ui, vi, wi)，
//    其中 ui 是源節點，vi 是目標節點， wi 是一個信號從源節點傳遞到目標節點的時間。
//
//    現在，從某個節點 K 發出一個信號。需要多久才能使所有節點都收到信號？如果不能使所有節點收到信號，返回 -1 。

    public int networkDelayTime(int[][] times, int n, int k) {
        // 節點編號是從 1 開始的，所以要一個大小為 n + 1 的鄰接表
        List<int[]>[] graph = new LinkedList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new LinkedList<>();
        }

        // 構造圖
        for (int[] edge : times) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];
            graph[from].add(new int[]{to, weight});
        }

        // 啟動 dijkstra 算法計算以節點 k 為起點到其他節點的最短路徑
        int[] distTo = dijkstra(k, graph);

        // 找到最長的那一條最短路徑
        int res = 0;
        for (int i = 1; i < distTo.length; i++) {
            if (distTo[i] == Integer.MAX_VALUE) {
                // 有節點不可達，返回 -1
                return -1;
            }
            res = Math.max(res, distTo[i]);
        }
        return res;
    }

    // 輸入一幅圖和一個起點 start，計算 start 到其他節點的最短距離
    public int[] dijkstra(int start, List<int[]>[] graph) {
        // 定義：distTo[i] 的值就是起點 start 到達節點 i 的最短路徑權重
        int[] distTo = new int[graph.length];
        Arrays.fill(distTo, Integer.MAX_VALUE);
        // base case，start 到 start 的最短距離就是 0
        distTo[start] = 0;

        // 優先級隊列，distFromStart 較小的排在前面
        Queue<State> pq = new PriorityQueue<>((a, b) -> {
            return a.distFromStart - b.distFromStart;
        });
        // 從起點 start 開始進行 BFS
        pq.offer(new State(start, 0));

        while (!pq.isEmpty()) {
            State curState = pq.poll();
            int curNodeID = curState.id;
            int curDistFromStart = curState.distFromStart;

            if (curDistFromStart > distTo[curNodeID]) {
                continue;
            }

            // 將 curNode 的相鄰節點裝入隊列
            for (int[] neighbor : graph[curNodeID]) {
                int nextNodeID = neighbor[0];
                int distToNextNode = distTo[curNodeID] + neighbor[1];
                // 更新 dp table
                if (distTo[nextNodeID] > distToNextNode) {
                    distTo[nextNodeID] = distToNextNode;
                    pq.offer(new State(nextNodeID, distToNextNode));
                }
            }
        }
        return distTo;
    }


    // https://leetcode.cn/problems/path-with-minimum-effort/
//    1631. 最小體力消耗路徑
//    你准備參加一場遠足活動。給你一個二維 rows x columns 的地圖 heights ，
//    其中 heights[row][col] 表示格子 (row, col) 的高度。一開始你在最左上角的格子 (0, 0) ，
//    且你希望去最右下角的格子 (rows-1, columns-1) （注意下標從 0 開始編號）。
//    你每次可以往 上，下，左，右 四個方向之一移動，你想要找到耗費 體力 最小的一條路徑。
//
//    一條路徑耗費的 體力值 是路徑上相鄰格子之間 高度差絕對值 的 最大值 決定的。
//
//    請你返回從左上角走到右下角的最小 體力消耗值 。

    // Dijkstra 算法，計算 (0, 0) 到 (m - 1, n - 1) 的最小體力消耗

    class minimumEffortPath {
        class State {
            // 矩陣中的一個位置
            int x, y;
            // 從起點 (0, 0) 到當前位置的最小體力消耗（距離）
            int effortFromStart;

            State(int x, int y, int effortFromStart) {
                this.x = x;
                this.y = y;
                this.effortFromStart = effortFromStart;
            }
        }

        // Dijkstra 算法，計算 (0, 0) 到 (m - 1, n - 1) 的最小體力消耗
        public int minimumEffortPath(int[][] heights) {
            int m = heights.length, n = heights[0].length;
            // 定義：從 (0, 0) 到 (i, j) 的最小體力消耗是 effortTo[i][j]
            int[][] effortTo = new int[m][n];
            // dp table 初始化為正無窮
            for (int i = 0; i < m; i++) {
                Arrays.fill(effortTo[i], Integer.MAX_VALUE);
            }
            // base case，起點到起點的最小消耗就是 0
            effortTo[0][0] = 0;

            // 優先級隊列，effortFromStart 較小的排在前面
            Queue<State> pq = new PriorityQueue<>((a, b) -> {
                return a.effortFromStart - b.effortFromStart;
            });

            // 從起點 (0, 0) 開始進行 BFS
            pq.offer(new State(0, 0, 0));

            while (!pq.isEmpty()) {
                State curState = pq.poll();
                int curX = curState.x;
                int curY = curState.y;
                int curEffortFromStart = curState.effortFromStart;

                // 到達終點提前結束
                if (curX == m - 1 && curY == n - 1) {
                    return curEffortFromStart;
                }

                if (curEffortFromStart > effortTo[curX][curY]) {
                    continue;
                }
                // 將 (curX, curY) 的相鄰坐標裝入隊列
                for (int[] neighbor : adj(heights, curX, curY)) {
                    int nextX = neighbor[0];
                    int nextY = neighbor[1];
                    // 計算從 (curX, curY) 達到 (nextX, nextY) 的消耗
                    int effortToNextNode = Math.max(
                            effortTo[curX][curY],
                            Math.abs(heights[curX][curY] - heights[nextX][nextY])
                    );
                    // 更新 dp table
                    if (effortTo[nextX][nextY] > effortToNextNode) {
                        effortTo[nextX][nextY] = effortToNextNode;
                        pq.offer(new State(nextX, nextY, effortToNextNode));
                    }
                }
            }
            // 正常情況不會達到這個 return
            return -1;
        }

        // 方向數組，上下左右的坐標偏移量
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        // 返回坐標 (x, y) 的上下左右相鄰坐標
        List<int[]> adj(int[][] matrix, int x, int y) {
            int m = matrix.length, n = matrix[0].length;
            // 存儲相鄰節點
            List<int[]> neighbors = new ArrayList<>();
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                if (nx >= m || nx < 0 || ny >= n || ny < 0) {
                    // 索引越界
                    continue;
                }
                neighbors.add(new int[]{nx, ny});
            }
            return neighbors;
        }
    }


//    https://leetcode.cn/problems/path-with-maximum-probability/
//    1514. 概率最大的路徑
//    給你一個由 n 個節點（下標從 0 開始）組成的無向加權圖，該圖由一個描述邊的列表組成，
//    其中 edges[i] = [a, b] 表示連接節點 a 和 b 的一條無向邊，且該邊遍歷成功的概率為 succProb[i] 。
//
//    指定兩個節點分別作為起點 start 和終點 end ，請你找出從起點到終點成功概率最大的路徑，並返回其成功概率。
//
//    如果不存在從 start 到 end 的路徑，請 返回 0 。只要答案與標准答案的誤差不超過 1e-5 ，就會被視作正確答案。

    class maxProbability {
        class State {
            // 圖節點的 id
            int id;
            // 從 start 節點到當前節點的距離
            double distFromStart;

            State(int id, double distFromStart) {
                this.id = id;
                this.distFromStart = distFromStart;
            }
        }

        public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
            List<double[]>[] graph = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new LinkedList<>();
            }
            // 構造無向圖
            for (int i = 0; i < edges.length; i++) {
                int from = edges[i][0];
                int to = edges[i][1];
                double weight = succProb[i];
                // 無向圖其實就是雙向圖
                graph[from].add(new double[]{(double) to, weight});
                graph[to].add(new double[]{(double) from, weight});
            }


            return dijkstra(start, end, graph);
        }


        double dijkstra(int start, int end, List<double[]>[] graph) {
            // 圖中節點的個數
            int V = graph.length;
            // 記錄最短路徑的權重，你可以理解為 dp table
            // 定義：distTo[i] 的值就是節點 start 到達節點 i 的最短路徑權重
            double[] distTo = new double[V];
            // dp table 初始化為正無窮
            Arrays.fill(distTo, -1);
            // base case，start 到 start 的最短距離就是 0
            distTo[start] = 1;

            // 優先級隊列，distFromStart 較小的排在前面
            Queue<State> pq = new PriorityQueue<>((a, b) -> {
                return Double.compare(b.distFromStart, a.distFromStart);
            });
            // 從起點 start 開始進行 BFS
            pq.offer(new State(start, 1));

            while (!pq.isEmpty()) {
                State curState = pq.poll();
                int curNodeID = curState.id;
                double curDistFromStart = curState.distFromStart;

                // 在這裡加一個判斷就行了，其他代碼不用改
                if (curNodeID == end) {
                    return curDistFromStart;
                }

                if (curDistFromStart < distTo[curNodeID]) {
                    // 已經有一條更短的路徑到達 curNode 節點了
                    continue;
                }
                // 將 curNode 的相鄰節點裝入隊列
                for (double[] neighbor : graph[curNodeID]) {
                    int nextNodeID = (int) neighbor[0];
                    // 看看從 curNode 達到 nextNode 的距離是否會更短
                    double distToNextNode = distTo[curNodeID] * neighbor[1];
                    if (distTo[nextNodeID] < distToNextNode) {
                        // 更新 dp table
                        distTo[nextNodeID] = distToNextNode;
                        // 將這個節點以及距離放入隊列
                        pq.offer(new State(nextNodeID, distToNextNode));
                    }
                }
            }
            return 0.0;
        }
    }
    

}
