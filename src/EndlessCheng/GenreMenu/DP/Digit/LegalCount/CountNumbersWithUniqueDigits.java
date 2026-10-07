package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountNumbersWithUniqueDigits {

    // https://leetcode.cn/problems/count-numbers-with-unique-digits/solutions/2619131/java-shu-wei-dp-by-happy-kalamqap-nuum/
    // 同 2376 題
    private char[] s;
    private int[][] memo;

    public int countNumbersWithUniqueDigits(int cnt) {
        if (cnt == 0) return 1;
        int n = 1;
        for (int i = 0; i < cnt; i++) {
            n *= 10;
        }
        n--;
        s = Integer.toString(n).toCharArray();
        memo = new int[10][1 << 10];
        for (int i = 0; i < 10; i++) Arrays.fill(memo[i], -1);
        return dfs(0, 0, true, false) + 1;
    }

    private int dfs(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length) return isNum ? 1 : 0;
        if (memo[i][mask] != -1 && !isLimit && isNum) return memo[i][mask];
        int ret = 0;
        if (!isNum) {
            ret += dfs(i + 1, mask, false, false);
        }
        int start = isNum ? 0 : 1;
        int end = isLimit ? s[i] - '0' : 9;
        for (int j = start; j <= end; j++) {
            if (((mask >> j) & 1) == 0) ret += dfs(i + 1, (1 << j) | mask, isLimit && j == end, true);
        }
        if (!isLimit && isNum) memo[i][mask] = ret;
        return ret;
    }


}
