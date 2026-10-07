package EndlessCheng.GenreMenu.DP.Divide.Best;

public class MaxPalindromes {

    // https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/solutions/1965456/zhong-xin-kuo-zhan-dppythonjavacgo-by-en-1yt1/
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        int[] f = new int[n + 1]; // 定義 f[i+1] 表示 s[0] 到 s[i] 中的不重疊回文子字符串的最大數目
        for (int i = 0; i < 2 * n - 1; i++) {
            // 更加優雅的方式枚舉所有奇數和偶數的中心點位置
            int l = i / 2, r = l + i % 2;
            // s[l]不包含在回文串中，那麼 f[i+1]=f[i]
            f[l + 1] = Math.max(f[l + 1], f[l]);
            for (; l >= 0 && r < n && s.charAt(l) == s.charAt(r); --l, ++r) {
                if (r - l + 1 >= k) { // 貪心處理，f[l]是非遞減的，更小的f[l]也不會影響答案
                    // s[r]包含在回文串中，並且回文長度大於等於k
                    f[r + 1] = Math.max(f[r + 1], f[l] + 1);
                }
            }
        }
        return f[n];
    }


}
