package WeeklyContest;

import java.util.*;

public class Week_369 {
    // https://leetcode.cn/problems/find-the-k-or-of-an-array/solutions/2503321/shi-yong-shu-zhuang-shu-zu-de-lowbitkuai-46wg/
    // 一、使用位移運算查看每個元素的31位；
    // 二、數組使用 i &（-i）返回的結果就是 i 二進制最後一個 1 對應的大小
    public int findKOr(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            while (num > 0) {
                int last = get(num);
                map.put(last, map.getOrDefault(last, 0) + 1);
                num -= last;
            }
        }
        int ans = 0;
        for (Map.Entry<Integer, Integer> node : map.entrySet()) {
            if (node.getValue() >= k) {
                ans += node.getKey();
            }
        }
        return ans;
    }

    private int get(int i) {
        return i & (-i);

    }


    // https://leetcode.cn/problems/minimum-equal-sum-of-two-arrays-after-replacing-zeros/solutions/2503247/ji-shu-by-oamuuvyi-hfeg/
    // 計算兩個數組的 0 的個數以及數組的和，分別記為 countZero1、sum1、countZero2 和 sum2，
    // 那麼數組的最小相等和 minSum=max{countZero1+sum1,countZero2+sum2}，(這邊是把0直接轉成1的狀況)。
    // 但如果某個數組 0 的個數為 0，並且數組的和小於 minSum，無法把它的和其增加到 minSum，
    // 也就是說兩個數組的和無法相等。
    public long minSum(int[] nums1, int[] nums2) {
        int countZero1 = 0, countZero2 = 0;
        long sum1 = 0, sum2 = 0;
        for (int num : nums1) {
            countZero1 += num == 0 ? 1 : 0;
            sum1 += num;
        }
        for (int num : nums2) {
            countZero2 += num == 0 ? 1 : 0;
            sum2 += num;
        }
        long minSum = Math.max(countZero1 + sum1, countZero2 + sum2);
        if ((countZero1 == 0 && countZero2 == 0 && sum1 != sum2) ||
                (countZero1 == 0 && sum1 < minSum) ||
                (countZero2 == 0 && sum2 < minSum)) {
            return -1;
        }
        return minSum;
    }


    // https://leetcode.cn/problems/minimum-increment-operations-to-make-array-beautiful/solutions/2503293/dong-tai-gui-hua-by-oamuuvyi-arov/
    public long minIncrementOperations(int[] nums, int k) {
        long[] dp = new long[3]; // dp[i] 表示修改第 i 項並使前 i 項變為美麗數組的最小修改次數
        for (int i = 0; i < 3; i++) {
            dp[i] = Math.max(0, k - nums[i]); // nums[i] 不增大 or nums[i] 增大
        }
        for (int i = 3; i < nums.length; i++) {
            long cur = Math.min(Math.min(dp[0], dp[1]), dp[2]) + Math.max(0, k - nums[i]);
            dp[0] = dp[1];
            dp[1] = dp[2];
            dp[2] = cur;
        }
        return Math.min(Math.min(dp[0], dp[1]), dp[2]);
    }

    // https://leetcode.cn/problems/minimum-increment-operations-to-make-array-beautiful/solutions/2503157/qiao-miao-she-ji-zhuang-tai-xuan-huo-bu-8547u/
    // 記憶化搜索
    // 把大於 k 的元素視作 k。
    // 由於大於 3 的子數組必然包含等於 3 的子數組，問題轉換成：每個長為 3 的子數組都需要包含至少一個 k。
    // 考慮最後一個元素「選或不選」，即是否增大：
    // 增大到 k：那麼對於左邊那個數來說，它右邊就有一個 k 了。
    // 不增大：那麼對於左邊那個數來說，它右邊有一個沒有增大的數。
    // 進一步地，如果倒數第二個數也不增大，那麼對於倒數第三個數，它右邊就有兩個沒有增大的數，那麼它一定要增大
    // （不用管右邊那兩個數是否為 k，因為下面的 dfs 會考慮到所有的情況，取最小值）。
    // 因此，用 i 表示當前位置，j 表示右邊有幾個沒有增大的數。
    // 定義 dfs(i,j) 表示在該狀態下對於前 i 個數的最小遞增運算數。
    // 增大到 k，即 dfs(i−1,0)+max(k−nums[i],0)。
    // 如果 j<2，則可以不增大，即 dfs(i−1,j+1)。
    // 這兩種情況取最小值。
    public long minIncrementOperationsMemo(int[] nums, int k) {
        int n = nums.length;
        long[][] memo = new long[n][3];
        for (long[] m : memo) {
            Arrays.fill(m, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, 0, memo, nums, k);
    }

    private long dfs(int i, int j, long[][] memo, int[] nums, int k) {
        if (i < 0) { // 遞歸邊界
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        long res = dfs(i - 1, 0, memo, nums, k) + Math.max(k - nums[i], 0); // nums[i] 增大
        if (j < 2) res = Math.min(res, dfs(i - 1, j + 1, memo, nums, k)); // nums[i] 不增大
        return memo[i][j] = res; // 記憶化
    }


    /// https://leetcode.cn/problems/maximum-points-after-collecting-coins-from-all-nodes/solutions/2503152/shu-xing-dp-ji-yi-hua-sou-suo-by-endless-phzx/
    public int maximumPoints(int[][] edges, int[] coins, int k) {
        int n = coins.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        int[][] memo = new int[n][14];
        for (int[] m : memo) {
            Arrays.fill(m, -1); // -1 表示沒有計算過
        }
        return dfs(0, 0, -1, memo, g, coins, k);
    }

    private int dfs(int i, int j, int fa, int[][] memo, List<Integer>[] g, int[] coins, int k) {
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res1 = (coins[i] >> j) - k;
        int res2 = coins[i] >> (j + 1);
        for (int ch : g[i]) {
            if (ch == fa) continue;
            res1 += dfs(ch, j, i, memo, g, coins, k); // 不右移
            if (j < 13) { // j+1 >= 14 相當於 res2 += 0，無需遞歸
                res2 += dfs(ch, j + 1, i, memo, g, coins, k); // 右移
            }
        }
        return memo[i][j] = Math.max(res1, res2); // 記憶化
    }
}
