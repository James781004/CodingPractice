package FuckingAlgorithm.DP;

import java.util.*;

public class Q13_FindCheapestPrice {
//    https://leetcode.cn/problems/cheapest-flights-within-k-stops/
//    787. K 站中轉內最便宜的航班
//    有 n 個城市通過一些航班連接。給你一個數組 flights ，
//    其中 flights[i] = [fromi, toi, pricei] ，表示該航班都從城市 fromi 開始，以價格 pricei 抵達 toi。
//
//    現在給定所有的城市和航班，以及出發城市 src 和目的地 dst，你的任務是找到出一條最多經過 k 站中轉的路線，
//    使得從 src 到 dst 的 價格最便宜 ，並返回該價格。 如果不存在這樣的路線，則輸出 -1。

    HashMap<Integer, List<int[]>> indegree;
    int src, dst;
    // 備忘錄
    int[][] memo;


    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        // 將中轉站個數轉化成邊的條數
        K++;
        this.src = src;
        this.dst = dst;
        // 初始化備忘錄，全部填一個特殊值
        memo = new int[n][K + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -888);
        }

        indegree = new HashMap<>();
        for (int[] f : flights) {
            int from = f[0];
            int to = f[1];
            int price = f[2];
            // 記錄誰指向該節點，以及之間的權重
            indegree.putIfAbsent(to, new LinkedList<>());
            indegree.get(to).add(new int[]{from, price});
        }

        return process(dst, K);
    }

    // 定義：從 src 出發，k 步之內到達 s 的最短路徑權重
    private int process(int s, int k) {
        // base case
        if (s == src) {
            return 0;
        }
        if (k == 0) {
            return -1;
        }
        // 查備忘錄，防止冗余計算
        if (memo[s][k] != -888) {
            return memo[s][k];
        }

        // 初始化為最大值，方便等會取最小值
        int res = Integer.MAX_VALUE;
        if (indegree.containsKey(s)) {
            // 當 s 有入度節點時，分解為子問題
            for (int[] v : indegree.get(s)) {
                int from = v[0];
                int price = v[1];
                // 從 src 到達相鄰的入度節點所需的最短路徑權重
                int subProblem = process(from, k - 1);


                // 跳過無解的情況
                if (subProblem != -1) {
                    res = Math.min(res, subProblem + price);
                }
            }
        }
        // 存入備忘錄
        memo[s][k] = res == Integer.MAX_VALUE ? -1 : res;
        return memo[s][k];
    }


    // Dijkstra 算法
    public int findCheapestPrice2(int n, int[][] flights, int src, int dst, int K) {
        List<int[]>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] edge : flights) {
            int from = edge[0];
            int to = edge[1];
            int price = edge[2];
            graph[from].add(new int[]{to, price});
        }

        // 啟動 dijkstra 算法
        // 計算以 src 為起點在 k 次中轉到達 dst 的最短路徑
        K++;
        return dijkstra(graph, src, K, dst);
    }

    class State {
        // 圖節點的 id
        int id;
        // 從 src 節點到當前節點的花費
        int costFromSrc;
        // 從 src 節點到當前節點經過的節點個數
        int nodeNumFromSrc;

        State(int id, int costFromSrc, int nodeNumFromSrc) {
            this.id = id;
            this.costFromSrc = costFromSrc;
            this.nodeNumFromSrc = nodeNumFromSrc;
        }
    }

    // 輸入一個起點 src，計算從 src 到其他節點的最短距離
    int dijkstra(List<int[]>[] graph, int src, int k, int dst) {
        // 定義：從起點 src 到達節點 i 的最短路徑權重為 distTo[i]
        int[] distTo = new int[graph.length];
        // 定義：從起點 src 到達節點 i 的最小權重路徑至少要經過 nodeNumTo[i] 個節點
        int[] nodeNumTo = new int[graph.length];
        Arrays.fill(distTo, Integer.MAX_VALUE);
        Arrays.fill(nodeNumTo, Integer.MAX_VALUE);
        // base case
        distTo[src] = 0;
        nodeNumTo[src] = 0;

        // 優先級隊列，costFromSrc 較小的排在前面
        Queue<State> pq = new PriorityQueue<>((a, b) -> {
            return a.costFromSrc - b.costFromSrc;
        });
        // 從起點 src 開始進行 BFS
        pq.offer(new State(src, 0, 0));

        while (!pq.isEmpty()) {
            State curState = pq.poll();
            int curNodeID = curState.id;
            int costFromSrc = curState.costFromSrc;
            int curNodeNumFromSrc = curState.nodeNumFromSrc;

            if (curNodeID == dst) {
                // 找到最短路徑
                return costFromSrc;
            }
            if (curNodeNumFromSrc == k) {
                // 中轉次數耗盡
                continue;
            }

            // 將 curNode 的相鄰節點裝入隊列
            for (int[] neighbor : graph[curNodeID]) {
                int nextNodeID = neighbor[0];
                int costToNextNode = costFromSrc + neighbor[1];
                // 中轉次數消耗 1
                int nextNodeNumFromSrc = curNodeNumFromSrc + 1;

                // 更新 dp table
                if (distTo[nextNodeID] > costToNextNode) {
                    distTo[nextNodeID] = costToNextNode;
                    nodeNumTo[nextNodeID] = nextNodeNumFromSrc;
                }
                // 剪枝，如果中轉次數更多，花費還更大，那必然不會是最短路徑
                if (costToNextNode > distTo[nextNodeID]
                        && nextNodeNumFromSrc > nodeNumTo[nextNodeID]) {
                    continue;
                }

                pq.offer(new State(nextNodeID, costToNextNode, nextNodeNumFromSrc));
            }
        }
        return -1;
    }
}
