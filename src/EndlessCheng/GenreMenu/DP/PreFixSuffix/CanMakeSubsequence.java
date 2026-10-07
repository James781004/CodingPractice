package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class CanMakeSubsequence {

    // https://leetcode.cn/problems/subsequence-after-one-replacement/solutions/3991828/san-zhi-zhen-pythonjavacgo-by-endlessche-2qdj/
    public boolean canMakeSubsequence(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int n = s.length;
        int m = t.length;

        // s[i,n-1] 是 t[suf[i]m-1] 的子序列（如果 suf[i]=-1 則不是子序列）
        int[] suf = new int[n + 1];
        suf[n] = m;
        int j = m;
        for (int i = n - 1; i >= 0; i--) {
            // 上一輪循環 s[i+1] 匹配了 t[j]，減一後繼續匹配 s[i]
            j--;
            while (j >= 0 && t[j] != s[i]) {
                j--;
            }
            suf[i] = j;
        }

        if (suf[0] >= 0) {
            // s 已是 t 的子序列
            return true;
        }

        int pre = -1;
        for (int i = 0; i < n; i++) {
            // 此時 s[0,i-1] 是 t[0,pre] 的子序列（如果 pre=m 則不是子序列）
            // 修改 s[i]，那麼在 pre 和 suf[i+1] 之間，至少要有一個字母
            if (suf[i + 1] - pre > 1) {
                return true;
            }

            // 上一輪循環 s[i-1] 匹配了 t[pre]，加一後繼續匹配 s[i]
            pre++;
            while (pre < m && t[pre] != s[i]) {
                pre++;
            }
        }
        return false;
    }


}
