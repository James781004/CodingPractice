package TeacherZuoCodingInterviewGuide.ch08;

import java.util.Arrays;

public class Q20_PrintMaxTopK {
//    CD34 打印N個數組整體最大的Top K
//    描述
//    有N個長度不一的數組，所有的數組都是有序的，請從大到小打印這N個數組整體最大的前K個數。
//    例如，輸入含有N行元素的二維數組可以代表N個一維數組。
//            219, 405, 538, 845, 971
//            148, 558
//            52, 99, 348, 691
//    再輸入整數k=5，則打印：
//    Top 5: 971, 845, 691, 558, 538
//            [要求]
//    時間覆雜度為O(klogk)，空間覆雜度為O(klogk)

    public static class HeapNode {
        public int value; // 值
        public int arrNum; // 來自數組
        public int index; // 來自數組位置

        public HeapNode(int value, int arrNum, int index) {
            this.value = value;
            this.arrNum = arrNum;
            this.index = index;
        }
    }

    // 使用heap sort完成
    public static void printTopK(int[][] matrix, int topK) {
//        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int heapSize = matrix.length;
        HeapNode[] heap = new HeapNode[heapSize];
        for (int i = 0; i < heapSize; i++) {
            int index = matrix[i].length - 1;
            heap[i] = new HeapNode(matrix[i][index], i, index);
            heapInsert(heap, i); // 先把每列最後一個元素加進heap
        }

        System.out.println("TOP " + topK + " : ");

        for (int i = 0; i < topK; i++) {
            if (heapSize == 0) { // heapSize用完了就直接停止
                break;
            }
            System.out.print(heap[0].value + " "); // 大根堆頭部就是最大值，直接先打印處理
            if (heap[0].index != 0) {
                heap[0].value = matrix[heap[0].arrNum][--heap[0].index]; // 大根堆頭部就是最大值，找同列前一位加入heap頭部
            } else {
                swap(heap, 0, --heapSize); // 大根堆頭部已經是該列第一位，直接換到尾部後移除，並且縮減heapSize
            }
            heapify(heap, 0, heapSize); // 整理堆
        }
    }

    public static void heapInsert(HeapNode[] heap, int index) {
        while (index != 0) {
            int parent = (index - 1) / 2;
            if (heap[parent].value < heap[index].value) {
                swap(heap, parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    public static void heapify(HeapNode[] heap, int index, int heapSize) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int largest = index;
        while (left < heapSize) {
            if (heap[left].value > heap[largest].value) {
                largest = left;
            }

            if (right < heapSize && heap[right].value > heap[largest].value) {
                largest = right;
            }

            if (largest != index) {
                swap(heap, largest, index);
            } else {
                break;
            }
            index = largest;
            left = index * 2 + 1;
            right = index * 2 + 2;
        }
    }


    public static void swap(HeapNode[] heap, int index1, int index2) {
        HeapNode tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }

    public static int[][] generateRandomMatrix(int maxRow, int maxCol,
                                               int maxValue) {
        if (maxRow < 0 || maxCol < 0) {
            return null;
        }
        int[][] matrix = new int[(int) (Math.random() * maxRow) + 1][];
        for (int i = 0; i != matrix.length; i++) {
            matrix[i] = new int[(int) (Math.random() * maxCol) + 1];
            for (int j = 0; j != matrix[i].length; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue);
            }
            Arrays.sort(matrix[i]);
        }
        return matrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = generateRandomMatrix(5, 10, 1000);
        printMatrix(matrix);
        System.out.println("===========================");
        printTopK(matrix, 100);
    }
}
