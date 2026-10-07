package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

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

        // 反轉兩個字符：
        // 如果都是 0 或者都是 1，那麼反轉後 1 的個數會加 2 或者減 2；
        // 如果一個是 0 另一個是 1，那麼反轉後 1 的個數不變。
        // 所以，無論是哪種操作，都不會改變 s1 中的 1 的個數的奇偶性。
        // 那麼只要 s1 和 s2 的 1 的個數一個是奇數一個是偶數，就直接返回 −1。
        // 否則，哪怕只用第二種操作，都一定可以讓 s1=s2
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

    // 考慮 s1 和 s2 的最後一對字符（也可以考慮第一對字符）：
    // 如果相同，那麼無需修改。
    // 如果不同：
    // 選擇第一種操作，相當於後面可以「免費」反轉一個字符。
    // 選擇第二種操作，那麼下一個字符要把 0 看作 1，把 1 看作 0。
    // 所以除了知道當前下標 i，還需要知道免費反轉次數 j，以及上一個字符是否選擇了第二種操作 preRev。
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


    public int minOperationsDP(String s1, String s2, int x) {
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
        int f0 = 0, f1 = x;
        for (int i = 1; i < p.size(); i++) {
            int newF = Math.min(f1 + x, f0 + (p.get(i) - p.get(i - 1)) * 2);
            f0 = f1;
            f1 = newF;
        }
        return f1 / 2;
    }


}
