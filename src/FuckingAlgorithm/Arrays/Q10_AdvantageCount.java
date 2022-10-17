package FuckingAlgorithm.Arrays;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Q10_AdvantageCount {
//    https://leetcode.cn/problems/advantage-shuffle/
//    870. 優勢洗牌
//    給定兩個大小相等的數組 nums1 和 nums2，nums1 相對於 nums2 的優勢可以用滿足 nums1[i] > nums2[i] 的索引 i 的數目來描述。
//
//    返回 nums1 的任意排列，使其相對於 nums2 的優勢最大化。


    public int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        // 給 nums2 降序排序
        PriorityQueue<int[]> maxpq = new PriorityQueue<>(
                (int[] p1, int[] p2) -> {
                    return p2[1] - p1[1];
                }
        );

        for (int i = 0; i < n; i++) {
            maxpq.offer(new int[]{i, nums2[i]});
        }
        // 給 nums1 升序排序
        Arrays.sort(nums1);

        // nums1[left] 是最小值，nums1[right] 是最大值
        int left = 0, right = n - 1;
        int[] res = new int[n];

        // 思路：田忌賽馬
        // 從最快的馬開始比
        // 比得過，跟他比
        // 比不過，換個墊底的來送人頭
        while (!maxpq.isEmpty()) {
            int[] pair = maxpq.poll();
            int i = pair[0], maxval = pair[1];
            // maxval 是 nums2 中的最大值，i 是對應索引
            if (maxval < nums1[right]) {
                // 如果 nums1[right] 能勝過 maxval，那就自己上
                res[i] = nums1[right];
                right--;
            } else {
                // 否則用最小值混一下，養精蓄銳
                res[i] = nums1[left];
                left++;
            }
        }

        return res;
    }
}
