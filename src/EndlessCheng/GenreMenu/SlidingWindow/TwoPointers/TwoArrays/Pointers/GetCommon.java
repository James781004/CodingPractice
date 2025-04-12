package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class GetCommon {

    // https://leetcode.cn/problems/minimum-common-value/solutions/2360488/ji-chu-shuang-zhi-zhen-by-sleepy-hersche-kdpm/
    public int getCommon(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        int i = 0;
        int j = 0;
        while (i < n1 && j < n2) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                return nums1[i];
            }
        }
        return -1;
    }


}
