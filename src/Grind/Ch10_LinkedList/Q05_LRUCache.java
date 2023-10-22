package Grind.Ch10_LinkedList;

import java.util.HashMap;

public class Q05_LRUCache {
    // https://leetcode.cn/problems/lru-cache/solutions/12711/lru-ce-lue-xiang-jie-he-shi-xian-by-labuladong/
    // 不用 Java library 自己寫
    class LRUCache {
        // key -> Node(key, val)
        private HashMap<Integer, Node> map;
        // Node(k1, v1) <-> Node(k2, v2)...
        private DoubleList cache;
        // 最大容量
        private int cap;

        public LRUCache(int capacity) {
            this.cap = capacity;
            map = new HashMap<>();
            cache = new DoubleList();
        }


        /* 將某個 key 提升為最近使用的 */
        private void makeRecently(int key) {
            Node x = map.get(key);
            // 先從鏈表中刪除這個節點
            cache.remove(x);
            // 重新插到隊尾
            cache.addLast(x);
        }

        /* 添加最近使用的元素 */
        private void addRecently(int key, int val) {
            Node x = new Node(key, val);
            // 鏈表尾部就是最近使用的元素
            cache.addLast(x);
            // 別忘了在 map 中添加 key 的映射
            map.put(key, x);
        }

        /* 刪除某一個 key */
        private void deleteKey(int key) {
            Node x = map.get(key);
            // 從鏈表中刪除
            cache.remove(x);
            // 從 map 中刪除
            map.remove(key);
        }

        /* 刪除最久未使用的元素 */
        private void removeLeastRecently() {
            // 鏈表頭部的第一個元素就是最久未使用的
            Node deletedNode = cache.removeFirst();
            // 同時別忘了從 map 中刪除它的 key
            int deletedKey = deletedNode.key;
            map.remove(deletedKey);
        }


        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            // 將該數據提升為最近使用的
            makeRecently(key);
            return map.get(key).val;
        }

        public void put(int key, int val) {
            if (map.containsKey(key)) {
                // 刪除舊的數據
                deleteKey(key);
                // 新插入的數據為最近使用的數據
                addRecently(key, val);
                return;
            }

            if (cap == cache.size()) {
                // 刪除最久未使用的元素
                removeLeastRecently();
            }
            // 添加為最近使用的元素
            addRecently(key, val);
        }

    }


    // 雙鏈表
    class DoubleList {
        // 頭尾虛節點
        private Node head, tail;
        // 鏈表元素數
        private int size;

        public DoubleList() {
            // 初始化雙向鏈表的數據
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        // 在鏈表尾部添加節點 x，時間 O(1)
        public void addLast(Node x) {
            x.prev = tail.prev;
            x.next = tail;
            tail.prev.next = x;
            tail.prev = x;
            size++;
        }

        // 刪除鏈表中的 x 節點（x 一定存在）
        // 由於是雙鏈表且給的是目標 Node 節點，時間 O(1)
        public void remove(Node x) {
            x.prev.next = x.next;
            x.next.prev = x.prev;
            size--;
        }

        // 刪除鏈表中第一個節點，並返回該節點，時間 O(1)
        public Node removeFirst() {
            if (head.next == tail)
                return null;
            Node first = head.next;
            remove(first);
            return first;
        }

        // 返回鏈表長度，時間 O(1)
        public int size() {
            return size;
        }

    }

    // 雙鏈表的節點
    class Node {
        public int key, val;
        public Node next, prev;

        public Node(int k, int v) {
            this.key = k;
            this.val = v;
        }
    }


    // LinkedHashMap 來實現 LRU 算法
//    class LRUCache {
//        int cap;
//        LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();
//
//        public LRUCache(int capacity) {
//            this.cap = capacity;
//        }
//
//        public int get(int key) {
//            if (!cache.containsKey(key)) {
//                return -1;
//            }
//            // 將 key 變為最近使用
//            makeRecently(key);
//            return cache.get(key);
//        }
//
//        public void put(int key, int val) {
//            if (cache.containsKey(key)) {
//                // 修改 key 的值
//                cache.put(key, val);
//                // 將 key 變為最近使用
//                makeRecently(key);
//                return;
//            }
//
//            if (cache.size() >= this.cap) {
//                // 鏈表頭部就是最久未使用的 key
//                int oldestKey = cache.keySet().iterator().next();
//                cache.remove(oldestKey);
//            }
//            // 將新的 key 添加鏈表尾部
//            cache.put(key, val);
//        }
//
//        private void makeRecently(int key) {
//            int val = cache.get(key);
//            // 刪除 key，重新插入到隊尾
//            cache.remove(key);
//            cache.put(key, val);
//        }
//    }
}
