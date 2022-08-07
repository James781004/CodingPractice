package TeacherZuoCodingInterviewGuide.ch04;

public class Q03_CoinsMin {
//    換錢的最少貨幣數
//
//    描述
//    給定數組arr，arr中所有的值都為正整數且不重覆。
//    每個值代表一種面值的貨幣，每種面值的貨幣可以使用任意張，
//    再給定一個aim，代表要找的錢數，求組成aim的最少貨幣數。

    public static int minCoins1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim == 0) {
            return -1;
        }
        return process(arr, 0, aim);
    }

    // 當前考慮的面值是arr[i]，還剩rest的錢需要找零
    // 如果返回-1說明自由使用arr[i..N-1]面值的情況下，無論如何也無法找零rest
    // 如果返回不是-1，代表自由使用arr[i..N-1]面值的情況下，找零rest需要的最少張數
    private static int process(int[] arr, int i, int rest) {
        // base case：
        // 已經沒有面值能夠考慮了
        // 如果此時剩余的錢為0，返回0張
        // 如果此時剩余的錢不是0，返回-1
        if (i == arr.length) {
            return rest == 0 ? 0 : -1;
        }

        // 最少張數，初始時為-1，因為還沒找到有效解
        int res = -1;

        // 依次嘗試使用當前面值(arr[i])0張、1張、k張，但不能超過rest
        for (int k = 0; k * arr[i] <= rest; k++) {
            // 使用了k張arr[i]，剩下的錢為rest - k * arr[i]
            // 交給剩下的面值去搞定(arr[i+1..N-1])
            int next = process(arr, i + 1, rest - k * arr[i]);
            if (next != -1) { // 說明這個後續過程有效
                res = res == -1 ? next + k : Math.min(res, next + k);
            }
        }
        return res;
    }


    public static int minCoins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];

        // 之前遞歸方法邊界條件可知：
        // if (i == arr.length) return rest == 0 ? 0 : -1;
        // 所以初始化時設置最後一排的值，除了dp[N][0]為0之外，其他都是-1
        for (int col = 1; col <= aim; col++) {
            dp[N][col] = -1;
        }
        for (int i = N - 1; i >= 0; i--) { // 從底往上計算每一行
            for (int rest = 0; rest <= aim; rest++) { // 每一行都從左往右
                dp[i][rest] = -1; // 初始時先設置dp[i][rest]的值無效
                if (dp[i + 1][rest] != -1) { // 下面的值如果有效
                    dp[i][rest] = dp[i + 1][rest]; // dp[i][rest]的值先設置成下面的值
                }
                // 左邊的位置不越界並且有效
                if (rest - arr[i] >= 0 && dp[i][rest - arr[i]] != -1) {
                    if (dp[i][rest] == -1) { // 如果之前下面的值無效
                        dp[i][rest] = dp[i][rest - arr[i]] + 1;
                    } else { // 說明下面和左邊的值都有效，取最小的
                        dp[i][rest] = Math.min(dp[i][rest],
                                dp[i][rest - arr[i]] + 1);
                    }
                }
            }
        }

        // 之前遞歸方法 return process(arr, 0, aim);
        // 所以這邊也要找[0, aim]當答案
        return dp[0][aim];
    }

    // for test
    public static int[] generateRandomArray(int len, int max) {
        int[] arr = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, max);
            int aim = (int) (Math.random() * 3 * max) + max;
            if (minCoins1(arr, aim) != minCoins2(arr, aim)) {
                System.out.println("ooops!");
                break;
            }
        }
    }
}
