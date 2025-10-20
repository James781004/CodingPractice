package EndlessCheng.GenreMenu.Backtracking.NormalTree.Multiply;

import java.util.Arrays;

public class PathExistenceQueries {

    // https://leetcode.cn/problems/path-existence-queries-in-a-graph-ii/solutions/3663266/pai-xu-shuang-zhi-zhen-bei-zeng-pythonja-ckht/
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        Integer[] idx = new Integer[n];
        Arrays.setAll(idx, i -> i);
        Arrays.sort(idx, (i, j) -> nums[i] - nums[j]);

        // rank[i] 表示 nums[i] 是 nums 中的第幾小，或者說節點 i 在 idx 中的下標
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[idx[i]] = i;
        }

        // 雙指針，從第 i 小的數開始，向左一步，最遠能跳到第 left 小的數
        int mx = 32 - Integer.numberOfLeadingZeros(n);
        int[][] pa = new int[n][mx];
        int left = 0;
        for (int i = 0; i < n; i++) {
            while (nums[idx[i]] - nums[idx[left]] > maxDiff) {
                left++;
            }
            pa[i][0] = left;
        }

        // 倍增
        for (int i = 0; i < mx - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                pa[x][i + 1] = pa[p][i];
            }
        }

        int[] ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int l = queries[qi][0];
            int r = queries[qi][1];
            if (l == r) { // 不用跳
                continue;
            }
            l = rank[l];
            r = rank[r];
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp; // 保證 l 在 r 左邊
            }
            // 從 r 開始，向左跳到 l
            int res = 0;
            for (int k = mx - 1; k >= 0; k--) {
                if (pa[r][k] > l) {
                    res |= 1 << k;
                    r = pa[r][k];
                }
            }
            ans[qi] = pa[r][0] > l ? -1 : res + 1; // 再跳一步就能到 l
        }
        return ans;
    }


}
