package EndlessCheng.GenreMenu.Backtracking.LinkedList.Comprehensive;

import java.util.HashMap;
import java.util.Map;

public class DesignLRUCache {

    // https://leetcode.cn/problems/lru-cache/solutions/2456294/tu-jie-yi-zhang-tu-miao-dong-lrupythonja-czgt/
    class LRUCache {
        private static class Node {
            int key, value;
            Node prev, next;

            Node(int k, int v) {
                key = k;
                value = v;
            }
        }

        private final int capacity;
        private final Node dummy = new Node(0, 0); // 哨兵節點
        private final Map<Integer, Node> keyToNode = new HashMap<>();

        public LRUCache(int capacity) {
            this.capacity = capacity;
            dummy.prev = dummy;
            dummy.next = dummy;
        }

        public int get(int key) {
            Node node = getNode(key); // getNode 會把對應節點移到鏈表頭部
            return node != null ? node.value : -1;
        }

        public void put(int key, int value) {
            Node node = getNode(key); // getNode 會把對應節點移到鏈表頭部
            if (node != null) { // 有這本書
                node.value = value; // 更新 value
                return;
            }
            node = new Node(key, value); // 新書
            keyToNode.put(key, node);
            pushFront(node); // 放在最上面
            if (keyToNode.size() > capacity) { // 書太多了
                Node backNode = dummy.prev;
                keyToNode.remove(backNode.key);
                remove(backNode); // 去掉最後一本書
            }
        }

        // 獲取 key 對應的節點，同時把該節點移到鏈表頭部
        private Node getNode(int key) {
            if (!keyToNode.containsKey(key)) { // 沒有這本書
                return null;
            }
            Node node = keyToNode.get(key); // 有這本書
            remove(node); // 把這本書抽出來
            pushFront(node); // 放在最上面
            return node;
        }

        // 刪除一個節點（抽出一本書）
        private void remove(Node x) {
            x.prev.next = x.next;
            x.next.prev = x.prev;
        }

        // 在鏈表頭添加一個節點（把一本書放在最上面）
        private void pushFront(Node x) {
            x.prev = dummy;
            x.next = dummy.next;
            x.prev.next = x;
            x.next.prev = x;
        }
    }

}
