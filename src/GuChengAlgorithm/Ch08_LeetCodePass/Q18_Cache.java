package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class Q18_Cache {
    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_6_189
    class LRU {
        Map<Integer, Node> map;
        Node head;  // dummy head
        Node tail;  // dummy tail
        int capacity;
        int size;

        public LRU(int capacity) {
            this.map = new HashMap<>();
            this.capacity = capacity;
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            Node cur = map.get(key);
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
            moveToTail(cur);
            return cur.val;
        }

        private void moveToTail(Node cur) {
            cur.prev = tail.prev;
            tail.prev.next = cur;
            tail.prev = cur;
            cur.next = tail;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node existNode = map.get(key);
                existNode.val = value;
                get(key);
                return;
            }

            if (size == capacity) {
                Node removeNode = head.next; // dummy head next就是LRU
                map.remove(removeNode.key);
                head.next = head.next.next;  // 移除原本的第一個節點，dummy head重新連接
                head.next.prev = head;
                size--;
            }

            Node insert = new Node(key, value);
            map.put(key, insert);
            size++;
            moveToTail(insert);
        }
    }

    class Node {
        Node prev;
        Node next;
        int key;
        int val;

        public Node(int k, int v) {
            key = k;
            val = v;
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g109b512cc95_0_0
    class LFU {
        Map<Integer, Integer> map;  // <key, value>
        Map<Integer, Integer> counts;  // <key, freq>
        Map<Integer, LinkedHashSet<Integer>> lists;  // bucket sort <freq, List<Element>>
        int capacity;
        int min;  // 當前位置最少遍歷的freq

        public LFU(int capacity) {
            map = new HashMap<>();
            counts = new HashMap<>();
            lists = new HashMap<>();
            lists.put(1, new LinkedHashSet<>());
            this.capacity = capacity;
            min = 0;
        }


        public int get(int key) {
            if (!map.containsKey(key)) return -1;

            // 從舊的freq bucket移除
            lists.get(counts.get(key)).remove(key);
            if (counts.get(key) == min && lists.get(min).size() == 0) min++;

            // 更新freq並且加入新的freq bucket
            counts.put(key, counts.get(key) + 1);
            lists.computeIfAbsent(counts.get(key), k -> new LinkedHashSet<>()).add(key);
            return map.get(key);
        }


        public void put(int key, int value) {
            if (capacity == 0) return;
            if (map.containsKey(key)) {
                map.put(key, value);  // 更新 key value
                get(key);  // 直接call get方法，更新freq，min++
            } else {
                if (map.size() == capacity) {
                    int tmp = lists.get(min).iterator().next();
                    lists.get(min).remove(tmp);
                    map.remove(tmp);
                    counts.remove(tmp);  // 加不加都可以，map裡面沒有就不會找到這個元素，如果後續又加入也會重置為1
                }
                map.put(key, value);
                counts.put(key, 1);
                min = 1;  // 每次有新元素加入，min就重置為1
                lists.get(min).add(key);
            }
        }
    }
}
