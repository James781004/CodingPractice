package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountDistinct {

    // https://leetcode.cn/problems/count-distinct-integers-after-removing-zeros/solutions/3832951/tong-ji-bu-han-0-de-zheng-shu-ge-shu-pyt-6pun/
    class Context {
        char[] minNum;
        char[] maxNum;
        int diff;
        long[][] cache;

        Context(char[] c1, char[] c2, long[][] cache) {
            this.minNum = c1;
            this.maxNum = c2;
            this.diff = c2.length - c1.length;
            this.cache = cache;
        }
    }

    // 在1-n范圍內，移除數位上的零後，數字一定也在范圍內，即變為不含數字零的數字，因此，只需要統計不含數字零的數字數量即可。
    // 數位DP，需要考慮前導零
    public long countDistinct(long n) {
        char[] minNum = {'1'};
        char[] maxNum = String.valueOf(n).toCharArray();
        int len = maxNum.length;
        // cache[i][0/1]: 已經填充了i位數字，並且不包含/包含0，填充完剩余數字後，整數不含數字零的數字數量
        long[][] cache = new long[len][2];
        for (long[] row : cache) {
            Arrays.fill(row, -1);
        }
        Context context = new Context(minNum, maxNum, cache);
        return dfs(0, 0, true, true, context);
    }

    private long dfs(int i, int isContains0, boolean isLowerBound, boolean isUpperBound, Context context) {
        if (i == context.maxNum.length) {
            // 統計不含數字零的數字數量
            return isContains0 == 0 ? 1 : 0;
        }
        if (!isLowerBound && !isUpperBound && context.cache[i][isContains0] > -1) {
            // 上下界只會遍歷一次，無需記憶化
            return context.cache[i][isContains0];
        }
        // 當前可選數字上界
        int high = isUpperBound ? context.maxNum[i] - '0' : 9;
        // 當前可選數字下界
        int low = (isLowerBound && i >= context.diff)
                ? context.minNum[i - context.diff] - '0'
                : 0;

        long res = 0;
        int start = low;
        // 考慮前導零，前導零不屬於整數數位零
        if (isLowerBound && i < context.diff) {
            res = dfs(i + 1, 0, true, false, context);
            start = 1;
        }
        // 枚舉當前數字（過濾掉前導零）
        for (int num = start; num <= high; num++) {
            // 注意：下界還是low，只是這裡如果num=low=0,屬於前導零，已經遍歷過，所以從1開始，下界還是0
            boolean isNextLower = isLowerBound && num == low;
            boolean isNextUpper = isUpperBound && num == high;
            int isNextContains0 = (isContains0 == 1 || num == 0) ? 1 : 0;
            res += dfs(i + 1, isNextContains0, isNextLower, isNextUpper, context);
        }
        if (!isUpperBound && !isLowerBound) {
            context.cache[i][isContains0] = res;
        }
        return res;
    }


}
