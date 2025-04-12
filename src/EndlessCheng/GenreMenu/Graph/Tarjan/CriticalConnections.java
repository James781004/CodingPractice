package EndlessCheng.GenreMenu.Graph.Tarjan;

import java.util.*;

public class CriticalConnections {

    // https://leetcode.cn/problems/critical-connections-in-a-network/solutions/309016/1192-java-dfstarjansuan-fa-shi-jian-fu-za-du-ove-b/
    // https://www.bilibili.com/video/BV15t4y197eq/?vd_source=3ffe88901c9b0b355ae9becd01f3e4bf
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // 構建一個map，存放每個節點的相鄰節點有哪些
        Map<Integer, Set<Integer>> map = new HashMap<>();
        buildMap(connections, map);

        // 創建一個數組，存放每個節點的 id
        int[] id = new int[n];
        Arrays.fill(id, -1);

        // 選取一個點作為根節點，dfs向下遞歸，過程中識別出哪個邊是critical connection
        List<List<Integer>> res = new ArrayList<>();
        dfs(0, 0, -1, id, map, res);    // 假設根節點有一個編號是-1父節點

        return res;
    }

    // Tarjan's算法，將整個環統合為一個節點
    public int dfs(int node, int nodeID, int par, int[] id, Map<Integer, Set<Integer>> map, List<List<Integer>> res) {
        id[node] = nodeID; // 正常繼承遞增的編號

        Set<Integer> set = map.get(node);
        for (Integer neighbor : set) {
            if (neighbor == par) { // 防止重復搜索
                continue;
            } else if (id[neighbor] == -1) { // 更新編號為環中最小的那個
                id[node] = Math.min(id[node], dfs(neighbor, nodeID + 1, node, id, map, res));
            } else {
                id[node] = Math.min(id[node], id[neighbor]);
            }
        }

        // node不能是0，因為不存在-1的家長，
        // id[node] == nodeId意味著不在環中的連接（node為環中的最小編號節點而par自然不在環裡）
        if (id[node] == nodeID && node != 0) {
            res.add(Arrays.asList(par, node));
        }

        return id[node];
    }

    public void buildMap(List<List<Integer>> con, Map<Integer, Set<Integer>> map) {
        for (List<Integer> edge : con) {
            int n1 = edge.get(0);
            int n2 = edge.get(1);

            Set<Integer> n1n = map.getOrDefault(n1, new HashSet<>());
            Set<Integer> n2n = map.getOrDefault(n2, new HashSet<>());

            n1n.add(n2);
            n2n.add(n1);

            map.put(n1, n1n);
            map.put(n2, n2n);
        }
    }

}
