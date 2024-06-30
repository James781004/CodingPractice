package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_404 {
    // https://leetcode.cn/problems/maximum-height-of-a-triangle/solutions/2826643/o1-shu-xue-gong-shi-pythonjavacgo-by-end-t2ht/
    public int maxHeightOfTriangle(int red, int blue) {
        return Math.max(f(red, blue), f(blue, red));
    }

    private int f(int n, int m) {
        int odd = (int) Math.sqrt(n);
        int even = (int) ((Math.sqrt(m * 4 + 1) - 1) / 2);
        return odd > even ? even * 2 + 1 : odd * 2;
    }


    // https://leetcode.cn/problems/find-the-maximum-length-of-valid-subsequence-ii/solutions/2826591/deng-jie-zhuan-huan-dong-tai-gui-hua-pyt-z2fs/
    public int maximumLength(int[] nums, int k) {
        int ans = 0;
        int[][] f = new int[k][k];
        for (int x : nums) {
            x %= k;
            for (int y = 0; y < k; y++) {
                f[y][x] = f[x][y] + 1;
                ans = Math.max(ans, f[y][x]);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-minimum-diameter-after-merging-two-trees/solutions/2826587/lian-jie-zhi-jing-zhong-dian-pythonjavac-0e1c/
    public int minimumDiameterAfterMerge(int[][] edges1, int[][] edges2) {
        int d1 = diameter(edges1);
        int d2 = diameter(edges2);
        return Math.max(Math.max(d1, d2), (d1 + 1) / 2 + (d2 + 1) / 2 + 1);
    }

    private int res;

    private int diameter(int[][] edges) {
        List<Integer>[] g = new ArrayList[edges.length + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        res = 0;
        dfs(0, -1, g);
        return res;
    }

    private int dfs(int x, int fa, List<Integer>[] g) {
        int maxLen = 0;
        for (int y : g[x]) {
            if (y != fa) {
                int subLen = dfs(y, x, g) + 1;
                res = Math.max(res, maxLen + subLen);
                maxLen = Math.max(maxLen, subLen);
            }
        }
        return maxLen;
    }

}


