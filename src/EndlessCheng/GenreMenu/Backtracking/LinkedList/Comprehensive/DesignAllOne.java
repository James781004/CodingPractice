package EndlessCheng.GenreMenu.Backtracking.LinkedList.Comprehensive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DesignAllOne {

    // https://leetcode.cn/problems/all-oone-data-structure/solutions/1341608/by-tong-zhu-qccw/
    class AllOne {

        // 雙向鏈表按次數從小到大排序
        Node head, tail;
        // 哈希表保存 key->Node 的映射
        Map<String, Node> map;

        public AllOne() {
            this.head = new Node(-1);
            this.tail = new Node(10000000);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.map = new HashMap<>();
        }

        public void inc(String key) {
            if (map.containsKey(key)) {
                // 包含key的情況下
                Node node = map.get(key);
                Node next = node.next;
                int newCount = node.count + 1;

                // 如果下一節點的數量等於新數量
                // 則key加入下一個節點
                if (next.count == newCount) {
                    next.addKey(key);
                    map.put(key, next);
                } else {
                    // 否則新建一個Node並插入到當前節點之後
                    Node newNode = new Node(key, newCount);
                    node.next(newNode);
                    map.put(key, newNode);
                }

                // 當前節點移除這個key
                node.removeKey(key);
            } else {
                // 不包含這個key的情況下
                // 直接看head的下一個節點的數量是不是1
                Node head = this.head;
                Node next = head.next;
                if (next.count == 1) {
                    next.addKey(key);
                    map.put(key, next);
                } else {
                    Node newNode = new Node(key, 1);
                    head.next(newNode);
                    map.put(key, newNode);
                }
            }
        }

        public void dec(String key) {
            // 題目約定了一定包含這個key
            Node node = map.get(key);
            Node prev = node.prev;
            int newCount = node.count - 1;

            if (prev.count == newCount) {
                prev.addKey(key);
                map.put(key, prev);
            } else if (newCount == 0) {
                map.remove(key);
            } else {
                Node newNode = new Node(key, newCount);
                node.prev(newNode);
                map.put(key, newNode);
            }

            node.removeKey(key);
        }

        public String getMaxKey() {
            if (tail.prev == head) {
                return "";
            }
            return tail.prev.keys.iterator().next();
        }

        public String getMinKey() {
            if (head.next == tail) {
                return "";
            }
            return head.next.keys.iterator().next();
        }

        static class Node {
            int count;
            Node next;
            Node prev;
            Set<String> keys;

            Node(int count) {
                this.count = count;
            }

            Node(String key, int count) {
                this.keys = new HashSet<>();
                this.keys.add(key);
                this.count = count;
            }

            void addKey(String key) {
                this.keys.add(key);
            }

            void removeKey(String key) {
                this.keys.remove(key);
                if (this.keys.isEmpty()) {
                    remove();
                }
            }

            void next(Node next) {
                // 在當前節點後面插入一個節點
                Node nextnext = this.next;
                this.next = next;
                next.prev = this;
                next.next = nextnext;
                nextnext.prev = next;
            }

            void prev(Node prev) {
                // 在當前節點前面插入一個節點
                Node prevprev = this.prev;
                this.prev = prev;
                prev.next = this;
                prev.prev = prevprev;
                prevprev.next = prev;
            }

            void remove() {
                // 刪除當前節點
                Node prev = this.prev;
                Node next = this.next;
                prev.next = next;
                next.prev = prev;
            }
        }
    }

}
