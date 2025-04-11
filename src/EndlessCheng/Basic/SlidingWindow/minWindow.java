package EndlessCheng.Basic.SlidingWindow;

public class minWindow {
    // https://leetcode.cn/problems/minimum-window-substring/solutions/2713911/liang-chong-fang-fa-cong-o52mn-dao-omnfu-3ezz/
    public String minWindow(String S, String t) {
        char[] s = S.toCharArray();
        int m = s.length;
        int ansLeft = -1;
        int ansRight = m;
        int left = 0;
        int less = 0;
        int[] cntS = new int[128]; // s 子串字母的出現次數
        int[] cntT = new int[128]; // t 中字母的出現次數
        for (char c : t.toCharArray()) {
            if (cntT[c]++ == 0) {
                less++; // 有 less 種字母的出現次數 < t 中的字母出現次數
            }
        }
        for (int right = 0; right < m; right++) { // 移動子串右端點
            char c = s[right]; // 右端點字母（移入子串）
            if (++cntS[c] == cntT[c]) {
                less--; // c 的出現次數從 < 變成 >=
            }
            while (less == 0) { // 涵蓋：所有字母的出現次數都是 >=
                if (right - left < ansRight - ansLeft) { // 找到更短的子串
                    ansLeft = left; // 記錄此時的左右端點
                    ansRight = right;
                }
                char x = s[left++]; // 左端點字母（移出子串）
                if (cntS[x]-- == cntT[x]) {
                    less++; // x 的出現次數從 >= 變成 <
                }
            }
        }
        return ansLeft < 0 ? "" : S.substring(ansLeft, ansRight + 1);
    }

}
