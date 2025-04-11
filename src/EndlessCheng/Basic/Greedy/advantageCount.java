package EndlessCheng.Basic.Greedy;

import java.util.Arrays;

public class advantageCount {

    // https://leetcode.cn/problems/advantage-shuffle/solutions/1875994/tian-ji-sai-ma-by-endlesscheng-yxm6/
    // 類比田忌賽馬，數組中最小值即下等馬，最大值上等馬
    // 每次用nums1中的下等馬去跟nums2中的下等馬pk
    // 如果干的過就干，干不過就用nums1中的下等馬去當炮灰，去干nums2中的上等馬
    // 在本題中，如果干的過，就用nums2中的下等馬的下標當做nums1中的下等馬的下標
    // 干不過，就用nums2中的上等馬的下標當作nums1中的下等馬的下標
    public int[] advantageCount(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        int len = nums2.length;
        Integer[] index = new Integer[len];
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            index[i] = i;
        }

        // 注意比較器引用的數組需要是對象，所以不能放基本數據類型的數組
        // 根據nums2的值升序，來排序nums2的值的對應下標
        // 排序後index[]中第一個元素是nums2中最小值的下標，第二個元素是nums2中第二小值的下標
        Arrays.sort(index, (a, b) -> (nums2[a] - nums2[b]));
        int left = 0;
        int right = len - 1;
        // 遍歷nums1
        for (int num : nums1) {
            // index[left] 為 nums2中最小值的下標，index[right] 為nums2中最大值的下標
            // 如果num比nums2中的最小值大，則本次res中num對應的下標為index[left],然後left++
            // 否則本次res中num對應的下標為index[right],然後right--
            int i = num > nums2[index[left]] ? index[left++] : index[right--];
            res[i] = num;
        }
        return res;

    }


}
