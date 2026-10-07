package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

import java.util.Arrays;

public class MaxWalls {

    // https://leetcode.cn/problems/maximum-walls-destroyed-by-robots/solutions/3762127/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-dzd9/
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        int[][] a = new int[n + 2][2];
        for (int i = 0; i < n; i++) {
            a[i][0] = robots[i];
            a[i][1] = distance[i];
        }

        // 添加兩個位置分別為 0 和 ∞ 的機器人，當作哨兵，從而簡化邊界的判斷
        a[n + 1][0] = Integer.MAX_VALUE;
        Arrays.sort(a, (p, q) -> p[0] - q[0]);
        Arrays.sort(walls);

        int[][] memo = new int[n + 1][2];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n, 1, a, walls, memo);
    }

    // 表示對於（排序後）下標在 [0,i] 中的機器人，在機器人 i+1 往左/右射擊的前提下，能摧毀的最大牆壁數量。
    // 其中 j=0 表示機器人 i+1 往左射擊，j=1 表示機器人 i+1 往右射擊。
    private int dfs(int i, int j, int[][] a, int[] walls, int[][] memo) {
        if (i == 0) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        int x = a[i][0], d = a[i][1];
        // 往左射，牆的坐標范圍為 [leftX, x]
        int leftX = Math.max(x - d, a[i - 1][0] + 1); // +1 表示不能射到左邊那個機器人
        int left = lowerBound(walls, leftX);
        int cur = lowerBound(walls, x + 1);
        int resLeft = dfs(i - 1, 0, a, walls, memo) + cur - left; // 下標在 [left, cur-1] 中的牆都能摧毀

        // 往右射，牆的坐標范圍為 [x, rightX]
        int x2 = a[i + 1][0];
        if (j == 0) { // 右邊那個機器人往左射
            x2 -= a[i + 1][1];
        }
        int rightX = Math.min(x + d, x2 - 1); // -1 表示不能射到右邊那個機器人（或者它往左射到的牆）
        int right = lowerBound(walls, rightX + 1);
        cur = lowerBound(walls, x);
        int resRight = dfs(i - 1, 1, a, walls, memo) + right - cur; // 下標在 [cur, right-1] 中的牆都能摧毀

        return memo[i][j] = Math.max(resLeft, resRight); // 記憶化
    }

    public int maxWallsDP(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        int[][] a = new int[n + 2][2];
        for (int i = 0; i < n; i++) {
            a[i][0] = robots[i];
            a[i][1] = distance[i];
        }
        a[n + 1][0] = Integer.MAX_VALUE;
        Arrays.sort(a, (p, q) -> p[0] - q[0]);
        Arrays.sort(walls);

        int[][] f = new int[n + 1][2];
        for (int i = 1; i <= n; i++) {
            int x = a[i][0], d = a[i][1];

            // 往左射，牆的坐標范圍為 [leftX, x]
            int leftX = Math.max(x - d, a[i - 1][0] + 1); // +1 表示不能射到左邊那個機器人
            int left = lowerBound(walls, leftX);
            int cur = lowerBound(walls, x + 1);
            int leftRes = f[i - 1][0] + cur - left; // 下標在 [left, cur-1] 中的牆都能摧毀

            cur = lowerBound(walls, x);
            for (int j = 0; j < 2; j++) {
                // 往右射，牆的坐標范圍為 [x, rightX]
                int x2 = a[i + 1][0];
                if (j == 0) { // 右邊那個機器人往左射
                    x2 -= a[i + 1][1];
                }
                int rightX = Math.min(x + d, x2 - 1); // -1 表示不能射到右邊那個機器人（或者它往左射到的牆）
                int right = lowerBound(walls, rightX + 1);
                f[i][j] = Math.max(leftRes, f[i - 1][1] + right - cur); // 下標在 [cur, right-1] 中的牆都能摧毀
            }
        }
        return f[n][1];
    }


    public int maxWallsDP2(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        int[][] a = new int[n + 2][2];
        for (int i = 0; i < n; i++) {
            a[i][0] = robots[i];
            a[i][1] = distance[i];
        }
        a[n + 1][0] = Integer.MAX_VALUE;
        Arrays.sort(a, (p, q) -> p[0] - q[0]);
        Arrays.sort(walls);

        int[] f = new int[2];
        for (int i = 1; i <= n; i++) {
            int x = a[i][0], d = a[i][1];

            // 往左射，牆的坐標范圍為 [leftX, x]
            int leftX = Math.max(x - d, a[i - 1][0] + 1); // +1 表示不能射到左邊那個機器人
            int left = lowerBound(walls, leftX);
            int cur = lowerBound(walls, x + 1);
            int leftRes = f[0] + cur - left; // 下標在 [left, cur-1] 中的牆都能摧毀

            cur = lowerBound(walls, x);
            for (int j = 0; j < 2; j++) {
                // 往右射，牆的坐標范圍為 [x, rightX]
                int x2 = a[i + 1][0];
                if (j == 0) { // 右邊那個機器人往左射
                    x2 -= a[i + 1][1];
                }
                int rightX = Math.min(x + d, x2 - 1); // -1 表示不能射到右邊那個機器人（或者它往左射到的牆）
                int right = lowerBound(walls, rightX + 1);
                f[j] = Math.max(leftRes, f[1] + right - cur); // 下標在 [cur, right-1] 中的牆都能摧毀
            }
        }
        return f[1];
    }


    private int lowerBound(int[] nums, int target) {
        int left = -1;
        int right = nums.length;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

}
