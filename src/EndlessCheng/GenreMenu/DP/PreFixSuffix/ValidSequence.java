package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class ValidSequence {

    // https://leetcode.cn/problems/find-the-lexicographically-smallest-valid-sequence/solutions/2934051/qian-hou-zhui-fen-jie-zi-xu-lie-pi-pei-t-le8d/
    public int[] validSequence(String word1, String word2) {
        char[] s = word1.toCharArray();
        char[] t = word2.toCharArray();
        int n = s.length;
        int m = t.length;

        int[] suf = new int[n + 1]; // 定義 suf[i] 為 s[i:] 對應的 t 的最長後綴的開始下標 j，即 t[j:] 是 s[i:] 的子序列
        suf[n] = m;
        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && s[i] == t[j]) {
                j--;
            }
            suf[i] = j + 1;
        }

        int[] ans = new int[m];
        boolean changed = false; // 是否修改過
        j = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] == t[j] || !changed && suf[i + 1] <= j + 1) {
                // 如果 s[i] !=t [j] 且 suf[i+1]≤j+1，說明修改 s[i] 為 t[j] 後，t[j+1:] 是 s[i+1:] 的子序列。
                // 此時一定要修改，如果不修改，那麼答案的第 j 個下標比 i 大，不是字典序最小的下標序列。
                if (s[i] != t[j]) {
                    changed = true;
                }

                // 如果 s[i]=t[j]，既然能匹配上，那麼就立刻匹配，直接把 i 加入答案。
                // 如果不匹配，可能後面就沒機會找到子序列了，或者答案的第 j 個下標比 i 大，不是字典序最小的下標序列。
                ans[j++] = i;
                if (j == m) {
                    return ans;
                }
            }
        }
        return new int[]{};
    }

}
