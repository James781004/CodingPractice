package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class CountPalindromes {

    // https://leetcode.cn/problems/count-palindromic-subsequences/solutions/1993115/qian-hou-zhui-fen-jie-o100-chang-shu-kon-51cv/
    private static final long MOD = (long) 1e9 + 7;

    public int countPalindromes(String S) {
        var s = S.toCharArray();
        int[] pre = new int[10], suf = new int[10];
        int[][] pre2 = new int[10][10], suf2 = new int[10][10];
        for (var i = s.length - 1; i >= 0; --i) {
            var d = s[i] - '0';
            for (var j = 0; j < 10; ++j)
                suf2[d][j] += suf[j];
            ++suf[d];
        }

        var ans = 0L;
        for (var d : s) {
            d -= '0';
            --suf[d];
            for (var j = 0; j < 10; ++j)
                suf2[d][j] -= suf[j]; // 撤銷
            for (var j = 0; j < 10; ++j)
                for (var k = 0; k < 10; ++k)
                    ans += (long) pre2[j][k] * suf2[j][k]; // 枚舉所有字符組合
            for (var j = 0; j < 10; ++j)
                pre2[d][j] += pre[j];
            ++pre[d];
        }
        return (int) (ans % MOD);
    }

   
}
