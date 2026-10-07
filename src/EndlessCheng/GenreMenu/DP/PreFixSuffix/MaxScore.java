package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MaxScore {

    // https://leetcode.cn/problems/maximum-score-after-splitting-a-string/solutions/3007878/mei-ju-fen-ge-xian-on-qian-hou-zhui-fen-a3pio/
    public int maxScore(String S) {
        char[] s = S.toCharArray();
        int right1 = 0;
        for (char c : s) {
            right1 += c - '0';
        }

        int ans = 0;
        int left0 = 0;
        for (int i = 0; i < s.length - 1; i++) { // 移動分割線
            if (s[i] == '0') {
                left0++;
            } else {
                right1--;
            }
            ans = Math.max(ans, left0 + right1);
        }
        return ans;
    }

}
