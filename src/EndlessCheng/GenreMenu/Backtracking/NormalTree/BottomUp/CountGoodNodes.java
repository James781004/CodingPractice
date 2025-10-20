package EndlessCheng.GenreMenu.Backtracking.NormalTree.BottomUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountGoodNodes {

    // https://leetcode.cn/problems/count-the-number-of-good-nodes/solutions/2876198/dfs-ji-suan-zi-shu-da-xiao-pythonjavacgo-9atl/
    public int countGoodNodes(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        dfs(0, -1, g);
        return ans;
    }

    private int ans;

    private int dfs(int x, int fa, List<Integer>[] g) {
        int size = 1;
        int sz0 = 0;
        boolean ok = true;
        for (int y : g[x]) {
            if (y == fa) {
                continue; // 不能遞歸到父節點
            }
            int sz = dfs(y, x, g);
            if (sz0 == 0) {
                sz0 = sz; // 記錄第一個兒子子樹的大小
            } else if (sz != sz0) { // 存在大小不一樣的兒子子樹
                ok = false; // 注意不能 break，其他子樹 y 仍然要遞歸
            }
            size += sz;
        }
        ans += ok ? 1 : 0;
        return size;
    }


}
