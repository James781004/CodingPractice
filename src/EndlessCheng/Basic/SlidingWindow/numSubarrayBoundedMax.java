package EndlessCheng.Basic.SlidingWindow;

public class numSubarrayBoundedMax {

    // https://leetcode.cn/problems/number-of-subarrays-with-bounded-maximum/solutions/1988198/tu-jie-yi-ci-bian-li-jian-ji-xie-fa-pyth-n75l/
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int count = 0, head = -1, hair = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > right) { // 超出最大值
                hair = i; // 有效子數組左邊界下標 不能小於 hair
            } else if (nums[i] >= left) { // nums[i] 在有效區間[left,right]
                head = i; // 有效子數組左邊界 不能大於head
            }
            if (hair < head) count += head - hair; // 以nums[i]作為右邊界的 左邊界的個數
        }
        return count;
    }
}


