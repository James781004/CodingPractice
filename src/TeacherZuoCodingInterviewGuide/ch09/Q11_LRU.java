package TeacherZuoCodingInterviewGuide.ch09;

import java.util.HashMap;

public class Q11_LRU {
//    CD64 設計LRU緩存結構
//    描述
//    設計LRU緩存結構，該結構在構造時確定大小，假設大小為K，並有如下兩個功能
//    set(key, value)：將記錄(key, value)插入該結構
//    get(key)：返回key對應的value值
//[要求]
//    set和get方法的時間覆雜度為O(1)
//    某個key的set或get操作一旦發生，認為這個key的記錄成了最常使用的。
//    當緩存的大小超過K時，移除最不經常使用的記錄，即set或get最久遠的。

    // 可以用LinkedHashMap，保持key的順序，以便在容量達到k還需往map中添加元素時獲得第一個key並刪除。
    // 需要注意的是，如果map中需要添加重復的key，則需要先把老的key刪掉，然後重新添加鍵值對，
    // 以保證這個剛操作的key又成為了最新的key。
//    LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
//    StringBuilder sb = new StringBuilder();
//        for(int i = 0; i < n; i++){
//        params = br.readLine().split(" ");
//        int opt = Integer.parseInt(params[0]), x = Integer.parseInt(params[1]);
//        if(opt == 1){
//            int y = Integer.parseInt(params[2]);
//            if(map.size() < k){
//                if(map.containsKey(x)) map.remove(x);     // 重新设置过，也要将设置的key在链表中调到末尾
//            }else{
//                // 把最老的key删除
//                int firstKey = map.keySet().iterator().next();
//                map.remove(firstKey);
//            }
//            map.put(x, y);
//        }else{
//            if(!map.containsKey(x)){
//                sb.append(-1 + "\n");
//            }else{
//                sb.append(map.get(x) + "\n");
//                // 将这个键值对改为最新使用，追加到链表的最后
//                int y = map.get(x);
//                map.remove(x);
//                map.put(x, y);
//            }
//        }
//    }
//        System.out.println(sb.toString().trim());


    // 以下是自己建立LinkedHashMap結構的作法
    public static class Node<V> {
        public V value;
        public Node<V> last;
        public Node<V> next;

        public Node(V value) {
            this.value = value;
        }
    }


    public static class NodeDoubleLinkedList<V> {
        private Node<V> head;
        private Node<V> tail;

        public NodeDoubleLinkedList() {
            this.head = null;
            this.tail = null;
        }

        public void addNode(Node<V> newNode) {
            if (newNode == null) return;
            if (this.head == null) {
                this.head = newNode;
                this.tail = newNode;
            } else {
                this.tail.next = newNode;
                newNode.last = this.tail;
                this.tail = newNode;
            }
        }

        public void moveNodeToTail(Node<V> node) {
            if (this.tail == node) return;

            // 首先調整node所在位置的頭尾關係
            if (this.head == node) {
                this.head = node.next;
                this.head.last = null;
            } else {
                node.last.next = node.next;
                node.next.last = node.last;
            }

            // 然後把node加到尾部，並調整tail指針
            node.last = this.tail;
            node.next = null;
            this.tail.next = node;
            this.tail = node;
        }

        public Node<V> removeHead() {
            if (this.head == null) return null;
            Node<V> res = this.head;
            if (this.head == this.tail) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = res.next;
                res.next = null;
                this.head.last = null;
            }
            return res;
        }
    }

    public static class MyCache<K, V> {
        private HashMap<K, Node<V>> keyNodeMap;
        private HashMap<Node<V>, K> nodeKeyMap;
        private NodeDoubleLinkedList<V> nodeList;
        private int capacity;

        public MyCache(int capacity) {
            if (capacity < 1) {
                throw new RuntimeException("should be more than 0.");
            }
            this.keyNodeMap = new HashMap<K, Node<V>>();
            this.nodeKeyMap = new HashMap<Node<V>, K>();
            this.nodeList = new NodeDoubleLinkedList<V>();
            this.capacity = capacity;
        }

        public V get(K key) {
            if (this.keyNodeMap.containsKey(key)) {
                Node<V> res = this.keyNodeMap.get(key);
                this.nodeList.moveNodeToTail(res); // 操作過的節點移動到尾部
                return res.value;
            }
            return null;
        }

        public void set(K key, V value) {
            if (this.keyNodeMap.containsKey(key)) {
                Node<V> node = this.keyNodeMap.get(key);
                node.value = value;
                this.nodeList.moveNodeToTail(node); // 操作過的節點移動到尾部
            } else {
                Node<V> newNode = new Node<V>(value);
                this.keyNodeMap.put(key, newNode);
                this.nodeKeyMap.put(newNode, key);
                this.nodeList.addNode(newNode);
                if (this.keyNodeMap.size() == this.capacity + 1) {
                    this.removeMostUnusedCache(); // 超過容量，開始移除頭節點
                }
            }
        }

        // 雙向鍊表頭節點是最不常用的(LRU)
        // 因為我們前面get, set動作最後都會把當下操作過的節點移動到尾部
        private void removeMostUnusedCache() {
            Node<V> removeNode = this.nodeList.removeHead();
            K removeKey = this.nodeKeyMap.get(removeNode);
            this.nodeKeyMap.remove(removeNode);
            this.keyNodeMap.remove(removeKey);
        }
    }

    public static void main(String[] args) {
        MyCache<String, Integer> testCache = new MyCache<String, Integer>(3);
        testCache.set("A", 1);
        testCache.set("B", 2);
        testCache.set("C", 3);
        System.out.println(testCache.get("B"));
        System.out.println(testCache.get("A"));
        testCache.set("D", 4);
        System.out.println(testCache.get("D"));
        System.out.println(testCache.get("C"));

    }
}
