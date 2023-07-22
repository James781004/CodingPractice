package WeeklyContest;

import java.util.Arrays;

class Week_353 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2769.Find%20the%20Maximum%20Achievable%20Number/README.md
    public int theMaximumAchievableX(int num, int t) {
        return num + t * 2;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2770.Maximum%20Number%20of%20Jumps%20to%20Reach%20the%20Last%20Index/README.md
    // 對於每個位置 i，我們考慮向後搜索能跳到的位置 j，如果滿足 nums[j] - nums[i] <= target，
    // 那麼可以從 i 跳到 j，並且從 j 開始繼續向後搜索。
    private Integer[] f;
    private int[] nums;
    private int n;
    private int target;

    public int maximumJumps(int[] nums, int target) {
        n = nums.length;
        this.target = target;
        this.nums = nums;
        f = new Integer[n];
        int ans = dfs(0);
        return ans < 0 ? -1 : ans;
    }

    // 設計一個函數 dfs(i)，表示從位置 i 開始跳躍到末尾下標所需的最大跳躍次數。那麼答案就是 dfs(0)。
    private int dfs(int i) {
        if (i == n - 1) {
            return 0; // 已經到達了末尾下標，不需要跳躍，因此返回 0;
        }
        if (f[i] != null) {
            return f[i]; // 記憶化搜索
        }
        int ans = -(1 << 30);

        // 枚舉從位置 i 開始能跳到的位置 j，並計算從 j 開始跳躍到末尾下標所需的最大跳躍次數
        // dfs(i) 就等於所有 dfs(j) 中的最大值加 1
        // 如果不存在從 i 開始能跳到的位置 j，那麼 dfs(i) = -inf
        for (int j = i + 1; j < n; ++j) {
            if (Math.abs(nums[i] - nums[j]) <= target) {
                ans = Math.max(ans, 1 + dfs(j));
            }
        }
        return f[i] = ans;
    }


    // https://leetcode.cn/problems/maximum-number-of-jumps-to-reach-the-last-index/solution/dong-tai-gui-hua-cong-ji-yi-hua-sou-suo-2ptkg/
    // java 版本遞推dp
    public int maximumJumpsDP(int[] nums, int target) {
        int n = nums.length;
        int[] dp = new int[n];  // dp[i]表示達到i的最大跳躍次數
        Arrays.fill(dp, -1);    // -1表示到不了這個位置
        dp[0] = 0;
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                // 能從j跳過來且j可以被達到
                if (Math.abs(nums[i] - nums[j]) <= target && dp[j] != -1) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n - 1];
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2771.Longest%20Non-decreasing%20Subarray%20From%20Two%20Arrays/README.md
    public int maxNonDecreasingLength(int[] nums1, int[] nums2) {
        int n = nums1.length;

        // f 表示以 nums1 元素為結尾的最長非遞減子數組長度，
        // g 表示以 nums2 元素為結尾的最長非遞減子數組長度
        int f = 1, g = 1;
        int ans = 1;
        for (int i = 1; i < n; ++i) {
            // 給定一個初值，可以理解為以i處元素結尾的連續子數組的長度為1
            // 這樣可以保證在i處元素都小於nums1[i-1]和nums2[i-1]時有默認值
            // 這邊用兩個變量代表dp滾動數組
            int ff = 1, gg = 1;

            // 1 當nums1[i] >= nums1[i-1]時，此時以nums1[i]結尾的連續子數組的長度就是以nums1[i-1]結尾的連續子數組長度+1
            if (nums1[i] >= nums1[i - 1]) {
                ff = Math.max(ff, f + 1);
            }

            // 2 當nums1[i] >= nums2[i-1]時，此時以nums1[i]結尾的連續子數組的長度就是以nums2[i-1]結尾的連續子數組長度+1
            if (nums1[i] >= nums2[i - 1]) {
                ff = Math.max(ff, g + 1);
            }

            // 3 當nums2[i] >= nums1[i-1]時，此時以nums2[i]結尾的連續子數組的長度就是以nums1[i-1]結尾的連續子數組長度+1
            if (nums2[i] >= nums1[i - 1]) {
                gg = Math.max(gg, f + 1);
            }

            // 4 當nums2[i] >= nums2[i-1]時，此時以nums2[i]結尾的連續子數組的長度就是以nums2[i-1]結尾的連續子數組長度+1
            if (nums2[i] >= nums2[i - 1]) {
                gg = Math.max(gg, g + 1);
            }

            // 比較連續子數組的最大長度
            f = ff;
            g = gg;
            ans = Math.max(ans, Math.max(f, g));
        }
        return ans;
    }


    // dp二維數組
    public int maxNonDecreasingLength2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        if (n == 1) return 1;
        int[][] dp = new int[n][2];
        dp[0][0] = dp[0][1] = 1;
        int ans = 0;
        for (int i = 1; i < n; i++) {
            // 給定一個初值，可以理解為以i處元素結尾的連續子數組的長度為1
            // 這樣可以保證在i處元素都小於nums1[i-1]和nums2[i-1]時有默認值
            dp[i][0] = dp[i][1] = 1;
            // 1 當nums1[i] >= nums1[i-1]時，此時以nums1[i]結尾的連續子數組的長度就是以nums1[i-1]結尾的連續子數組長度+1
            if (nums1[i] >= nums1[i - 1]) dp[i][0] = Math.max(dp[i][0], dp[i - 1][0] + 1);
            // 2 當nums1[i] >= nums2[i-1]時，此時以nums1[i]結尾的連續子數組的長度就是以nums2[i-1]結尾的連續子數組長度+1
            if (nums1[i] >= nums2[i - 1]) dp[i][0] = Math.max(dp[i][0], dp[i - 1][1] + 1);
            // 3 當nums2[i] >= nums1[i-1]時，此時以nums2[i]結尾的連續子數組的長度就是以nums1[i-1]結尾的連續子數組長度+1
            if (nums2[i] >= nums1[i - 1]) dp[i][1] = Math.max(dp[i][1], dp[i - 1][0] + 1);
            // 4 當nums2[i] >= nums2[i-1]時，此時以nums2[i]結尾的連續子數組的長度就是以nums2[i-1]結尾的連續子數組長度+1
            if (nums2[i] >= nums2[i - 1]) dp[i][1] = Math.max(dp[i][1], dp[i - 1][1] + 1);
            // 比較連續子數組的最大長度
            ans = Math.max(ans, Math.max(dp[i][0], dp[i][1]));
        }

        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2772.Apply%20Operations%20to%20Make%20All%20Array%20Elements%20Equal%20to%20Zero/README.md
    // 差分數組 + 前綴和
    // 對一段連續的元素同時進行加減操作，可以使用差分數組來維護這些操作
    public boolean checkArray(int[] nums, int k) {
        int n = nums.length;
        int[] d = new int[n + 1]; // d[i] 表示差分數組
        int s = 0;
        for (int i = 0; i < n; ++i) {
            s += d[i]; // 當前位置的變化量 (前綴和)

            nums[i] += s; // 當前 nums[i] 的實際值
            if (nums[i] == 0) { //  當前 nums[i] 已經是 0 就無須進行操作，直接跳過
                continue;
            }
            if (nums[i] < 0 || i + k > n) {
                return false; // 前面的操作 nums[i] 已經變成了負數，或者 nums[i + k] 越界，返回 false
            }

            // 假設當前選取以 nums[i] 開頭的子數組，那麼首先必須考慮將 nums[i] 操作成 0，
            // 否則之後窗口向右移動後就沒辦法再處理當前的 nums[i]，
            // 所以這裡需要將 [i...i+k-1] 這段區間的所有元素都減去 nums[i]，
            // 表示對 nums[i...i+k-1] 進行 -1 操作 nums[i] 次，
            // 因此將 s 減去 nums[i] (累計變化量前綴和)， 並將d[i + k] 加上 nums[i] (懶加載)
            s -= nums[i];
            d[i + k] += nums[i];
        }
        return true;
    }
}

