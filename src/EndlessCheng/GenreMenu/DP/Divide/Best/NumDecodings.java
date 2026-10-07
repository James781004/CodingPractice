package EndlessCheng.GenreMenu.DP.Divide.Best;

public class NumDecodings {

    // https://leetcode.cn/problems/decode-ways/solutions/1052885/jie-ma-fang-fa-tu-jie-dp-zui-qing-xi-yi-97hng/
    public int numDecodings(String s) {
        int n = s.length();
        int[] f = new int[n + 1]; // 表示前i個數字一共有多少種解碼方式
        f[0] = 1;

        // 臨界判斷條件
        if (s.isEmpty()) {
            return 0;
        }


        for (int i = 1; i <= n; i++) {
            if (s.charAt(i - 1) != '0') f[i] = f[i - 1]; // 單獨解碼s[i - 1]
            if (i >= 2) {
                int t = (s.charAt(i - 2) - '0') * 10 + s.charAt(i - 1) - '0';
                if (t > 10 && t <= 26) f[i] = f[i - 1] + f[i - 2]; // 將s[i - 2] 和 s[i - 1]組合解碼

                // 10 和 20 因為末尾是0，較為特殊，單獨判斷
                if (t == 10 || t == 20) f[i] = f[i - 2];

            }
        }
        return f[n];
    }


}
