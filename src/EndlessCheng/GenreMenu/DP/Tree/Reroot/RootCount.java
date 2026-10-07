package EndlessCheng.GenreMenu.DP.Tree.Reroot;

import java.util.*;

public class RootCount {

    // https://leetcode.cn/problems/count-number-of-possible-root-nodes/solutions/2147714/huan-gen-dppythonjavacgo-by-endlesscheng-ccwy/
    private List<Integer>[] g;
    private Set<Long> s = new HashSet<>();
    private int k, ans, cnt0;

    public int rootCount(int[][] edges, int[][] guesses, int k) {
        this.k = k;
        g = new ArrayList[edges.length + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
        }

        for (int[] e : guesses) { // guesses 轉成哈希表
            s.add((long) e[0] << 32 | e[1]); // 兩個 4 字節 int 壓縮成一個 8 字節 long
        }

        dfs(0, -1);
        reroot(0, -1, cnt0);
        return ans;
    }

    private void dfs(int x, int fa) {
        for (int y : g[x]) {
            if (y != fa) {
                if (s.contains((long) x << 32 | y)) { // 以 0 為根時，猜對了
                    cnt0++;
                }
                dfs(y, x);
            }
        }
    }

    private void reroot(int x, int fa, int cnt) {
        if (cnt >= k) { // 此時 cnt 就是以 x 為根時的猜對次數
            ans++;
        }
        for (int y : g[x]) {
            if (y != fa) {
                int c = cnt;
                if (s.contains((long) x << 32 | y)) c--; // 原來是對的，現在錯了
                if (s.contains((long) y << 32 | x)) c++; // 原來是錯的，現在對了
                reroot(y, x, c);
            }
        }
    }


}
