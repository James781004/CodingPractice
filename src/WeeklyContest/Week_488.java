package WeeklyContest;

import java.util.*;

public class Week_488 {

    // https://leetcode.cn/problems/count-dominant-indices/solutions/3898742/on-yi-ci-bian-li-pythonjavacgo-by-endles-lda5/
    public int dominantIndices(int[] nums) {
        int n = nums.length;
        int sufSum = 0;
        int ans = 0;
        for (int i = n - 2; i >= 0; i--) {
            sufSum += nums[i + 1];
            if (nums[i] * (n - 1 - i) > sufSum) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/merge-adjacent-equal-elements/solutions/3898725/yong-zhan-mo-ni-pythonjavacgo-by-endless-vi8x/
    public List<Long> mergeAdjacent(int[] nums) {
        List<Long> st = new ArrayList<>();
        for (int x : nums) {
            st.add((long) x);
            while (st.size() > 1 && st.getLast().equals(st.get(st.size() - 2))) {
                st.removeLast();
                int i = st.size() - 1;
                st.set(i, st.get(i) * 2);
            }
        }
        return st;
    }


    // https://leetcode.cn/problems/count-subarrays-with-cost-less-than-or-equal-to-k/solutions/3898726/on-zuo-fa-dan-diao-dui-lie-yue-duan-yue-l09a0/
    public long countSubarrays(int[] nums, long k) {
        Deque<Integer> minQ = new ArrayDeque<>();
        Deque<Integer> maxQ = new ArrayDeque<>();
        long ans = 0;
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            // 1. 入：元素進入窗口
            int x = nums[right];
            while (!minQ.isEmpty() && x <= nums[minQ.peekLast()]) {
                minQ.pollLast();
            }
            minQ.addLast(right);

            while (!maxQ.isEmpty() && x >= nums[maxQ.peekLast()]) {
                maxQ.pollLast();
            }
            maxQ.addLast(right);

            // 2. 出：如果窗口不滿足要求，左端點離開窗口
            // 只需改下面這行代碼，其余邏輯和 2762 題完全一致
            while ((long) (nums[maxQ.peekFirst()] - nums[minQ.peekFirst()]) * (right - left + 1) > k) {
                left++;
                if (minQ.peekFirst() < left) {
                    minQ.pollFirst();
                }
                if (maxQ.peekFirst() < left) {
                    maxQ.pollFirst();
                }
            }

            // 3. 更新答案
            ans += right - left + 1;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-score-using-exactly-k-pairs/solutions/3898739/zi-xu-lie-dpcong-ji-yi-hua-sou-suo-dao-d-y9aw/
    public long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;

        long[][][] memo = new long[k + 1][n][m];
        for (long[][] mat : memo) {
            for (long[] row : mat) {
                Arrays.fill(row, Long.MIN_VALUE);
            }
        }

        return dfs(k, n - 1, m - 1, nums1, nums2, memo);
    }

    // 定義 dfs(k,i,j) 表示從 nums1 的 [0,i] 和 nums2 的 [0,j] 中各選一個長度恰好為 k 的子序列，
    // 這兩個子序列的最大點積
    private long dfs(int k, int i, int j, int[] nums1, int[] nums2, long[][][] memo) {
        if (k == 0) { // 選完了
            return 0;
        }
        if (i + 1 < k || j + 1 < k) { // 剩余元素不足 k 個
            return Long.MIN_VALUE; // 下面計算 max 不會取到 MIN_VALUE
        }
        if (memo[k][i][j] != Long.MIN_VALUE) { // 之前計算過
            return memo[k][i][j];
        }

        long res1 = dfs(k, i - 1, j, nums1, nums2, memo); // 不選 nums1[i]
        long res2 = dfs(k, i, j - 1, nums1, nums2, memo); // 不選 nums2[j]
        long res3 = dfs(k - 1, i - 1, j - 1, nums1, nums2, memo) + (long) nums1[i] * nums2[j]; // 選 nums1[i] 和 nums2[j]
        return memo[k][i][j] = Math.max(Math.max(res1, res2), res3); // 記憶化
    }

    public long maxScoreDP(int[] nums1, int[] nums2, int K) {
        int n = nums1.length;
        int m = nums2.length;
        long[][][] f = new long[K + 1][n + 1][m + 1];
        for (int k = 1; k <= K; k++) {
            for (long[] row : f[k]) {
                Arrays.fill(row, Long.MIN_VALUE);
            }
        }
        for (int k = 1; k <= K; k++) {
            for (int i = k - 1; i < n; i++) {
                for (int j = k - 1; j < m; j++) {
                    f[k][i + 1][j + 1] = Math.max(Math.max(f[k][i][j + 1], f[k][i + 1][j]),
                            f[k - 1][i][j] + (long) nums1[i] * nums2[j]);
                }
            }
        }
        return f[K][n][m];
    }


}









