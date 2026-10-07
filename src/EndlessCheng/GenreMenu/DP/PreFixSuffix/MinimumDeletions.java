package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MinimumDeletions {

    // https://leetcode.cn/problems/minimum-deletions-to-make-string-balanced/solutions/2149746/qian-hou-zhui-fen-jie-yi-zhang-tu-miao-d-dor2/
    public int minimumDeletions(String S) {
        char[] s = S.toCharArray();
        int del = 0;
        for (char c : s) {
            del += 'b' - c; // 統計 'a' 的個數
        }

        int ans = del;
        for (char c : s) {
            // 把 if-else 寫成 (c - 'a') * 2 - 1 會快很多
            // 'a' -> -1    'b' -> 1
            del += (c - 'a') * 2 - 1;
            ans = Math.min(ans, del);
        }
        return ans;
    }

}
