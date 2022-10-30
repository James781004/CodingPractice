package FuckingAlgorithm.StructureDesign;

import java.util.HashMap;

public class Q01_LRU {
//    https://leetcode.cn/problems/lru-cache/
//    146. LRU 緩存
//    請你設計並實現一個滿足  LRU (最近最少使用) 緩存 約束的數據結構。
//    實現 LRUCache 類：
//    LRUCache(int capacity) 以 正整數 作為容量 capacity 初始化 LRU 緩存
//    int get(int key) 如果關鍵字 key 存在於緩存中，則返回關鍵字的值，否則返回 -1 。
//    void put(int key, int value) 如果關鍵字 key 已經存在，則變更其數據值 value ；
//    如果不存在，則向緩存中插入該組 key-value 。
//    如果插入操作導致關鍵字數量超過 capacity ，則應該 逐出 最久未使用的關鍵字。
//    函數 get 和 put 必須以 O(1) 的平均時間復雜度運行。

    class Node {
        public int key, val;
        public Node next, prev;

        public Node(int k, int v) {
            this.key = k;
            this.val = v;
        }
    }

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


    class LRUCache {
        // key -> Node(key, val)
        private HashMap<Integer, Node> map;
        // Node(k1, v1) <-> Node(k2, v2)...
        private DoubleList cache;
        // 最大容量
        private int cap;

//        LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

        public LRUCache(int capacity) {
            this.cap = capacity;
            map = new HashMap<>();
            cache = new DoubleList();
        }

        public int get(int key) {
            if (!map.containsKey(key)) return -1;
            makeRecently(key);
            return map.get(key).val;
        }

        /* 將某個 key 提升為最近使用的 */
        private void makeRecently(int key) {
            Node x = map.get(key);
            // 先從鏈表中刪除這個節點
            cache.remove(x);
            // 重新插到隊尾
            cache.addLast(x);
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
            Node deletedNode = cache.removeFirst();
            int deletedKey = deletedNode.key;
            map.remove(deletedKey);
        }
    }
}
