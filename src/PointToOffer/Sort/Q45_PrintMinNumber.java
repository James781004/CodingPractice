package PointToOffer.Sort;

import java.util.Arrays;

public class Q45_PrintMinNumber {
    public String PrintMinNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) return "";
        int n = numbers.length;
        String[] nums = new String[n];
        for (int i = 0; i < n; i++) {
            nums[i] = numbers[i] + "";
        }
        Arrays.sort(nums, (s1, s2) -> (s1 + s2).compareTo(s2 + s1));
        String ret = "";
        for (String str : nums)
            ret += str;
        return ret;
    }
}
