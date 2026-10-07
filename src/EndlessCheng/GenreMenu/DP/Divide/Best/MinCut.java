package EndlessCheng.GenreMenu.DP.Divide.Best;

import java.util.Arrays;

public class MinCut {

    // https://leetcode.cn/problems/palindrome-partitioning-ii/solutions/3588633/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-bnlb/
    public int minCut(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] palMemo = new int[n][n];
        for (int[] row : palMemo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        int[] dfsMemo = new int[n];
        Arrays.fill(dfsMemo, -1); // -1 表示沒有計算過
        return dfs(n - 1, s, palMemo, dfsMemo);
    }

    private int dfs(int r, char[] s, int[][] palMemo, int[] dfsMemo) {
        if (isPalindrome(0, r, s, palMemo)) { // 已是回文串，無需分割
            return 0;
        }
        if (dfsMemo[r] != -1) { // 之前計算過
            return dfsMemo[r];
        }
        int res = Integer.MAX_VALUE;
        for (int l = 1; l <= r; l++) { // 枚舉分割位置
            if (isPalindrome(l, r, s, palMemo)) {
                res = Math.min(res, dfs(l - 1, s, palMemo, dfsMemo) + 1); // 在 l-1 和 l 之間切一刀
            }
        }
        return dfsMemo[r] = res; // 記憶化
    }

    private boolean isPalindrome(int l, int r, char[] s, int[][] palMemo) {
        if (l >= r) {
            return true;
        }
        if (palMemo[l][r] != -1) { // 之前計算過
            return palMemo[l][r] == 1;
        }
        boolean res = s[l] == s[r] && isPalindrome(l + 1, r - 1, s, palMemo);
        palMemo[l][r] = res ? 1 : 0; // 記憶化
        return res;
    }


    public int minCutDP(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        // isPalindrome[l][r] 表示 s[l] 到 s[r] 是否為回文串
        boolean[][] isPalindrome = new boolean[n][n];
        for (boolean[] row : isPalindrome) {
            Arrays.fill(row, true);
        }
        for (int l = n - 2; l >= 0; l--) {
            for (int r = l + 1; r < n; r++) {
                isPalindrome[l][r] = s[l] == s[r] && isPalindrome[l + 1][r - 1];
            }
        }

        int[] f = new int[n];
        for (int r = 0; r < n; r++) {
            if (isPalindrome[0][r]) { // 已是回文串，無需分割
                continue;
            }
            int res = Integer.MAX_VALUE;
            for (int l = 1; l <= r; l++) { // 枚舉分割位置
                if (isPalindrome[l][r]) {
                    res = Math.min(res, f[l - 1] + 1); // 在 l-1 和 l 之間切一刀
                }
            }
            f[r] = res;
        }
        return f[n - 1];
    }


}
