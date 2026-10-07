package EndlessCheng.GenreMenu.DP.Enhance.WQS;

public class MaximumScore {

    // https://leetcode.cn/problems/maximize-cyclic-partition-score/solutions/3827714/lai-dian-numpyke-ji-jia-su-yun-suan-by-s-5zio/
    // 輔助類別：用來記錄 (利潤 val, 交易/分割次數 cnt)
    private static class Pair {
        long val;
        int cnt;

        Pair(long val, int cnt) {
            this.val = val;
            this.cnt = cnt;
        }
    }

    private static final long NEG = -1_000_000_000_000_000_000L; // 用來表示極小值 (-INF)

    public long maximumScore(int[] nums, int k) {
        int n = nums.length;

        // 1. 找到陣列中的最大值位置 max_i
        int maxI = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[maxI]) {
                maxI = i;
            }
        }

        // 2. 破環成鏈：因為是環形陣列，最佳切割點只會在最大值的兩側
        // 切分方式 1: 從 maxI 開始重組陣列 [maxI ... n-1, 0 ... maxI-1]
        int[] arr1 = new int[n];
        for (int i = 0; i < n; i++) {
            arr1[i] = nums[(maxI + i) % n];
        }

        // 切分方式 2: 從 maxI + 1 開始重組陣列 [(maxI + 1) ... n-1, 0 ... maxI]
        int[] arr2 = new int[n];
        for (int i = 0; i < n; i++) {
            arr2[i] = nums[(maxI + 1 + i) % n];
        }

        // 3. 兩次求解取最大值
        long ans1 = maximumProfit(arr1, k);
        long ans2 = maximumProfit(arr2, k);

        return Math.max(ans1, ans2);
    }

    // 核心：利用 WQS 二分求解「限制最多 k 次分割/交易的最大得分」
    private long maximumProfit(int[] prices, int k) {
        // 先測試懲罰項 lam = 0 時的情況
        Pair p0 = cal(prices, 0);
        if (p0.cnt <= k) {
            return p0.val;
        }

        // 尋找二分搜尋的上限 hi
        long maxVal = 0;
        for (int p : prices) {
            maxVal = Math.max(maxVal, p);
        }
        long hi = maxVal + 2;

        // Python 的 bisect_left(range(hi), True, key=check) - 1 的 Java 對應二分寫法
        // 尋找滿足 cal(lam).cnt >= k 的最大 lam (即 cal(lam).cnt < k 的臨界點 lam)
        long left = 0, right = hi;
        long targetLam = 0;

        while (left <= right) {
            long mid = left + (right - left) / 2;
            Pair res = cal(prices, mid);

            if (res.cnt >= k) {
                targetLam = mid;
                left = mid + 1; // 嘗試增強懲罰，減少分割次數
            } else {
                right = mid - 1; // 懲罰太重導致分割次數 < k，減輕懲罰
            }
        }

        Pair finalRes = cal(prices, targetLam);
        return finalRes.val + (long) k * targetLam;
    }

    // DP 狀態機：給定懲罰項 lam，計算最佳得分與分割次數
    private Pair cal(int[] prices, long lam) {
        Pair dp0 = new Pair(0, 0);      // 空倉 / 未在段落中
        Pair dpL = new Pair(NEG, 0);    // 做多中 (持多)
        Pair dpS = new Pair(NEG, 0);    // 做空中 (持空)

        for (int p : prices) {
            Pair prev0 = dp0;
            Pair prevL = dpL;
            Pair prevS = dpS;

            // 保持不變
            dp0 = prev0;

            // 1. 平倉（完成一個子段分割）：扣除懲罰項 lam，分割次數 cnt + 1
            Pair flatL = new Pair(prevL.val + p - lam, prevL.cnt + 1); // 平多
            dp0 = better(dp0, flatL);

            Pair flatS = new Pair(prevS.val - p - lam, prevS.cnt + 1); // 平空
            dp0 = better(dp0, flatS);

            // 2. 開倉（開啟一個新子段分割）：只能從之前的空倉狀態轉移
            Pair openL = new Pair(prev0.val - p, prev0.cnt); // 開多
            dpL = better(prevL, openL);

            Pair openS = new Pair(prev0.val + p, prev0.cnt); // 開空
            dpS = better(prevS, openS);
        }

        return dp0;
    }

    // 比較函數：挑選更優的 Pair
    // 優先追求得分 (val) 最大；若得分相同，追求分割次數 (cnt) 更多
    private Pair better(Pair a, Pair b) {
        if (a.val != b.val) {
            return a.val > b.val ? a : b;
        }
        return a.cnt > b.cnt ? a : b;
    }

}
