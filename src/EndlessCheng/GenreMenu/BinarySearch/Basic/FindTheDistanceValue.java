package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.Arrays;

public class FindTheDistanceValue {

    // https://leetcode.cn/problems/find-the-distance-value-between-two-arrays/solutions/3010185/liang-chong-fang-fa-er-fen-cha-zhao-san-15u9b/
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr2);
        int ans = 0;
        for (int x : arr1) {
            int i = Arrays.binarySearch(arr2, x - d);
            if (i < 0) {
                i = ~i; // -i - 1
            }
            if (i == arr2.length || arr2[i] > x + d) {
                ans++;
            }
        }
        return ans;
    }


    // 排序+雙指針
    public int findTheDistanceValue2(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        int ans = 0;
        int j = 0;
        for (int x : arr1) {
            while (j < arr2.length && arr2[j] < x - d) {
                j++;
            }
            if (j == arr2.length || arr2[j] > x + d) {
                ans++;
            }
        }
        return ans;
    }


}
