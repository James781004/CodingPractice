package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.Arrays;

public class MaxSum {

    // https://leetcode.cn/problems/max-pair-sum-in-an-array/solutions/2385996/yi-ci-bian-li-by-endlesscheng-6zt9/
    public int maxSum(int[] nums) {
        int ans = -1;
        int[] maxVal = new int[10];
        Arrays.fill(maxVal, Integer.MIN_VALUE); // 表示不存在最大值
        for (int v : nums) {
            int maxD = 0;
            for (int x = v; x > 0; x /= 10) {
                maxD = Math.max(maxD, x % 10);
            }
            ans = Math.max(ans, v + maxVal[maxD]);
            maxVal[maxD] = Math.max(maxVal[maxD], v);
        }
        return ans;
    }

}
