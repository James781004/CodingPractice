package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MaxSumOfThreeSubarrays {

    // https://leetcode.cn/problems/maximum-sum-of-3-non-overlapping-subarrays/solutions/2532695/python3javacgotypescript-yi-ti-yi-jie-yu-awl4/
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] s = new int[n + 1]; // 預處理得到數組 nums 的前綴和數組 s
        for (int i = 0; i < n; ++i) {
            s[i + 1] = s[i] + nums[i];
        }
        int[][] pre = new int[n][0]; // pre[i] 表示 [0,i] 范圍內長度為 k 的子數組的最大和及其起始位置
        int[][] suf = new int[n][0]; // suf[i] 表示 [i,n) 范圍內長度為 k 的子數組的最大和及其起始位置
        for (int i = 0, t = 0, idx = 0; i < n - k + 1; i++) { // 枚舉中間子數組的起始位置 i
            int cur = s[i + k] - s[i]; // (s[i+k]−s[i]) 表示 [i,i+k) 范圍內長度為 k 的子數組的和
            if (cur > t) {
                pre[i + k - 1] = new int[]{cur, i};
                t = cur;
                idx = i;
            } else {
                pre[i + k - 1] = new int[]{t, idx};
            }
        }
        for (int i = n - k, t = 0, idx = 0; i >= 0; i--) {
            int cur = s[i + k] - s[i]; // (s[i+k]−s[i]) 表示 [i,i+k) 范圍內長度為 k 的子數組的和
            if (cur >= t) {
                suf[i] = new int[]{cur, i};
                t = cur;
                idx = i;
            } else {
                suf[i] = new int[]{t, idx};
            }
        }

        // 三個子數組的和就是 pre[i−1][0]+suf[i+k][0]+(s[i+k]−s[i])
        // 其中 pre[i−1][0] 表示 [0,i−1] 范圍內長度為 k 的子數組的最大和
        // suf[i+k][0] 表示 [i+k,n) 范圍內長度為 k 的子數組的最大和
        // (s[i+k]−s[i]) 表示 [i,i+k) 范圍內長度為 k 的子數組的和
        int[] ans = new int[0];
        for (int i = k, t = 0; i < n - 2 * k + 1; ++i) {
            int cur = s[i + k] - s[i] + pre[i - 1][0] + suf[i + k][0];
            if (cur > t) {
                ans = new int[]{pre[i - 1][1], i, suf[i + k][1]};
                t = cur;
            }
        }
        return ans;
    }

}
