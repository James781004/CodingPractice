package EndlessCheng.GenreMenu.Backtracking.NormalTree.Traverse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MinReorder {

    // https://leetcode.cn/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/solutions/2557145/javapython3cyan-du-you-xian-sou-suo-si-w-d7v3/
    private int res = 0;

    public int minReorder(int n, int[][] connections) {
        // 生成樹結構，tree[i]中的每個元素表示節點i的鄰節點，正負號表示方向
        List<List<Integer>> tree = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }
        for (int[] con : connections) {
            tree.get(con[0]).add(con[1]);    // con[0] 指向 con[1]，存儲正數
            tree.get(con[1]).add(-con[0]);   // con[1] 不指向 con[0]，存儲負數
        }
        dfs(0, -1, tree);                    // 從根節點開始搜索
        return res;
    }

    private void dfs(int node, int pa, List<List<Integer>> tree) {
        for (int neighbor : tree.get(node)) {      // 枚舉節點node的所有鄰節點
            int nei = Math.abs(neighbor);       // 獲取鄰節點編號
            if (nei != pa) {                      // 鄰節點不為父節點，一定是未處理的
                if (neighbor > 0) res++;          // 如果由當前節點指向鄰節點，需要反向
                dfs(nei, node, tree);           // 遞歸搜索鄰節點
            }
        }
    }


    public int minReorderBFS(int n, int[][] connections) {
        // 生成樹結構，tree[i]中的每個元素表示節點i的鄰節點，正負號表示方向
        List<List<Integer>> tree = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }
        for (int[] con : connections) {
            tree.get(con[0]).add(con[1]);    // con[0] 指向 con[1]，存儲正數
            tree.get(con[1]).add(-con[0]);   // con[1] 不指向 con[0]，存儲負數
        }
        // 廣度優先搜索
        boolean[] visited = new boolean[n];         // visited[i] 表示節點i 是否已經處理
        Queue<Integer> queue = new LinkedList<>();  // BFS使用的隊列
        queue.offer(0);                             // 從0節點開始遍歷
        visited[0] = true;
        int res = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();              // 獲取隊首節點
            for (int neighbor : tree.get(node)) {
                // 枚舉節點node的所有鄰節點
                int nei = Math.abs(neighbor);     // 獲取鄰節點編號
                if (visited[nei]) continue;         // 跳過處理過的節點
                visited[nei] = true;              // 標記鄰節點已處理
                queue.offer(nei);                 // 鄰節點入隊
                if (neighbor > 0) res += 1;         // 如果由當前節點指向鄰節點，需要反向
            }
        }
        return res;
    }


}
