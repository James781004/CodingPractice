package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class CountGoodRotations {

    // https://leetcode.cn/problems/count-good-cyclic-rotations/solutions/4023626/liang-chong-fang-fa-qian-zhui-he-ding-ch-wh1y/
    public int countGoodRotations(int[] nums) {
        int n = nums.length;
        long[] sum = new long[n * 2 + 1];
        for (int i = 0; i < n * 2; i++) {
            sum[i + 1] = sum[i] + nums[i % n];
        }

        int ans = 0;
        for (int i = n; i < n * 2; i++) {
            // sum[i-n/2]-sum[i-n] > sum[i]-sum[i-n/2]
            if (sum[i - n / 2] * 2 > sum[i] + sum[i - n]) {
                ans++;
            }
        }
        return ans;
    }
    
}
