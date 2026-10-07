package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

import java.util.Arrays;

public class CountArrays {

    // https://leetcode.cn/problems/count-non-decreasing-arrays-with-given-digit-sums/solutions/3939449/liang-chong-fang-fa-qian-zhui-he-you-hua-dndq/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 5001;
    private static final int MAX_DIGIT_SUM = 31; // 4999 的數位和最大
    private static final int[] digSum = new int[MX];
    private static boolean initialized = false;

    // 這樣寫比 static block 快
    public CountArrays() {
        if (initialized) {
            return;
        }
        initialized = true;

        // 預處理數位和
        for (int x = 0; x < MX; x++) {
            // 去掉 x 的個位，問題變成 x / 10 的數位和，即 digSum[x / 10]
            digSum[x] = digSum[x / 10] + x % 10;
        }
    }

    public int countArrays(int[] digitSum) {
        int[] sum = new int[MX]; // f 的前綴和
        Arrays.fill(sum, 1);
        for (int ds : digitSum) {
            if (ds > MAX_DIGIT_SUM) {
                return 0;
            }
            for (int x = 0; x < MX; x++) {
                // 如果 digSum[x] != ds，那麼 f[x] = 0，否則 f[x] = sum[x]
                // 把 f[x] 的值填到 sum[x] 中，那麼只需要把 digSum[x] != ds 的 sum[x] 置為 0
                if (digSum[x] != ds) {
                    sum[x] = 0;
                }
                if (x > 0) {
                    sum[x] = (sum[x] + sum[x - 1]) % MOD;
                }
            }
        }
        return sum[MX - 1];
    }


}
