package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ElectricCarPlan {

    // https://leetcode.cn/problems/DFPeFJ/solutions/3033616/bi-ji-dijkstrabian-chong-kuo-dian-zui-du-7cja/
    public int electricCarPlan(int[][] paths, int cnt, int start, int end, int[] charge) {
        int n = charge.length; // 獲取城市數量
        List<List<int[]>> graph = new ArrayList<>(); // 創建鄰接表表示圖
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>()); // 初始化每個城市的鄰接表
        }
        // 構建圖：將路徑信息添加到鄰接表中
        for (int[] p : paths) {
            graph.get(p[0]).add(new int[]{p[1], p[2]}); // 添加雙向邊
            graph.get(p[1]).add(new int[]{p[0], p[2]});
        }
        // 初始化距離數組：distance[i][j]表示到達城市i時剩余電量為j的最小時間
        int[][] distance = new int[n][cnt + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < cnt + 1; j++) {
                distance[i][j] = Integer.MAX_VALUE; // 初始化為無窮大
            }
        }
        distance[start][0] = 0; // 起點初始狀態：電量為0，時間為0
        boolean[][] visited = new boolean[n][cnt + 1]; // 記錄每個狀態是否被訪問過
        // 優先隊列：按時間從小到大排序，存儲狀態（當前城市、剩余電量、總時間）
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.add(new int[]{start, 0, 0}); // 初始狀態入隊
        // Dijkstra算法核心
        while (!pq.isEmpty()) {
            int[] record = pq.poll(); // 取出當前狀態
            int cur = record[0]; // 當前城市
            int power = record[1]; // 當前電量
            int cost = record[2]; // 當前時間
            if (visited[cur][power]) continue; // 如果狀態已訪問，跳過
            if (cur == end) return cost; // 到達終點，返回時間
            visited[cur][power] = true; // 標記當前狀態為已訪問
            // 如果電量未滿，嘗試充電
            if (power < cnt) {
                int newCost = cost + charge[cur]; // 充電後的時間
                if (!visited[cur][power + 1] && newCost < distance[cur][power + 1]) {
                    distance[cur][power + 1] = newCost; // 更新狀態
                    pq.add(new int[]{cur, power + 1, newCost}); // 新狀態入隊
                }
            }
            // 遍歷鄰接城市
            for (int[] edges : graph.get(cur)) {
                int next = edges[0]; // 下一個城市
                int nextPower = power - edges[1]; // 到達下一個城市後的電量
                int nextCost = cost + edges[1]; // 到達下一個城市後的時間
                // 如果電量充足且狀態未訪問，更新狀態
                if (nextPower >= 0 && !visited[next][nextPower] && nextCost < distance[next][nextPower]) {
                    distance[next][nextPower] = nextCost; // 更新狀態
                    pq.add(new int[]{next, nextPower, nextCost}); // 新狀態入隊
                }
            }
        }
        return 666; // 未找到路徑，返回666表示牛逼
    }


}
