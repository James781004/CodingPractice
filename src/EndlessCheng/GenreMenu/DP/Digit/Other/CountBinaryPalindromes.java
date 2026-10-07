package EndlessCheng.GenreMenu.DP.Digit.Other;

public class CountBinaryPalindromes {

    // https://leetcode.cn/problems/count-binary-palindromic-numbers/solutions/3774534/olog-n-zuo-fa-pythonjavacgo-by-endlessch-1ggp/
    public int countBinaryPalindromes(long n) {
        if (n == 0) {
            return 1;
        }

        // n 的二進制長度
        int m = 64 - Long.numberOfLeadingZeros(n);

        // 對於二進制長度小於 m 的回文數，左半邊隨便填
        int ans = 1; // 0 也是回文數
        // 枚舉二進制長度，最高位填 1，回文數左半的其余位置隨便填
        for (int i = 1; i < m; i++) {
            ans += 1 << ((i - 1) / 2);
        }

        // 最高位一定是 1，從次高位開始填
        for (int i = m - 2; i >= m / 2; i--) {
            if ((n >> i & 1) > 0) {
                // 這一位可以填 0，那麼回文數左半的剩余位置可以隨便填
                ans += 1 << (i - m / 2);
            }
            // 在後續循環中，這一位填 1
        }

        long pal = n >> (m / 2);
        // 左半反轉到右半
        // 如果 m 是奇數，那麼去掉回文中心再反轉
        for (long v = pal >> (m % 2); v > 0; v /= 2) {
            pal = pal * 2 + v % 2;
        }
        if (pal <= n) {
            ans++;
        }

        return ans;
    }

}
