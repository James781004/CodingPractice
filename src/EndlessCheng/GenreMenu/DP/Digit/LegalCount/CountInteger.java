package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountInteger {

    // https://leetcode.cn/problems/count-of-integers/solutions/2296043/shu-wei-dp-tong-yong-mo-ban-pythonjavacg-9tuc/
    private static final int MOD = 1_000_000_007;

    class Context {
        char[] minNum;
        char[] maxNum;
        int diff;
        int minSum;
        int maxSum;
        int[][] cache;

        Context(char[] c1, char[] c2, int minSum, int maxSum, int[][] cache) {
            this.minNum = c1;
            this.maxNum = c2;
            this.minSum = minSum;
            this.maxSum = maxSum;
            this.cache = cache;
            this.diff = c2.length - c1.length;
        }
    }

    // 數位DP
    public int count(String num1, String num2, int min_sum, int max_sum) {
        char[] c1 = num1.toCharArray();
        char[] c2 = num2.toCharArray();
        int n = c2.length;
        // cache[i][s]: 已經填充前i個數位，和為s時，剩余待填充整數的方案數
        int[][] cache = new int[n][Math.min(9 * n, max_sum) + 1];
        for (int[] row : cache) {
            Arrays.fill(row, -1);
        }
        Context context = new Context(c1, c2, min_sum, max_sum, cache);
        return dfs(0, 0, true, true, context);
    }

    private int dfs(int i, int digitSum, boolean isLowerBound, boolean isUpperBound, Context context) {
        int n = context.maxNum.length;
        if (i == n) {
            return digitSum >= context.minSum ? 1 : 0;
        }
        if (!isLowerBound && !isUpperBound && context.cache[i][digitSum] > -1) {
            // 非上下界才需要記憶化，因為上下界不會重復遍歷
            return context.cache[i][digitSum];
        }
        int res = 0;
        // 當前可選數字的下界
        int low = (isLowerBound && i >= context.diff)
                ? context.minNum[i - context.diff] - '0'
                : 0;
        // 當前可選數字的上界
        int high = isUpperBound ? context.maxNum[i] - '0' : 9;
        // 枚舉范圍內的數字
        for (int num = low; num <= high; num++) {
            if (num + digitSum > context.maxSum) {
                // 剪枝：由於num遞增，和不會減少
                break;
            }
            boolean nextIsLower = isLowerBound && num == low;
            boolean nextIsUpper = isUpperBound && num == high;
            res = (res + dfs(i + 1, num + digitSum,
                    nextIsLower, nextIsUpper, context)) % MOD;
        }
        if (!isLowerBound && !isUpperBound) {
            context.cache[i][digitSum] = res;
        }
        return res;
    }


}
