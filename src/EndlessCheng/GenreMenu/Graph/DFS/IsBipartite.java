package EndlessCheng.GenreMenu.Graph.DFS;

public class IsBipartite {

    // https://leetcode.cn/problems/is-graph-bipartite/solutions/333138/bfs-dfs-bing-cha-ji-san-chong-fang-fa-pan-duan-er-/
    public boolean isBipartite(int[][] graph) {
        // 定義 visited 數組，初始值為 0 表示未被訪問，賦值為 1 或者 -1 表示兩種不同的顏色。
        int[] visited = new int[graph.length];
        // 因為圖中可能含有多個連通域，所以我們需要判斷是否存在頂點未被訪問，若存在則從它開始再進行一輪 dfs 染色。
        for (int i = 0; i < graph.length; i++) {
            if (visited[i] == 0 && !dfs(graph, i, 1, visited)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int v, int color, int[] visited) {
        // 如果要對某頂點染色時，發現它已經被染色了，則判斷它的顏色是否與本次要染的顏色相同，如果矛盾，說明此無向圖無法被正確染色，返回 false。
        if (visited[v] != 0) {
            return visited[v] == color;
        }

        // 對當前頂點進行染色，並將當前頂點的所有鄰接點染成相反的顏色。
        visited[v] = color;
        for (int w : graph[v]) {
            if (!dfs(graph, w, -color, visited)) {
                return false;
            }
        }
        return true;
    }


}
