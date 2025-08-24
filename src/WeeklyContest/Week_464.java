package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_464 {

    // https://leetcode.cn/problems/gcd-of-odd-and-even-sums/solutions/3762080/da-an-jiu-shi-npythonjavacgo-by-endlessc-u100/
    public int gcdOfOddEvenSums(int n) {
        return n;
    }


    // https://leetcode.cn/problems/partition-array-into-k-distinct-groups/solutions/3762099/jie-lun-ti-pythonjavacgo-by-endlesscheng-dfzw/
    public boolean partitionArray(int[] nums, int k) {
        int n = nums.length;
        if (n % k > 0) {
            return false;
        }
        Map<Integer, Integer> cnt = new HashMap<>();
        int mx = 0;
        for (int x : nums) {
            int c = cnt.merge(x, 1, Integer::sum); // c = ++cnt[x]
            mx = Math.max(mx, c);
        }
        return mx <= n / k;
    }


    // https://leetcode.cn/problems/jump-game-ix/solutions/3762167/jie-lun-ti-pythonjavacgo-by-endlesscheng-x2qu/
    public int[] maxValue(int[] nums) {
        int n = nums.length;
        int[] preMax = new int[n];
        preMax[0] = nums[0];
        for (int i = 1; i < n; i++) {
            preMax[i] = Math.max(preMax[i - 1], nums[i]);
        }

        int sufMin = Integer.MAX_VALUE;
        int mx = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (preMax[i] <= sufMin) {
                mx = preMax[i]; // 無法跳到 [i+1,n-1] 中，只能跳到 [0,i] 中的最大值
            }
            sufMin = Math.min(sufMin, nums[i]);
            nums[i] = mx;
        }
        return nums;
    }


    // https://leetcode.cn/problems/maximum-walls-destroyed-by-robots/solutions/3762127/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-dzd9/
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        int[][] a = new int[n][2];
        for (int i = 0; i < n; i++) {
            a[i][0] = robots[i];
            a[i][1] = distance[i];
        }
        Arrays.sort(a, (p, q) -> p[0] - q[0]);
        Arrays.sort(walls);

        int[][] memo = new int[n][2];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, 1, a, walls, memo);
    }

    private int dfs(int i, int j, int[][] a, int[] walls, int[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        // 往左射
        int leftX = a[i][0] - a[i][1];
        if (i > 0) {
            leftX = Math.max(leftX, a[i - 1][0] + 1); // 不能到達左邊那個機器人
        }
        int left = lowerBound(walls, leftX);
        int cur = lowerBound(walls, a[i][0] + 1);
        int res = dfs(i - 1, 0, a, walls, memo) + cur - left; // [left, cur-1] 中的牆都能摧毀

        // 往右射
        int rightX = a[i][0] + a[i][1];
        if (i + 1 < a.length) {
            int x2 = a[i + 1][0];
            if (j == 0) { // 右邊那個機器人往左射
                x2 -= a[i + 1][1];
            }
            rightX = Math.min(rightX, x2 - 1); // 不能到達右邊那個機器人（或者他往左射到的牆）
        }
        int right = lowerBound(walls, rightX + 1);
        cur = lowerBound(walls, a[i][0]);
        res = Math.max(res, dfs(i - 1, 1, a, walls, memo) + right - cur); // [cur, right-1] 中的牆都能摧毀
        return memo[i][j] = res; // 記憶化
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









