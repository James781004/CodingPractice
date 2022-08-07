package 程序员代码面试指南.ch01;

import java.util.LinkedList;

public class Q10_AllLessNumSubArray {
//    描述
//    给定数组 arr 和整数 num，共返回有多少个子数组满足如下情况：
//    max(arr[i...j]) - min(arr[i...j]) <= num
//    max(arr[i...j])表示子数组arr[i...j]中的最大值，min[arr[i...j])表示子数组arr[i...j]中的最小值。
//
//    输入描述：
//    第一行输入两个数 n 和 num，其中 n 表示数组 arr 的长度
//            第二行输入n个整数X_iX
//    i
//        ，表示数组arr中的每个元素
//    输出描述：
//    输出给定数组中满足条件的子数组个数
//            示例1
//    输入：
//            5 2
//            1 2 3 4 5
//    输出：
//            12
//    备注：
//            10000001 ≤ n ≤ 1000000
//            1000000 ≤ arri ≤ 10000000
//          20000000≤num≤2000000


    // 兩個結論：
    // 1.到j不滿足條件，a[i..j-1]滿足條件所以a[m..n]都滿足條件(i<=m<=n<=j-1)
    // 2.a[i..j]不滿足條件，則包含它的也都不滿足條件(a[l..k],l<=i<=j<=k)
    // 用a[i..j-1]代入條件：max(a[i..j])-min(a[i..j])<=num里進行歸納
    public static int getNum(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num == 0) return 0;

        LinkedList<Integer> qmin = new LinkedList<Integer>();
        LinkedList<Integer> qmax = new LinkedList<Integer>();
        int i = 0, j = 0, res = 0;

        while (i < arr.length) {  // 求以arr[i]為開頭，滿足條件最長的子序列
            while (j < arr.length) { // arr[j]為當前右邊界
                // 為了保證同一個下標只進棧一次，出棧一次，這樣時間覆雜度才能保證(每個元素O(1)，n個元素O(n))
                // 如果break,j不變，而qmin.peekLast()正好是上一輪的j，後面i++，所以判斷[i+1..j]是否滿足條件
                // 到j不滿足條件，所以[i+1..j]不一定滿足條件
                if (qmin.isEmpty() || qmin.peekLast() != j) {

                    // qmin是保存當下最小值，尾部大於或等於arr[j]就去掉
                    while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]) {
                        qmin.pollLast();
                    }
                    qmin.addLast(j);

                    // qmax是保存當下最大值，尾部小於或等於arr[j]就去掉
                    while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]) {
                        qmax.pollLast();
                    }
                    qmax.addLast(j);
                }

                // 當不再滿足題意，max(arr[i...j] - min(arr[i...j]) <= num 退出當前循環
                // 此時最大值減去最小值大於num一定是由於j的插入
                if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
                    break;
                }

                j++;
            }

            // 以arr[i]為第一個元素的子數組，滿足條件的j-i個
            // (因為前面break後，j位置其實是在正確右邊界位置+1的位置，所以其實最多只有取到a[i..j-1]，範圍j-1-i+1 -> j-i)
            // 以i開頭一共j-i個，a[i..i]到a[i..j-1]
            res += j - i;

            // 開頭i要自增，應該把隊列中的i移除
            // i只可能在最大和最小地方出現，因為arr[i]必定是第一個進入隊列的元素，如果不在就是已經提前被彈出了
            // 總之下次循環從i+1開始，必須先刪除隊列中已經不在窗口內的元素arr[i]
            if (qmin.peekFirst() == i) {
                qmin.pollFirst();
            }
            if (qmax.peekFirst() == i) {
                qmax.pollFirst();
            }
            i++;
        }

        return res;
    }


    // for test
    public static int[] getRandomArray(int len) {
        if (len < 0) {
            return null;
        }
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * 10);
        }
        return arr;
    }

    // for test
    public static int getNumRightWay(int[] arr, int num) {
        int res = 0;
        for (int start = 0; start < arr.length; start++) {
            for (int end = start; end < arr.length; end++) {
                if (isValid(arr, start, end, num)) {
                    res++;
                }
            }
        }
        return res;
    }

    public static boolean isValid(int[] arr, int start, int end, int num) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = start; i <= end; i++) {
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }
        return (max - min) <= num;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int size = 5;
        int testTime = 1000000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = getRandomArray(size);
            int num = (int) (Math.random() * size);
            if (getNum(arr, num) != getNumRightWay(arr, num)) {
                printArray(arr);
                System.out.println(num);
                System.out.println(getNum(arr, num));
                System.out.println(getNumRightWay(arr, num));
                break;
            }
        }
    }
}

