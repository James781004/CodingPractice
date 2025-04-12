package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

public class ValidSequence {

    // https://leetcode.cn/problems/find-the-lexicographically-smallest-valid-sequence/solutions/2934051/qian-hou-zhui-fen-jie-zi-xu-lie-pi-pei-t-le8d/
    public int[] validSequence(String word1, String word2) {
        char[] s = word1.toCharArray();
        char[] t = word2.toCharArray();
        int n = s.length;
        int m = t.length;

        int[] suf = new int[n + 1];
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
                // 貪心策略: 如果 s[i] != t[j] 且 suf[i+1]≤j+1，
                // 說明修改 s[i] 為 t[j] 後，t[j+1:] 是 s[i+1:] 的子序列。
                // 此時一定要修改，如果不修改，那麼答案的第 j 個下標比 i 大，不是字典序最小的下標序列。
                if (s[i] != t[j]) {
                    changed = true;
                }

                // 修改後，繼續向後匹配，在 s[i]=t[j] 時把 i 加入答案
                ans[j++] = i;

                // 如果發現 j 等於 t 的長度，說明匹配完成，立刻返回答案
                if (j == m) {
                    return ans;
                }
            }
        }

        // 如果循環中沒有返回，那麼循環結束後返回空數組
        return new int[]{};
    }


}
