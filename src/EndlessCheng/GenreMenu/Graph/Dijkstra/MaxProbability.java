package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class MaxProbability {

    // https://leetcode.cn/problems/path-with-maximum-probability/solutions/990188/dijkstra-suan-fa-xiang-jie-by-labuladong-8zhv/
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        List<double[]>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }
        // 構造鄰接表結構表示圖
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            double weight = succProb[i];
            // 無向圖就是雙向圖；先把 int 統一轉成 double，待會再轉回來
            graph[from].add(new double[]{(double) to, weight});
            graph[to].add(new double[]{(double) from, weight});
        }

        return dijkstra(start, end, graph);
    }

    class State {
        // 圖節點的 id
        int id;
        // 從 start 節點到達當前節點的概率
        double probFromStart;

        State(int id, double probFromStart) {
            this.id = id;
            this.probFromStart = probFromStart;
        }
    }

    double dijkstra(int start, int end, List<double[]>[] graph) {
        // 定義：probTo[i] 的值就是節點 start 到達節點 i 的最大概率
        double[] probTo = new double[graph.length];
        // dp table 初始化為一個取不到的最小值
        Arrays.fill(probTo, -1);
        // base case，start 到 start 的概率就是 1
        probTo[start] = 1;

        // 優先級隊列，probFromStart 較大的排在前面
        PriorityQueue<State> pq = new PriorityQueue<>((a, b) -> Double.compare(b.probFromStart, a.probFromStart));
        // 從起點 start 開始進行 BFS
        pq.offer(new State(start, 1));

        while (!pq.isEmpty()) {
            State curState = pq.poll();
            int curNodeID = curState.id;
            double curProbFromStart = curState.probFromStart;

            // 遇到終點提前返回
            if (curNodeID == end) {
                return curProbFromStart;
            }

            if (curProbFromStart < probTo[curNodeID]) {
                // 已經有一條概率更大的路徑到達 curNode 節點了
                continue;
            }
            // 將 curNode 的相鄰節點裝入隊列
            for (double[] neighbor : graph[curNodeID]) {
                int nextNodeID = (int) neighbor[0];
                // 看看從 curNode 達到 nextNode 的概率是否會更大
                double probToNextNode = probTo[curNodeID] * neighbor[1];
                if (probTo[nextNodeID] < probToNextNode) {
                    probTo[nextNodeID] = probToNextNode;
                    pq.offer(new State(nextNodeID, probToNextNode));
                }
            }
        }
        // 如果到達這裡，說明從 start 開始無法到達 end，返回 0
        return 0.0;
    }


}
