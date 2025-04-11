package EndlessCheng.Basic.DP;

public class countTexts {

    // https://leetcode.cn/problems/count-number-of-texts/solutions/1477311/by-endlesscheng-gj8f/
    static final int MOD = (int) 1e9 + 7, MX = (int) 1e5 + 1;
    static final int[] f = new int[MX], g = new int[MX];

    // 預處理：長為 i 的只有一種字符的字符串對應的文字信息種類數
    static {
        f[0] = g[0] = 1;
        f[1] = g[1] = 1;
        f[2] = g[2] = 2;
        f[3] = g[3] = 4;
        for (int i = 4; i < MX; i++) {
            f[i] = (int) (((long) f[i - 1] + f[i - 2] + f[i - 3]) % MOD);
            g[i] = (int) (((long) g[i - 1] + g[i - 2] + g[i - 3] + g[i - 4]) % MOD);
        }
    }

    public int countTexts(String s) {
        int ans = 1, cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            cnt++;
            char c = s.charAt(i);
            if (i == s.length() - 1 || c != s.charAt(i + 1)) {
                ans = (int) ((long) ans * (c != '7' && c != '9' ? f[cnt] : g[cnt]) % MOD);
                cnt = 0;
            }
        }
        return ans;
    }

}
