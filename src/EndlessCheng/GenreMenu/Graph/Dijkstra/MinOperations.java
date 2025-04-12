package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MinOperations {

    // https://leetcode.cn/problems/digit-operations-to-make-two-integers-equal/solutions/3013737/dijkstra-zui-duan-lu-pythonjavacgo-by-en-ofby/
    private static final int MX = 10000;
    private static final boolean[] np = new boolean[MX];

    // 使用埃氏篩初始化質數標記數組
    static {
        np[1] = true; // 1 不是質數
        for (int i = 2; i < MX; i++) {
            if (!np[i]) {
                for (int j = i * i; j < MX; j += i) {
                    np[j] = true; // 標記所有 i 的倍數為非質數
                }
            }
        }
    }

    public int minOperations(int n, int m) {
        if (!np[n] || !np[m]) { // 如果 n 或 m 是質數, 直接返回 -1
            return -1;
        }
        int lenN = Integer.toString(n).length(); // 獲取 n 的位數
        int[] dis = new int[(int) Math.pow(10, lenN)]; // dis 數組存儲從起點到每個狀態的最小代價
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[n] = n; // 起點的代價就是 n 本身
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        pq.offer(new int[]{n, n}); // 優先隊列存儲代價和當前值（最小堆）
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int disX = top[0];
            int x = top[1];
            if (x == m) { // 如果找到了目標值 m, 返回代價
                return disX;
            }
            if (disX > dis[x]) { // 如果當前代價不是最優解, 跳過
                continue;
            }
            
            int pow10 = 1;  // 位數權重
            for (int v = x; v > 0; v /= 10) { // 遍歷每一位數字
                int d = v % 10; // 當前位數字
                if (d > 0) { // 嘗試減少當前位數的值
                    int y = x - pow10;
                    int newD = disX + y;
                    if (np[y] && newD < dis[y]) { // 更新代價
                        dis[y] = newD;
                        pq.offer(new int[]{newD, y});
                    }
                }
                if (d < 9) { // 嘗試增加當前位數的值
                    int y = x + pow10;
                    int newD = disX + y;
                    if (np[y] && newD < dis[y]) { // 更新代價
                        dis[y] = newD;
                        pq.offer(new int[]{newD, y});
                    }
                }
                pow10 *= 10; // 更新位數權重
            }
        }
        return -1;
    }

}
