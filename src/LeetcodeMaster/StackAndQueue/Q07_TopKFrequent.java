package LeetcodeMaster.StackAndQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Q07_TopKFrequent {
//    347.前 K 個高頻元素
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0347.%E5%89%8DK%E4%B8%AA%E9%AB%98%E9%A2%91%E5%85%83%E7%B4%A0.md
//
//    給定一個非空的整數數組，返回其中出現頻率前 k 高的元素。
//
//    示例 1:
//
//    輸入: nums = [1,1,1,2,2,3], k = 2
//    輸出: [1,2]
//    示例 2:
//
//    輸入: nums = [1], k = 1
//    輸出: [1]
//    提示：
//
//    你可以假設給定的 k 總是合理的，且 1 ≤ k ≤ 數組中不相同的元素的個數。
//    你的算法的時間覆雜度必須優於  , n 是數組的大小。
//    題目數據保證答案唯一，換句話說，數組中前 k 個高頻元素的集合是唯一的。
//    你可以按任意順序返回答案。

    public int[] topKFrequent(int[] nums, int k) {
        int[] res = new int[k];
        HashMap<Integer, Integer> map = new HashMap<>(); // 要統計元素出現頻率，map<nums[i]對應出現的次數>
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        // 根據map的value值(即出現頻率)，構建於一個大頂堆（o1 - o2: 小頂堆， o2 - o1 : 大頂堆）
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>((o1, o2) -> o2.getValue() - o1.getValue());
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            queue.offer(entry);
        }

        // 找出前K個高頻元素
        for (int i = k - 1; i >= 0; i--) {
            res[i] = queue.poll().getKey();
        }

        return res;
    }
}
