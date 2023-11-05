package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_370 {
    // https://leetcode.cn/problems/find-champion-i/solutions/2513106/java-mei-you-ren-zhan-sheng-ta-jiu-shuo-lqt9u/
    public int findChampion(int[][] grid) {
        boolean b = true;

        for (int i = 0; i < grid.length; i++) {
            b = true;
            for (int j = 0; j < grid[0].length; j++) {
                if (i != j && grid[j][i] == 1) {
                    b = false;
                }
            }
            if (b) { // 沒有人戰勝他就說明他這一列值都為0
                return i;
            }
        }

        return 0;
    }


    // https://leetcode.cn/problems/find-champion-ii/solutions/2513144/bu-zai-edgesi1-zhong-de-wei-yi-bian-hao-44ur6/
    // 對每個節點，判斷它是否出現在 edges[i][1] 中。
    // 如果恰好有一個節點沒有出現，說明沒有可以擊敗它的隊伍，返回這個節點的編號。否則返回 −1
    public int findChampion(int n, int[][] edges) {
        boolean[] weak = new boolean[n];
        for (int[] e : edges) {
            weak[e[1]] = true;
        }
        int ans = -1;
        for (int i = 0; i < n; i++) {
            if (!weak[i]) {
                if (ans != -1) {
                    return -1;
                }
                ans = i;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-score-after-applying-operations-on-a-tree/solutions/2513101/shu-xing-dpxuan-huo-bu-xuan-pythonjavacg-7aj6/
    public long maximumScoreAfterOperations(int[][] edges, int[] values) {
        List<Integer>[] g = new ArrayList[values.length];
        Arrays.setAll(g, e -> new ArrayList<>());
        g[0].add(-1); // 避免誤把根節點當作葉子
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        // 先把所有分數加入答案
        long ans = 0;
        for (int v : values) {
            ans += v;
        }
        return ans - dfs(0, -1, g, values);
    }

    // dfs(x) 計算以 x 為根的子樹是健康時，失去的最小分數
    private long dfs(int x, int fa, List<Integer>[] g, int[] values) {
        if (g[x].size() == 1) { // x 是葉子
            return values[x];
        }
        long loss = 0; // 不選 values[x]
        for (int y : g[x]) {
            if (y != fa) { // 不向上遞歸
                loss += dfs(y, x, g, values); // 計算以 y 為根的子樹是健康時，失去的最小分數
            }
        }
        return Math.min(values[x], loss); // 選/不選 values[x]，取最小值
    }


    // https://leetcode.cn/problems/maximum-balanced-subsequence-sum/solutions/2513121/shu-zhuang-shu-zu-you-hua-dp-by-endlessc-3zf4/
    public long maxBalancedSubsequenceSum(int[] nums) {
        int n = nums.length;
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = nums[i] - i;
        }
        Arrays.sort(b);

        BIT t = new BIT(b.length + 1);
        long ans = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            // j 為 nums[i]-i 離散化後的值（從 1 開始）
            int j = Arrays.binarySearch(b, nums[i] - i) + 1;
            long f = Math.max(t.preMax(j), 0) + nums[i];
            ans = Math.max(ans, f);
            t.update(j, f);
        }
        return ans;
    }

    // 樹狀數組模板（維護前綴最大值）
    class BIT {
        private long[] tree;

        public BIT(int n) {
            tree = new long[n];
            Arrays.fill(tree, Long.MIN_VALUE);
        }

        public void update(int i, long val) {
            while (i < tree.length) {
                tree[i] = Math.max(tree[i], val);
                i += i & -i;
            }
        }

        public long preMax(int i) {
            long res = Long.MIN_VALUE;
            while (i > 0) {
                res = Math.max(res, tree[i]);
                i &= i - 1;
            }
            return res;
        }
    }
}
