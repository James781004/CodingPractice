package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Week_423 {

    // https://leetcode.cn/problems/adjacent-increasing-subarrays-detection-ii/solutions/2983500/on-yi-ci-bian-li-jian-ji-xie-fa-pythonja-ye0a/
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int ans = 0;
        int preCnt = 0; // 設當前嚴格遞增段的長度為 cnt，上一個嚴格遞增段的長度為 preCnt
        int cnt = 0;
        for (int i = 0; i < nums.size(); i++) {
            cnt++;

            // 指針抵達 nums 最尾部: nums.size() - 1，
            // 或者抵達當前嚴格遞增段最尾部: nums.get(i) >= nums.get(i + 1)，
            // 開始記錄答案，記錄完成後 cnt 歸零。
            // 答案有兩種情況：
            // 兩個子數組屬於同一個嚴格遞增段，那麼 k 最大是 cnt / 2
            // 兩個子數組分別屬於一對相鄰嚴格遞增段，那麼 k 最大是 min(preCnt,cnt)。
            if (i == nums.size() - 1 || nums.get(i) >= nums.get(i + 1)) {
                ans = Math.max(ans, Math.max(cnt / 2, Math.min(preCnt, cnt)));
                preCnt = cnt;
                cnt = 0;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/sum-of-good-subsequences/solutions/2983496/te-shu-zi-xu-lie-dppythonjavacgo-by-endl-vv7e/
    public int sumOfGoodSubsequences(int[] nums) {
        final int MOD = 1_000_000_007;
        Map<Integer, Integer> f = new HashMap<>();
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            long c = cnt.getOrDefault(x - 1, 0) + cnt.getOrDefault(x + 1, 0) + 1;
            f.put(x, (int) ((x * c + f.getOrDefault(x, 0) + f.getOrDefault(x - 1, 0) + f.getOrDefault(x + 1, 0)) % MOD));
            cnt.put(x, (int) ((cnt.getOrDefault(x, 0) + c) % MOD));
        }
        long ans = 0;
        for (int s : f.values()) {
            ans += s;
        }
        return (int) (ans % MOD);
    }


    // https://leetcode.cn/problems/count-k-reducible-numbers-less-than-n/solutions/2983541/xian-xing-dp-shu-wei-dppythonjavacgo-by-yw0dl/
    private static final int MOD = 1_000_000_007;

    public int countKReducibleNumbers(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] memo = new int[n][n + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        long ans = 0;
        int[] f = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            f[i] = f[Integer.bitCount(i)] + 1;
            if (f[i] <= k) {
                ans += dfs(0, i, true, s, memo);
            }
        }
        return (int) (ans % MOD);
    }

    private int dfs(int i, int left1, boolean isLimit, char[] s, int[][] memo) {
        if (i == s.length) {
            return !isLimit && left1 == 0 ? 1 : 0;
        }
        if (!isLimit && memo[i][left1] != -1) {
            return memo[i][left1];
        }
        int up = isLimit ? s[i] - '0' : 1;
        int res = 0;
        for (int d = 0; d <= Math.min(up, left1); d++) {
            res = (res + dfs(i + 1, left1 - d, isLimit && d == up, s, memo)) % MOD;
        }
        if (!isLimit) {
            memo[i][left1] = res;
        }
        return res;
    }


}






