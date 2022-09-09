package LeetcodeMaster.Arrays;

public class Q14_ValidMountainArray {
//    941.有效的山脈數組
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0941.%E6%9C%89%E6%95%88%E7%9A%84%E5%B1%B1%E8%84%89%E6%95%B0%E7%BB%84.md
//
//    給定一個整數數組 arr，如果它是有效的山脈數組就返回 true，否則返回 false。
//
//    讓我們回顧一下，如果 A 滿足下述條件，那麼它是一個山脈數組：
//
//    arr.length >= 3
//    在 0 < i < arr.length - 1 條件下，存在 i 使得：
//    arr[0] < arr[1] < ... arr[i-1] < arr[i]
//    arr[i] > arr[i+1] > ... > arr[arr.length - 1]
//
//
//    示例 1：
//
//    輸入：arr = [2,1]
//    輸出：false
//    示例 2：
//
//    輸入：arr = [3,5,5]
//    輸出：false
//    示例 3：
//
//    輸入：arr = [0,3,2,1]
//    輸出：true

    public boolean validMountainArray(int[] arr) {
        if (arr.length < 3) { // 此時，一定不是有效的山脈數組
            return false;
        }

        // 雙指針
        int left = 0;
        int right = arr.length - 1;

        // 注意防止指針越界
        while (left + 1 < arr.length && arr[left] < arr[left + 1]) {
            left++;
        }

        // 注意防止指針越界
        while (right > 0 && arr[right] < arr[right - 1]) {
            right--;
        }

        // 如果left或者right都在起始位置，說明不是山峰
        if (left == right && left != 0 && right != arr.length - 1) {
            return true;
        }
        return false;
    }
}
