package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MinStartingIndex {

    // https://leetcode.cn/problems/find-the-occurrence-of-first-almost-equal-substring/solutions/2934098/qian-hou-zhui-fen-jie-z-shu-zu-pythonjav-0est/
    public int minStartingIndex(String s, String pattern) {
        int[] preZ = calcZ(pattern + s); // 每個從 s[i] 開始的字符串 s[i..]，計算它能匹配 pattern 多長的前綴
        int[] sufZ = calcZ(rev(pattern) + rev(s)); // 每個以 s[j] 結尾的字符串 s[..j]，計算它能匹配 pattern 多長的後綴
        // 可以不反轉 sufZ，下面寫 sufZ[sufZ.length - i]
        int n = s.length();
        int m = pattern.length();
        for (int i = m; i <= n; i++) {
            if (preZ[i] + sufZ[sufZ.length - i] >= m - 1) {
                return i - m;
            }
        }
        return -1;
    }

    private int[] calcZ(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] z = new int[n];
        int boxL = 0;
        int boxR = 0; // z-box 左右邊界
        for (int i = 1; i < n; i++) {
            if (i <= boxR) {
                z[i] = Math.min(z[i - boxL], boxR - i + 1);
            }
            while (i + z[i] < n && s[z[i]] == s[i + z[i]]) {
                boxL = i;
                boxR = i + z[i];
                z[i]++;
            }
        }
        return z;
    }

    private String rev(String s) {
        return new StringBuilder(s).reverse().toString();
    }

}
