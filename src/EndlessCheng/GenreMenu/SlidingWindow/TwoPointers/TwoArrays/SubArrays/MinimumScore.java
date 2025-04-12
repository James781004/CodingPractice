package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

public class MinimumScore {

    // https://leetcode.cn/problems/subsequence-with-the-minimum-score/solutions/2107010/qian-hou-zhui-fen-jie-san-zhi-zhen-pytho-6cmr/
    public int minimumScore(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int n = s.length;
        int m = t.length;

        int[] suf = new int[n + 1];
        suf[n] = m;
        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) { // 匹配後綴並把結果記錄進 suf
            if (s[i] == t[j]) {
                j--;
            }
            if (j < 0) { // t 是 s 的子序列
                return 0;
            }
            suf[i] = j + 1;
        }

        int ans = suf[0]; // 刪除 t[:suf[0]]
        j = 0;
        for (int i = 0; i < n; i++) { // 枚舉前綴分割線
            if (s[i] == t[j]) { // 注意上面判斷了 t 是 s 子序列的情況，這裡 j 不會越界
                j++;
                ans = Math.min(ans, suf[i + 1] - j); // 刪除 t[j:suf[i+1]]
            }
        }
        return ans;
    }


}
