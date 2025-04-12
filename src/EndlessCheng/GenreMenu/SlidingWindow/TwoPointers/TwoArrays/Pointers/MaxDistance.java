package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class MaxDistance {

    // https://leetcode.cn/problems/maximum-distance-between-a-pair-of-values/solutions/2270712/xi-you-yuan-su-1855-xia-biao-dui-zhong-d-z3vz/
    public int maxDistance(int[] nums1, int[] nums2) {
        final int m = nums1.length;
        final int n = nums2.length;
        int res = 0;
        int j = 0;
        for (int i = 0; i < m; i++) {
            while (j < n && nums2[j] >= nums1[i]) {
                j++;
            }
            res = Math.max(res, j - i - 1);
        }

        return res;
    }


}
