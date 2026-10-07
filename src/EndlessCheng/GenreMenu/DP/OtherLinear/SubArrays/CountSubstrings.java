package EndlessCheng.GenreMenu.DP.OtherLinear.SubArrays;

public class CountSubstrings {

    // https://leetcode.cn/problems/count-substrings-divisible-by-last-digit/solutions/3068623/gong-shi-tui-dao-dong-tai-gui-hua-python-iw4a/
    public long countSubstrings(String s) {
        long ans = 0;
        int[][] f = new int[10][9]; // 定義 f[i+1][m][rem] 表示以 s_i 結尾的、模 m 余數為 rem 的子串個數
        for (char d : s.toCharArray()) {
            d -= '0';
            for (int m = 1; m < 10; m++) { // 枚舉模數 m
                // 滾動數組計算 f
                int[] nf = new int[m];
                nf[d % m] = 1;
                for (int rem = 0; rem < m; rem++) { // 枚舉模 m 的余數 rem
                    nf[(rem * 10 + d) % m] += f[m][rem]; // 刷表法
                }
                f[m] = nf;
            }
            // 以 s[i] 結尾的，模 s[i] 余數為 0 的子串個數
            ans += f[d][0];
        }
        return ans;
    }


}
