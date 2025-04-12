package EndlessCheng.GenreMenu.BinarySearch.Kth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KthSmallestProduct {

    // https://leetcode.cn/problems/kth-smallest-product-of-two-sorted-arrays/solutions/1051301/java-er-fen-by-avlgood2018-o0kx/
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        List<Long> n1 = new ArrayList<>();
        List<Long> p1 = new ArrayList<>();
        List<Long> n2 = new ArrayList<>();
        List<Long> p2 = new ArrayList<>();
        for (int x : nums1) {
            if (x < 0) {
                n1.add((long) -x);
            } else {
                p1.add((long) x);
            }
        }
        for (int x : nums2) {
            if (x < 0) {
                n2.add((long) -x);
            } else {
                p2.add((long) x);
            }
        }
        Collections.sort(n1);
        Collections.sort(n2);
        Collections.sort(p1);
        Collections.sort(p2);
        long negativeCount = n1.size() * p2.size() + n2.size() * p1.size();
        int sign = 1;
        List<Long> l1, l2, r1, r2;
        if (k > negativeCount) {
            // 在正數裡找第 k - negativeCount大
            k -= negativeCount;
            l1 = n1;
            r1 = n2;
            l2 = p1;
            r2 = p2;
        } else {
            sign = -1;
            // 因為把負數取反了，正數裡排第1的（最小），負數裡反而最大（排第negativeCount）
            k = negativeCount - k + 1;
            l1 = n1;
            r1 = p2;
            l2 = p1;
            r2 = n2;
        }

        long left = 0L;
        long right = 10000000000L;
        while (left < right) {
            long mid = (left + right) / 2;
            if (count(l1, r1, mid) + count(l2, r2, mid) >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left * sign;
    }

    // 統計乘積小於等於target的組合數量
    private long count(List<Long> arr1, List<Long> arr2, long target) {
        long result = 0;
        int j = arr2.size() - 1;
        for (int i = 0; i < arr1.size(); i++) {
            while (j >= 0 && arr1.get(i) * arr2.get(j) > target) {
                j--;
            }
            result += j + 1;
        }
        return result;
    }


}
