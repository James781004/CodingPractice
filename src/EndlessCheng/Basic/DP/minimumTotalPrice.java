package EndlessCheng.Basic.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class minimumTotalPrice {

    // https://leetcode.cn/problems/minimize-the-total-price-of-the-trips/solutions/2229503/lei-si-da-jia-jie-she-iii-pythonjavacgo-4k3wq/
    private List<Integer>[] g;
    private int[] price, cnt;
    private int end;

    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        cnt = new int[n];
        for (int[] t : trips) {
            end = t[1];
            dfs(t[0], -1);
        }

        this.price = price;
        int[] res = dp(0, -1);
        return Math.min(res[0], res[1]);
    }

    private boolean dfs(int x, int fa) {
        if (x == end) {
            cnt[x]++;
            return true; // 找到 end
        }
        for (int y : g[x]) {
            if (y != fa && dfs(y, x)) {
                cnt[x]++; // x 是 end 的祖先節點，也就在路徑上
                return true;
            }
        }
        return false; // 未找到 end
    }

    // 類似 337. 打家劫舍 III
    private int[] dp(int x, int fa) {
        int notHalve = price[x] * cnt[x]; // x 不變
        int halve = notHalve / 2; // x 減半
        for (int y : g[x]) {
            if (y != fa) {
                int[] res = dp(y, x); // 計算 y 不變/減半的最小價值總和
                notHalve += Math.min(res[0], res[1]); // x 不變，那麼 y 可以不變，可以減半，取這兩種情況的最小值
                halve += res[0]; // x 減半，那麼 y 只能不變
            }
        }
        return new int[]{notHalve, halve};
    }


}
