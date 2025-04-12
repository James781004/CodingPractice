package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.HashMap;
import java.util.Map;

public class MaximumUniqueSubarray {

    // https://leetcode.cn/problems/maximum-erasure-value/solutions/2633705/1695-shan-chu-zi-shu-zu-de-zui-da-de-fen-7ejh/
    public int maximumUniqueSubarray(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> window = new HashMap<>(); // 記錄窗口內的數字出現的次數
        int left = 0, right = 0;
        int result = 0;
        int sum = 0;
        while (right < n) {
            window.put(nums[right], window.getOrDefault(nums[right], 0) + 1);
            sum += nums[right]; // 記錄窗口內的所有元素之和
            if (window.size() == right - left + 1) { // 當窗口內的元素個數等於窗口長度時，就更新最大窗口長度
                result = Math.max(result, sum);
            }

            // 當窗口中出現重復字符時，即窗口中的數字個數小於窗口長度時，就嘗試不斷移動左指針縮小窗口
            while (left <= right && window.size() < right - left + 1) {
                window.put(nums[left], window.get(nums[left]) - 1);
                if (window.get(nums[left]).equals(0)) {
                    window.remove(nums[left]);
                }
                sum -= nums[left];
                left++;
            }
            right++;
        }
        return result;
    }


}
