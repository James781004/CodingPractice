package Grind.Ch13_Heap;

import java.util.*;

public class Q05_TopKFrequentWords {
    // https://leetcode.cn/problems/top-k-frequent-words/solutions/731580/xiao-gen-dui-huo-zhe-hashbiao-pai-xu-by-9uj06/
    public List<String> topKFrequent(String[] words, int k) {
        // 1.先用哈希表統計單詞出現的頻率
        Map<String, Integer> count = new HashMap();
        for (String word : words) {
            count.put(word, count.getOrDefault(word, 0) + 1);
        }

        // 2.構建小根堆 這裡需要自己構建比較規則 此處為 lambda 寫法 Java 的優先隊列默認實現就是小根堆
        PriorityQueue<String> minHeap = new PriorityQueue<>((s1, s2) -> {
            if (count.get(s1).equals(count.get(s2))) {
                // 因為建立的是小根堆，彈出的元素是不需要的，那彈出的元素就是排在前面的元素，
                // (s1, s2)-> s1-s2是升序，當次數不相等的時候需要彈出次數少的；
                // 當次數相等的時候需要彈出字典序在後面的，所以compare裡需要降序，因為排在前面的是字典序在後面的。
                return s2.compareTo(s1);
            } else {
                return count.get(s1) - count.get(s2);
            }
        });

        // 3.依次向堆加入元素。
        for (String s : count.keySet()) {
            minHeap.offer(s);
            // 當堆中元素個數大於 k 個的時候，需要彈出堆頂最小的元素。
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // 4.依次彈出堆中的 K 個元素，放入結果集合中。
        List<String> res = new ArrayList<String>(k);
        while (minHeap.size() > 0) {
            res.add(minHeap.poll());
        }

        // 5.注意最後需要反轉元素的順序。
        Collections.reverse(res);
        return res;
    }
}
