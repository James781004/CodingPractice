package EndlessCheng.GenreMenu.Backtracking.Backtracking.Permutations;

import java.util.List;

public class FindMinimumTime {

    // https://leetcode.cn/problems/minimum-time-to-break-locks-i/solutions/3014389/san-chong-fang-fa-pai-lie-xing-hui-su-zh-cnpe/
    public int findMinimumTime(List<Integer> strength, int k) {
        boolean[] done = new boolean[strength.size()];
        dfs(0, 0, strength.toArray(Integer[]::new), k, done);
        return ans;
    }

    private int ans = Integer.MAX_VALUE;

    private void dfs(int i, int time, Integer[] strength, int k, boolean[] done) {
        // 剪枝：答案不可能變小
        if (time >= ans) {
            return;
        }
        if (i == strength.length) {
            ans = time;
            return;
        }
        int x = 1 + k * i;
        for (int j = 0; j < strength.length; j++) {
            if (!done[j]) {
                done[j] = true; // 已開鎖
                dfs(i + 1, time + (strength[j] - 1) / x + 1, strength, k, done);
                done[j] = false; // 恢復現場
            }
        }
    }


}
