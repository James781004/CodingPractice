package LeetcodeMaster.HashMap;

import java.util.HashSet;
import java.util.Set;

public class Q03_Intersection {
//349. 兩個數組的交集
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0349.%E4%B8%A4%E4%B8%AA%E6%95%B0%E7%BB%84%E7%9A%84%E4%BA%A4%E9%9B%86.md
//
//    題意：給定兩個數組，編寫一個函數來計算它們的交集。
//
//    說明： 輸出結果中的每個元素一定是唯一的。 我們可以不考慮輸出結果的順序。

    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) return new int[0];
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> resSet = new HashSet<>();

        //遍歷數組1
        for (int i : nums1) {
            set1.add(i);
        }

        //遍歷數組2的過程中判斷哈希表中是否存在該元素
        for (int i : nums2) {
            if (set1.contains(i)) {
                resSet.add(i);
            }
        }

        //將結果幾何轉為數組
        return resSet.stream().mapToInt(x -> x).toArray();

    }

}
