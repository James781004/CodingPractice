package EndlessCheng.GenreMenu.Backtracking.Backtracking.Other;

public class KthCharacter {

    // https://leetcode.cn/problems/find-the-k-th-character-in-string-game-ii/solutions/2934284/liang-chong-zuo-fa-di-gui-die-dai-python-5f6z/
    public char kthCharacter(long k, int[] operations) {
        // 從 k-1 的二進制長度減一開始，詳細解釋見下文
        return f(k, operations, 63 - Long.numberOfLeadingZeros(k - 1));
    }

    private char f(long k, int[] operations, int i) {
        if (i < 0) {
            return 'a';
        }
        int op = operations[i];
        if (k <= (1L << i)) { // k 在左半段
            return f(k, operations, i - 1);
        }
        // k 在右半段
        char ans = f(k - (1L << i), operations, i - 1);
        return (char) ('a' + (ans - 'a' + op) % 26);
    }


}
