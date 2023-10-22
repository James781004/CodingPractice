package Grind.Ch01_Array;

public class Q08_MajorityElement {
    // https://leetcode.com/problems/majority-element/
    public int majorityElement(int[] nums) {
        // 我們想尋找的那個眾數
        int target = 0;
        // 計數器（類比帶電粒子例子中的帶電性）
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                // 當計數器為 0 時，假設 nums[i] 就是眾數
                target = nums[i];
                // 眾數出現了一次
                count = 1;
            } else if (nums[i] == target) {
                // 如果遇到的是目標眾數，計數器累加
                count++;
            } else {
                // 如果遇到的不是目標眾數，計數器遞減
                count--;
            }
        }
        // 回想帶電粒子的例子
        // 此時的 count 必然大於 0，此時的 target 必然就是目標眾數
        return target;
    }
}
