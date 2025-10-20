package EndlessCheng.GenreMenu.Backtracking.NormalTree.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberOfGoodPaths {

    // https://leetcode.cn/problems/number-of-good-paths/solutions/1847967/bing-cha-ji-by-endlesscheng-tbz8/
    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        int n = vals.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
        }

        fa = new int[n];
        var id = new Integer[n];
        for (int i = 0; i < n; i++)
            fa[i] = id[i] = i;
        Arrays.sort(id, (i, j) -> vals[i] - vals[j]);
        // size[x] 表示節點值等於 vals[x] 的節點個數，
        // 如果按照節點值從小到大合並，size[x] 也是連通塊內的等於最大節點值的節點個數
        var size = new int[n];
        Arrays.fill(size, 1);

        int ans = n; // 單個節點的好路徑
        for (var x : id) {
            int vx = vals[x], fx = find(x);
            for (var y : g[x]) {
                y = find(y);
                if (y == fx || vals[y] > vx)
                    continue; // 只考慮最大節點值不超過 vx 的連通塊
                if (vals[y] == vx) { // 可以構成好路徑
                    ans += size[fx] * size[y]; // 乘法原理
                    size[fx] += size[y]; // 統計連通塊內節點值等於 vx 的節點個數
                }
                fa[y] = fx; // 把小的節點值合並到大的節點值上
            }
        }
        return ans;
    }

    private int[] fa;

    private int find(int x) {
        if (fa[x] != x) fa[x] = find(fa[x]);
        return fa[x];
    }


}
