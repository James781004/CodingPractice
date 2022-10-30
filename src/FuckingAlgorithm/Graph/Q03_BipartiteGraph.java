package FuckingAlgorithm.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q03_BipartiteGraph {
//    https://leetcode.cn/problems/is-graph-bipartite/
//    785. 判斷二分圖
//    存在一個 無向圖 ，圖中有 n 個節點。其中每個節點都有一個介於 0 到 n - 1 之間的唯一編號。
//    給你一個二維數組 graph ，其中 graph[u] 是一個節點數組，由節點 u 的鄰接節點組成。
//    形式上，對於 graph[u] 中的每個 v ，都存在一條位於節點 u 和節點 v 之間的無向邊。該無向圖同時具有以下屬性：
//    不存在自環（graph[u] 不包含 u）。
//    不存在平行邊（graph[u] 不包含重復值）。
//    如果 v 在 graph[u] 內，那麼 u 也應該在 graph[v] 內（該圖是無向圖）
//    這個圖可能不是連通圖，也就是說兩個節點 u 和 v 之間可能不存在一條連通彼此的路徑。
//    二分圖 定義：如果能將一個圖的節點集合分割成兩個獨立的子集 A 和 B ，
//    並使圖中的每一條邊的兩個節點一個來自 A 集合，一個來自 B 集合，就將這個圖稱為 二分圖 。
//
//    如果圖是二分圖，返回 true ；否則，返回 false 。

    class isBipartite {
        // 記錄圖是否符合二分圖性質
        private boolean ok = true;
        // 記錄圖中節點的顏色，false 和 true 代表兩種不同顏色
        private boolean[] color;
        // 記錄圖中節點是否被訪問過
        private boolean[] visited;

        // 主函數，輸入鄰接表，判斷是否是二分圖
        public boolean isBipartite(int[][] graph) {
            int n = graph.length;
            color = new boolean[n];
            visited = new boolean[n];
            // 因為圖不一定是聯通的，可能存在多個子圖
            // 所以要把每個節點都作為起點進行一次遍歷
            // 如果發現任何一個子圖不是二分圖，整幅圖都不算二分圖
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    dfs(graph, v);
                }
            }
            return ok;
        }

        // DFS 遍歷框架
        private void dfs(int[][] graph, int v) {
            // 如果已經確定不是二分圖了，就不用浪費時間再遞歸遍歷了
            if (!ok) return;

            visited[v] = true;
            for (int w : graph[v]) {
                if (!visited[w]) {
                    // 相鄰節點 w 沒有被訪問過
                    // 那麼應該給節點 w 涂上和節點 v 不同的顏色
                    color[w] = !color[v];
                    // 繼續遍歷 w
                    dfs(graph, w);
                } else {
                    // 相鄰節點 w 已經被訪問過
                    // 根據 v 和 w 的顏色判斷是否是二分圖
                    if (color[w] == color[v]) {
                        // 若相同，則此圖不是二分圖
                        ok = false;
                        return;
                    }
                }
            }
        }

        // 從 start 節點開始進行 BFS 遍歷
        private void bfs(int[][] graph, int start) {
            Queue<Integer> q = new LinkedList<>();
            visited[start] = true;
            q.offer(start);

            while (!q.isEmpty() && ok) {
                int v = q.poll();
                for (int w : graph[v]) {
                    if (!visited[w]) {
                        // 相鄰節點 w 沒有被訪問過
                        // 那麼應該給節點 w 涂上和節點 v 不同的顏色
                        color[w] = !color[v];
                        // 標記 w 節點，並放入隊列
                        visited[w] = true;
                        q.offer(w);
                    } else {
                        // 相鄰節點 w 已經被訪問過
                        // 根據 v 和 w 的顏色判斷是否是二分圖
                        if (color[w] == color[v]) {
                            // 若相同，則此圖不是二分圖
                            ok = false;
                            return;
                        }
                    }
                }
            }
        }
    }

//    https://leetcode.cn/problems/possible-bipartition/
//    886. 可能的二分法
//    給定一組 n 人（編號為 1, 2, ..., n）， 我們想把每個人分進任意大小的兩組。每個人都可能不喜歡其他人，那麼他們不應該屬於同一組。
//
//    給定整數 n 和數組 dislikes ，其中 dislikes[i] = [ai, bi] ，
//    表示不允許將編號為 ai 和  bi的人歸入同一組。當可以用這種方法將所有人分進兩組時，返回 true；否則返回 false。

    class possibleBipartition {
        private boolean ok = true;
        private boolean[] color;
        private boolean[] visited;

        // 把每個人看做圖中的節點，相互討厭的關系看做圖中的邊，那麼 dislikes 數組就可以構成一幅圖
        // 題目說互相討厭的人不能放在同一組裡，相當於圖中的所有相鄰節點都要放進兩個不同的組
        public boolean possibleBipartition(int n, int[][] dislikes) {
            // 圖節點編號從 1 開始
            color = new boolean[n + 1];
            visited = new boolean[n + 1];
            // 轉化成鄰接表表示圖結構
            List<Integer>[] graph = buildGraph(n, dislikes);

            for (int v = 1; v <= n; v++) {
                if (!visited[v]) {
                    dfs(graph, v);
                }
            }

            return ok;
        }

        // 建圖函數
        private List<Integer>[] buildGraph(int n, int[][] dislikes) {
            // 圖節點編號為 1...n
            List<Integer>[] graph = new LinkedList[n + 1];
            for (int i = 1; i <= n; i++) {
                graph[i] = new LinkedList<>();
            }
            for (int[] edge : dislikes) {
                int v = edge[1];
                int w = edge[0];
                // 「無向圖」相當於「雙向圖」
                // v -> w
                graph[v].add(w);
                // w -> v
                graph[w].add(v);
            }

            return graph;
        }

        // 和之前的 dfs 函數完全相同
        private void dfs(List<Integer>[] graph, int v) {
            if (!ok) return;
            visited[v] = true;
            for (int w : graph[v]) {
                if (!visited[w]) {
                    color[w] = !color[v];
                    dfs(graph, w);
                } else {
                    if (color[w] == color[v]) {
                        ok = false;
                        return;
                    }
                }
            }
        }

        // 從 start 節點開始進行 BFS 遍歷
        private void bfs(int[][] graph, int start) {
            Queue<Integer> q = new LinkedList<>();
            visited[start] = true;
            q.offer(start);

            while (!q.isEmpty() && ok) {
                int v = q.poll();
                for (int w : graph[v]) {
                    if (!visited[w]) {
                        // 相鄰節點 w 沒有被訪問過
                        // 那麼應該給節點 w 涂上和節點 v 不同的顏色
                        color[w] = !color[v];
                        // 標記 w 節點，並放入隊列
                        visited[w] = true;
                        q.offer(w);
                    } else {
                        // 相鄰節點 w 已經被訪問過
                        // 根據 v 和 w 的顏色判斷是否是二分圖
                        if (color[w] == color[v]) {
                            // 若相同，則此圖不是二分圖
                            ok = false;
                            return;
                        }
                    }
                }
            }
        }


        // 反向點 + 並查集
        private int[] p;

        // 我們遍歷每一個人，他與他不喜歡的人不應該在同一個集合中，如果在同一個集合中，就產生了沖突，直接返回 false。
        // 如果沒有沖突，那麼就將他所有不喜歡的人合並到同一個集合中。
        // 遍歷結束，說明沒有沖突，返回 true。
        public boolean possibleBipartitionUnionFind(int n, int[][] dislikes) {
            p = new int[n];
            List<Integer>[] g = new List[n];
            for (int i = 0; i < n; ++i) {
                p[i] = i;
                g[i] = new ArrayList<>();
            }
            for (int[] e : dislikes) {
                int a = e[0] - 1, b = e[1] - 1;
                g[a].add(b);
                g[b].add(a);
            }
            for (int i = 0; i < n; ++i) {
                for (int j : g[i]) {
                    if (find(i) == find(j)) {
                        return false;
                    }
                    p[find(j)] = find(g[i].get(0));
                }
            }
            return true;
        }

        private int find(int x) {
            if (p[x] != x) {
                p[x] = find(p[x]);
            }
            return p[x];
        }

    }


}
