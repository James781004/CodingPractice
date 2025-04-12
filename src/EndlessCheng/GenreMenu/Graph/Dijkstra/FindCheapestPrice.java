package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class FindCheapestPrice {

    // https://leetcode.cn/problems/cheapest-flights-within-k-stops/solutions/2841432/787-k-zhan-zhong-zhuan-nei-zui-bian-yi-d-7vqd/
    static final int INFINITY = Integer.MAX_VALUE / 2;

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // 將邊數組轉換成鄰接結點列表
        List<int[]>[] adjacentArr = new List[n];
        for (int i = 0; i < n; i++) {
            adjacentArr[i] = new ArrayList<int[]>();
        }
        for (int[] flight : flights) {
            int from = flight[0], to = flight[1], price = flight[2];
            adjacentArr[from].add(new int[]{to, price});
        }

        // cheapestPrices[i][j] 表示從城市 src 出發經過 i 次航班到達城市 j 的最低價格
        // 初始時，cheapestPrices 中的所有元素值都是 ∞
        int[][] cheapestPrices = new int[k + 2][n];
        for (int i = 0; i <= k + 1; i++) {
            Arrays.fill(cheapestPrices[i], INFINITY);
        }

        // 由於初始時經過的航班數量是 0，位於城市 src，因此 cheapestPrices[0][src]=0
        cheapestPrices[0][src] = 0;

        // Dijkstra 算法
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{src, 0, 0}); // 初始時將狀態 [src,0,0] 加入優先隊列
        while (!pq.isEmpty()) {
            int[] arr = pq.poll();
            int curr = arr[0], totalPrice = arr[1], totalStops = arr[2];
            if (cheapestPrices[totalStops][curr] < totalPrice) {
                continue;
            }
            for (int[] adjacent : adjacentArr[curr]) {
                int next = adjacent[0], price = adjacent[1];
                int nextTotalStops = totalStops + 1;

                // 航班總數量不超過 k+1 的情況下，計算後繼城市對應的狀態並加入優先隊列
                if (nextTotalStops <= k + 1 && cheapestPrices[nextTotalStops][next] > totalPrice + price) {
                    cheapestPrices[nextTotalStops][next] = totalPrice + price;
                    pq.offer(new int[]{next, cheapestPrices[nextTotalStops][next], nextTotalStops});
                }
            }
        }

        // 在 k 站中轉內從城市 src 到達城市 dst 的最低價格
        int cheapestPrice = INFINITY;
        for (int i = 0; i <= k + 1; i++) {
            cheapestPrice = Math.min(cheapestPrice, cheapestPrices[i][dst]);
        }
        return cheapestPrice != INFINITY ? cheapestPrice : -1;
    }

}
