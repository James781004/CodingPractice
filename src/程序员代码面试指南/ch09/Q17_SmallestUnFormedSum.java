package 程序员代码面试指南.ch09;

import java.util.Arrays;
import java.util.HashSet;

public class Q17_SmallestUnFormedSum {
//    CD68 正數數組的最小不可組成和
//    描述
//    給定一個正數數組arr，其中所有的值都為整數，以下是最小不可組成和的概念
//    把arr每個子集內的所有元素加起來會出現很多值，其中最小的記為min，最大的記為max
//    在區間[min,max]上，如果有數不可以被arr某一個子集相加得到，那麽其中最小的那個數是arr的最小不可組成和
//    在區間[min,max]上，如果所有的數都可以被arr的某一個子集相加得到，那麽max+1
//    是arr的最小不可組成和
//            請寫函數返回正數數組arr的最小不可組成和
//    CD69 正數數組的最小不可組成和-進階問題
//    描述
//    給定一個正數數組arr，其中所有的值都為整數，以下是最小不可組成和的概念
//    把arr每個子集內的所有元素加起來會出現很多值，其中最小的記為min，最大的記為max
//    在區間[min, max]上，如果有數不可以被arr某一個子集相加得到，那麽其中最小的那個數是arr的最小不可組成和
//    在區間[min, max]上，如果所有的數都可以被arr的某一個子集相加得到，那麽max+1是arr的最小不可組成和
//            請寫函數返回正數數組arr的最小不可組成和
//    保證1一定出現過！
//    時間覆雜度為O(n)，額外空間覆雜度為O(1)

    public static int unformedSum1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 1;
        }
        HashSet<Integer> set = new HashSet<>();
        process(arr, 0, 0, set); // 收集所有子集和
        int min = Integer.MAX_VALUE;
        for (int i = 0; i != arr.length; i++) {
            min = Math.min(min, arr[i]);
        }
        for (int i = min + 1; i != Integer.MIN_VALUE; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        return 0;
    }

    public static void process(int[] arr, int i, int sum, HashSet<Integer> set) {
        if (i == arr.length) {
            set.add(sum);
            return;
        }
        process(arr, i + 1, sum, set); // 不考慮arr[i]的子集和
        process(arr, i + 1, sum + arr[i], set); // 考慮arr[i]的子集和
    }

    public static int unformedSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 1;
        }
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            min = Math.min(min, arr[i]);
        }

        boolean[] dp = new boolean[sum + 1]; // 表示dp[i]是arr可以湊出來的子集和
        dp[0] = true; // 不考慮arr中任何元素，一定能湊出0
        for (int i = 0; i != arr.length; i++) { // i代表當前考慮arr[0...i]的子集和
            for (int j = sum; j >= arr[i]; j--) { // 從sum倒扣，由右往左尋找答案
                // 舉例:
                // 1. 如果第一輪當j == arr[0]得到dp[j - arr[0]] == true (預設dp[0] = true)
                // 2. 下一輪當j == arr[0] + arr[1] 時，結果也會變成true
                // 3. 再下一輪當j == arr[0] + arr[2] 或者 j == arr[1] + arr[2]
                //    或者 j == arr[0] + arr[1] + arr[2]時，結果也會變成true
                // 以此類推完成dp
                dp[j] = dp[j - arr[i]] ? true : dp[j];
            }
        }
        for (int i = min; i != dp.length; i++) {
            if (!dp[i]) {
                return i;
            }
        }
        return sum + 1;
    }

    // arr contains 1
    public static int unformedSum3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Arrays.sort(arr);
        int range = 0;
        for (int i = 0; i != arr.length; i++) {
            if (arr[i] > range + 1) {
                // arr已排序，如果arr[i]已超出當前範圍極限(range+1)，
                // 往後就不可能出現range+1的數，所以直接返回range+1
                return range + 1;
            } else {
                range += arr[i];
            }
        }
        return range + 1;
    }

    public static int[] generateArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) + 1;
        }
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 27;
        int max = 30;
        int[] arr = generateArray(len, max);
        printArray(arr);
        long start = System.currentTimeMillis();
        System.out.println(unformedSum1(arr));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
        System.out.println("======================================");

        start = System.currentTimeMillis();
        System.out.println(unformedSum2(arr));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
        System.out.println("======================================");

        System.out.println("set arr[0] to 1");
        arr[0] = 1;
        start = System.currentTimeMillis();
        System.out.println(unformedSum3(arr));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");

    }
}


