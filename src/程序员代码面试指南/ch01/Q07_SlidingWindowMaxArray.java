package 程序员代码面试指南.ch01;

import java.util.LinkedList;

public class Q07_SlidingWindowMaxArray {
//    滑动窗口的最大值
//    给定一个长度为 n 的数组 nums 和滑动窗口的大小 size ，找出所有滑动窗口里数值的最大值。
//
//    例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
//    针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
//    {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}，
//    {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。

    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        LinkedList<Integer> qmax = new LinkedList<Integer>(); // 保存下標位置(元素值從大排到小)的雙端鏈表
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {

            // 如果當前arr[i]大於鏈表尾部元素
            // 就排除掉鏈表尾部，直到隊尾大於arr[i]，或者鏈表為空為止
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]) {
                qmax.pollLast();
            }
            qmax.addLast(i); // 從尾部開始放入新元素

            // 如果鏈表頭部存放的位置已經低於目前的左邊界位置(i - w)
            // 就排除掉頭部元素
            // 不必擔心鏈表其他元素，他們原本位置一定都在頭部元素之後(for loop往前走的關係)
            if (qmax.peekFirst() == i - w) {
                qmax.pollFirst();
            }

            // 如果目前下標位置已經足夠形成窗口(i從0開始，所以w-1是第一個形成的窗口)，
            // 鏈表頭部元素就是窗口最大值，因為前面while循環有處理元素值排序
            if (i >= w - 1) {
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
        int w = 3;
        printArray(getMaxWindow(arr, w));

    }

}