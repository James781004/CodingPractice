package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountFancy {

    // https://leetcode.cn/problems/count-fancy-numbers-in-a-range/solutions/3925643/zhuang-tai-ji-shu-wei-dppythonjavacgo-by-4gpn/
    static class Solution {
        // 數位和不是好數的好數（只有 139 個）
        private static final List<Long> goodNums = new ArrayList<>();
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            // 枚舉 {0,1,2,...,9} 的非空子集
            for (int mask = 1; mask < (1 << 10); mask++) {
                // 構造嚴格遞減好數
                long x = 0;
                int sum = 0;
                for (int i = 9; i >= 0; i--) { // 從大到小遍歷子集中的元素
                    if ((mask >> i & 1) > 0) {
                        x = x * 10 + i;
                        sum += i;
                    }
                }
                if (!isGood(sum)) {
                    goodNums.add(x);
                }

                // 構造嚴格遞增好數
                if ((mask & 1) > 0) { // 不能包含 0
                    continue;
                }
                x = 0;
                sum = 0;
                for (int i = 1; i < 10; i++) { // 從小到大遍歷子集中的元素
                    if ((mask >> i & 1) > 0) {
                        x = x * 10 + i;
                        sum += i;
                    }
                }
                if (!isGood(sum)) {
                    goodNums.add(x);
                }
            }

            Collections.sort(goodNums); // 方便二分求個數
        }

        public long countFancy(long l, long r) {
            // 計算 [l, r] 內的數位和不是好數的好數的個數
            long ans = lowerBound(r + 1) - lowerBound(l);

            // 計算 [l, r] 內的數位和是好數的數的個數（這個數是不是好數都可以）
            char[] lowS = String.valueOf(l).toCharArray();
            char[] highS = String.valueOf(r).toCharArray();
            int n = highS.length;
            long[][] memo = new long[n][n * 9 + 1]; // 數位和最大 9n
            return ans + dfs(0, 0, true, true, lowS, highS, memo);
        }

        // 數位 DP
        // digitSum，表示已填數字之和（數位和）
        private long dfs(int i, int digitSum, boolean limitLow, boolean limitHigh,
                         char[] lowS, char[] highS, long[][] memo) {
            if (i == highS.length) {
                return isGood(digitSum) ? 1 : 0;
            }

            if (!limitLow && !limitHigh && memo[i][digitSum] > 0) {
                return memo[i][digitSum] - 1;
            }

            int diffLh = highS.length - lowS.length;
            int lo = limitLow && i >= diffLh ? lowS[i - diffLh] - '0' : 0;
            int hi = limitHigh ? highS[i] - '0' : 9;

            long res = 0;
            int d = lo;

            // 通過 limitLow 和 i 可以判斷能否不填數字，無需 isNum 參數
            if (limitLow && i < diffLh) {
                // 不填數字，上界不受約束
                res = dfs(i + 1, 0, true, false, lowS, highS, memo);
                d = 1; // 下面填數字，從 1 開始填
            }

            for (; d <= hi; d++) {
                res += dfs(i + 1, digitSum + d,
                        limitLow && d == lo, limitHigh && d == hi, lowS, highS, memo);
            }

            if (!limitLow && !limitHigh) {
                memo[i][digitSum] = res + 1; // 這樣寫無需初始化 memo 為 -1
            }
            return res;
        }

        // 判斷數位和 s 是否為好數
        private boolean isGood(int s) {
            if (s < 100) { // s 是個位數或者兩位數
                return s / 10 != s % 10; // 十位和個位不相等即為好數
            }
            // s 是三位數，其百位一定是 1
            return 1 < s / 10 % 10 && s / 10 % 10 < s % 10; // 只能嚴格遞增
        }

        // 返回 goodNums 中第一個 >= x 的數的下標
        // 如果沒有這樣的數，返回 goodNums.size()
        private int lowerBound(long x) {
            // goodNums 沒有重復元素，可以用庫函數
            int i = Collections.binarySearch(goodNums, x);
            return i < 0 ? ~i : i;
        }
    }


}
