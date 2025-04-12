package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

import java.util.Arrays;

public class FindTheDistanceValue {

    // https://leetcode.cn/problems/find-the-distance-value-between-two-arrays/solutions/2678234/1385-liang-ge-shu-zu-jian-de-ju-chi-zhi-ssaen/
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int distance = 0;
        Arrays.sort(arr2);
        int n = arr2.length;
        for (int num1 : arr1) {

            // 使用二分查找在數組 arr2 中尋找大於等於 num1 的元素的最小下標 index
            int index = binarySearch(arr2, num1);

            // 如果 index=n 或 arr2[index]−num1 > d，則數組 arr2 中不存在大於等於 num1 且小於等於 num1+d 的元素。
            // 如果 index=0 或 num1−arr2[index−1]>d，則數組 arr2 中不存在小於 num1 且大於等於 num1−d 的元素。
            // 如果上述兩個條件同時成立，則元素 num1 不存在對應的元素 num2 使得 ∣num1−num2∣≤d，將距離值增加 1。
            if ((index == n || arr2[index] - num1 > d) && (index == 0 || num1 - arr2[index - 1] > d)) {
                distance++;
            }
        }
        return distance;
    }

    public int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }


}
