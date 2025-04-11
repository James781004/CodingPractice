package EndlessCheng.Basic.TwoPointers;

import java.util.Arrays;

public class triangleNumber {

    // https://leetcode.cn/problems/valid-triangle-number/solutions/2432875/zhuan-huan-cheng-abcyong-xiang-xiang-shu-1ex3/
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        for (int k = 2; k < nums.length; k++) {
            int c = nums[k];
            int i = 0; // a=nums[i]
            int j = k - 1; // b=nums[j]
            while (i < j) {
                if (nums[i] + nums[j] > c) {
                    // 由於 nums 已經從小到大排序
                    // nums[i]+nums[j] > c 同時意味著：
                    // nums[i+1]+nums[j] > c
                    // nums[i+2]+nums[j] > c
                    // ...
                    // nums[j-1]+nums[j] > c
                    // 從 i 到 j-1 一共 j-i 個
                    ans += j - i;
                    j--;
                } else {
                    // 由於 nums 已經從小到大排序
                    // nums[i]+nums[j] <= c 同時意味著
                    // nums[i]+nums[j-1] <= c
                    // ...
                    // nums[i]+nums[i+1] <= c
                    // 所以在後續的內層循環中，nums[i] 不可能作為三角形的邊長，沒有用了
                    i++;
                }
            }
        }
        return ans;
    }

}
