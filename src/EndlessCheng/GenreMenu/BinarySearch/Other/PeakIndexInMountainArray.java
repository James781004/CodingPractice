package EndlessCheng.GenreMenu.BinarySearch.Other;

public class PeakIndexInMountainArray {

    // https://leetcode.cn/problems/peak-index-in-a-mountain-array/solutions/2984800/er-fen-gen-ju-shang-po-huan-shi-xia-po-p-uoev/
    public int peakIndexInMountainArray(int[] arr) {
        // 根據題意，arr 沒有重復元素，且峰頂的下標一定在區間 [1,n−2] 中
        int left = 0;
        int right = arr.length - 2;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > arr[mid + 1]) {
                right = mid; // 下坡：如果 arr[i]>arr[i+1]，那麼峰頂下標 ≤i
            } else {
                left = mid; // 上坡：如果 arr[i]<arr[i+1]，那麼峰頂下標 >i
            }
        }
        return right;
    }


}
