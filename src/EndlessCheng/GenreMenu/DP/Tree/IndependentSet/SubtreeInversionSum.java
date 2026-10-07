package EndlessCheng.GenreMenu.DP.Tree.IndependentSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubtreeInversionSum {

    // https://leetcode.cn/problems/subtree-inversion-sum/solutions/3673852/shu-xing-dppythonjavacgo-by-endlesscheng-pjwg/
    public long subtreeInversionSum(int[][] edges, int[] nums, int k) {
        int n = nums.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        List<long[]> f = new ArrayList<>();
        long[] res = dfs(0, -1, g, nums, k, f);
        return res[0] + res[1]; // 對於根節點來說，上面一定反轉了偶數次（0 次）
    }

    private long[] dfs(int x, int fa, List<Integer>[] g, int[] nums, int k, List<long[]> f) {
        f.add(new long[]{0, 0}); // 用於刷表

        long s = nums[x]; // 子樹和
        long notInv0 = 0; // 不反轉 x 時的額外增量（0 表示上面反轉了偶數次，1 表示上面反轉了奇數次）
        long notInv1 = 0;
        for (int y : g[x]) {
            if (y == fa) {
                continue;
            }
            long[] resY = dfs(y, x, g, nums, k, f);
            s += resY[0];
            // 不反轉 x，反轉次數的奇偶性不變
            notInv0 += resY[1];
            notInv1 += resY[2];
        }

        long[] subRes = f.removeLast(); // 被刷表後的結果

        // 反轉 x
        // x 上面反轉了偶數次，反轉 x 會帶來 -2 倍子樹和的增量，且對於 x 的 k 級後代來說，上面反轉了奇數次（所以是 subRes1）
        long inv0 = subRes[1] - s * 2;
        // x 上面反轉了奇數次，反轉 x 會帶來 2 倍子樹和的增量，且對於 x 的 k 級後代來說，上面反轉了偶數次（所以是 subRes0）
        long inv1 = subRes[0] + s * 2;

        long res0 = Math.max(notInv0, inv0);
        long res1 = Math.max(notInv1, inv1);

        // 刷表法：更新 x 的 k 級祖先的狀態
        if (f.size() >= k) {
            long[] ancestor = f.get(f.size() - k);
            ancestor[0] += res0;
            ancestor[1] += res1;
        }

        return new long[]{s, res0, res1};
    }


}
