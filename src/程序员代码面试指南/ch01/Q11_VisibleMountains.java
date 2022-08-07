package 程序员代码面试指南.ch01;

import java.util.HashSet;
import java.util.Stack;

public class Q11_VisibleMountains {
    //    描述
//    一个不含有负数的数组可以代表一圈环形山，每个位置的值代表山的高度。
//    比如，{3,1,2,4,5}，{4,5,3,1,2}或{1,2,4,5,3}都代表同样结构的环形山。
//    3->1->2->4->5->3 方向叫作 next 方向(逆时针)，3->5->4->2->1->3 方向叫作 last 方向(顺时针)。
//    山峰 A 和 山峰 B 能够相互看见的条件为:
//            1. 如果 A 和 B 是同一座山，认为不能相互看见。
//            2. 如果 A 和 B 是不同的山，并且在环中相邻，认为可以相互看见。
//            3. 如果 A 和 B 是不同的山，并且在环中不相邻，假设两座山高度的最小值为 min。
//            如果 A 通过 next 方向到 B 的途中没有高度比 min 大的山峰，
//            或者 A 通过 last 方向到 B 的途中没有高度比 min 大的山峰，认为 A 和 B 可以相互看见。
//    问题如下：
//    给定一个不含有负数且没有重复值的数组 arr，请问有多少对山峰能够相互看见？
//    输入描述：
//    第一行一个整数 T，表示测试数据的组数。
//
//    每组数据的第一行有三个数字 n, p, m，其中 n 表示 山峰的数量，
//
//    山峰的高度数组等于 1 - p 的 p! 个全排列按字典序排序后的第 m 个全排列的前 n 项。
//    输出描述：
//    输出一行一个整数表示答案。
//    示例1
//    输入：
//            1
//            5 5 2
//    输出：
//            7
//    说明：
//            1-5 的全排列排序后的第二个排列 为 1 2 3 5 4
//    备注：
//            100001≤T≤10000
//            10000001≤n≤p≤1000000
//            1≤m≤p!


    // 包裝資料，把值和次數都先記下來: (value, times)
    public static class Record {
        public int value;
        public int times;

        public Record(int value) {
            this.times = 1;
            this.value = value;
        }
    }


    public static int getVisibleNum(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int size = arr.length;
        int maxIndex = 0;

        // 先在環中找到其中一個最大值的位置，哪一個都行
        for (int i = 0; i < size; i++) {
            maxIndex = arr[maxIndex] < arr[i] ? i : maxIndex;
        }

        Stack<Record> stack = new Stack<>();

        // 先把(最大值,1)這個記錄放入stack中
        stack.push(new Record(arr[maxIndex]));

        // 從最大值位置的下一個位置開始沿next方向遍歷
        int index = nextIndex(maxIndex, size);

        // 用“小找大”的方式統計所有可見山峰對
        int res = 0;

        // 遍歷階段開始，當index再次回到maxIndex的時候，說明轉了一圈，遍歷階段就結束
        while (index != maxIndex) {
            // 當前數要進入棧，判斷會不會破壞第一維的數字從頂到底依次變大
            // 如果破壞了，就依次彈出棧頂記錄，並計算山峰對數量
            while (stack.peek().value < arr[index]) {
                int times = stack.pop().times;
                res += getInternalSum(times) + 2 * times;
            }

            // 當前數字arr[index]要進入棧了，如果和當前棧頂數字一樣就合並
            // 不一樣就把記錄(arr[index],1)放入棧中
            if (stack.peek().value == arr[index]) {
                stack.peek().times++;
            } else {
                stack.push(new Record(arr[index]));
            }

            index = nextIndex(index, size);
        }

        // 清算階段開始了
        // 清算階段的第1小階段
        // 旁邊就是maxIndex，pop之後棧頂也肯定大於當前值，所以算法和之前while類似
        while (stack.size() > 2) {
            int times = stack.pop().times;
            res += getInternalSum(times) + 2 * times;
        }

        // 清算階段的第2小階段
        // 1. 如果棧底maxIndex的times為1，說明即使當前pop值就算順逆時鐘都可以遇到maxIndex，
        //    其實也都是同一個maxIndex山峰，按題義不能重複計算，所以只能加上一次times
        // 2. 如果棧底maxIndex的times大於1，說明當前pop值順逆時鐘都可以遇到maxIndex，
        //    而且遇見的是不同的maxIndex山峰，所以要加上2 * times
        while (stack.size() == 2) {
            int times = stack.pop().times;
            res += getInternalSum(times) + (stack.peek().times == 1 ? times : 2 * times);
        }

        // 清算階段的第3小階段
        // 就是maxIndex之間彼此的內部山峰對
        res += getInternalSum(stack.pop().times);
        return res;
    }

    // 如果k==1返回0，如果k>1返回C(2,k)
    // 例如有三座山等高，times = 3
    // 其內部結果就是3 * (3-1) / 2 -> [1,2], [1,3], [2,3]這三對
    public static int getInternalSum(int k) {
        return k == 1 ? 0 : (k * (k - 1) / 2);
    }

    // 環形數組中當前位置為i，數組長度為size，返回i的下一個位置
    public static int nextIndex(int i, int size) {
        return i < (size - 1) ? (i + 1) : 0;
    }

    // 環形數組中當前位置為i，數組長度為size，返回i的上一個位置
    public static int lastIndex(int i, int size) {
        return i > 0 ? (i - 1) : (size - 1);
    }

    // for test, O(N^2)的解法，绝对正确
    public static int rightWay(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        HashSet<String> equalCounted = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            // 枚举从每一个位置出发，根据“小找大”原则能找到多少对儿，并且保证不重复找
            res += getVisibleNumFromIndex(arr, i, equalCounted);
        }
        return res;
    }

    // for test
    // 根据“小找大”的原则返回从index出发能找到多少对
    // 相等情况下，比如arr[1]==3，arr[5]==3
    // 之前如果从位置1找过位置5，那么等到从位置5出发时就不再找位置1（去重）
    // 之前找过的、所有相等情况的山峰对，都保存在了equalCounted中
    public static int getVisibleNumFromIndex(int[] arr, int index,
                                             HashSet<String> equalCounted) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != index) { // 不找自己
                if (arr[i] == arr[index]) {
                    String key = Math.min(index, i) + "_" + Math.max(index, i);
                    // 相等情况下，确保之前没找过这一对
                    if (equalCounted.add(key) && isVisible(arr, index, i)) {
                        res++;
                    }
                } else if (isVisible(arr, index, i)) { // 不相等的情况下直接找
                    res++;
                }
            }
        }
        return res;
    }

    // for test
    // 调用该函数的前提是，lowIndex和highIndex一定不是同一个位置
    // 在“小找大”的策略下，从lowIndex位置能不能看到highIndex位置
    // next方向或者last方向有一个能走通，就返回true，否则返回false
    public static boolean isVisible(int[] arr, int lowIndex, int highIndex) {
        if (arr[lowIndex] > arr[highIndex]) { // “大找小”的情况直接返回false
            return false;
        }
        int size = arr.length;
        boolean walkNext = true;
        int mid = nextIndex(lowIndex, size);
        // lowIndex通过next方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
        while (mid != highIndex) {
            if (arr[mid] > arr[lowIndex]) {
                walkNext = false;// next方向失败
                break;
            }
            mid = nextIndex(mid, size);
        }
        boolean walkLast = true;
        mid = lastIndex(lowIndex, size);
        // lowIndex通过last方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
        while (mid != highIndex) {
            if (arr[mid] > arr[lowIndex]) {
                walkLast = false; // last方向失败
                break;
            }
            mid = lastIndex(mid, size);
        }
        return walkNext || walkLast; // 有一个成功就是能相互看见
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 10;
        int testTimes = 3000000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = getRandomArray(size, max);
            if (rightWay(arr) != getVisibleNum(arr)) {
                printArray(arr);
                System.out.println(rightWay(arr));
                System.out.println(getVisibleNum(arr));
                break;
            }
        }
    }

}
