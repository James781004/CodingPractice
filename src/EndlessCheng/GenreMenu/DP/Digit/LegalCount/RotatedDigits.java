package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class RotatedDigits {

    // https://leetcode.cn/problems/rotated-digits/solutions/1847409/by-endlesscheng-9b96/
    private static int[] DIFFS = {0, 0, 1, -1, -1, 1, 1, -1, 0, 1};

    public int rotatedDigits(int n) {
        char[] s = Integer.toString(n).toCharArray();
        int[][] memo = new int[s.length][2];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, 0, true, s, memo);
    }

    // f(i,hasDiff,isLimit) 表示構造從左往右第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // hasDiff 表示前面填的數字是否包含 2,5,6,9（至少一個）。
    // isLimit 表示當前是否受到了 n 的約束。若為真，則第 i 位填入的數字至多為 s[i]，否則可以是 9。
    // 如果在受到約束的情況下填了 s[i]，那麼後續填入的數字仍會受到 n 的約束。
    private int dfs(int i, int hasDiff, boolean isLimit, char[] s, int[][] memo) {
        if (i == s.length) {
            return hasDiff; // 只有包含 2/5/6/9 才算一個好數
        }
        if (!isLimit && memo[i][hasDiff] >= 0) {
            return memo[i][hasDiff];
        }
        int res = 0;
        int up = isLimit ? s[i] - '0' : 9;
        for (int d = 0; d <= up; d++) { // 枚舉要填入的數字 d
            if (DIFFS[d] != -1) { // d 不是 3/4/7
                res += dfs(i + 1, hasDiff | DIFFS[d], isLimit && d == up, s, memo);
            }
        }
        if (!isLimit) {
            memo[i][hasDiff] = res;
        }
        return res;
    }


}
