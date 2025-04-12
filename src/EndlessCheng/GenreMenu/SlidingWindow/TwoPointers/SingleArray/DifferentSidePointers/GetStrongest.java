package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

import java.util.Arrays;

public class GetStrongest {

    // https://leetcode.cn/problems/the-k-strongest-values-in-an-array/solutions/388170/javapai-xu-shuang-zhi-zhen-by-gxu5y3scni/
    public int[] getStrongest(int[] arr, int k) {
        Arrays.sort(arr); // 將數組按數值從小到大排序
        int mid = arr[(arr.length - 1) / 2], index = 0;
        // mid為數組的中位數，注意題目中定義的中位數與“一般定義”不同
        int[] ans = new int[k];
        int left = 0, right = arr.length - 1;
        while (index < k) { // 直到獲取k個數為止
            int a = Math.abs(arr[left] - mid), b = Math.abs(arr[right] - mid);
            /* 按規則獲取數字 */
            if (a > b) {
                ans[index] = arr[left];
                left++;
                index++;
            } else { // 由於數組中的數字按升序排序，因此右邊的數總是大於左邊的
                ans[index] = arr[right];
                right--;
                index++;
            }
        }
        return ans;
    }

}
