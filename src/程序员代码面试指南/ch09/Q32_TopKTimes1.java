package 程序员代码面试指南.ch09;

import java.util.HashMap;
import java.util.Map;

public class Q32_TopKTimes1 {
//    CD84 出現次數的TopK問題
//    描述
//    給定String類型的數組strArr，再給定整數k，請嚴格按照排名順序打印 出次數前k名的字符串。
//            [要求]
//    如果strArr長度為N，時間覆雜度請達到O(NlogK)


    public static class Node {
        public String str; // 目前輸入字符
        public int times; // 詞頻

        public Node(String s, int t) {
            str = s;
            times = t;
        }
    }

    public static void printTopKAndRank(String[] arr, int topK) {
        if (arr == null || topK < 1) return;

        // 生成詞頻統計表
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            String cur = arr[i];
            if (!map.containsKey(cur)) {
                map.put(cur, 1);
            } else {
                map.put(cur, map.get(cur) + 1);
            }
        }

        // 遍歷map並決定每筆資料是否進堆
        Node[] heap = new Node[topK];
        int index = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String str = entry.getKey();
            int times = entry.getValue();
            Node node = new Node(str, times);
            if (index != topK) { // 堆裡元素總數未滿topK，直接heapInsert
                heap[index] = node;
                heapInsert(heap, index++);
            } else {
                if (heap[0].times < node.times) { // 堆頭times小於新元素，就以新元素替換堆頭，然後heapify
                    heap[0] = node;
                    heapify(heap, 0, topK);
                }
            }
        }

        // 因為需要嚴格按照詞頻統計打印，所以這邊把小根堆所有元素按詞頻由大到小排序
        // 把值最小的堆頭移往隊尾，然後剩下部分heapify，不斷重複直到i走到頭
        for (int i = index - 1; i > 0; i--) {
            swap(heap, 0, i);
            heapify(heap, 0, i);
        }

        // 按照排名印出k筆資料
        for (int i = 0; i != heap.length; i++) {
            if (heap[i] == null) {
                break;
            } else {
                System.out.print("No." + (i + 1) + ": ");
                System.out.print(heap[i].str + ", times: ");
                System.out.println(heap[i].times);
            }
        }


        //    如果沒有要求自行實現一個結構，使用PriorityQueue比較方便
//        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
//            @Override
//            public int compare(Node Node1, Node Node2) {
//                if (Node1.times != Node2.times)
//                    return Node2.times - Node1.times;
//                else
//                    return Node1.str.compareTo(Node2.str);
//            }
//        });
//        for (String key : map.keySet())
//            pq.offer(new Node(key, map.get(key)));
//        for (int i = 0; i < topK; i++) {
//            Node Node = pq.poll();
//            System.out.println(Node.str + " " + Node.times);
//        }
    }


    private static void heapInsert(Node[] heap, int index) {
        while (index != 0) {
            int parent = (index - 1) / 2;
            if (heap[index].times < heap[parent].times) {
                swap(heap, parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    private static void heapify(Node[] heap, int index, int heapSize) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int smallest = index;
        while (left < heapSize) {
            if (heap[left].times < heap[smallest].times) {
                smallest = left;
            }
            if (right < heapSize && heap[right].times < heap[smallest].times) {
                smallest = right;
            }
            index = smallest;
            left = index * 2 + 1;
            right = index * 2 + 2;
        }
    }

    private static void swap(Node[] heap, int index1, int index2) {
        Node tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }


    public static String[] generateRandomArray(int len, int max) {
        String[] res = new String[len];
        for (int i = 0; i != len; i++) {
            res[i] = String.valueOf((int) (Math.random() * (max + 1)));
        }
        return res;
    }

    public static void printArray(String[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] arr = generateRandomArray(50, 10);
        int topK = 3;
        printArray(arr);
        printTopKAndRank(arr, topK);

    }


}
