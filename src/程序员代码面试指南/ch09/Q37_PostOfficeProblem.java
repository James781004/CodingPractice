package 程序员代码面试指南.ch09;

import java.util.Arrays;

public class Q37_PostOfficeProblem {
//    CD90 郵局選址問題
//    描述
//    一條直線上有居民點，郵局只能建在居民點上。
//    給定一個有序整形數組arr，每個值表示居民點的一維坐標，再給定一個正數num，表示郵局數量。
//    選擇num個居民點建立num個郵局，使所有的居民點到郵局的總距離最短，返回最短的總距離。


    public static int minDistances1(int[] arr, int num) {
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }

        // w[i][j]表示如果在arr[i…j]上只能建立一個郵局，最短的總距離。
        int[][] w = new int[arr.length + 1][arr.length + 1];

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                // 首先，郵局建在全部中點絕對有最短總距離結果
                // w[i][j]表示如果在arr[i…j]上只能建立一個郵局，最短的總距離。
                // 對於arr[i...j-1]或者arr[i...j]來說，其實中點都是在arr[(i + j) / 2]位置
                // 例如：arr[1,2,3,4]中點為arr[2]或arr[3]總距離沒有區別，加上arr[5]中點就是arr[3]，距離多出arr[5]-arr[3]
                // 所以如果前面w[i][j - 1]已經保存了arr[i...j-1]的總距離結果，
                // w[i][j]就是w[i][j - 1]多出來個arr[j]的情況，
                // 其實就是多加上了arr[j]到中點arr[(i + j) / 2]的距離而已
                w[i][j] = w[i][j - 1] + arr[j] - arr[(i + j) / 2];
            }
        }

        // 假設dp[i][j]表示如果在arr[0…j]上建立i+1個郵局的最短總距離。
        // 所以dp[0][j]的值表示如果在arr[0…j]上建立一個郵局最短的總距離。很明顯，就是w[0][j]。
        int[][] dp = new int[num][arr.length];
        for (int j = 0; j != arr.length; j++) {
            dp[0][j] = w[0][j];
        }

        // 當可以建立不止一個郵局時，情況如下：
        //　　
        // 1、前i-1個郵局負責arr[0]，第i個郵局負責arr[1…j]，總距離為dp[i-1][0] + w[1][j]
        // 2、前i-1個郵局負責arr[0…1]，第i個郵局負責arr[2…j]，總距離為dp[i-1][1] + w[2][j]
        // 3、前i-1個郵局負責arr[0…k]，第i個郵局負責arr[k+1…j]，總距離為dp[i-1][k] + w[k+1][j]
        //
        // 實際上k的取值到j-1就可以了，因為在還有最後一個居民的時候仍然可以建立一個郵局，
        // 那麽在該居民點建立一個郵局一定沒壞處，
        // 這樣就可以不用考慮：前i-1個郵局負責arr[0…j]，第i個郵局負責arr[j+1…j]的情況，避免w矩陣溢出。

        for (int i = 1; i < num; i++) { // i表示先前已經分配過的i-1個郵局結果
            for (int j = i + 1; j < arr.length; j++) { // j表示arr[0...j]目前所在右邊界
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = 0; k <= j; k++) { // k表示分隔點，k之前的總距離已經處理過，接下來處理k+1開始的範圍
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + w[k + 1][j]);
                }
            }
        }
        return dp[num - 1][arr.length - 1];
    }

    // dp四邊形不等式優化
    public static int minDistances2(int[] arr, int num) {
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }
        int[][] w = new int[arr.length + 1][arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                w[i][j] = w[i][j - 1] + arr[j] - arr[(i + j) / 2];
            }
        }
        int[][] dp = new int[num][arr.length];
        int[][] s = new int[num][arr.length];
        for (int j = 0; j != arr.length; j++) {
            dp[0][j] = w[0][j];
            s[0][j] = 0;
        }
        int minK = 0;
        int maxK = 0;
        int cur = 0;
        for (int i = 1; i < num; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                minK = s[i - 1][j];
                maxK = j == arr.length - 1 ? arr.length - 1 : s[i][j + 1];
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = minK; k <= maxK; k++) {
                    cur = dp[i - 1][k] + w[k + 1][j];
                    if (cur <= dp[i][j]) {
                        dp[i][j] = cur;
                        s[i][j] = k;
                    }
                }
            }
        }
        return dp[num - 1][arr.length - 1];
    }

    // for test
    public static int[] getSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int[] arr = {-2, -1, 0, 1, 2, 1000};
        int num = 2;
        System.out.println(minDistances1(arr, num));
        System.out.println(minDistances2(arr, num));

        int times = 100; // test time
        int len = 1000; // test array length
        int range = 2000; // every number in [0,range)
        int p = 50; // post office number
        long time1 = 0; // method1 all run time
        long time2 = 0;// method2 all run time
        long start = 0;
        long end = 0;
        int res1 = 0;
        int res2 = 0;
        for (int i = 0; i != times; i++) {
            arr = getSortedArray(len, range);
            start = System.currentTimeMillis();
            res1 = minDistances1(arr, p);
            end = System.currentTimeMillis();
            time1 += end - start;
            start = System.currentTimeMillis();
            res2 = minDistances2(arr, p);
            end = System.currentTimeMillis();
            time2 += end - start;
            if (res1 != res2) {
                printArray(arr);
                break;
            }
            if (i % 10 == 0) {
                System.out.print(". ");
            }
        }
        System.out.println();
        System.out.println("method1 all run time(ms): " + time1);
        System.out.println("method2 all run time(ms): " + time2);

    }

}
