package Grind.Ch16_Binary;

public class Q02_FindTheDuplicateNumber {
    // https://leetcode.cn/problems/find-the-duplicate-number/solutions/7038/er-fen-fa-si-lu-ji-dai-ma-python-by-liweiwei1419/
    // n + 1 個整數，放在長度為 n 的數組裡，根據「抽屜原理」，至少會有 1 個整數是重復的；
    // 抽屜原理：把 10 個蘋果放進 9 個抽屜，至少有一個抽屜裡至少放 2 個蘋果。
    // 解題思路：每一次猜一個數，然後遍歷整個輸入數組，進而縮小搜索區間，最後確定重復的是哪個數。
    public int findDuplicate(int[] nums) {
        int len = nums.length; // n + 1 = len, n = len - 1

        // 在 [1..n] 查找 nums 中重復的元素
        int left = 1;
        int right = len - 1;
        while (left < right) {
            int mid = (left + right) / 2;

            // nums 中小於等於 mid 的元素的個數
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }

            if (count > mid) {
                // 下一輪搜索的區間 [left..mid]
                right = mid;
            } else {
                // 下一輪搜索的區間 [mid + 1..right]
                left = mid + 1;
            }
        }
        return left;
    }

    // 快慢指針
    // https://leetcode.cn/problems/find-the-duplicate-number/solutions/58841/287xun-zhao-zhong-fu-shu-by-kirsche/
    public int findDuplicate2(int[] nums) {
        int slow = 0;
        int fast = 0;
        slow = nums[slow];
        fast = nums[nums[fast]];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        int pre1 = 0;
        int pre2 = slow;
        while (pre1 != pre2) {
            pre1 = nums[pre1];
            pre2 = nums[pre2];
        }
        return pre1;
    }
}
