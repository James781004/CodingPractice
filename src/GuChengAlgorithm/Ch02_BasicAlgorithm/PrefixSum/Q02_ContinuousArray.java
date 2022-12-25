package GuChengAlgorithm.Ch02_BasicAlgorithm.PrefixSum;

import java.util.HashMap;
import java.util.Map;

public class Q02_ContinuousArray {
    // https://docs.google.com/presentation/d/14oHMlIK-GXLlADlJIcvPcIx_cHTKWcOv-sOve5L2Aqw/edit#slide=id.ge3eafaac94_1_0
    public boolean checkSunArraySum(int[] nums, int k) {
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();  // <index, position>
        map.put(0, -1); // 這邊初始化放-1，因為這個value接下來是代表下標位置，如果找到了第一組0區間，就可以巧妙利用這個-1算長度
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (k != 0) sum &= k;
            if (map.containsKey(sum)) {  // 如果相同餘數再次出現，表示這中間的sub array是可以被k整除的
                if (i - map.get(sum) > 1) return true;
            } else map.put(sum, i);
        }
        return false;
    }


    // https://docs.google.com/presentation/d/14oHMlIK-GXLlADlJIcvPcIx_cHTKWcOv-sOve5L2Aqw/edit#slide=id.ge3eafaac94_1_18
    // 暴力解法
    public int findMaxLength(int[] nums) {
        int maxLen = 0;
        for (int start = 0; start < nums.length; start++) {
            int zeros = 0, ones = 0;
            for (int end = start; end < nums.length; end++) {
                if (nums[end] == 0) zeros++;
                else ones++;
                if (zeros == ones) maxLen = Math.max(maxLen, end - start + 1);
            }
        }
        return maxLen;
    }


    public int findMaxLengthON(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) nums[i] = -1;  // 把0轉換成-1，後面尋找preSum為0的區間即可
        }
        int res = 0, sum = 0;
        Map<Integer, Integer> map = new HashMap<>();  // <index, position>
        map.put(0, -1); // 這邊初始化放-1，因為這個value是代表下標位置，如果找到了第一組0區間，就可以巧妙利用這個-1算長度
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];  // 如果找到preSum為0的區間，就找的了題目要求的sub array，可以計算長度
            if (map.containsKey(sum)) res = Math.max(res, i - map.get(sum));
            else map.put(sum, i);
        }
        return res;
    }

}
