package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinOperations {

    // https://leetcode.cn/problems/apply-operations-to-make-two-strings-equal/solutions/2472122/ji-yi-hua-sou-suo-by-endlesscheng-64vq/
    public int minOperations(String s1, String s2, int x) {
        char[] s = s1.toCharArray(), t = s2.toCharArray();
        int n = s.length, diff = 0;
        for (int i = 0; i < n; i++) {
            diff ^= s[i] ^ t[i];
        }
        if (diff != 0) {
            return -1;
        }
        int[][][] memo = new int[n][n + 1][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(memo[i][j], -1);// -1 表示沒有計算過
            }
        }
        return dfs(n - 1, 0, 0, memo, s, t, x);
    }

    private int dfs(int i, int j, int preRev, int[][][] memo, char[] s, char[] t, int x) {
        if (i < 0) { // 遞歸邊界
            return j > 0 || preRev > 0 ? Integer.MAX_VALUE / 2 : 0;
        }
        if (memo[i][j][preRev] != -1) { // 之前計算過
            return memo[i][j][preRev];
        }
        if ((s[i] == t[i]) == (preRev == 0)) { // 無需反轉
            return dfs(i - 1, j, 0, memo, s, t, x);
        }
        int res = Math.min(dfs(i - 1, j + 1, 0, memo, s, t, x) + x, dfs(i - 1, j, 1, memo, s, t, x) + 1);
        if (j > 0) { // 可以免費反轉
            res = Math.min(res, dfs(i - 1, j - 1, 0, memo, s, t, x));
        }
        return memo[i][j][preRev] = res; // 記憶化
    }


    public int minOperations2(String s1, String s2, int x) {
        if (s1.equals(s2)) {
            return 0;
        }
        List<Integer> p = new ArrayList<>();
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                p.add(i);
            }
        }
        if (p.size() % 2 != 0) {
            return -1;
        }
        int f0 = 0, f1 = x; //  f[i] 表示修改 p 的前 i 個位置的最小花費
        for (int i = 1; i < p.size(); i++) {
            int newF = Math.min(f1 + x, f0 + (p.get(i) - p.get(i - 1)) * 2);
            f0 = f1;
            f1 = newF;
        }
        return f1 / 2;
    }


}
