package FuckingAlgorithm.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Q09_RandomizedSet {
//    https://leetcode.cn/problems/insert-delete-getrandom-o1/
//    380. O(1) 時間插入、刪除和獲取隨機元素
//    實現RandomizedSet 類：
//
//    RandomizedSet() 初始化 RandomizedSet 對象
//    bool insert(int val) 當元素 val 不存在時，向集合中插入該項，並返回 true ；否則，返回 false 。
//    bool remove(int val) 當元素 val 存在時，從集合中移除該項，並返回 true ；否則，返回 false 。
//    int getRandom() 隨機返回現有集合中的一項（測試用例保證調用此方法時集合中至少存在一個元素）。每個元素應該有 相同的概率 被返回。
//    你必須實現類的所有函數，並滿足每個函數的 平均 時間復雜度為 O(1) 。

    class RandomizedSet {
        List<Integer> list; // 存儲元素的值
        HashMap<Integer, Integer> valToIndex; // 記錄每個元素對應在 nums 中的索引
        Random random;

        public RandomizedSet() {
            list = new ArrayList<>();
            valToIndex = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            if (valToIndex.containsKey(val)) {
                return false; // 若 val 已存在，不用再插入
            }

            // 若 val 不存在，插入到 nums 尾部，
            // 並記錄 val 對應的索引值
            list.add(val);
            valToIndex.put(val, list.size() - 1);
            return true;
        }

        public boolean remove(int val) {
            if (!valToIndex.containsKey(val)) {
                return false; // 若 val 不存在，不用再刪除
            }
            int index = valToIndex.get(val);  // 先拿到 val 的索引
            int last = list.get(list.size() - 1); // 將最後一個元素對應的索引修改為 index
            list.set(index, last); // 交換 val 和最後一個元素
            list.remove(list.size() - 1); // 在數組中刪除元素 val
            valToIndex.put(last, index); // 交換 val 和最後一個元素(就是把[last, x]替換成[last, index], 與[val, index]同值不同key)
            valToIndex.remove(val); // 刪除元素 val 對應的索引
            return true;
        }

        public int getRandom() { // 隨機獲取 nums 中的一個元素
            return list.get(random.nextInt(list.size()));
        }
    }


//    https://leetcode.cn/problems/random-pick-with-blacklist/
//    710. 黑名單中的隨機數
//    給定一個整數 n 和一個 無重復 黑名單整數數組 blacklist 。
//    設計一種算法，從 [0, n - 1] 范圍內的任意整數中選取一個 未加入 黑名單 blacklist 的整數。
//    任何在上述范圍內且不在黑名單 blacklist 中的整數都應該有 同等的可能性 被返回。
//
//    優化你的算法，使它最小化調用語言 內置 隨機函數的次數。
//
//    實現 Solution 類:
//
//    Solution(int n, int[] blacklist) 初始化整數 n 和被加入黑名單 blacklist 的整數
//    int pick() 返回一個范圍為 [0, n - 1] 且不在黑名單 blacklist 中的隨機整數

    class BlackList {
        int size = 0;
        HashMap<Integer, Integer> map = new HashMap<>();

        public BlackList(int n, int[] blacklist) {
            size = n - blacklist.length;

            // 先將所有黑名單數字加入 map
            // 這裡賦值多少都可以
            // 目的僅僅是把鍵存進哈希表
            // 方便快速判斷數字是否在黑名單內
            for (int item : blacklist) {
                map.put(item, -1);
            }
            int last = n - 1;
            for (int item : blacklist) {
                if (item >= size) { // 如果 item 已經在區間 [size, n) 可以直接忽略
                    continue;
                }
                while (map.containsKey(last)) {
                    last--; // 跳過所有黑名單中的數字
                }
                map.put(item, last); // 將黑名單中的索引映射到合法數字
                last--;
            }
        }

        public int pick() {
            int index = (int) (Math.random() * size);
            return map.getOrDefault(index, index);
        }
    }
}
