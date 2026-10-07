package EndlessCheng.GenreMenu.DP.Linear.LCS.Advance;

import java.util.Arrays;

public class NumWays {


    // https://leetcode.cn/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/solutions/2836348/ji-yi-hua-sou-suo-di-tui-kong-jian-you-h-3b2w/
    private final int mod = 1_000_000_007;
    int[][] idx;

    public int numWays(String[] words, String target) {
        int n = words[0].length();
        int m = target.length();
        int[][] memo = new int[n][m];
        idx = new int[n][26];
        for (int[] a : memo) Arrays.fill(a, -1);
        for (int i = 0; i < n; ++i) {
            for (String s : words) {
                idx[i][s.charAt(i) - 'a']++; // 預處理第i位為某個字符的word的個數
            }
        }
        return dfs(words, target, memo, n - 1, m - 1) % mod;
    }

    // 定義dfs(i,j),代表了使用word[0...i]中的字符構造target[0...i]的方案數。
    // (其中word代表了words中的每一個單詞)
    // 如果words中每個單詞word對應的i位置等於target[j]的數量為cnt，那麼可以有如下的狀態轉移方程：
    // dfs(i,j)=cnt∗dfs(i−1,j−1)+dfs(i−1,j)代表了選或者是不選
    // 如果cnt為0，那麼就只能是dfs(i,j)=dfs(i−1,j)
    // 直接用上面的式子寫在一起即可。
    public int dfs(String[] words, String target, int[][] memo, int i, int j) {
        if (j < 0) return 1; // 構造成功一個target
        if (i < 0) return 0; // target還沒構造完word就已經遍歷完了每一位
        if (memo[i][j] != -1) return memo[i][j];
        int cnt = idx[i][target.charAt(j) - 'a']; // 第i位為target.charAt(j)的word的個數
        long res = dfs(words, target, memo, i - 1, j);
        long t = dfs(words, target, memo, i - 1, j - 1);
        res = (res + t * cnt % mod) % mod; // 如果cnt是0返回的就是不選word的第i位，否則就是用word的第i位成功構造target的第j位
        return memo[i][j] = (int) res;
    }


}
