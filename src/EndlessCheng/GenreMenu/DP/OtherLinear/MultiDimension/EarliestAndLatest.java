package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class EarliestAndLatest {

    // https://leetcode.cn/problems/the-earliest-and-latest-rounds-where-players-compete/solutions/825860/dpmei-ju-xia-yi-lun-liang-ming-xuan-shou-okfu/
    public int[] earliestAndLatest(int n, int firstPlayer, int secondPlayer) {
        int[][][][] memo = new int[n + 1][n + 1][n + 1][2];
        return dfs(n, firstPlayer, secondPlayer, memo);
    }

    // 當前有 n 人，A 的位置為 first，B 的位置為 second 的情況下，最早回合數和最晚回合數
    private int[] dfs(int n, int first, int second, int[][][][] memo) {
        // AB 相遇
        if (first + second == n + 1) {
            return new int[]{1, 1};
        }

        // 保證 A 左邊人數比 B 右邊人數少
        // 注：題目已保證 first < second
        if (first + second > n + 1) {
            int tmp = first;
            first = n + 1 - second;
            second = n + 1 - tmp;
        }

        int[] mem = memo[n][first][second];
        if (mem[0] > 0) { // 之前計算過
            return mem;
        }

        int m = (n + 1) / 2; // 下一回合人數
        // AB 之間保留 [minMid, maxMid) 個人
        int minMid = second <= m ? 0 : second - n / 2 - 1;
        int maxMid = second <= m ? second - first : m - first; // 左閉右開區間，無需減一
        int earliest = Integer.MAX_VALUE;
        int latest = 0;

        for (int left = 0; left < first; left++) { // 枚舉 A 左側保留 left 個人
            for (int mid = minMid; mid < maxMid; mid++) { // 枚舉 AB 之間保留 mid 個人
                // 無需枚舉 B 右側保留多少個人，因為剩下的 m-2-left-mid 個人都在 B 右側
                int[] res = dfs(m, left + 1, left + mid + 2, memo);
                earliest = Math.min(earliest, res[0]);
                latest = Math.max(latest, res[1]);
            }
        }

        // 加上當前回合
        mem[0] = earliest + 1;
        mem[1] = latest + 1;
        return mem;
    }


}
