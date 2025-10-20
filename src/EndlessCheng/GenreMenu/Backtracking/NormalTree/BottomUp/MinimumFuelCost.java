package EndlessCheng.GenreMenu.Backtracking.NormalTree.BottomUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumFuelCost {

    // https://leetcode.cn/problems/minimum-fuel-cost-to-report-to-the-capital/solutions/1981361/kao-lu-mei-tiao-bian-shang-zhi-shao-xu-y-uamv/
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length + 1;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : roads) {
            int x = e[0], y = e[1];
            g[x].add(y); // 記錄每個點的鄰居
            g[y].add(x);
        }

        dfs(0, -1, g, seats);
        return ans;
    }

    private long ans;

    private int dfs(int x, int fa, List<Integer>[] g, int seats) {
        int size = 1;
        for (int y : g[x]) {
            if (y != fa) { // 遞歸子節點，不能遞歸父節點
                size += dfs(y, x, g, seats); // 統計子樹大小
            }
        }
        if (x > 0) { // x 不是根節點
            ans += (size - 1) / seats + 1; // ceil(size/seats)
        }
        return size;
    }


}
