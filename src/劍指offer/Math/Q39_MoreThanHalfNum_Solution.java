package 劍指offer.Math;

import java.util.Arrays;

public class Q39_MoreThanHalfNum_Solution {
    public int MoreThanHalfNum_Solution(int[] nums) {
        // 使用 cnt 来统计一个元素出现的次数，当遍历到的元素和统计元素相等时，令 cnt++，否则令 cnt--。
        // 如果前面查找了 i 个元素，且 cnt == 0，说明前 i 个元素没有 majority，
        // 或者有 majority，但是出现的次数少于 i / 2 ，因为如果多于 i / 2 的话 cnt 就一定不会为 0 。
        // 此时剩下的 n - i 个元素中，majority 的数目依然多于 (n - i) / 2，因此继续查找就能找出 majority。
        int majority = nums[0];
        for (int i = 1, cnt = 1; i < nums.length; i++) {
            cnt = nums[i] == majority ? cnt + 1 : cnt - 1;
            if (cnt == 0) {
                majority = nums[i];
                cnt = 1;
            }
        }

        int cnt = 0;
        for (int val : nums) {
            if (val == majority) cnt++;
        }

        return cnt > nums.length / 2 ? majority : 0;
    }


    public int MoreThanHalfNum_Solution_Sort(int[] array) {
        if (array == null || array.length == 0) return 0;
        if (array.length == 1) return array[0];
        // 快速排序
        Arrays.sort(array);
        // 返回数组的中间值
        return array[array.length / 2];
    }
}
