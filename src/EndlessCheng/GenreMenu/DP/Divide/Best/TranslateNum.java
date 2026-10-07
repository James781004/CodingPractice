package EndlessCheng.GenreMenu.DP.Divide.Best;

public class TranslateNum {

    // https://leetcode.cn/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof/solutions/1034242/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-tu-ji-z45c/
    public int translateNum(int num) {
        String s = String.valueOf(num); // 將數字轉為字符串
        int n = s.length();
        int[] f = new int[n + 1]; // 前i個數字一共有多少種不同的翻譯方法
        f[0] = 1;  // 初始化，翻譯前0個數的方法數為1。
        for (int i = 1; i <= n; i++) {
            // 單獨翻譯s[i]。由於求的是方案數，
            // 如果確定了第i個數字的翻譯方式，
            // 那麼翻譯前i個數字和翻譯前i - 1個數的方法數就是相同的，
            // 即f[i] = f[i - 1]。
            f[i] = f[i - 1];

            // 將s[i]和s[i - 1]組合起來翻譯(組合的數字范圍在10 ~ 25之間)。
            // 如果確定了第i個數和第i - 1個數的翻譯方式，
            // 那麼翻譯前i個數字和翻譯前i - 2個數的翻譯方法數就是相同的，
            // 即f[i] = f[i - 2]。
            if (i > 1) {
                int t = (s.charAt(i - 2) - '0') * 10 + s.charAt(i - 1) - '0';
                if (t >= 10 && t <= 25) // 組合的數字范圍在10 ~ 25之間
                    f[i] += f[i - 2];  // 將s[i] 和 s[i - 1]組合翻譯
            }
        }
        return f[n];
    }


}
