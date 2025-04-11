package EndlessCheng.Basic.LinkedList;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    // https://leetcode.cn/problems/lfu-cache/solutions/2457716/tu-jie-yi-zhang-tu-miao-dong-lfupythonja-f56h/
    private static class Node {
        int key, value, freq = 1; // 新書只讀了一次
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> keyToNode = new HashMap<>();
    private final Map<Integer, Node> freqToDummy = new HashMap<>();
    private int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = getNode(key);
        return node != null ? node.value : -1;
    }

    public void put(int key, int value) {
        Node node = getNode(key);
        if (node != null) { // 有這本書
            node.value = value; // 更新 value
            return;
        }
        if (keyToNode.size() == capacity) { // 書太多了
            Node dummy = freqToDummy.get(minFreq);
            Node backNode = dummy.prev; // 最左邊那摞書的最下面的書
            keyToNode.remove(backNode.key);
            remove(backNode); // 移除
            if (dummy.prev == dummy) { // 這摞書是空的
                freqToDummy.remove(minFreq); // 移除空鏈表
            }
        }
        node = new Node(key, value); // 新書
        keyToNode.put(key, node);
        pushFront(1, node); // 放在「看過 1 次」的最上面
        minFreq = 1;
    }

    private Node getNode(int key) {
        if (!keyToNode.containsKey(key)) { // 沒有這本書
            return null;
        }
        Node node = keyToNode.get(key); // 有這本書
        remove(node); // 把這本書抽出來
        Node dummy = freqToDummy.get(node.freq);
        if (dummy.prev == dummy) { // 抽出來後，這摞書是空的
            freqToDummy.remove(node.freq); // 移除空鏈表
            if (minFreq == node.freq) { // 這摞書是最左邊的
                minFreq++;
            }
        }
        pushFront(++node.freq, node); // 放在右邊這摞書的最上面
        return node;
    }

    // 創建一個新的雙向鏈表
    private Node newList() {
        Node dummy = new Node(0, 0); // 哨兵節點
        dummy.prev = dummy;
        dummy.next = dummy;
        return dummy;
    }

    // 在鏈表頭添加一個節點（把一本書放在最上面）
    private void pushFront(int freq, Node x) {
        Node dummy = freqToDummy.computeIfAbsent(freq, k -> newList());
        x.prev = dummy;
        x.next = dummy.next;
        x.prev.next = x;
        x.next.prev = x;
    }

    // 刪除一個節點（抽出一本書）
    private void remove(Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }


}
