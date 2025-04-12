package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Shortest;

public class ShortestBeautifulSubstring {

    // https://leetcode.cn/problems/shortest-and-lexicographically-smallest-beautiful-string/solutions/2483144/mei-ju-pythonjavacgo-by-endlesscheng-5th8/
    public String shortestBeautifulSubstring(String S, int k) {
        if (S.replace("0", "").length() < k) {
            return "";
        }
        char[] s = S.toCharArray();
        String ans = S;
        int cnt1 = 0, left = 0;
        for (int right = 0; right < s.length; right++) {
            cnt1 += s[right] - '0';
            while (cnt1 > k || s[left] == '0') {
                cnt1 -= s[left++] - '0';
            }
            if (cnt1 == k) {
                String t = S.substring(left, right + 1);
                if (t.length() < ans.length() || t.length() == ans.length() && t.compareTo(ans) < 0) {
                    ans = t;
                }
            }
        }
        return ans;
    }


}
