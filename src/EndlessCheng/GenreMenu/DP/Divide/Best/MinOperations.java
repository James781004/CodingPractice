package EndlessCheng.GenreMenu.DP.Divide.Best;

public class MinOperations {

    // https://leetcode.cn/problems/minimum-steps-to-convert-string-with-operations/solutions/3695734/hua-fen-xing-dp-tan-xin-pythonjavacgo-by-17kb/
    public int minOperations(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int n = s.length;

        // 預處理 revOp
        int[][] revOp = new int[n][n];
        // 中心擴展法
        // i 為偶數表示奇長度子串，i 為奇數表示偶長度子串
        for (int i = 0; i < 2 * n - 1; i++) {
            cnt = new int[26][26];
            op = 1;
            // 從閉區間 [l, r] 開始向左右擴展
            int l = i / 2;
            int r = (i + 1) / 2;
            while (l >= 0 && r < n) {
                update(s[l], t[r]);
                if (l != r) {
                    update(s[r], t[l]);
                }
                revOp[l][r] = op;
                l--;
                r++;
            }
        }

        int[] f = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int res = Integer.MAX_VALUE;
            cnt = new int[26][26];
            op = 0; // 不反轉時的最小操作次數
            for (int j = i; j >= 0; j--) {
                update(s[j], t[j]);
                res = Math.min(res, f[j] + Math.min(op, revOp[j][i]));
            }
            f[i + 1] = res;
        }
        return f[n];
    }

    private int op;
    private int[][] cnt;

    private void update(char x, char y) {
        if (x == y) {
            return;
        }
        x -= 'a';
        y -= 'a';
        if (cnt[y][x] > 0) {
            cnt[y][x]--;
        } else {
            cnt[x][y]++;
            op++;
        }
    }


}
