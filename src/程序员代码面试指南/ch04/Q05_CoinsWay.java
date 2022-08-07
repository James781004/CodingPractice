package 程序员代码面试指南.ch04;

public class Q05_CoinsWay {
    // 題目
    // 給定一個數組arr，arr中所有的值都為正數且不重覆，每個值代表一種面值的貨幣，
    // 每種面值的貨幣有無數張，再給定一個整數aim代表要找的錢數，求組成aim的最少貨幣數。

    public static int coins1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process1(arr, 0, aim);
    }

    public static int process1(int[] arr, int index, int aim) {
        int res = 0;
        if (index == arr.length) {
            res = aim == 0 ? 1 : 0;
        } else {
            for (int i = 0; arr[index] * i <= aim; i++) {
                res += process1(arr, index + 1, aim - arr[index] * i);
            }
        }
        return res;
    }

    public static int coins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] map = new int[arr.length + 1][aim + 1];
        return process2(arr, 0, aim, map);
    }

    public static int process2(int[] arr, int index, int aim, int[][] map) {
        int res = 0;
        if (index == arr.length) {
            res = aim == 0 ? 1 : 0;
        } else {
            int mapValue = 0;
            for (int i = 0; arr[index] * i <= aim; i++) {
                mapValue = map[index + 1][aim - arr[index] * i];
                if (mapValue != 0) {
                    res += mapValue == -1 ? 0 : mapValue;
                } else {
                    res += process2(arr, index + 1, aim - arr[index] * i, map);
                }
            }
        }
        map[index][aim] = res == 0 ? -1 : res;
        return res;
    }

    public static int process2_2(int[] arr, int index, int aim, int[][] map) {
        if (index == arr.length) {
            return aim == 0 ? 1 : 0;
        } else {
            if (map[index][aim] > 0) return map[index][aim];
            map[index][aim] = process2_2(arr, index + 1, aim, map);
            if (aim - arr[index] >= 0) {
                map[index][aim] += process2_2(arr, index, aim - arr[index], map);
            }

        }

        return map[index][aim];
    }

    public static int coins3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] dp = new int[arr.length][aim + 1]; // dp[目前位置面額][目標金額]
//        dp[0][0] = 1;  // 湊出0元方法只有一種，就是都不拿
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; arr[0] * j <= aim; j++) {
            dp[0][arr[0] * j] = 1;
        }
        int num = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                num = 0;
                for (int k = 0; j - arr[i] * k >= 0; k++) {
                    num += dp[i - 1][j - arr[i] * k];
                }
                dp[i][j] = num;
            }
        }
        return dp[arr.length - 1][aim];
    }

    public static int coins4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] dp = new int[arr.length][aim + 1]; // dp[目前arr位置][目標金額]

        // 湊出0元方法只有一種，就是都不拿
        //        dp[0][0] = 1;
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }

        // 能湊出當前目標值的才算是一種方法
        // 例如：arr[0] == 3，初始化時必須先列出只用3湊出的數值
        // 需要更新的有dp[0][arr[0] * j]
        // dp[0][3], dp[0][6], dp[0][9].....
        // 只能使用arr[0]的情況下其排列方法都只有一種
        // 而其他dp[0][j]因為湊不出來，所以保持0
        for (int j = 1; arr[0] * j <= aim; j++) {
            dp[0][arr[0] * j] = 1;
        }
        for (int i = 1; i < arr.length; i++) { // i代表目前arr位置，目前位置面額為arr[i]
            for (int j = 1; j <= aim; j++) { // j代表目前目標金額，遞增到aim停止

                // 前一種幣值累積的結果
                dp[i][j] = dp[i - 1][j];

                // j代表當下計算的aim值
                // j - arr[i]代表同一種幣值前一次取值的累積結果
                // 例如：
                // arr[i] = 5, aim = 11, j - arr[i] = 6
                // dp[5][11]除了取決於dp[4][11]，也必須要看dp[5][11 - 5]
                // 所以也要注意j - arr[i]當然不能小於0，否則表上沒資料越界
                dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
            }
        }
        return dp[arr.length - 1][aim];
    }

    public static int coins5(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[] dp = new int[aim + 1];
        for (int j = 0; arr[0] * j <= aim; j++) {
            dp[arr[0] * j] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[j] += j - arr[i] >= 0 ? dp[j - arr[i]] : 0;
            }
        }
        return dp[aim];
    }

    public static void main(String[] args) {
        int[] coins = {10, 5, 1, 25};
        int aim = 2000;

        long start = 0;
        long end = 0;
        System.out.println("===========�����ݹ�ķ���===========");
        start = System.currentTimeMillis();
        System.out.println(coins1(coins, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        aim = 20000;

        System.out.println("===========���������ķ���===========");
        start = System.currentTimeMillis();
        System.out.println(coins2(coins, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        System.out.println("=====��̬�滮O(N*(aim^2))�ķ���=====");
        start = System.currentTimeMillis();
        System.out.println(coins3(coins, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        System.out.println("=======��̬�滮O(N*aim)�ķ���=======");
        start = System.currentTimeMillis();
        System.out.println(coins4(coins, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        System.out.println("====��̬�滮O(N*aim)�ķ���+�ռ�ѹ��===");
        start = System.currentTimeMillis();
        System.out.println(coins5(coins, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

    }
}
