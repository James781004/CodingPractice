package TeacherZuoCodingInterviewGuide.ch09;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class Q12_LFU {
//    CD65 LFU緩存結構設計
//    描述
//    一個緩存結構需要實現如下功能。
//    set(key, value)：將記錄(key, value)插入該結構
//    get(key)：返回key對應的value值
//    但是緩存結構中最多放K條記錄，如果新的第K+1條記錄要加入，就需要根據策略刪掉一條記錄，然後才能把新記錄加入。
//    這個策略為：在緩存結構的K條記錄中，哪一個key從進入緩存結構的時刻開始，被調用set或者get的次數最少，就刪掉這個key的記錄；
//    如果調用次數最少的key有多個，上次調用發生最早的key被刪除
//    這就是LFU緩存替換算法。實現這個結構，K作為參數給出
//[要求]
//    set和get方法的時間覆雜度為O(1)


    //    可以使用LinkedHashSet
    //    顧名思義，是鏈表和哈希集合的結合體。
    //    鏈表不能快速訪問鏈表節點，但是插入元素具有時序；
    //    哈希集合中的元素無序，但是可以對元素進行快速的訪問和刪除。
    public static class LFUCacheByLinkedHashSet {
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

        public LFUCacheByLinkedHashSet(int capacity) {
            keyToVal = new HashMap<>();
            keyToFreq = new HashMap<>();
            freqToKeys = new HashMap<>();
            this.cap = capacity;
            this.minFreq = 0;
        }

        public int get(int key) {
            if (!keyToVal.containsKey(key)) {
                return -1;
            }
            // 增加 key 對應的 freq
            increaseFreq(key);
            return keyToVal.get(key);
        }

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

        private void removeMinFreqKey() {
            // freq 最小的 key 列表
            LinkedHashSet<Integer> keyList = freqToKeys.get(this.minFreq);
            // 其中最先被插入的那個 key 就是該被淘汰的 key
            int deletedKey = keyList.iterator().next();
            /* 更新 FK 表 */
            keyList.remove(deletedKey);
            if (keyList.isEmpty()) {
                freqToKeys.remove(this.minFreq);
            }
            /* 更新 KV 表 */
            keyToVal.remove(deletedKey);
            /* 更新 KF 表 */
            keyToFreq.remove(deletedKey);
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
    }


    // 以下是自行建立的LinkedHashSet結構
    // 節點的數據結構
    public static class Node {
        public Integer key;
        public Integer value;
        public Integer times; // 這個節點發生get或者set的次數總和
        public Node up; // 節點之間是雙向鏈表所以有上一個節點
        public Node down;// 節點之間是雙向鏈表所以有下一個節點

        public Node(int key, int value, int times) {
            this.key = key;
            this.value = value;
            this.times = times;
        }
    }

    // 桶結構
    public static class NodeList {
        public Node head; // 桶的頭節點
        public Node tail; // 桶的尾節點
        public NodeList last; // 桶之間是雙向鏈表所以有前一個桶
        public NodeList next; // 桶之間是雙向鏈表所以有後一個桶

        public NodeList(Node node) {
            head = node;
            tail = node;
        }

        // 把一個新的節點加入這個桶，新的節點都放在頂端變成新的頭部
        public void addNodeFromHead(Node newHead) {
            newHead.down = head;
            head.up = newHead;
            head = newHead;
        }

        // 判斷這個桶是不是空的
        public boolean isEmpty() {
            return head == null;
        }

        // 刪除node節點並保證node的上下環境重新連接
        public void deleteNode(Node node) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                if (node == head) {
                    head = node.down;
                    head.up = null;
                } else if (node == tail) {
                    tail = node.up;
                    tail.down = null;
                } else {
                    node.up.down = node.down;
                    node.down.up = node.up;
                }
            }
            node.up = null;
            node.down = null;
        }
    }

    // 總緩存結構
    public static class LFUCache {
        private int capacity; // 緩存的大小限制，即K
        private int size; // 緩存目前有多少個節點
        private HashMap<Integer, Node> records;// 表示key(Integer)由哪個節點(Node)代表
        private HashMap<Node, NodeList> heads; // 表示節點(Node)在哪個桶(NodeList)里
        private NodeList headList; // 整個結構中位於最左的桶

        public LFUCache(int K) {
            this.capacity = K;
            this.size = 0;
            this.records = new HashMap<>();
            this.heads = new HashMap<>();
            headList = null;
        }

        // removeNodeList：剛剛減少了一個節點的桶
        // 這個函數的功能是，判斷剛剛減少了一個節點的桶是不是已經空了。
        // 1）如果不空，什麼也不做
        //
        // 2)如果空了，removeNodeList還是整個緩存結構最左的桶(headList)。
        // 刪掉這個桶的同時也要讓最左的桶變成removeNodeList的下一個。
        //
        // 3)如果空了，removeNodeList不是整個緩存結構最左的桶(headList)。
        // 把這個桶刪除，並保證上一個的桶和下一個桶之間還是雙向鏈表的連接方式
        //
        // 函數的返回值表示剛剛減少了一個節點的桶是不是已經空了，空了返回true；不空返回false
        private boolean modifyHeadList(NodeList removeNodeList) {
            if (removeNodeList.isEmpty()) {
                if (headList == removeNodeList) {
                    headList = removeNodeList.next;
                    if (headList != null) {
                        headList.last = null;
                    }
                } else {
                    removeNodeList.last.next = removeNodeList.next;
                    if (removeNodeList.next != null) {
                        removeNodeList.next.last = removeNodeList.last;
                    }
                }
                return true;
            }
            return false;
        }

        // 函數的功能
        // node這個節點的次數+1了，這個節點原來在oldNodeList里。
        // 把node從oldNodeList刪掉，然後放到次數+1的桶中
        // 整個過程既要保證桶之間仍然是雙向鏈表，也要保證節點之間仍然是雙向鏈表
        private void move(Node node, NodeList oldNodeList) {
            oldNodeList.deleteNode(node);
            // preList表示次數+1的桶的前一個桶是誰
            // 如果oldNodeList刪掉node之後還有節點，oldNodeList就是次數+1的桶的前一個桶
            // 如果oldNodeList刪掉node之後空了，oldNodeList是需要刪除的，所以次數+1的桶的前一個桶，是oldNodeList的前一個
            // 所以preList也有可能為空，如果本來oldNodeList就是headList且沒有後續list的話
            NodeList preList = modifyHeadList(oldNodeList) ? oldNodeList.last
                    : oldNodeList;
            // nextList表示次數+1的桶的後一個桶是誰
            NodeList nextList = oldNodeList.next;

            // nextList不存在的話就新增一個
            if (nextList == null) {
                NodeList newList = new NodeList(node);
                if (preList != null) {
                    preList.next = newList;
                }
                newList.last = preList;
                if (headList == null) {
                    headList = newList;
                }
                heads.put(node, newList);
            } else {
                // nextList存在，nextList.head.times符合，直接插入頭部
                if (nextList.head.times.equals(node.times)) {
                    nextList.addNodeFromHead(node);
                    heads.put(node, nextList);
                } else {
                    // nextList存在，times不符合，新增newList並設定好前後關係
                    NodeList newList = new NodeList(node);
                    if (preList != null) {
                        preList.next = newList;
                    }
                    newList.last = preList;
                    newList.next = nextList;
                    nextList.last = newList;

                    // 如果headList == nextList表示本結構當前其實只有一個list存在
                    // 所以這狀況headList指針指向newList
                    if (headList == nextList) {
                        headList = newList;
                    }
                    heads.put(node, newList);
                }
            }
        }

        public void set(int key, int value) {
            if (records.containsKey(key)) {
                Node node = records.get(key);
                node.value = value;
                node.times++;
                NodeList curNodeList = heads.get(node);
                move(node, curNodeList);
            } else {
                // 如果size已經到了極限，就必須刪除目前headList的尾部（定義上的LFU）
                // 並且要進行modifyHeadList操作來調整headList
                // 同時在records, heads中的LFU資料也必須移除，然後整體size減1
                if (size == capacity) {
                    Node node = headList.tail;
                    headList.deleteNode(node);
                    modifyHeadList(headList);
                    records.remove(node.key);
                    heads.remove(node);
                    size--;
                }
                Node node = new Node(key, value, 1); // 新節點times必定是1

                // headList為空表示還在初始狀態，新增headList然後加入新節點即可
                if (headList == null) {
                    headList = new NodeList(node);
                } else {
                    // 如果前headList是1，新節點就放入headList頭部
                    if (headList.head.times.equals(node.times)) {
                        headList.addNodeFromHead(node);
                    } else {
                        // 當前headList已經不是1
                        // 那就新增一個newList並設定成新的headList
                        NodeList newList = new NodeList(node);
                        newList.next = headList;
                        headList.last = newList;
                        headList = newList;
                    }
                }

                // records, heads中加入新節點資料，然後整體size++
                records.put(key, node);
                heads.put(node, headList);
                size++;
            }
        }

        public Integer get(int key) {
            if (!records.containsKey(key)) {
                return null;
            }
            Node node = records.get(key);
            node.times++; // 對node進行了操作，所以times++
            NodeList curNodeList = heads.get(node); // 從heads找尋node所在的NodeList
            move(node, curNodeList); // node.times已經變動，所以要移動到適當位置
            return node.value;
        }
    }
}
