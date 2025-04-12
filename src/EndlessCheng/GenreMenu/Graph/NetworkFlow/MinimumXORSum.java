package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MinimumXORSum {

    // https://leetcode.cn/problems/minimum-xor-sum-of-two-arrays/solutions/1/kmsuan-fa-zhuang-ya-dper-fen-tu-dai-quan-7v6w/
    private static final int INF = Integer.MAX_VALUE / 2; // 避免溢出
    private static final int N = 14 * 2 + 10; // 點數（最多 14 個數字，擴展為 2n + 2 個節點）
    private static final int M = (14 * 14 + 2 * 14) * 2 + 10; // 邊數（最多 n^2 + 2n 條邊，每條邊有正向和反向）

    // 鄰接表存圖
    private int[] h = new int[N]; // 頭節點
    private int[] e = new int[M]; // 目標節點
    private int[] f = new int[M]; // 容量（流量）
    private int[] w = new int[M]; // 邊的權重（費用）
    private int[] ne = new int[M]; // 下一條邊
    private int idx; // 邊的索引

    // SPFA 相關變數
    private int[] d = new int[N]; // 最短距離
    private int[] pre = new int[N]; // 記錄路徑
    private int[] incf = new int[N]; // 記錄當前可行流量
    private boolean[] st = new boolean[N]; // 記錄節點是否在隊列中

    /**
     * 添加一條有向邊（正向 + 反向邊）
     *
     * @param a    起點
     * @param b    終點
     * @param cap  容量（流量）
     * @param cost 邊的費用
     */
    private void add(int a, int b, int cap, int cost) {
        // 添加正向邊
        e[idx] = b;
        f[idx] = cap;
        w[idx] = cost;
        ne[idx] = h[a];
        h[a] = idx++;

        // 添加反向邊
        e[idx] = a;
        f[idx] = 0;
        w[idx] = -cost;
        ne[idx] = h[b];
        h[b] = idx++;
    }

    /**
     * SPFA（最短路徑優化 Bellman-Ford）計算最小費用增廣路
     *
     * @param S 源點
     * @param T 匯點
     * @return 是否找到增廣路
     */
    private boolean spfa(int S, int T) {
        Arrays.fill(d, INF); // 初始化最短路徑
        Arrays.fill(incf, 0); // 初始化可增廣流量
        Arrays.fill(st, false); // 記錄是否在隊列中

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(S);
        d[S] = 0;
        incf[S] = INF;

        while (!queue.isEmpty()) {
            int t = queue.poll();
            st[t] = false;

            // 遍歷所有相鄰節點
            for (int i = h[t]; i != -1; i = ne[i]) {
                int ver = e[i];
                // 如果還有流量，且新的距離更短
                if (f[i] > 0 && d[ver] > d[t] + w[i]) {
                    d[ver] = d[t] + w[i]; // 更新距離
                    pre[ver] = i; // 記錄該節點的前驅邊
                    incf[ver] = Math.min(f[i], incf[t]); // 更新可用流量
                    if (!st[ver]) {
                        queue.offer(ver);
                        st[ver] = true;
                    }
                }
            }
        }
        return incf[T] > 0; // 判斷是否還有增廣路
    }

    /**
     * EK（Edmonds-Karp）計算最小費用最大流
     *
     * @param S 源點
     * @param T 匯點
     * @return 最小費用
     */
    private int EK(int S, int T) {
        int cost = 0;
        while (spfa(S, T)) { // 只要還能找到增廣路
            int t = incf[T];
            cost += t * d[T]; // 累加費用

            // 反向更新流量
            for (int i = T; i != S; i = e[pre[i] ^ 1]) {
                f[pre[i]] -= t;
                f[pre[i] ^ 1] += t;
            }
        }
        return cost;
    }

    /**
     * 計算 nums1 和 nums2 的最小 XOR 總和
     *
     * @param nums1 第一組數字
     * @param nums2 第二組數字
     * @return 最小 XOR 總和
     */
    public int minimumXORSum(int[] nums1, int[] nums2) {
        Arrays.fill(h, -1);
        idx = 0;

        int n = nums1.length;
        int S = 0, T = n * 2 + 1;

        // 添加來源點到左側的邊（容量 1，費用 0）
        for (int i = 1; i <= n; i++) {
            add(S, i, 1, 0);
            add(n + i, T, 1, 0);
        }

        // 建立二分圖的流網絡
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                add(i, n + j, 1, nums1[i - 1] ^ nums2[j - 1]);
            }
        }

        return EK(S, T); // 執行最小費用最大流
    }
}
