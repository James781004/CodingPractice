package TeacherZuoCodingInterviewGuide.ch01;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q08_MonotonousStack {
//    描述
//    给定一个不含有重复值的数组 arr，找到每一个 i 位置左边和右边离 i 位置最近且值比 arr[i] 小的位置。返回所有位置相应的信息。
//
//    输入描述：
//    第一行输入一个数字 n，表示数组 arr 的长度。
//
//    以下一行输出 n个数字，表示数组的值。
//    输出描述：
//    输出n行，每行两个数字 L 和 R，如果不存在，则值为-1，下标从0开始。
//    示例1
//    输入：
//            7
//            3 4 1 5 6 2 7
//    复制
//    输出：
//            -1 2
//            0 2
//            -1 -1
//            2 5
//            3 5
//            2 -1
//            5 -1
//    复制
//    备注：
//            0000001≤n≤1000000
//            1000000−1000000≤arri≤1000000

    public static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            // stack 從下而上必須是遞增結構(愈放愈大)，如果頂部(stack.peek())比準備進來的arr[i]大，則開始操作：
            // pop 出頂部並且用popIndex記錄下位置index
            // 這個index的左右邊小於自己的最近位置已經找到：
            // 右邊就是現在的數字arr[i]，位置就是i
            // 左邊要看stack是否為空，如果空了表示左邊不存在返回-1，不然就返回stack頂部的數字
            // 持續操作到stack為空或者stack頂部小於arr[i]，才把i位置放進stack
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int popIndex = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                res[popIndex][0] = leftLessIndex;
                res[popIndex][1] = i;
            }
            stack.push(i);
        }

        // arr數組走完，處理stack裡面剩下的index
        // pop 出頂部並且用popIndex記錄下位置index
        // 這個index的左右邊小於自己的最近位置已經找到：
        // 右邊已經沒數字，所以固定返回-1
        // 左邊要看stack是否為空，如果空了表示左邊不存在返回-1，不然就返回stack頂部的數字
        while (!stack.isEmpty()) {
            int popIndex = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            res[popIndex][0] = leftLessIndex;
            res[popIndex][1] = -1;
        }

        return res;
    }

    // 如果arr有重複值的情況
    public static int[][] getNearLess(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>(); // 使用List來處理重複數值，重複且晚加入的元素從頂部list隊尾入棧
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> popList = stack.pop();

                // 取位於正下方的list中，最晚加入的元素(即list.size()-1)
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer popIndex : popList) {
                    res[popIndex][0] = leftLessIndex;
                    res[popIndex][1] = i;
                }
            }

            // stack 頂部是重複值（即arr[i]）的狀況，直接放入頂部的List
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                // stack 頂部新增List
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }

        while (!stack.isEmpty()) {
            List<Integer> popList = stack.pop();
            // 取位於正下方的列表中，最晚加入的那个
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer popIndex : popList) {
                res[popIndex][0] = leftLessIndex;
                res[popIndex][1] = -1; // 右邊已經沒數字，所以固定返回-1
            }
        }

        return res;
    }

    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops!");
                printArray(arr2);
                break;
            }
        }
    }
}
