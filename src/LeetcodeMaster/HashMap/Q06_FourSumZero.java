package LeetcodeMaster.HashMap;

import java.util.HashMap;
import java.util.Map;

public class Q06_FourSumZero {
//    第454題.四數相加II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0454.%E5%9B%9B%E6%95%B0%E7%9B%B8%E5%8A%A0II.md
//
//    給定四個包含整數的數組列表 A , B , C , D ,計算有多少個元組 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
//
//    為了使問題簡單化，所有的 A, B, C, D 具有相同的長度 N，且 0 ≤ N ≤ 500 。所有整數的範圍在 -2^28 到 2^28 - 1 之間，最終結果不會超過 2^31 - 1 。
//
//    例如:
//
//    輸入:
//
//    A = [ 1, 2]
//    B = [-2,-1]
//    C = [-1, 2]
//    D = [ 0, 2]
//    輸出:
//
//            2
//
//    解釋:
//
//    兩個元組如下:
//
//            (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
//            (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0

    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map = new HashMap<>();
        int temp;
        int res = 0;
        //統計兩個數組中的元素之和，同時統計出現的次數，放入map
        for (int i : nums1) {
            for (int j : nums2) {
                temp = i + j;
                if (map.containsKey(temp)) {
                    map.put(temp, map.get(temp) + 1);
                } else {
                    map.put(temp, 1);
                }
            }
        }

        //統計剩余的兩個元素的和，在map中找是否存在相加為0的情況，同時記錄次數
        for (int i : nums3) {
            for (int j : nums4) {
                temp = 0 - (i + j);
                if (map.containsKey(temp)) {
                    res += map.get(temp);
                }
            }
        }

        return res;
    }

}
