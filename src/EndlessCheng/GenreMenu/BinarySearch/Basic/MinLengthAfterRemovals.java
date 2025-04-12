package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.List;

public class MinLengthAfterRemovals {

    // https://leetcode.cn/problems/minimum-array-length-after-pair-removals/solutions/2446146/olog-n-tan-xin-er-fen-cha-zhao-pythonjav-t3qn/
    public int minLengthAfterRemovals(List<Integer> nums) {
        int n = nums.size();

        // 由於 nums 是有序的，如果 maxCnt 超過數組長度的一半，那麼 nums[n/2] 一定是出現次數最多的那個數
        // 分類討論：
        // 如果 maxCnt⋅2>n，其余所有 n−maxCnt 個數都要與 x 消除，所以最後剩下 maxCnt⋅2−n 個數。
        // 如果 maxCnt⋅2≤n 且 n 是偶數，那麼可以把其余數消除至剩下 maxCnt 個數，然後再和 x 消除，最後剩下 0 個數。
        // 如果 maxCnt⋅2≤n 且 n 是奇數，同上，最後剩下 1 個數。
        int x = nums.get(n / 2);
        int maxCnt = lowerBound(nums, x + 1) - lowerBound(nums, x);
        return Math.max(maxCnt * 2 - n, n % 2);
    }

    // 開區間寫法
    private int lowerBound(List<Integer> nums, int target) {
        int left = -1, right = nums.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums.get(mid) < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right; // 或者 left+1
    }


}
