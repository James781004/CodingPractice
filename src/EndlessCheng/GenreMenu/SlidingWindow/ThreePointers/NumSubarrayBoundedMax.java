package EndlessCheng.GenreMenu.SlidingWindow.ThreePointers;

public class NumSubarrayBoundedMax {

    // https://leetcode.cn/problems/number-of-subarrays-with-bounded-maximum/solutions/1988198/tu-jie-yi-ci-bian-li-jian-ji-xie-fa-pyth-n75l/
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int n = nums.length, ans = 0, i0 = -1, i1 = -1;
        for (int i = 0; i < n; i++) {
            if (nums[i] > right) i0 = i; // 不能包含的下標 i0，相當於上一個值大於 right 的下標
            if (nums[i] >= left) i1 = i; // 必須包含的下標 (為了保證 i1 ≥ i0 （否則相減會算出負數），可以在 nums[i]>right 時也更新 i1)
            ans += i1 - i0;
        }
        return ans;
    }


}
