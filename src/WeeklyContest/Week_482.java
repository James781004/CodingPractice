package WeeklyContest;

public class Week_482 {

    // https://leetcode.cn/problems/maximum-score-of-a-split/solutions/3867597/qian-hou-zhui-fen-jie-o1-kong-jian-you-h-ytf3/
    public long maximumScore(int[] nums) {
        int n = nums.length;
        int[] sufMin = new int[n]; // 後綴最小值
        sufMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            sufMin[i] = Math.min(sufMin[i + 1], nums[i]);
        }

        long ans = Long.MIN_VALUE;
        long preSum = 0; // 前綴和
        for (int i = 0; i < n - 1; i++) {
            preSum += nums[i];
            ans = Math.max(ans, preSum - sufMin[i + 1]);
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-cost-to-acquire-required-items/solutions/3867591/fen-lei-tao-lun-o1-zuo-fa-pythonjavacgo-gg05w/
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long res1 = (long) cost1 * need1 + (long) cost2 * need2; // 各買各的
        if (need1 > need2) {
            int tmp = need1;
            need1 = need2;
            need2 = tmp;
            cost2 = cost1;
        }
        long res2 = (long) costBoth * need2; // 我包了
        long res3 = (long) costBoth * need1 + (long) cost2 * (need2 - need1); // 混合策略
        return Math.min(res1, Math.min(res2, res3));
    }


    // https://leetcode.cn/problems/smallest-all-ones-multiple/solutions/3867638/ge-chao-yuan-li-de-yun-yong-by-adoring-m-9xdl/
    public int minAllOneMultiple(int k) {
        // 如果 k 能被 2 或 5 整除，那麽以 1 結尾的數不可能被 k 整除
        if (k % 2 == 0 || k % 5 == 0) {
            return -1;
        }

        int cur = 0;  // 當前余數，初始為 0

        // 嘗試從 1 位到 k 位的全 1 數字
        for (int i = 1; i <= k; i++) {
            // 在當前數字末尾加一個 1，並計算新的余數
            cur = (cur * 10 + 1) % k;

            // 如果余數為 0，說明找到了滿足條件的數字
            if (cur == 0) {
                return i;  // 返回當前數字的位數
            }
        }

        // 如果循環了 k 次還沒找到，說明不存在這樣的數字
        return -1;
    }


    // https://leetcode.cn/problems/number-of-balanced-integers-in-a-range/solutions/3867594/mo-ban-shang-xia-jie-shu-wei-dppythonjav-lnd8/
    public long countBalanced(long low, long high) {
        // 最小的滿足要求的數是 11
        if (high < 11) {
            return 0;
        }

        low = Math.max(low, 11);
        char[] lowS = String.valueOf(low).toCharArray();
        char[] highS = String.valueOf(high).toCharArray();

        int n = highS.length;
        // diff 至少 floor(n/2) * 9，至多 ceil(n/2) * 9，值域大小 n * 9
        long[][][] memo = new long[n][n * 9 + 1][2];

        return dfs(0, n / 2 * 9, 1, true, true, lowS, highS, memo);
    }

    private long dfs(int i, int diff, int parity, boolean limitLow, boolean limitHigh, char[] lowS, char[] highS, long[][][] memo) {
        int n = highS.length;
        if (i == n) {
            return diff == n / 2 * 9 ? 1 : 0;
        }

        if (!limitLow && !limitHigh && memo[i][diff][parity] > 0) {
            return memo[i][diff][parity] - 1; // 記憶化的時候 +1，這裡減掉
        }

        int diffLH = n - lowS.length;
        int lo = limitLow && i >= diffLH ? lowS[i - diffLH] - '0' : 0;
        int hi = limitHigh ? highS[i] - '0' : 9;

        long res = 0;
        int d = lo;

        // 通過 limitLow 和 i 可以判斷能否不填數字，無需 isNum 參數
        if (limitLow && i < diffLH) {
            // 不填數字，上界不受約束
            res = dfs(i + 1, diff, parity, true, false, lowS, highS, memo);
            d = 1; // 下面填數字，至少從 1 開始填
        }

        for (; d <= hi; d++) {
            res += dfs(i + 1,
                    diff + (parity > 0 ? d : -d),
                    parity ^ 1, // 下一個位置奇偶性翻轉
                    limitLow && d == lo,
                    limitHigh && d == hi,
                    lowS, highS, memo);
        }

        if (!limitLow && !limitHigh) {
            memo[i][diff][parity] = res + 1; // 記憶化的時候加一，這樣 memo 可以初始化成 0
        }
        return res;
    }


}









