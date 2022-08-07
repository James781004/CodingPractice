package 程序员代码面试指南.ch04;

public class Q06_BurstBalloons {
    //    打氣球的最大分數
//    描述
//    給定一個數組arr,長度為n。代表排有分數的氣球。
//    每打爆一個氣球都能獲得分數，假設打爆氣球的分數為X，獲得分數的規則如下:
//    1)如果被打爆氣球的左邊有沒被打爆的氣球，找到離被打爆氣球最近的氣球，
//    假設分數為L:如果被打爆氣球的右邊有沒被打爆的氣球，
//    找到離被打爆氣球最近的氣球，假設分數為R.獲得分數為L*X*R
//    2)如果被打爆的氣球的左邊有沒被打爆的氣球，找到離被打爆氣球最近的氣球，
//    假設分數為L:如果被打爆氣球的右邊所有氣球都已經被打爆，獲得分數為L*X。
//    3)如果被打爆氣球的左邊所有的氣球都已經被打爆:如果被打爆氣球的右邊有沒被打爆的氣球，
//    找到離被打爆氣球最近的氣球。獲得分數為X*R.
//    4)如果被打爆氣球的左邊和右邊所有的氣球都已經被打爆。獲得分數為X。
//    目標是打爆所有氣球，獲得每次打爆的分數。通過選擇打爆氣球的順序，可以得到不同的總分，請返回能獲得的最大分數。

    public static int maxCoins1(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        if (arr.length == 1) return 1;
        int N = arr.length;
        int[] help = new int[N + 2]; // 前後空位多放的代表1的假氣球，以方便計算
        help[0] = 1;
        help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        return process(help, 1, N);
    }

    // 打爆arr[L..R]範圍上的所有氣球，返回最大的分數
    // 假設arr[L-1]和arr[R+1]一定沒有被打爆
    public static int process(int[] arr, int L, int R) {

        // 如果arr[L..R]範圍上只有一個氣球，直接打爆即可
        if (L == R) {
            return arr[L - 1] * arr[L] * arr[R + 1];
        }

        // 最後打爆arr[L]的方案，和最後打爆arr[R]的方案，先比較一下
        int max = Math.max(
                arr[L - 1] * arr[L] * arr[R + 1] + process(arr, L + 1, R),
                arr[L - 1] * arr[R] * arr[R + 1] + process(arr, L, R - 1));

        // 嘗試中間位置的氣球最後被打爆的每一種方案
        for (int i = L + 1; i < R; i++) {
            max = Math.max(max,
                    arr[L - 1] * arr[i] * arr[R + 1] +
                            process(arr, L, i - 1) +
                            process(arr, i + 1, R));
        }
        return max;
    }

    public static int maxCoins1_2(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        if (arr.length == 1) return 1;
        int N = arr.length;
        int[] help = new int[N + 2]; // 前後空位多放的代表1的假氣球，以方便計算
        help[0] = 1;
        help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }

        int[][] memory = new int[help.length][help.length];
        return burstBalloons(help, 1, N, memory);
    }

    // 記憶化搜索的方案
    // 主函數裡面必須先建立起記憶表： int[][] memory = new int[help.length][help.length];
    // 每次return之前先用memory保存結果，進入遞歸後先檢查memory[left][right]是否有記錄可以直接調用
    private static int burstBalloons(int[] arr, int left, int right, int[][] memory) {
        if (memory[left][right] > 0) return memory[left][right];

        // 如果arr[L..R]範圍上只有一個氣球，直接打爆即可
        if (left == right) {
            memory[left][right] = arr[left - 1] * arr[left] * arr[right + 1];
            return memory[left][right];
        }

        // 最後打爆arr[left]的方案，和最後打爆arr[right]的方案，先比較一下
        int max = Math.max(
                arr[left - 1] * arr[left] * arr[right + 1] + burstBalloons(arr, left + 1, right, memory),
                arr[left - 1] * arr[right] * arr[right + 1] + burstBalloons(arr, left, right - 1, memory));

        // 嘗試中間位置的氣球最後被打爆的每一種方案
        for (int i = left + 1; i < right; i++) {
            max = Math.max(max,
                    arr[left - 1] * arr[i] * arr[right + 1] +
                            burstBalloons(arr, left, i - 1, memory) +
                            burstBalloons(arr, i + 1, right, memory));
        }

        memory[left][right] = max;
        return memory[left][right];
    }

    public static int maxCoins2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        int N = arr.length;
        int[] help = new int[N + 2];
        help[0] = 1;
        help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        int[][] dp = new int[N + 2][N + 2]; // dp[左邊界][右邊界]

        // 如果arr[L..R]範圍上只有一個氣球，直接打爆即可
        // 遞歸方法: if (L == R) return arr[L - 1] * arr[L] * arr[R + 1];
        for (int i = 1; i <= N; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
            System.out.println(dp[i][i]);
        }

        // 根據前面的遞歸方法改寫成dp表
        for (int L = N; L >= 1; L--) {
            for (int R = L + 1; R <= N; R++) {
                // 求解dp[L][R]，表示help[L..R]上打爆所有氣球的最大分數
                // 最後打爆help[L]的方案
                int finalL = help[L - 1] * help[L] * help[R + 1] + dp[L + 1][R];
                // 最後打爆help[R]的方案
                int finalR = help[L - 1] * help[R] * help[R + 1] + dp[L][R - 1];
                // 最後打爆help[L]的方案，和最後打爆help[R]的方案，先比較一下
                dp[L][R] = Math.max(finalL, finalR);
                // 嘗試中間位置的氣球最後被打爆的每一種方案
                for (int i = L + 1; i < R; i++) {
                    dp[L][R] = Math.max(dp[L][R], help[L - 1] * help[i]
                            * help[R + 1] + dp[L][i - 1] + dp[i + 1][R]);
                }
            }
        }
        return dp[1][N];
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 3, 5, 1, 6};
        System.out.println(maxCoins1(arr));
        System.out.println(maxCoins1_2(arr));
        System.out.println(maxCoins2(arr));
    }
}
