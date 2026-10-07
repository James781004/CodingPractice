package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class LongestCommonPrefix {

    // https://leetcode.cn/problems/longest-common-prefix-between-adjacent-strings-after-removals/solutions/3710963/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-8sn2/
    public int[] longestCommonPrefix(String[] words) {
        int n = words.length;
        int[] ans = new int[n];
        if (n == 1) { // 不存在相鄰對
            return ans;
        }

        // 後綴 [i,n-1] 中的相鄰 LCP 長度的最大值
        int[] sufMax = new int[n];
        for (int i = n - 2; i > 0; i--) {
            sufMax[i] = Math.max(sufMax[i + 1], lcp(words[i], words[i + 1]));
        }

        ans[0] = sufMax[1];
        int preMax = 0; // 前綴 [0,i-1] 中的相鄰 LCP 長度的最大值
        for (int i = 1; i < n - 1; i++) {
            ans[i] = Math.max(Math.max(preMax, lcp(words[i - 1], words[i + 1])), sufMax[i + 1]);
            preMax = Math.max(preMax, lcp(words[i - 1], words[i])); // 為下一輪循環做准備
        }
        ans[n - 1] = preMax;
        return ans;
    }

    private int lcp(String s, String t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                return i;
            }
        }
        return n;
    }

}
