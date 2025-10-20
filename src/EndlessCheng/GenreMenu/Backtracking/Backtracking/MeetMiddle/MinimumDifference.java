package EndlessCheng.GenreMenu.Backtracking.Backtracking.MeetMiddle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimumDifference {

    // https://leetcode.cn/problems/partition-array-into-two-arrays-to-minimize-sum-difference/solutions/1039419/zhe-ban-mei-ju-pai-xu-er-fen-by-endlessc-04fn/
    private List<List<Integer>> q; // 左半邊選取 i 個正數的所有情況
    private int n, m, res;

    /*
        meet in the middle
        兩數組之差可以看作一個數組取正 另一個數組取負的元素和
        1.求枚舉左半邊的所有情況
        2.然後枚舉右半邊的情況
        後半段是cnt個正數, 此時在前半段從q[cnt]二分查找接近s的數
        由於求和後還要做差 那麼後半段的cnt正數 就變為了m - cnt個正數
    */
    public int minimumDifference(int[] nums) {
        n = nums.length;
        m = n / 2;
        res = Integer.MAX_VALUE;

        q = new ArrayList<>();
        for (int i = 0; i <= m; i++) {
            q.add(new ArrayList<>());
        }

        dfs1(nums, 0, 0, 0);

        for (int i = 0; i <= m; i++) {
            Collections.sort(q.get(i));
        }

        dfs2(nums, m, 0, 0);

        return res;
    }


    // 枚舉左半邊
    private void dfs1(int[] a, int u, int s, int cnt) { // cnt = 正數個數
        if (u == m) {
            q.get(cnt).add(s);
            return;
        }
        dfs1(a, u + 1, s + a[u], cnt + 1); // 選為正數
        dfs1(a, u + 1, s - a[u], cnt);     // 選為負數
    }

    // 枚舉右半邊
    private void dfs2(int[] a, int u, int s, int cnt) {
        if (cnt > m) return;
        if (u == n) {
            List<Integer> cur = q.get(cnt);
            int t = cur.size();
            int l = 0, r = t - 1;

            // 在 cur 中二分查找 <= s 的最大值
            while (l < r) {
                int mid = (l + r + 1) >> 1;
                if (cur.get(mid) <= s) {
                    l = mid;
                } else {
                    r = mid - 1;
                }
            }

            res = Math.min(res, Math.abs(s - cur.get(l)));
            if (l + 1 < t) {
                res = Math.min(res, Math.abs(s - cur.get(l + 1)));
            }
            return;
        }

        dfs2(a, u + 1, s + a[u], cnt + 1); // 選為正數
        dfs2(a, u + 1, s - a[u], cnt);     // 選為負數
    }
}
