package EndlessCheng.GenreMenu.Backtracking.NormalTree.TimeStamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeQueries {

    // https://leetcode.cn/problems/shortest-path-in-a-weighted-tree/solutions/3649372/dfs-shi-jian-chuo-chai-fen-shu-zhuang-sh-h8q3/

    public int[] treeQueries(int n, int[][] edges, int[][] queries) {
        List<Integer>[] g = new ArrayList[n + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        int[] in = new int[n + 1];
        int[] out = new int[n + 1];
        dfs(1, 0, g, in, out);

        int[] weight = new int[n + 1];
        FenwickTree diff = new FenwickTree(n);

        for (int[] e : edges) {
            update(e[0], e[1], e[2], in, out, weight, diff);
        }

        List<Integer> ans = new ArrayList<>();
        for (int[] q : queries) {
            if (q[0] == 1) {
                update(q[1], q[2], q[3], in, out, weight, diff);
            } else {
                ans.add(diff.pre(in[q[1]]));
            }
        }
        return ans.stream().mapToInt(i -> i).toArray();
    }

    private int clock = 0;

    private void dfs(int x, int fa, List<Integer>[] g, int[] in, int[] out) {
        in[x] = ++clock; // 進來的時間
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x, g, in, out);
            }
        }
        out[x] = clock; // 離開的時間
    }

    private void update(int x, int y, int w, int[] in, int[] out, int[] weight, FenwickTree diff) {
        // 保證 y 是 x 的兒子
        if (in[x] > in[y]) {
            y = x;
        }
        int d = w - weight[y]; // 邊權的增量
        weight[y] = w;
        // 把子樹 y 中的最短路長度都增加 d（用差分樹狀數組維護）
        diff.update(in[y], d);
        diff.update(out[y] + 1, -d);
    }


    class FenwickTree {
        private final int[] tree;

        public FenwickTree(int n) {
            tree = new int[n + 1]; // 使用下標 1 到 n
        }

        // a[i] 增加 val
        // 1 <= i <= n
        public void update(int i, int val) {
            for (; i < tree.length; i += i & -i) {
                tree[i] += val;
            }
        }

        // 求前綴和 a[1] + ... + a[i]
        // 1 <= i <= n
        public int pre(int i) {
            int res = 0;
            for (; i > 0; i &= i - 1) {
                res += tree[i];
            }
            return res;
        }
    }


}
