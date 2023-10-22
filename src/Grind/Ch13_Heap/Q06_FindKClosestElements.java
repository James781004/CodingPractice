package Grind.Ch13_Heap;

import java.util.ArrayList;
import java.util.List;

public class Q06_FindKClosestElements {
    // https://leetcode.cn/problems/find-k-closest-elements/solutions/12476/pai-chu-fa-shuang-zhi-zhen-er-fen-fa-python-dai-ma/
    // 一個一個刪，因為數組有序，且返回的是連續升序子數組，所以 每一次刪除的元素一定位於邊界 ；
    // 一共 7 個元素，要保留 3 個元素，因此要刪除 4 個元素；
    // 因為要刪除的元素都位於邊界，於是可以使用 雙指針 對撞的方式確定保留的區間」
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

    // 由排除法知：如果 x 的值落在長度為 size 的區間內，要得到 size - 1 個符合題意的最接近的元素，此時看左右邊界：
    // 如果左邊界與 x 的差值的絕對值較小，刪除右邊界；
    // 如果右邊界與 x 的差值的絕對值較小，刪除左邊界；
    // 如果左、右邊界與 x 的差值的絕對值相等，刪除右邊界。
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
