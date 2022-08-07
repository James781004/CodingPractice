package 程序员代码面试指南.ch09;

import java.util.HashMap;

public class Q09_SetAllHashMap {
//    CD62 設計有setAll功能的哈希表
//    描述
//    哈希表常見的三個操作時put、get和containsKey，而且這三個操作的時間覆雜度為O(1)。
//    現在想加一個setAll功能，就是把所有記錄value都設成統一的值。
//    請設計並實現這種有setAll功能的哈希表，並且put、get、containsKey和setAll四個操作的時間覆雜度都為O(1)。
//            [友情提示]: C++選手若有需要可以使用unordered_map替換map來將覆雜度從O(log n)降為O(1)

    public static class MyValue<V> {
        private V value;
        private long time;

        public MyValue(V value, long time) {
            this.value = value;
            this.time = time;
        }

        public V getValue() {
            return this.value;
        }

        public long getTime() {
            return this.time;
        }
    }

    public static class MyHashMap<K, V> {
        private HashMap<K, MyValue<V>> baseMap;
        private long time;
        private MyValue<V> setAll;

        public MyHashMap() {
            this.baseMap = new HashMap<K, MyValue<V>>();
            this.time = 0;
            this.setAll = new MyValue<V>(null, -1);
        }

        public boolean containsKey(K key) {
            return this.baseMap.containsKey(key);
        }

        public void put(K key, V value) {
            this.baseMap.put(key, new MyValue<V>(value, this.time++));
        }

        // 使用一個參數setAll來保存下參數，類似快取的概念
        // 之後調用get方法時，再根據setAll.getTime()判斷是否使用這個快取
        public void setAll(V value) {
            this.setAll = new MyValue<V>(value, this.time++);
        }

        // 用times來判斷返回值
        // 加入時間在setAll之前的就全部返回setAll
        public V get(K key) {
            if (this.containsKey(key)) {
                if (this.baseMap.get(key).getTime() > this.setAll.getTime()) {
                    return this.baseMap.get(key).getValue();
                } else {
                    return this.setAll.getValue();
                }
            } else {
                return null;
            }
        }
    }


    public static void main(String[] args) {
        MyHashMap<String, Integer> test = new MyHashMap<String, Integer>();
        test.put("Tom", 1);
        test.put("James", 2);
        System.out.println(test.containsKey("Tom"));
        System.out.println(test.get("Tom"));
        System.out.println(test.containsKey("James"));
        System.out.println(test.get("James"));
        test.setAll(3);
        test.put("Rose", 4);
        System.out.println(test.get("Tom"));
        System.out.println(test.get("James"));
        System.out.println(test.get("Rose"));
        test.setAll(5);
        System.out.println(test.get("Tom"));
        System.out.println(test.get("James"));
        System.out.println(test.get("Rose"));

    }
}
