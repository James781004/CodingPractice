package WeeklyContest;

import java.util.Arrays;

class Week_350 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2739.Total%20Distance%20Traveled/README.md
    public int distanceTraveled(int mainTank, int additionalTank) {
        int ans = 0, cur = 0;

        // 一直模擬到主油箱中的燃料消耗完為止
        while (mainTank > 0) {
            cur++;
            ans += 10;
            mainTank--;

            // 每當主油箱中的燃料消耗 5 升時，
            // 如果副油箱中有燃料，則將副油箱中的 1 升燃料轉移到主油箱中。
            if (cur % 5 == 0 && additionalTank > 0) {
                additionalTank--;
                mainTank++;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2740.Find%20the%20Value%20of%20the%20Partition/README.md
    public int findValueOfPartition(int[] nums) {
        Arrays.sort(nums);
        int ans = 1 << 30;
        for (int i = 1; i < nums.length; ++i) {
            ans = Math.min(ans, nums[i] - nums[i - 1]);
        }
        return ans;
    }


    // https://leetcode.cn/problems/special-permutations/solution/zhuang-ya-dp-by-endlesscheng-4jkr/
    // 位運算技巧： https://leetcode.cn/circle/discuss/CaOJ45/
    class SpecialPerm {
        private static final int MOD = (int) 1e9 + 7;
        private int[][] f = new int[1 << 14][14];
        private int n;

        private int dfs(int i, int j, int[] nums) {
            if (i == 0) return 1;
            if (f[i][j] > 0) return f[i][j];
            int res = 0;
            for (int k = 0; k < n; ++k) {
                // (i >> k) & 1 == 1 判斷i屬於k集合
                if (((i >> k) & 1) == 1 && (nums[j] % nums[k] == 0 || nums[k] % nums[j] == 0)) {
                    // i ^ (1 << k) 刪除1，表示集合刪除元素
                    res = (res + dfs(i ^ (1 << k), k, nums)) % MOD;
                }
            }
            return f[i][j] = res % MOD;
        }

        public int specialPerm(int[] nums) {
            n = nums.length;
            int ans = 0;
            for (int i = 0; i < n; ++i) {
                // (1 << n) - 1 求全集
                // ((1 << n) - 1) ^ (1 << i) 全集刪除元素i位置的1，表示集合刪除元素
                ans = (ans + dfs(((1 << n) - 1) ^ (1 << i), i, nums)) % MOD;
            }
            return ans % MOD;
        }


        public int specialPermDP(int[] nums) {
            int n = nums.length;
            int[][] f = new int[1 << n][n];
            for (int i = 0; i < n; ++i) f[0][i] = 1;
            for (int i = 0; i < 1 << n; ++i) {
                for (int j = 0; j < n; ++j) {
                    for (int k = 0; k < n; ++k) {
                        if (((i >> k) & 1) == 1 && (nums[j] % nums[k] == 0 || nums[k] % nums[j] == 0)) {
                            f[i][j] = (f[i][j] + f[i ^ (1 << k)][k]) % MOD;
                        }
                    }
                }
            }
            int ans = 0;
            for (int i = 0; i < n; ++i) ans = (ans + f[((1 << n) - 1) ^ (1 << i)][i]) % MOD;
            return ans;
        }
    }


    // https://leetcode.cn/problems/painting-the-walls/solution/xuan-huo-bu-xuan-de-dian-xing-si-lu-by-e-ulcd/
    // https://www.bilibili.com/video/BV1Hj411D7Tr/
    class PaintWalls {
        int n;
        int[][] memo;
        int[] cost, time;

        public int paintWalls(int[] cost, int[] time) {
            this.n = cost.length;
            this.cost = cost;
            this.time = time;
            this.memo = new int[n][2 * n + 1];// 免費時長可以為負數，因此需要加偏移量
            for (int i = 0; i < n; i++) {
                Arrays.fill(memo[i], -1);
            }
            return dfs(n - 1, n);// 偏移量防止負數
        }

        // 免費時長為j，刷前i片牆需要的最小花費
        public int dfs(int i, int j) {
            if (i < j - n) return 0;// 剩余所有牆都可以由免費油漆工刷
            if (i < 0) return 0x3f3f3f3f; // i已經小於零且j小於0(這邊偏移變成小於n)，免費時間沒還清就不符合條件
            if (memo[i][j] != -1) return memo[i][j];
            // 免費油漆工刷
            int res = dfs(i - 1, j - 1);
            // 付費油漆工刷
            res = Math.min(res, dfs(i - 1, j + time[i]) + cost[i]);
            return memo[i][j] = res;
        }


        // 01背包
        public int paintWallsDP(int[] cost, int[] time) {
            int n = cost.length;
            int[] f = new int[n + 1];
            Arrays.fill(f, Integer.MAX_VALUE / 2); // 防止加法溢出
            f[0] = 0;
            for (int i = 0; i < n; i++) {
                int c = cost[i], t = time[i] + 1;  // 注意這裡加一了
                for (int j = n; j > 0; j--)
                    f[j] = Math.min(f[j], f[Math.max(j - t, 0)] + c);
            }
            return f[n];
        }
    }
}

