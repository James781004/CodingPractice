package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountLargestGroup {

    // https://leetcode.cn/problems/count-largest-group/solutions/3648666/liang-chong-fang-fa-bao-li-mei-ju-shu-we-g00u/
    public int countLargestGroup(int n) {
        char[] s = String.valueOf(n).toCharArray();
        int m = s.length;
        int[][] memo = new int[m][m * 9 + 1]; // 設 n 的十進制字符串為 s，設 s 的長度為 m。那麼數位和至多為 9m（每一位都填 9）
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        int maxCnt = 0;
        int ans = 0;

        // 枚舉目標數位和 target=1,2,…,9m，計算 [1,n] 中有多少個整數的數位和恰好等於 target
        for (int target = 1; target <= m * 9; target++) {
            int cnt = dfs(0, target, true, s, memo);
            if (cnt > maxCnt) {
                maxCnt = cnt;
                ans = 1;
            } else if (cnt == maxCnt) {
                ans++;
            }
        }
        return ans;
    }

    // 狀態定義：dfs(i,left,limitHigh) 表示構造第 i 位及其之後數位的合法方案數，其余參數的含義為：
    // left 表示剩余數位的數位和，也就是 i 到 m−1 填的數位之和必須恰好等於 left。
    // limitHigh 表示當前是否受到了 n 的約束（我們要構造的數字不能超過 n）。
    // 若為真，則第 i 位填入的數字至多為 s[i]，否則至多為 9，這個數記作 hi。
    // 如果在受到約束的情況下填了 hi，那麼後續填入的數字仍會受到 n 的約束。
    // 例如 n=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
    private int dfs(int i, int left, boolean limitHigh, char[] s, int[][] memo) {
        if (i == s.length) {
            return left == 0 ? 1 : 0;
        }
        if (!limitHigh && memo[i][left] != -1) {
            return memo[i][left];
        }

        int hi = limitHigh ? s[i] - '0' : 9; // 當前數位至多填 hi
        int res = 0;

        // 狀態轉移。枚舉第 i 位填數字 d=0,1,…,min(hi,left)，
        // 把 i 加一，把 left 減少 d，繼續向後遞歸（枚舉右邊的數位）
        for (int d = 0; d <= Math.min(hi, left); d++) { // 枚舉當前數位填 d
            res += dfs(i + 1, left - d, limitHigh && d == hi, s, memo);
        }

        if (!limitHigh) {
            memo[i][left] = res;
        }
        return res;
    }


}
