package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.HashSet;
import java.util.List;

public class LongestValidSubstring {

    // https://leetcode.cn/problems/length-of-the-longest-valid-substring/solutions/2345796/ha-xi-biao-shuang-zhi-zhen-pythonjavacgo-bcez/
    public int longestValidSubstring(String word, List<String> forbidden) {
        var fb = new HashSet<String>();
        fb.addAll(forbidden);
        int ans = 0, left = 0, n = word.length();
        for (int right = 0; right < n; right++) { // 初始化子串左端點 left=0，枚舉子串右端點 right
            for (int i = right; i >= left && i > right - 10; i--) {
                if (fb.contains(word.substring(i, right + 1))) {
                    left = i + 1; // 當子串右端點 >= right 時，合法子串一定不能包含 word[i]
                    break;
                }
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


}
