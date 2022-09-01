package LeetcodeMaster.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q20_MultiPack {
    // https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E8%83%8C%E5%8C%85%E9%97%AE%E9%A2%98%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80%E5%A4%9A%E9%87%8D%E8%83%8C%E5%8C%85.md

    public void testMultiPack1() {
        // 版本一：改變物品數量為01背包格式
        List<Integer> weight = new ArrayList<>(Arrays.asList(1, 3, 4));
        List<Integer> value = new ArrayList<>(Arrays.asList(15, 20, 30));
        List<Integer> nums = new ArrayList<>(Arrays.asList(2, 3, 2));
        int bagWeight = 10;

        for (int i = 0; i < nums.size(); i++) {
            while (nums.get(i) > 1) { // 把物品展開為i
                weight.add(weight.get(i));
                value.add(value.get(i));
                nums.set(i, nums.get(i) - 1);
            }
        }

        int[] dp = new int[bagWeight + 1];
        for (int i = 0; i < weight.size(); i++) { // 遍歷物品
            for (int j = bagWeight; j >= weight.get(i); j--) { // 遍歷背包容量
                dp[j] = Math.max(dp[j], dp[j - weight.get(i)] + value.get(i));
            }
            System.out.println(Arrays.toString(dp));
        }
    }


    public void testMultiPack2() {
        // 版本二：改變遍歷個數
        int[] weight = new int[]{1, 3, 4};
        int[] value = new int[]{15, 20, 30};
        int[] nums = new int[]{2, 3, 2};
        int bagWeight = 10;

        int[] dp = new int[bagWeight + 1];
        for (int i = 0; i < weight.length; i++) { // 遍歷物品
            for (int j = bagWeight; j >= weight[i]; j--) { // 遍歷背包容量
                // 以上為01背包，然後加一個遍歷個數
                for (int k = 1; k <= nums[i] && (j - k * weight[i]) >= 0; k++) { // 遍歷個數
                    dp[j] = Math.max(dp[j], dp[j - k * weight[i]] + k * value[i]);
                }
                System.out.println(Arrays.toString(dp));
            }
        }
    }
}
