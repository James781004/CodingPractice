package EndlessCheng.GenreMenu.Backtracking.NormalTree.TimeStamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumScore {

    // https://leetcode.cn/problems/minimum-score-after-removals-on-a-tree/solutions/1625899/dfs-shi-jian-chuo-chu-li-shu-shang-wen-t-x1kk/
    private List<Integer>[] g;
    private int[] nums, xor, in, out;
    private int clock;

    public int minimumScore(int[] nums, int[][] edges) {
        int n = nums.length;
        g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        this.nums = nums;
        xor = new int[n];
        in = new int[n];
        out = new int[n];
        dfs(0, -1);

        int ans = Integer.MAX_VALUE;
        for (int i = 2; i < n; i++) {
            for (int j = 1; j < i; j++) {
                int x, y, z;
                if (isAncestor(i, j)) { // i 是 j 的祖先節點
                    x = xor[j];
                    y = xor[i] ^ x;
                    z = xor[0] ^ xor[i];
                } else if (isAncestor(j, i)) { // j 是 i 的祖先節點
                    x = xor[i];
                    y = xor[j] ^ x;
                    z = xor[0] ^ xor[j];
                } else { // 刪除的兩條邊分別屬於兩棵不相交的子樹
                    x = xor[i];
                    y = xor[j];
                    z = xor[0] ^ x ^ y;
                }
                ans = Math.min(ans, Math.max(Math.max(x, y), z) - Math.min(Math.min(x, y), z));
                if (ans == 0) {
                    return 0; // 提前退出
                }
            }
        }
        return ans;
    }

    private void dfs(int x, int fa) {
        in[x] = ++clock;
        xor[x] = nums[x];
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x);
                xor[x] ^= xor[y];
            }
        }
        out[x] = clock;
    }

    private boolean isAncestor(int x, int y) {
        return in[x] < in[y] && in[y] <= out[x];
    }


}
