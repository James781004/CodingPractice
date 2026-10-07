package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountPrimeSetBits {

    // https://leetcode.cn/problems/prime-number-of-set-bits-in-binary-representation/solutions/3905546/san-chong-fang-fa-bao-li-mei-ju-shu-wei-ej6m6/
    class Context {
        int minNum;
        int maxNum;
        int[][] cache;

        Context(int minNum, int maxNum, int[][] cache) {
            this.minNum = minNum;
            this.maxNum = maxNum;
            this.cache = cache;
        }
    }

    // 數位DP，不考慮前導零
    public int countPrimeSetBits(int left, int right) {
        int n = 32 - Integer.numberOfLeadingZeros(right);
        // cache[i]: 已經填充了i位，二進制1的個數為j，填充完後的整數二進制1個數是質數的數量
        int[][] cache = new int[n][n + 1];
        for (int[] row : cache) {
            Arrays.fill(row, -1);
        }
        Context context = new Context(left, right, cache);
        // 從二進制高位往低位倒序遍歷
        return dfs(n - 1, 0, true, true, context);
    }

    private int dfs(int i, int cnt1, boolean isLowerBound, boolean isUpperBound, Context context) {
        if (i == -1) {
            // 二進制數填充完成，統計二進制1的個數是質數的個數
            return isPrime(cnt1) ? 1 : 0;
        }
        if (!isLowerBound && !isUpperBound && context.cache[i][cnt1] > -1) {
            return context.cache[i][cnt1];
        }
        // 上界和下界
        int high = isUpperBound ? (context.maxNum >> i & 1) : 1;
        int low = isLowerBound ? (context.minNum >> i & 1) : 0;
        int res = 0;
        for (int bit = low; bit <= high; bit++) {
            boolean isNextLower = isLowerBound && bit == low;
            boolean isNextUpper = isUpperBound && bit == high;
            res += dfs(i - 1, bit + cnt1, isNextLower, isNextUpper, context);
        }
        if (!isLowerBound && !isUpperBound) {
            context.cache[i][cnt1] = res;
        }
        return res;
    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            // 0,1不是質數
            return false;
        }
        if (n <= 3) {
            // 2,3是質數
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        // 6倍步長遍歷（大於5的質數必在 6k±1 位置）
        for (int i = 5; i <= n / i; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }


}
