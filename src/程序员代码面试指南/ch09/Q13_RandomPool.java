package 程序员代码面试指南.ch09;

import java.util.HashMap;

public class Q13_RandomPool {
    /**
     * 【題目】 設計一種結構，在該結構中有如下三個功能：
     * insert(key)：將某個key加入到該結構，做到不重覆加入。
     * delete(key)：將原本在結構中的某個key移除。 getRandom()：
     * 等概率隨機返回結構中的任何一個key。
     * 【要求】 Insert、delete和getRandom方法的時間覆雜度都是   O(1)
     * <p>
     * 題目要求必須是O(1)的時間覆雜度，那麽上哈希表就可以了，但是隨機返回等概率的key 哈希表是做不到的，
     * 因此，我們可以借助兩個hash表來做
     * <p>
     * 比如 添加數據
     * hash1   key  value          hash2    key value
     * z      1              1         z
     * x      2              2         x
     * y      3              3         y
     * hash1記錄的是key的值 value 記錄當前數據的大小， 而key是唯一的，所以可以保證數據的唯一性。
     * hash2 key 記錄的是數據的大小，由於添加數據size不斷變化 所以可以保證唯一，在使用一個random函數 隨機
     */

    public static class Pool<K> {
        private HashMap<K, Integer> keyIndexMap;
        private HashMap<Integer, K> indexKeyMap;
        private int size;

        public Pool() {
            this.keyIndexMap = new HashMap<K, Integer>();
            this.indexKeyMap = new HashMap<Integer, K>();
            this.size = 0;
        }

        public void insert(K key) {
            if (!this.indexKeyMap.containsKey(key)) {
                this.keyIndexMap.put(key, this.size);
                this.indexKeyMap.put(this.size++, key);
            }
        }

        public void delete(K key) {
            if (this.indexKeyMap.containsKey(key)) {
                // 先找到要刪除的下標
                int deleteIndex = this.keyIndexMap.get(key);

                // size-- 獲取到當前indexKeyMap中最後一個數據
                int lastIndex = --this.size;
                K lastKey = this.indexKeyMap.get(lastIndex);

                // 然後將原本最後一個元素添加到deleteIndex的位置
                // 也就是將當前目標元素位置的資料以最後一個元素的資料"覆蓋"，然後移除最後一個元素
                // keyIndexMap: 相當於新增 (lastKey, deleteIndex)
                // indexKeyMap: 轉換 (deleteIndex, key) -> (deleteIndex, lastKey)
                // 這麼做是為了保證Pool中資料的index(0 ~ size-1)是連續的，以便進行random操作
                this.keyIndexMap.put(lastKey, deleteIndex);
                this.indexKeyMap.put(deleteIndex, lastKey);

                // keyIndexMap最後把目標(key, deleteIndex)刪除
                this.keyIndexMap.remove(key);

                // indexKeyMap最後把多出來的(lastIndex, lastKey)刪除
                this.indexKeyMap.remove(lastIndex);
            }
        }

        public K getRandom() {
            if (this.size == 0) return null;
            int randomIndex = (int) (Math.random() * this.size);
            return this.indexKeyMap.get(randomIndex);
        }
    }

    public static void main(String[] args) {
        Pool<String> pool = new Pool<String>();
        pool.insert("zuo");
        pool.insert("cheng");
        pool.insert("yun");
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());

    }
}
