package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountGoodIntegersOnPath {

    // https://leetcode.cn/problems/count-good-integers-on-a-grid-path/solutions/3954671/shang-xia-jie-shu-wei-dppythonjavacgo-by-feyc/
    public long countGoodIntegersOnPath(long l, long r, String directions) {
        char[] lowS = String.valueOf(l).toCharArray();
        char[] highS = String.valueOf(r).toCharArray();
        int n = highS.length;

        boolean[] inPath = new boolean[n];
        int pos = n - 16; // 右下角是下標 n-1，那麼左上角是下標 n-16
        for (char d : directions.toCharArray()) {
            if (pos >= 0) { // 只需要對網格圖的後 n 個格子做標記
                inPath[pos] = true; // 標記在路徑中的格子
            }
            pos += d == 'R' ? 1 : 4; // 往下相當於往右數 4 個位置
        }
        inPath[n - 1] = true; // 終點一定在路徑中

        long[][] memo = new long[n][10];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(0, 0, true, true, lowS, highS, inPath, memo);
    }

    // dfs(i,pre,limitLow,limitHigh) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // pre 表示前一個在路徑中的數字，初始值為 0。
    // limitHigh 表示當前是否受到了 high 的約束（當前要填的數字不能超過 high）。
    // 若為真，則第 i 位填入的數字至多為 high[i]，否則至多為 9，這個數記作 hi。
    // 如果在受到約束的情況下填了 hi，那麼後續填入的數字仍會受到 high 的約束。
    // 例如 high=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    // limitLow 表示當前是否受到了 low 的約束（當前要填的數字不能低於 low）。
    // 若為真，則第 i 位填入的數字至少為 low[i]，否則至少為 0，這個數記作 lo。
    // 如果在受到約束的情況下填了 lo，那麼後續填入的數字仍會受到 low 的約束。
    private long dfs(int i, int pre, boolean limitLow, boolean limitHigh, char[] lowS, char[] highS, boolean[] inPath, long[][] memo) {
        if (i == highS.length) { // 成功到達終點
            return 1; // 找到了一個好整數
        }

        if (!limitLow && !limitHigh && memo[i][pre] >= 0) {
            return memo[i][pre];
        }

        int diff = highS.length - lowS.length;
        int lo = limitLow && i >= diff ? lowS[i - diff] - '0' : 0;
        int hi = limitHigh ? highS[i] - '0' : 9;

        long res = 0;
        // 如果當前位置在路徑中，那麼當前位置填的數必須 >= pre
        int start = inPath[i] ? Math.max(lo, pre) : lo;

        // 枚舉第 i 位填數字 d=lo,lo+1…,hi
        for (int d = start; d <= hi; d++) {
            // 繼續遞歸，把 i 加一，如果 i 在路徑中，那麼把 pre 置為 d，否則 pre 不變
            // 如果 i 在路徑中，那麼 d 從 max(lo,pre) 開始枚舉，從而保證路徑上的數字是非遞減的
            res += dfs(i + 1,
                    inPath[i] ? d : pre,
                    limitLow && d == lo,
                    limitHigh && d == hi,
                    lowS, highS, inPath, memo);
        }

        if (!limitLow && !limitHigh) {
            memo[i][pre] = res;
        }
        return res;
    }


}
