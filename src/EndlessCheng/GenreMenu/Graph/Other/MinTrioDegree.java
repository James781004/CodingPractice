package EndlessCheng.GenreMenu.Graph.Other;

public class MinTrioDegree {

    // https://leetcode.cn/problems/minimum-degree-of-a-connected-trio-in-a-graph/solutions/2420622/javapython3cmei-ju-shu-xue-mei-ju-suo-yo-ewyt/
    public int minTrioDegree(int n, int[][] edges) {
        int[][] links = new int[n][n];  // 標記任意兩個節點之間是否有連接
        int[] degrees = new int[n];     // 表示每個節點連接的邊數
        int x, y;
        for (int[] e : edges) {
            x = e[0] - 1;   // 節點索引的能夠與節點編號-1
            y = e[1] - 1;
            links[x][y] = links[y][x] = 1;  // 標記兩個節點之間存在連接
            degrees[x]++;   // 兩個節點的度都增加1
            degrees[y]++;
        }
        int res = n * n;    // 結果，初始化為一個不可能的值
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (links[i][j] == 0) continue;   // 兩個節點之間不存在連接跳過
                for (int k = j + 1; k < n; k++) {
                    if (links[i][k] == 0 || links[j][k] == 0) continue;   // 三個節點之間存在不連接的兩個節點，跳過
                    res = Math.min(res, degrees[i] + degrees[j] + degrees[k] - 6);  // 找到一個連通三元組，更新最小度
                }
            }
        }
        return res == n * n ? -1 : res;   // res沒有變化說明沒有不存在三元組返回-1
    }

}
