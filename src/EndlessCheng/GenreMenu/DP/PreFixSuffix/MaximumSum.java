package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MaximumSum {

    // https://leetcode.cn/problems/maximum-subarray-sum-with-one-deletion/solutions/2321829/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-hzz6/
    public int maximumSum(int[] arr) {
        int n = arr.length;
        int[] leftSum = new int[n];
        for (int i = 1; i < n - 1; i++) {
            leftSum[i] = Math.max(leftSum[i], leftSum[i - 1] + arr[i - 1]);
        }
        int ans = Integer.MIN_VALUE;
        for (int i = n - 1, rightSum = 0; i >= 0; i--) {
            int sum = rightSum + leftSum[i] + (rightSum == 0 && leftSum[i] == 0 ? arr[i] : Math.max(0, arr[i]));
            if (ans < sum) ans = sum;
            rightSum = Math.max(0, rightSum + arr[i]);
        }
        return ans;
    }
}
