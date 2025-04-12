package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.List;

public class MakeConnected {

    // https://leetcode.cn/problems/number-of-operations-to-make-network-connected/solutions/572496/dfsbing-cha-ji-jie-jue-lian-tong-kuai-sh-7cvf/
    public int makeConnected(int n, int[][] connections) {
        int[] visit = new int[n]; // 用來記錄節點訪問情況。0--未訪問  1--已訪問
        int count = 0;//用來記錄連通塊的數量

        // 理想情況下是n-1條邊才能連接所有節點，如果小於n-1，顯然無法連接所有節點，返回-1.
        if (connections.length < n - 1) return -1;

        // con[][]用來存放各連通塊
        // 初始化各元素連通的元素，雙向連通。
        List<Integer>[] con = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            con[i] = new ArrayList<>();
            // connections的長度不一定等於n的值，所以不能寫在一個循環里，不然會發生數組越界
            // con[connections[i][0]].add(connections[i][1]);
            // con[connections[i][1]].add(connections[i][0]);
        }
        for (int i = 0; i < connections.length; i++) {
            con[connections[i][0]].add(connections[i][1]);
            con[connections[i][1]].add(connections[i][0]);
        }

        for (int i = 0; i < n; i++) {
            if (visit[i] == 0) { // 如果該節點未被訪問過，則加入到相應的連通塊中，並將其visit[i]==1
                DFS(visit, con, i);
                // 確定完連通圖後，count記錄數量
                count++;
            }
        }
        return count - 1;
    }

    private void DFS(int[] visit, List<Integer>[] con, int node) {
        visit[node] = 1; // 訪問位置1
        for (int i = 0; i < con[node].size(); i++) {
            if (visit[con[node].get(i)] == 0) {
                DFS(visit, con, con[node].get(i));
            }
        }
    }


}
