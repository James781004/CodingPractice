package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Shortest;

public class MinWindow {

    // https://leetcode.cn/problems/minimum-window-substring/solutions/2713911/liang-chong-fang-fa-cong-o52mn-dao-omnfu-3ezz/
    public String minWindow(String S, String t) {
        char[] s = S.toCharArray();
        int m = s.length;
        int ansLeft = -1;
        int ansRight = m;
        int[] cnt = new int[128];
        int less = 0;
        for (char c : t.toCharArray()) {
            if (cnt[c] == 0) {
                less++; // 有 less 種字母的出現次數 < t 中的字母出現次數
            }
            cnt[c]++;
        }

        int left = 0;
        for (int right = 0; right < m; right++) { // 移動子串右端點
            char c = s[right]; // 右端點字母
            cnt[c]--; // 右端點字母移入子串
            if (cnt[c] == 0) {
                // 原來窗口內 c 的出現次數比 t 的少，現在一樣多
                less--;
            }
            while (less == 0) { // 涵蓋：所有字母的出現次數都是 >=
                if (right - left < ansRight - ansLeft) { // 找到更短的子串
                    ansLeft = left; // 記錄此時的左右端點
                    ansRight = right;
                }
                char x = s[left]; // 左端點字母
                if (cnt[x] == 0) {
                    // x 移出窗口之前，檢查出現次數，
                    // 如果窗口內 x 的出現次數和 t 一樣，
                    // 那麼 x 移出窗口後，窗口內 x 的出現次數比 t 的少
                    less++;
                }
                cnt[x]++; // 左端點字母移出子串
                left++;
            }
        }
        return ansLeft < 0 ? "" : S.substring(ansLeft, ansRight + 1);
    }


}
