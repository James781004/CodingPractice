package FuckingAlgorithm.StructureDesign;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class Q02_LFU {
//    https://www.cnblogs.com/labuladong/p/13975044.html
//    https://leetcode.cn/problems/lfu-cache/
//    460. LFU 緩存
//    請你為 最不經常使用（LFU）緩存算法設計並實現數據結構。
//
//    實現 LFUCache 類：
//
//    LFUCache(int capacity) - 用數據結構的容量 capacity 初始化對象
//    int get(int key) - 如果鍵 key 存在於緩存中，則獲取鍵的值，否則返回 -1 。
//    void put(int key, int value) - 如果鍵 key 已存在，則變更其值；如果鍵不存在，請插入鍵值對。
//    當緩存達到其容量 capacity 時，則應該在插入新項之前，移除最不經常使用的項。
//    在此問題中，當存在平局（即兩個或更多個鍵具有相同使用頻率）時，應該去除 最近最久未使用 的鍵。
//    為了確定最不常使用的鍵，可以為緩存中的每個鍵維護一個 使用計數器 。使用計數最小的鍵是最久未使用的鍵。
//
//    當一個鍵首次插入到緩存中時，它的使用計數器被設置為 1 (由於 put 操作)。對緩存中的鍵執行 get 或 put 操作，使用計數器的值將會遞增。
//
//    函數 get 和 put 必須以 O(1) 的平均時間復雜度運行。

    class LFUCache {
        // key 到 val 的映射，我們後文稱為 KV 表
        HashMap<Integer, Integer> keyToVal;
        // key 到 freq 的映射，我們後文稱為 KF 表
        HashMap<Integer, Integer> keyToFreq;
        // freq 到 key 列表的映射，我們後文稱為 FK 表
        HashMap<Integer, LinkedHashSet<Integer>> freqToKeys;
        // 記錄最小的頻次
        int minFreq;
        // 記錄 LFU 緩存的最大容量
        int cap;

        // 構造容量為 capacity 的緩存
        public LFUCache(int capacity) {
            keyToVal = new HashMap<>();
            keyToFreq = new HashMap<>();
            freqToKeys = new HashMap<>();
            this.cap = capacity;
            this.minFreq = 0;
        }

        // 在緩存中查詢 key
        public int get(int key) {
            if (!keyToVal.containsKey(key)) return -1;
            // 增加 key 對應的 freq
            increaseFreq(key);
            return keyToVal.get(key);
        }

        // 將 key 和 val 存入緩存
        public void put(int key, int val) {
            if (this.cap <= 0) return;

            /* 若 key 已存在，修改對應的 val 即可 */
            if (keyToVal.containsKey(key)) {
                keyToVal.put(key, val);
                // key 對應的 freq 加一
                increaseFreq(key);
                return;
            }

            /* key 不存在，需要插入 */
            /* 容量已滿的話需要淘汰一個 freq 最小的 key */
            if (this.cap <= keyToVal.size()) {
                removeMinFreqKey();
            }

            /* 插入 key 和 val，對應的 freq 為 1 */
            // 插入 KV 表
            keyToVal.put(key, val);
            // 插入 KF 表
            keyToFreq.put(key, 1);
            // 插入 FK 表
            freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
            freqToKeys.get(1).add(key);
            // 插入新 key 後最小的 freq 肯定是 1
            this.minFreq = 1;
        }

        private void increaseFreq(int key) {
            int freq = keyToFreq.get(key);
            /* 更新 KF 表 */
            keyToFreq.put(key, freq + 1);
            /* 更新 FK 表 */
            // 將 key 從 freq 對應的列表中刪除
            freqToKeys.get(freq).remove(key);
            // 將 key 加入 freq + 1 對應的列表中
            freqToKeys.putIfAbsent(freq + 1, new LinkedHashSet<>());
            freqToKeys.get(freq + 1).add(key);
            // 如果 freq 對應的列表空了，移除這個 freq
            if (freqToKeys.get(freq).isEmpty()) {
                freqToKeys.remove(freq);
                // 如果這個 freq 恰好是 minFreq，更新 minFreq
                if (freq == this.minFreq) {
                    this.minFreq++;
                }
            }
        }

        private void removeMinFreqKey() {
            // freq 最小的 key 列表
            LinkedHashSet<Integer> keyList = freqToKeys.get(this.minFreq);

            // 其中最先被插入的那個 key 就是該被淘汰的 key
            int deletedKey = keyList.iterator().next();

            /* 更新 FK 表 */
            keyList.remove(deletedKey);
            if (keyList.isEmpty()) {
                freqToKeys.remove(this.minFreq);
                // 問：這裡需要更新 minFreq 的值嗎？
                // 這裡沒必要更新 minFreq 變量，因為 removeMinFreqKey 在 put 方法中插入新 key 時可能調用。
                // 回頭看 put 的代碼，插入新 key 時一定會把 minFreq 更新成 1，
                // 所以說即便這裡 minFreq 變了，我們也不需要管它。
            }

            /* 更新 KV 表 */
            keyToVal.remove(deletedKey);
            /* 更新 KF 表 */
            keyToFreq.remove(deletedKey);
        }


    }

}
