package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class ScoreBalance {

    // https://leetcode.cn/problems/equal-score-substrings/solutions/3803681/liang-ci-bian-li-pythonjavacgo-by-endles-60gb/
    public boolean scoreBalance(String S) {
        char[] s = S.toCharArray();
        int total = 0;
        for (char b : s) {
            total += b & 31;
        }

        int left = 0;
        for (char b : s) { // 字母位置是正數，可以遍歷到 s 末尾（末尾一定不滿足要求）
            left += b & 31;
            if (left * 2 == total) {
                return true;
            }
        }
        return false;
    }

}
