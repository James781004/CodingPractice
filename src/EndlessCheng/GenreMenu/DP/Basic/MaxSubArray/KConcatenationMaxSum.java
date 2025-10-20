package EndlessCheng.GenreMenu.DP.Basic.MaxSubArray;

public class KConcatenationMaxSum {

    // https://leetcode.cn/problems/k-concatenation-maximum-sum/solutions/3675237/fu-yong-53-ti-dai-ma-jian-ji-xie-fa-pyth-qmtp/
    public int kConcatenationMaxSum(int[] arr, int k) {
        if (k == 1) {
            return maxSubArray(arr, 1);
        }
        long ans = maxSubArray(arr, 2); // arr+arr 的最大子數組和
        int s = 0;
        for (int x : arr) {
            s += x;
        }
        ans += (long) Math.max(s, 0) * (k - 2);
        return (int) (ans % 1_000_000_007);
    }

    // 53. 最大子數組和（repeat 表示重復次數）
    private int maxSubArray(int[] nums, int repeat) {
        int n = nums.length;
        int ans = 0; // 本題允許子數組為空，ans 可以初始化成 0
        int f = 0;
        for (int i = 0; i < n * repeat; i++) {
            f = Math.max(f, 0) + nums[i % n];
            ans = Math.max(ans, f);
        }
        return ans;
    }


}
