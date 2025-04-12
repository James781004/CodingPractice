package EndlessCheng.GenreMenu.MonoStack.Contribution;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class SubArrayRanges {

    // https://leetcode.cn/problems/sum-of-subarray-ranges/solutions/1153054/cong-on2-dao-ondan-diao-zhan-ji-suan-mei-o1op/
    public long subArrayRanges(int[] nums) {
        long ans = solve(nums);
        Arrays.setAll(nums, i -> -nums[i]);   // 將所有元素取反
        return ans + solve(nums);
    }

    public long solve(int[] nums) {
        int n = nums.length;
        Deque<Integer> stk = new ArrayDeque();
        int[] right = new int[n], left = new int[n];
        Arrays.fill(right, n);
        Arrays.fill(left, -1);
        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && nums[i] >= nums[stk.peek()]) {
                right[stk.pop()] = i;                   // 右側第一個大於等於的
            }
            if (!stk.isEmpty()) left[i] = stk.peek();   // 左側第一個嚴格大於的
            stk.push(i);
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += (long) nums[i] * (right[i] - i) * (i - left[i]);
        }
        return ans;
    }

}
