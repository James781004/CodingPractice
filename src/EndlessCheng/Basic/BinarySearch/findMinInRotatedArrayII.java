package EndlessCheng.Basic.BinarySearch;

public class findMinInRotatedArrayII {
    // https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array-ii/solutions/2131553/zhi-yao-ni-hui-153-jiu-neng-kan-dong-pyt-qqc6/
    public int findMin(int[] nums) {
        int left = -1, right = nums.length - 1; // 開區間 (-1, n-1)
        while (left + 1 < right) { // 開區間不為空
            int mid = (left + right) >>> 1;
            if (nums[mid] < nums[right]) right = mid; // 藍色
            else if (nums[mid] > nums[right]) left = mid; // 紅色
            else --right; // 當二分元素 nums[mid] 與區間右端點元素相同時，既然無法確定最小值所在區間，那麼干脆去掉右端點元素，繼續二分。對應到代碼上，就是 right 減一。
        }
        return nums[right];
    }

}
