package EndlessCheng.GenreMenu.DP.Enhance.Other;

import java.util.Arrays;

public class MinArraySum {

    // https://leetcode.cn/problems/minimum-sum-after-divisible-sum-deletions/solutions/3755268/dong-tai-gui-hua-qian-zhui-he-pythonjava-nia8/
    public long minArraySum(int[] nums, int k) {
        long[] minF = new long[k];
        Arrays.fill(minF, Long.MAX_VALUE);
        minF[0] = 0; // sum[0] = 0，對應的 f[0] = 0
        long f = 0;
        int sum = 0;
        for (int x : nums) {
            sum = (sum + x) % k;
            // 不刪除 x，那麼轉移來源為 f + x
            // 刪除以 x 結尾的子數組，問題變成剩余前綴的最小和
            // 其中剩余前綴的元素和模 k 等於 sum，對應的 f 值的最小值記錄在 minF[sum] 中
            f = Math.min(f + x, minF[sum]);
            // 維護前綴和 sum 對應的最小和，由於上面計算了 min，這裡無需再計算 min
            minF[sum] = f;
        }
        return f;
    }


}
