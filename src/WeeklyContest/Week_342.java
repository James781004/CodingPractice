package WeeklyContest;

public class Week_342 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2651.Calculate%20Delayed%20Arrival%20Time/README.md
    public int findDelayedArrivalTime(int arrivalTime, int delayedTime) {
        return (arrivalTime + delayedTime) % 24;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2652.Sum%20Multiples/README.md
    public int sumOfMultiples(int n) {
        int ans = 0;
        for (int x = 1; x <= n; ++x) {
            if (x % 3 == 0 || x % 5 == 0 || x % 7 == 0) {
                ans += x;
            }
        }
        return ans;
    }


    // https://www.bilibili.com/video/BV1Bs4y1A7Wa/
    // https://leetcode.cn/problems/sliding-subarray-beauty/solution/hua-dong-chuang-kou-bao-li-mei-ju-by-end-9mvl/
    // 由於值域很小，所以借鑑計數排序，用一個 cnt 數組維護窗口內每個數的出現次數。
    // 然後遍歷 cnt 去求第 x 小的數。
    // 什麼是第 x 小的數？
    // 設它是 num，那麼 < num 的數有 <x 個，≤num 的數有 ≥x 個，
    // 就說明 num 是第 x 小的數。
    public int[] getSubArrayBeauty(int[] nums, int k, int x) {
        final int BIAS = 50;
        int[] cnt = new int[BIAS * 2 + 1]; // 統計 -50 ~ 50 之間數字數量
        int n = nums.length;
        for (int i = 0; i < k - 1; ++i) // 先往窗口內添加 k-1 個數
            ++cnt[nums[i] + BIAS];

        int[] ans = new int[n - k + 1];
        for (int i = k - 1; i < n; ++i) {
            ++cnt[nums[i] + BIAS]; // 進入窗口（保證窗口有恰好 k 個數）
            int left = x;
            for (int j = 0; j < BIAS; ++j) { // 暴力枚舉負數范圍 [-50,-1]
                left -= cnt[j];
                if (left <= 0) { // 找到美麗值: 比 j 小的數已經有超過x個，j必定是第x小的數
                    ans[i - k + 1] = j - BIAS;
                    break;
                }
            }
            --cnt[nums[i - k + 1] + BIAS]; // 離開窗口
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-number-of-operations-to-make-all-array-elements-equal-to-1/solution/liang-chong-fang-fa-bao-li-mei-ju-li-yon-refp/
    public int minOperations(int[] nums) {
        int n = nums.length, gcdAll = 0, cnt1 = 0;
        for (int x : nums) {
            gcdAll = gcd(gcdAll, x);
            if (x == 1) ++cnt1;
        }
        if (gcdAll > 1) return -1;  // 如果所有數的 GCD（最大公約數）大於 1，那麼無論如何都無法操作出1，返回 -1
        if (cnt1 > 0) return n - cnt1;  // 如果 nums 中有一個 1，那麼從 1 向左向右不斷替換就能把所有數變成 1

        // 如果 nums 中沒有 1
        // 操作相鄰的數，這個 1 必然是一個連續子數組的 GCD
        int minSize = n;
        for (int i = 0; i < n; ++i) {
            int g = 0;
            for (int j = i; j < n; ++j) {
                g = gcd(g, nums[j]);
                if (g == 1) {  // 找到gcd是1的連續子數組，統計 minSize 長度，只要操作 minSize - 1 次就可以得到 1
                    // 這裡本來是 j-i+1，把 +1 提出來合並到 return 中
                    minSize = Math.min(minSize, j - i);
                    break;
                }
            }
        }
        return minSize + n - 1;  // 這裡本來是 minSize + n - 2
    }


    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

}
