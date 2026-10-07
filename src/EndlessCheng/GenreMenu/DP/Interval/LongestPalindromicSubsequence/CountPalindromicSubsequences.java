package EndlessCheng.GenreMenu.DP.Interval.LongestPalindromicSubsequence;

public class CountPalindromicSubsequences {

    // https://leetcode.cn/problems/count-different-palindromic-subsequences/solutions/1592067/tong-ji-butong-by-jiang-hui-4-q5xf/
    public int countPalindromicSubsequences(String s) {
        /*
        1 <= s.length <= 1000
        s[i] 僅包含 'a', 'b', 'c' 或 'd'
        由給出的范圍可知，題目要求時間復雜度應該在O(N^2)附近，且s[i]只有4種可能
        區間DP:
        1.狀態定義:f[i][j]為s[i,j]之間不同回文序列數目(對MOD取余) i>=j
        2.狀態轉移:要求f[i][j]就要考慮邊界s[i]與s[j]的情況，因為回文可以通過兩邊加上相同字母構成重復子問題
            2.1 當s[i]==s[j]時，分為以下3種情況
                2.1.0 這時候其實是初始化的情況 f[i][j]=1
                2.1.1 當s[i+1,j-1]之間沒有字母(0個)等於s[i]的，說明中間的回文序列可以左右各自加上s[i]與s[j]構成新的回文串
                而多出來的兩個為a與aa這種情況 即：f[i][j]=2*f[i+1][j-1]+2
                2.1.2 當s[i+1,j-1]之間有1個字母等於s[i]的，與2.1.1不同的是中間已經統計過'a'這種情況，只能統計'aa'
                多出來的一個為aa這種情況 即：f[i][j]=2*f[i+1][j-1]+1
                2.1.3 當s[i+1,j-1]之間有>=2個字母等於s[i]的，這時要找到s[i+1,j-1]之間的首個重復的s[l]==s[i]與s[r]==s[i]
                並在2*f[i+1][j-1]基礎上減去中間s[l+1,r-1]重復的回文序列 即：f[i][j]=2*f[i+1][j-1]-f[l+1][r-1]
                其中的a與aa在中間區間中首個2.1.1情況已經統計過了哦，不用重復統計
            2.2 當s[i]!=s[j]時，只能s[i]與s[j]不能同時對回文子序列組成起到作用
                2.2.1 s[i][j]整體的貢獻=(有s[i]貢獻起到作用+s[i+1,j-1]單獨起作用)+(有s[j]貢獻起到作用+s[i+1,j-1]單獨起作用)-s[i+1,j-1]單獨起作用
                即 f[i][j]=f[i+1][j]+f[i][j-1]-f[i+1][j-1]
        3.初始化:f[i][i]=1表示長度為1只能有1個回文串
        4.遍歷順序:基於長度len的遍歷，len∈[2,n]，左邊界i∈[0,j到n]
        5.返回形式:返回f[0][n-1]即為答案
        注意:可能越界的地方要注意進行取模
         */
        int MOD = (int) 1e9 + 7;
        int n = s.length();
        int[][] f = new int[n][n];
        // 初始化
        for (int i = 0; i < n; i++) {
            f[i][i] = 1;
        }
        // 枚舉長度len∈[2,n]
        for (int len = 2; len <= n; len++) {
            // 枚舉左邊界i∈[0,j到n-1]
            for (int i = 0; i + len - 1 < n; i++) {
                // j為右邊界
                int j = i + len - 1;
                char a = s.charAt(i), b = s.charAt(j);
                // s[i]==s[j]
                if (a == b) {
                    // 左右指針用於判斷有沒有s[i+1,j-1]是否有等於s[i]的情況
                    int l = i + 1, r = j - 1;
                    while (l <= r && s.charAt(l) != a) l++;
                    while (l <= r && s.charAt(r) != a) r--;
                    if (l > r) {
                        // 1.中間沒有等於s[i]的
                        f[i][j] = 2 * f[i + 1][j - 1] + 2;
                    } else if (l == r) {
                        // 2.中間有1個等於s[i]的
                        f[i][j] = 2 * f[i + 1][j - 1] + 1;
                    } else {
                        // 3.中間有2個或以上等於s[i]的
                        f[i][j] = 2 * f[i + 1][j - 1] - f[l + 1][r - 1];
                    }
                } else {
                    // s[i]!=s[j]
                    f[i][j] = f[i + 1][j] + f[i][j - 1] - f[i + 1][j - 1];
                }
                // 統一處理f[i][j]出現負數情況
                f[i][j] = f[i][j] >= 0 ? f[i][j] % MOD : f[i][j] + MOD;
            }
        }
        return f[0][n - 1];
    }

}
