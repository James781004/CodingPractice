package TeacherZuoCodingInterviewGuide.ch09;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Q31_TopKSumCrossTwoArrays {
//    CD83 兩個有序數組間相加和的Topk問題
//    描述
//    給定兩個有序數組arr1和arr2，再給定一個整數k，返回來自arr1和arr2的兩個數相加和最大的前k個，兩個數必須分別來自兩個數組
//            按照降序輸出
//[要求]
//    時間覆雜度為O(klogk)

    public static class Node {
        public int index1; // arr1中的位置
        public int index2; // arr2中的位置
        public int value; // arr1[index1] + arr2[index2]的值

        public Node(int i1, int i2, int sum) {
            index1 = i1;
            index2 = i2;
            value = sum;
        }
    }

    // 從大value排到小value
    public static class MaxHeapComp implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o2.value - o1.value;
        }
    }

    public static int[] topKSum(int[] arr1, int[] arr2, int topK) {
        if (arr1 == null || arr2 == null || topK < 1) return null;
        topK = Math.min(topK, arr1.length) * arr2.length;
        int[] res = new int[topK];
        int resIndex = 0; // res的下標位置
        PriorityQueue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComp());
        HashSet<String> positionSet = new HashSet<>();
        int i1 = arr1.length - 1;
        int i2 = arr2.length - 1;
        maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2])); // 首先加入必定是最大的組合: 兩數組最後一個數字的和
        positionSet.add(String.valueOf(i1 + "_" + i2)); // 已經使用過的組合就加入set
        while (resIndex != topK) {
            Node cueNode = maxHeap.poll(); // 大根堆頂部必定是當下最大值
            res[resIndex++] = cueNode.value; // 當下最大值存進res，下標後移
            i1 = cueNode.index1; // 更新i1, i2下標
            i2 = cueNode.index2;

            // 加入接下來可能是最大的組合: arr1[i1 - 1] + arr2[i2]
            if (!positionSet.contains(String.valueOf((i1 - 1) + "_" + i2))) {
                maxHeap.add(new Node(i1, i2, arr1[i1 - 1] + arr2[i2]));
                positionSet.add(String.valueOf((i1 - 1) + "_" + i2));  // 已經使用過的組合就加入set
            }

            // 加入接下來可能是最大的組合: arr1[i1] + arr2[i2 - 1]
            if (!positionSet.contains(String.valueOf(i1 + "_" + (i2 - 1)))) {
                maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2 - 1]));
                positionSet.add(String.valueOf(i1 + "_" + (i2 - 1)));  // 已經使用過的組合就加入set
            }
        }
        return res;
    }


    // For test, this method is inefficient but absolutely right
    public static int[] topKSumTest(int[] arr1, int[] arr2, int topK) {
        int[] all = new int[arr1.length * arr2.length];
        int index = 0;
        for (int i = 0; i != arr1.length; i++) {
            for (int j = 0; j != arr2.length; j++) {
                all[index++] = arr1[i] + arr2[j];
            }
        }
        Arrays.sort(all);
        int[] res = new int[Math.min(topK, all.length)];
        index = all.length - 1;
        for (int i = 0; i != res.length; i++) {
            res[i] = all[index--];
        }
        return res;
    }

    public static int[] generateRandomSortArray(int len) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * 50000) + 1;
        }
        Arrays.sort(res);
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i != arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int a1Len = 5000;
        int a2Len = 4000;
        int k = 2000;
        int[] arr1 = generateRandomSortArray(a1Len);
        int[] arr2 = generateRandomSortArray(a2Len);
        long start = System.currentTimeMillis();
        int[] res = topKSum(arr1, arr2, k);
        long end = System.currentTimeMillis();
        System.out.println(end - start + " ms");

        start = System.currentTimeMillis();
        int[] absolutelyRight = topKSumTest(arr1, arr2, k);
        end = System.currentTimeMillis();
        System.out.println(end - start + " ms");

        System.out.println(isEqual(res, absolutelyRight));

    }
}
