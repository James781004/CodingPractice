package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class FindIntegers {

    // https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/solutions/1750941/by-endlesscheng-1egu/
    public int findIntegers(int n) {
        int m = Integer.SIZE - Integer.numberOfLeadingZeros(n);
        int[][] memo = new int[m][2];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(m - 1, 0, true, n, memo); // 從高位到低位
    }

    // dfs(i,pre,isLimit,isNum) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // pre 表示前一個比特位是否為 1。如果是 1，則當前比特位不能填 1。
    // isLimit 表示當前是否受到了 n 的約束。
    // 若為真，則第 i 位填入的數字至多為 n >> i & 1，否則可以是 1。
    // 設填入的數字至多為 up，如果在 isLimit 為真的情況下填了 up，那麼後續填入的數字仍會受到 n 的約束。
    private int dfs(int i, int pre, boolean isLimit, int n, int[][] memo) {
        if (i < 0) {
            return 1;
        }
        if (!isLimit && memo[i][pre] >= 0) { // 之前計算過
            return memo[i][pre];
        }
        int up = isLimit ? n >> i & 1 : 1;
        int res = dfs(i - 1, 0, isLimit && up == 0, n, memo); // 填 0
        if (pre == 0 && up == 1) { // 可以填 1
            res += dfs(i - 1, 1, isLimit, n, memo); // 填 1
        }
        if (!isLimit) {
            memo[i][pre] = res; // 記憶化
        }
        return res;
    }

}
