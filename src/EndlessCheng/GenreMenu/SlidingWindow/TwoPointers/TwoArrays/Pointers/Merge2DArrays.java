package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

import java.util.ArrayList;
import java.util.List;

public class Merge2DArrays {

    // https://leetcode.cn/problems/merge-two-2d-arrays-by-summing-values/solutions/2608422/2570-he-bing-liang-ge-er-wei-shu-zu-qiu-w696v/
    public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        List<int[]> mergedList = new ArrayList<int[]>();
        int m = nums1.length, n = nums2.length;
        int index1 = 0, index2 = 0;
        while (index1 < m && index2 < n) {
            int id1 = nums1[index1][0], val1 = nums1[index1][1];
            int id2 = nums2[index2][0], val2 = nums2[index2][1];
            if (id1 < id2) {
                mergedList.add(new int[]{id1, val1});
                index1++;
            } else if (id1 > id2) {
                mergedList.add(new int[]{id2, val2});
                index2++;
            } else {
                mergedList.add(new int[]{id1, val1 + val2});
                index1++;
                index2++;
            }
        }
        while (index1 < m) {
            int id = nums1[index1][0], val = nums1[index1][1];
            mergedList.add(new int[]{id, val});
            index1++;
        }
        while (index2 < n) {
            int id = nums2[index2][0], val = nums2[index2][1];
            mergedList.add(new int[]{id, val});
            index2++;
        }
        return mergedList.toArray(new int[mergedList.size()][]);
    }


}
