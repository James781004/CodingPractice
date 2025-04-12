package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.LinkedList;
import java.util.Queue;

public class FindCircleNum {

    // https://leetcode.cn/problems/number-of-provinces/solutions/550179/dfs-bfs-bing-cha-ji-3-chong-fang-fa-ji-s-edkl/
    public int findCircleNumDFS(int[][] isConnected) {
        // int[][] isConnected 是無向圖的鄰接矩陣，n 為無向圖的頂點數量
        int n = isConnected.length;
        // 定義 boolean 數組標識頂點是否被訪問
        boolean[] visited = new boolean[n];
        // 定義 cnt 來累計遍歷過的連通域的數量
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            // 若當前頂點 i 未被訪問，說明又是一個新的連通域，則遍歷新的連通域且cnt+=1.
            if (!visited[i]) {
                cnt++;
                dfs(i, isConnected, visited);
            }
        }
        return cnt;
    }

    private void dfs(int i, int[][] isConnected, boolean[] visited) {
        // 對當前頂點 i 進行訪問標記
        visited[i] = true;

        // 繼續遍歷與頂點 i 相鄰的頂點（使用 visited 數組防止重復訪問）
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                dfs(j, isConnected, visited);
            }
        }
    }


    public int findCircleNumBFS(int[][] isConnected) {
        // int[][] isConnected 是無向圖的鄰接矩陣，n 為無向圖的頂點數量
        int n = isConnected.length;
        // 定義 boolean 數組標識頂點是否被訪問
        boolean[] visited = new boolean[n];

        // 定義 cnt 來累計遍歷過的連通域的數量
        int cnt = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            // 若當前頂點 i 未被訪問，說明又是一個新的連通域，則bfs新的連通域且cnt+=1.
            if (!visited[i]) {
                cnt++;
                queue.offer(i);
                visited[i] = true;
                while (!queue.isEmpty()) {
                    int v = queue.poll();
                    for (int w = 0; w < n; w++) {
                        if (isConnected[v][w] == 1 && !visited[w]) {
                            visited[w] = true;
                            queue.offer(w);
                        }
                    }
                }
            }
        }
        return cnt;
    }


}
