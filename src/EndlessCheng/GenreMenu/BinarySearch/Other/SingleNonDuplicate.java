package EndlessCheng.GenreMenu.BinarySearch.Other;

public class SingleNonDuplicate {

    // https://leetcode.cn/problems/single-element-in-a-sorted-array/solutions/2983333/er-fen-xing-zhi-fen-xi-jian-ji-xie-fa-py-0rng/
    // 題目有兩個已知條件：
    // 數組是有序的。
    // 除了一個數出現一次外，其余每個數都出現兩次。
    //
    // 第二個條件意味著，數組的長度一定是奇數。
    // 第一個條件意味著，出現兩次的數，必然相鄰，不可能出現 1,2,1 這樣的順序
    // 這也意味著，只出現一次的那個數，一定位於偶數下標上。
    // 這啟發我們去檢查偶數下標 2k。
    public int singleNonDuplicate(int[] nums) {
        int left = -1;
        int right = nums.length / 2;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            // 如果 nums[2k] = nums[2k+1]，說明只出現一次的數的下標 > 2k。
            // 如果 nums[2k] != nums[2k+1]，說明只出現一次的數的下標 ≤ 2k。
            if (nums[mid * 2] != nums[mid * 2 + 1]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return nums[right * 2];
    }


}
