package 程序员代码面试指南.ch04;

import java.util.HashMap;

public class Q12_MostEOR {
//    題目描述
//    給定一個整型數組arr，其中可能有正有負有零。
//    你可以隨意把整個數組切成若幹個不相容的子數組，
//    求異或和為0的子數組最多可能有多少個？
//    整數異或和定義：把數組中所有的數異或起來得到的值。
//    鏈接：https://www.nowcoder.com/questionTerminal/77e9828bbe3c4d4a9e0d49cc7537bb6d
//    來源：牛客網

    /*
        dp[i]位置有兩種情況：

        1. arr[i]位置無法被算進最後一組異或和為0的子數組中，此時dp[i] = dp[i-1]；
        2. 可以被算進最後一組異或和為0的子數組中，dp[i] = dp[k-1] + 1,
        其中arr[k...i]為最後一組異或和為0的子數組。
        */
    public static int mostEOR(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int eor = 0;

        // dp[i]表示0...i最多有幾個異或和為0的子數組
        int[] dp = new int[arr.length];

        // map記錄從[0...i]得到的所有異或和，以及最近一次他們出現的數位
        HashMap<Integer, Integer> map = new HashMap<>();
        // 表示沒開始遍歷前，異或和為0
        map.put(0, -1);
        dp[0] = arr[0] == 0 ? 1 : 0;
        map.put(arr[0], 0);

//        考慮arr[i]所在的子數組異或和是否為0，可以分為兩種情況：
//        （1）arr[i]所在的子數組異或和不為0，此時dp[i] = dp[i-1]。
//        （2）arr[i]所載的子數組異或和為0，此時dp[i] = dp[k]+1，
//        其中k代表的含義是arr[0…i]的異或和等於arr[0…k]的異或和，且k是滿足這個條件的最後一個位置。
        for (int i = 1; i < arr.length; i++) {
            // 記錄每一次更新的異或和
            eor ^= arr[i];

            // 如果之前在k-1位置出現過一次eor，那麼k...i表示最近的最後一組異或和為0的子數組
            if (map.containsKey(eor)) {
                int preEorIndex = map.get(eor);
                dp[i] = preEorIndex == -1 ? 1 : (dp[preEorIndex] + 1);
            }
            dp[i] = Math.max(dp[i - 1], dp[i]);
            map.put(eor, i);
        }
        return dp[dp.length - 1];
    }

    // for test
    public static int comparator(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] eors = new int[arr.length];
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
            eors[i] = eor;
        }
        int[] mosts = new int[arr.length];
        mosts[0] = arr[0] == 0 ? 1 : 0;
        for (int i = 1; i < arr.length; i++) {
            mosts[i] = eors[i] == 0 ? 1 : 0;
            for (int j = 0; j < i; j++) {
                if ((eors[i] ^ eors[j]) == 0) {
                    mosts[i] = Math.max(mosts[i], mosts[j] + 1);
                }
            }
            mosts[i] = Math.max(mosts[i], mosts[i - 1]);
        }
        return mosts[mosts.length - 1];
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 300;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int res = mostEOR(arr);
            int comp = comparator(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
