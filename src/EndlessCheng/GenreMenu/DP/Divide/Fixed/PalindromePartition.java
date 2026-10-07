package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Arrays;

public class PalindromePartition {

    // https://leetcode.cn/problems/palindrome-partitioning-iii/solutions/3081309/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-z363/
    public int palindromePartition(String s, int k) {
        int n = s.length();
        int[][] memoChange = new int[n][n];
        for (int[] row : memoChange) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        int[][] memoDfs = new int[k][n];
        for (int[] row : memoDfs) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(k - 1, n - 1, s.toCharArray(), memoDfs, memoChange);
    }

    // 把 s[:r+1] 切 i 刀，分成 i+1 個子串，每個子串改成回文串的最小總修改次數
    private int dfs(int i, int r, char[] s, int[][] memoDfs, int[][] memoChange) {
        if (i == 0) { // 只有一個子串
            return minChange(0, r, s, memoChange);
        }
        if (memoDfs[i][r] != -1) { // 之前計算過
            return memoDfs[i][r];
        }
        int res = Integer.MAX_VALUE;
        // 枚舉子串左端點 l
        for (int l = i; l <= r; l++) {
            res = Math.min(res, dfs(i - 1, l - 1, s, memoDfs, memoChange) + minChange(l, r, s, memoChange));
        }
        return memoDfs[i][r] = res; // 記憶化
    }

    // 把 s[i:j+1] 改成回文串的最小修改次數
    private int minChange(int i, int j, char[] s, int[][] memoChange) {
        if (i >= j) { // 子串只有一個字母，或者子串是空串
            return 0; // 無需修改
        }
        if (memoChange[i][j] != -1) { // 之前計算過
            return memoChange[i][j];
        }
        int res = minChange(i + 1, j - 1, s, memoChange);
        if (s[i] != s[j]) {
            res++;
        }
        return memoChange[i][j] = res; // 記憶化
    }


    public int palindromePartitionDP(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] minChange = new int[n][n]; // 把子串 s[i] 到 s[j] 改成回文串，至少要改多少個字母
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                minChange[i][j] = minChange[i + 1][j - 1] + (s[i] != s[j] ? 1 : 0);
            }
        }

        // 把 s[0] 到 s[r] 劃分成 i+1 個非空子串，每個子串修改成回文串，總修改次數的最小值
        int[][] f = new int[k][n];
        f[0] = minChange[0]; // 無需 copy
        for (int i = 1; i < k; i++) {
            // 右邊還有 k−1−i 個子串，所以 r 至多枚舉到 n−1−(k−1−i)=n−k+i
            for (int r = i; r <= n - k + i; r++) {
                f[i][r] = Integer.MAX_VALUE;
                for (int l = i; l <= r; l++) {
                    f[i][r] = Math.min(f[i][r], f[i - 1][l - 1] + minChange[l][r]);
                }
            }
        }
        return f[k - 1][n - 1];
    }

    public int palindromePartitionDP2(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] minChange = new int[n][n];
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                minChange[i][j] = minChange[i + 1][j - 1] + (s[i] != s[j] ? 1 : 0);
            }
        }

        int[] f = minChange[0];
        for (int i = 1; i < k; i++) {
            for (int r = n - k + i; r >= i; r--) {
                f[r] = Integer.MAX_VALUE;
                for (int l = i; l <= r; l++) {
                    f[r] = Math.min(f[r], f[l - 1] + minChange[l][r]);
                }
            }
        }
        return f[n - 1];
    }


}
