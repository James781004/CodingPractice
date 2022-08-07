package 程序员代码面试指南.ch09;

import java.util.HashMap;

public class Q32_TopKTimes2 {
//    CD84 出現次數的TopK問題 (進階)
//    描述
//    給定String類型的數組strArr，再給定整數k，請嚴格按照排名順序打印 出次數前k名的字符串。
//            [要求]
//    如果strArr長度為N，時間覆雜度請達到O(NlogK)

    public static class Node {
        public String str;
        public int times;

        public Node(String s, int t) {
            str = s;
            times = t;
        }
    }

    public static class TopKRecord {
        private Node[] heap; // 儲存Node的基本結構
        private int index; // 表示目前在heap中的下標位置
        private HashMap<String, Node> strNodeMap; // 表示字串對應node
        private HashMap<Node, Integer> nodeIndexMap; // 表示node在heap裡的位置，如果不在heap上，value設為-1

        public TopKRecord(int size) {
            heap = new Node[size];
            index = 0;
            strNodeMap = new HashMap<String, Node>();
            nodeIndexMap = new HashMap<Node, Integer>();
        }

        public void add(String str) {
            Node curNode = null;
            int preIndex = -1;
            if (!strNodeMap.containsKey(str)) { // 加入新字串的狀況
                curNode = new Node(str, 1);
                strNodeMap.put(str, curNode);
                nodeIndexMap.put(curNode, -1); // 新字串不在heap上，value設為-1
            } else {
                curNode = strNodeMap.get(str);
                curNode.times++;
                preIndex = nodeIndexMap.get(curNode); // 取得字串先前在heap裡面的位置資訊
            }

            if (preIndex == -1) { // -1表示str原本位置不在heap上，或者node還在初始化階段
                if (index == heap.length) { // heap滿了，如果heap[0]詞頻小於curNode詞頻，就替換掉
                    if (heap[0].times < curNode.times) {
                        nodeIndexMap.put(heap[0], -1); // heap[0]詞頻小於curNode詞頻，移除之，value設為-1
                        nodeIndexMap.put(curNode, 0); // curNode更新位置
                        heap[0] = curNode;
                        heapify(0, index); // 向下heapify調整小根堆
                    }
                } else { // heap沒滿，curNode先放入堆底(index位置)，然後heapInsert調整
                    nodeIndexMap.put(curNode, index);
                    heap[index] = curNode;
                    heapInsert(index++);
                }
            } else { // str原本就在heap上，表示str在詞頻增加前就是Top K，所以必須向下heapify調整小根堆
                heapify(preIndex, index);
            }
        }

        public void printTopK() { // 進階題目沒有要求嚴格照詞頻排序打印，否則打印前要再次大到小heapify排序
            System.out.println("TOP: ");
            for (int i = 0; i != heap.length; i++) {
                if (heap[i] == null) {
                    break;
                }
                System.out.print("Str: " + heap[i].str);
                System.out.println(" Times: " + heap[i].times);
            }
        }

        private void heapInsert(int index) {
            while (index != 0) {
                int parent = (index - 1) / 2;
                if (heap[index].times < heap[parent].times) {
                    swap(parent, index);
                    index = parent;
                } else {
                    break;
                }
            }
        }

        private void heapify(int index, int heapSize) {
            int l = index * 2 + 1;
            int r = index * 2 + 2;
            int smallest = index;
            while (l < heapSize) {
                if (heap[l].times < heap[index].times) {
                    smallest = l;
                }
                if (r < heapSize && heap[r].times < heap[smallest].times) {
                    smallest = r;
                }
                if (smallest != index) {
                    swap(smallest, index);
                } else {
                    break;
                }
                index = smallest;
                l = index * 2 + 1;
                r = index * 2 + 2;
            }
        }

        private void swap(int index1, int index2) {
            nodeIndexMap.put(heap[index1], index2); // 注意node在heap裡的位置在swap的同時，也要跟著修正
            nodeIndexMap.put(heap[index2], index1);
            Node tmp = heap[index1];
            heap[index1] = heap[index2];
            heap[index2] = tmp;
        }

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
        TopKRecord record = new TopKRecord(2);
        record.add("zuo");
        record.printTopK();
        record.add("cheng");
        record.add("cheng");
        record.printTopK();
        record.add("Yun");
        record.add("Yun");
        record.printTopK();

    }


}
