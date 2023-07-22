package WeeklyContest;

import java.util.Arrays;

class Week_256 {
    // https://leetcode.cn/problems/minimum-difference-between-highest-and-lowest-of-k-scores/submissions/
    public int minimumDifference(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = Integer.MAX_VALUE;
        for (int i = k - 1; i < nums.length; i++) {
            int start = i - k + 1;
            ans = Math.min(ans, nums[i] - nums[start]);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/tree/main/solution/1900-1999/1985.Find%20the%20Kth%20Largest%20Integer%20in%20the%20Array
    public String kthLargestNumber(String[] nums, int k) {
        Arrays.sort(
                nums, (a, b) -> a.length() == b.length() ? b.compareTo(a) : b.length() - a.length());
        return nums[k - 1];
    }


    // https://leetcode.cn/problems/minimum-number-of-work-sessions-to-finish-the-tasks/solution/zhuang-ya-dpshi-shi-hou-xue-xi-yi-xia-li-q4mk/
    public int minSessions(int[] tasks, int sessionTime) {
        int n = tasks.length, m = 1 << n;
        final int INF = 20;
        int[] dp = new int[m];
        Arrays.fill(dp, INF);

        // 預處理每個狀態，合法狀態預設為 1
        for (int i = 1; i < m; i++) {
            int state = i, idx = 0;
            int spend = 0;
            while (state > 0) {
                int bit = state & 1;
                if (bit == 1) {
                    spend += tasks[idx];
                }
                state >>= 1;
                idx++;
            }
            if (spend <= sessionTime) {
                dp[i] = 1;
            }
        }

        // 對每個狀態枚舉子集，跳過已經有最優解的狀態
        for (int i = 1; i < m; i++) {
            if (dp[i] == 1) {
                continue;
            }
            int split = i >> 1;
            // 由於轉移是由子集與補集得來，因此可以將子集分割為兩塊，避免重復枚舉
            for (int j = (i - 1) & i; j > split; j = (j - 1) & i) {
                // i 狀態的最優解可能由當前子集 j 與子集 j 的補集得來
                dp[i] = Math.min(dp[i], dp[j] + dp[i ^ j]);
            }
        }

        return dp[m - 1];
    }


    // https://leetcode.cn/problems/number-of-unique-good-subsequences/solution/dong-tai-gui-hua-jing-dian-ti-mu-de-bian-n4h3/
    public int numberOfUniqueGoodSubsequences(String s) {
        int n = s.length();

        // dp[i][0]= 字符串 binary 的從 i 開始的子串中，以 0 開頭的子序列的個數
        // dp[i][1]= 字符串 binary 的從 i 開始的子串中，以 1 開頭的子序列的個數
        // 代碼實現采用了滾動數組
        int dp0 = 0, dp1 = 0, mod = 1000000007, has0 = 0;

        // 後向前遍歷字串 binary
        for (int i = n - 1; i >= 0; --i) {
            if (s.charAt(i) == '0') {
                // 當 binary[i]==’0’ 時， dp[i][0] 可以這樣求解：
                // 1. 首先，這個 '0' 可以添加到之前所有的子序列的前面，此部分共有 dp[i+1][0]+dp[i+1][1] 個不同的子序列；
                // 2. 其次，還有單獨的 1 個 0，此部分的數量為 1；
                // 3. 最後，還有之前的以 0 開頭的子序列 dp[i+1][0]，但這部分和前兩部分重復了。
                //    舉個例子，比如 dp[i+1][0] 中包含子序列 0011，那麼前兩部分一定也包含 0011，
                //    這是因為在 dp[i+1][0] 或 dp[i+1][1] 中肯定包含子序列 011，
                //    而在前兩部分中已經在 011 的前面添加 0 得到 0011了。
                // 綜上，dp[i][0]=dp[i+1][0]+dp[i+1][1]+1。
                // 然後，由於 binary[i]==’0’，故 dp[i][1] 不會發生變化，dp[i][1]=dp[i+1][1]。
                has0 = 1;
                dp0 = (dp0 + dp1 + 1) % mod;
            } else {
                // 當 binary[i]==’0’ 時同理可推
                dp1 = (dp0 + dp1 + 1) % mod;
            }
        }

        // 最終答案就是，全部以 1 開頭的子序列的個數（也就是 dp[0][1]）+ 字符串 “0” （如果有）。
        return (dp1 + has0) % mod;
    }
}

