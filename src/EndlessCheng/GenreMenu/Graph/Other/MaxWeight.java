package EndlessCheng.GenreMenu.Graph.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxWeight {

    // https://leetcode.cn/problems/you-le-yuan-de-you-lan-ji-hua/solutions/1813666/by-liu-jian-ping-pinard-r3ee/
    public int maxWeight(int[][] edges, int[] value) {
        int n = value.length; // 點數量
        int m = edges.length; // 邊數量
        // 保存每個點的度
        int[] cnt = new int[n];
        for (int[] edge : edges) {
            cnt[edge[0]]++;
            cnt[edge[1]]++;
        }
        // 對邊按2個端點的權重和從大到小排序
        Arrays.sort(edges, (a, b) -> {
            return (value[b[0]] + value[b[1]]) - (value[a[0]] + value[a[1]]);
        });
        // 將無向圖構建為有向圖, 方向為度小的點指向度大的點，度一樣的時候，序號小的指向序號大的
        // graph[i]是從點i作起點的有向邊的列表，graph[i][j]代表從i開始的第j個有向邊，是一個二維變量，
        // graph[i][j][0]是有向邊終點, graph[i][j][1]是從點i到點graph[i][j][0]對應的邊的編號
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int node1 = edges[i][0];
            int node2 = edges[i][1];
            if (cnt[node1] < cnt[node2] || ((cnt[node1] == cnt[node2]) && (node1 < node2))) {
                graph[node1].add(new int[]{node2, i});
            } else graph[node2].add(new int[]{node1, i});
        }
        // 獲取所有的三元環
        // edgeAndPoint[i]是一個列表，edgeAndPoint[i][j]表示邊i可以和頂點j組成一個三元環
        List<Integer>[] edgeAndPoint = new List[m];
        for (int i = 0; i < m; i++) edgeAndPoint[i] = new ArrayList<>();
        // 用於標記當前點是基於那條邊的端點遍歷到的
        // tmpEdges1[i]表示端點i是在邊tmpEdges1[i]迭代時遍歷到
        int[] tmpEdges1 = new int[n];
        Arrays.fill(tmpEdges1, 10000000);
        // 用於標記當前點對應的邊序號
        // tmpEdges2[i]表示端點i和迭代時遍歷邊的端點組成的邊的序號
        int[] tmpEdges2 = new int[n];
        for (int i = 0; i < m; i++) {
            int node1 = edges[i][0];
            int node2 = edges[i][1];
            for (int[] nei : graph[node1]) {
                tmpEdges1[nei[0]] = i;
                tmpEdges2[nei[0]] = nei[1];
            }
            for (int[] nei : graph[node2]) {
                // 找到一個三元環
                if (tmpEdges1[nei[0]] == i) {
                    // 邊i和點nei[0]組成三元環
                    edgeAndPoint[i].add(nei[0]);
                    // 邊nei[1]和node1組成三元環
                    edgeAndPoint[nei[1]].add(node1);
                    // 邊tmpEdges2[nei[0]]和node2組成三元環
                    edgeAndPoint[tmpEdges2[nei[0]]].add(node2);

                }
            }
        }
        // 將從邊到點的三元環轉化為從點到邊的三元環
        // pointAndEdge[i]是一個列表，pointAndEdge[i][j]表示點i可以和邊j組成一個三元環
        List<Integer>[] pointAndEdge = new List[n];
        for (int i = 0; i < n; i++) pointAndEdge[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int point : edgeAndPoint[i]) pointAndEdge[point].add(i);
        }
        int res = 0;
        // 檢查頂點i的三元環
        for (int i = 0; i < n; i++) {
            int lastIndex = pointAndEdge[i].size() - 1;
            // 點i沒有三元環
            if (lastIndex < 0) continue;
            // 當前邊序號為edgeAndPoint[i].get(j)
            for (int j = 0; j < Math.min(3, pointAndEdge[i].size()) && lastIndex >= j; j++) {
                int edgeIndex = pointAndEdge[i].get(j);
                int node1 = edges[edgeIndex][0];
                int node2 = edges[edgeIndex][1];
                for (int k = j; k <= lastIndex; k++) {
                    int edgeIndex2 = pointAndEdge[i].get(k);
                    int node3 = edges[edgeIndex2][0];
                    int node4 = edges[edgeIndex2][1];
                    int curRes = value[i] + value[node1] + value[node2];
                    int counter = 0;
                    // 找到不重合的點
                    if (node3 != node1 && node3 != node2) {
                        curRes += value[node3];
                        counter++;
                    }
                    if (node4 != node1 && node4 != node2) {
                        curRes += value[node4];
                        counter++;
                    }
                    res = Math.max(res, curRes);
                    if (counter == 2) {
                        // 剪枝
                        lastIndex = k - 1;
                        break;
                    }
                }
            }
        }
        return res;
    }

}
