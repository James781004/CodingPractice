package EndlessCheng.GenreMenu.DP.Basic.Ladder;

public class CountTexts {

    // https://leetcode.cn/problems/count-number-of-texts/solutions/1477311/by-endlesscheng-gj8f/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 100_001;
    private static final long[] f = new long[MX];
    private static final long[] g = new long[MX];
    private static boolean done = false;

    // 這樣寫比 static block 快很多
    private void init() {
        if (done) {
            return;
        }
        done = true;
        f[0] = g[0] = 1;
        f[1] = g[1] = 1;
        f[2] = g[2] = 2;
        f[3] = g[3] = 4;
        for (int i = 4; i < MX; i++) {
            f[i] = (f[i - 1] + f[i - 2] + f[i - 3]) % MOD;
            g[i] = (g[i - 1] + g[i - 2] + g[i - 3] + g[i - 4]) % MOD;
        }
    }

    public int countTexts(String S) {
        init();
        long ans = 1;
        int cnt = 0;
        char[] s = S.toCharArray();
        for (int i = 0; i < s.length; i++) {
            char c = s[i];
            cnt++;
            if (i == s.length - 1 || c != s[i + 1]) {
                ans = ans * (c != '7' && c != '9' ? f[cnt] : g[cnt]) % MOD;
                cnt = 0;
            }
        }
        return (int) ans;
    }


}
