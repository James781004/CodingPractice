package EndlessCheng.GenreMenu.BinarySearch.Kth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KSmallestPairs {

    // https://leetcode.cn/problems/find-k-pairs-with-smallest-sums/solutions/2151459/373-cha-zhao-he-zui-xiao-de-k-dui-shu-zi-4qtk/
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        int m = nums1.length, n = nums2.length;

        // 二分查找第 k 小的數對和的值
        int low = nums1[0] + nums2[0];
        int high = nums1[m - 1] + nums2[n - 1];
        int pairSum = high;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            // count 可能超出 int 的范圍，需要使用 long
            long count = 0;
            int start = 0, end = n - 1;
            while (start < m && end >= 0) {
                if (nums1[start] + nums2[end] > mid) {
                    end--;
                } else {
                    start++;
                    count += end + 1;
                }
            }
            if (count < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
                pairSum = mid;
            }
        }
        int pos = n - 1;
        // 找到小於目標數對和 pairSum 的數對並保存
        for (int i = 0; i < m; ++i) {
            while (pos >= 0 && nums1[i] + nums2[pos] >= pairSum) {
                pos--;
            }
            for (int j = 0; j <= pos && k > 0; ++j, --k) {
                res.add(new ArrayList<>(Arrays.asList(nums1[i], nums2[j])));
            }
        }
        pos = n - 1;
        // 找到和等於目標數對和 pairSum 的剩余數對
        for (int i = 0; i < m && k > 0; ++i) {
            int start1 = i;
            while (i < m - 1 && nums1[i] == nums1[i + 1]) {
                i++;
            }
            while (pos >= 0 && nums1[i] + nums2[pos] > pairSum) {
                pos--;
            }
            int start2 = pos;
            while (pos > 0 && nums2[pos] == nums2[pos - 1]) {
                pos--;
            }
            if (nums1[i] + nums2[pos] != pairSum) {
                continue;
            }
            int count = (int) Math.min(k, (long) (i - start1 + 1) * (start2 - pos + 1));
            for (int j = 0; j < count && k > 0; ++j, --k) {
                res.add(new ArrayList<>(Arrays.asList(nums1[i], nums2[pos])));
            }
        }
        return res;
    }


}
