package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

import java.util.ArrayList;
import java.util.List;

public class FindKClosestElements {

    // https://leetcode.cn/problems/find-k-closest-elements/solutions/12476/pai-chu-fa-shuang-zhi-zhen-er-fen-fa-python-dai-ma/
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int size = arr.length;
        int left = 0;
        int right = size - 1;
        int removeNums = size - k;
        while (removeNums > 0) {
            if (x - arr[left] <= arr[right] - x) {
                right--;
            } else {
                left++;
            }
            removeNums--;
        }

        List<Integer> res = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            res.add(arr[i]);
        }
        return res;
    }


    // 二分查找最優區間的左邊界
    public List<Integer> findClosestElements2(int[] arr, int k, int x) {
        int size = arr.length;
        int left = 0;
        int right = size - k;
        while (left < right) {
            // int mid = left + (right - left) / 2;
            int mid = (left + right) / 2;
            // 嘗試從長度為 k + 1 的連續子區間刪除一個元素
            // 從而定位左區間端點的邊界值
            if (x - arr[mid] > arr[mid + k] - x) {
                // 下一輪搜索區間是 [mid + 1..right]
                left = mid + 1;
            } else {
                // 下一輪搜索區間是 [left..mid]
                right = mid;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            res.add(arr[i]);
        }
        return res;
    }


}
