package EndlessCheng.GenreMenu.DP.OtherLinear.SubArrays;

import java.util.Arrays;

public class MaxProduct {

    // https://leetcode.cn/problems/maximum-product-subarray/solutions/2968916/dong-tai-gui-hua-jian-ji-gao-xiao-python-i778/
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int[] fMax = new int[n];
        int[] fMin = new int[n];
        fMax[0] = fMin[0] = nums[0];
        for (int i = 1; i < n; i++) {
            int x = nums[i];
            // 把 x 加到右端點為 i-1 的（乘積最大/最小）子數組後面，
            // 或者單獨組成一個子數組，只有 x 一個元素
            fMax[i] = Math.max(Math.max(fMax[i - 1] * x, fMin[i - 1] * x), x);
            fMin[i] = Math.min(Math.min(fMax[i - 1] * x, fMin[i - 1] * x), x);
        }
        return Arrays.stream(fMax).max().getAsInt();
    }


    public int maxProduct2(int[] nums) {
        int ans = Integer.MIN_VALUE; // 注意答案可能是負數
        int fMax = 1;
        int fMin = 1;
        for (int x : nums) {
            int mx = fMax;
            fMax = Math.max(Math.max(fMax * x, fMin * x), x);
            fMin = Math.min(Math.min(mx * x, fMin * x), x);
            ans = Math.max(ans, fMax);
        }
        return ans;
    }


    public int maxProduct3(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int fMax = 1;
        int fMin = 1;
        for (int x : nums) {
            if (x < 0) {
                // 下面與 x 相乘後，最大的正數變成最小的負數，最小的負數變成最大的正數
                // 提前交換，這樣可以把 x < 0 和 x >= 0 的情況合並，合並後，計算 max 和 min 可以少一項
                int tmp = fMax;
                fMax = fMin;
                fMin = tmp;
            }
            fMax = Math.max(fMax * x, x);
            fMin = Math.min(fMin * x, x);
            ans = Math.max(ans, fMax);
        }
        return ans;
    }


}
