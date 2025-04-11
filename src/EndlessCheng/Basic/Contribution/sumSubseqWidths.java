package EndlessCheng.Basic.Contribution;

import java.util.Arrays;

public class sumSubseqWidths {

    // https://leetcode.cn/problems/sum-of-subsequence-widths/solutions/1977682/by-endlesscheng-upd1/
    private static final int MOD = 1_000_000_007;

    public int sumSubseqWidths(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] pow2 = new int[n];
        pow2[0] = 1;
        for (int i = 1; i < n; i++) {
            pow2[i] = pow2[i - 1] * 2 % MOD; // 預處理 2 的冪次
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += (long) (pow2[i] - pow2[n - 1 - i]) * nums[i]; // 在題目的數據范圍下，這不會溢出
        }
        return (int) (ans % MOD + MOD) % MOD; // 注意上面有減法，ans 可能為負數
    }


}
