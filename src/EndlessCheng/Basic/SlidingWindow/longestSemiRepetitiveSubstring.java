package EndlessCheng.Basic.SlidingWindow;

public class longestSemiRepetitiveSubstring {
    // https://leetcode.cn/problems/find-the-longest-semi-repetitive-substring/solutions/2304713/shuang-zhi-zhen-hua-chuang-pythonjavacgo-nurf/
    public int longestSemiRepetitiveSubstring(String S) {
        char[] s = S.toCharArray();
        int ans = 1, left = 0, same = 0, n = s.length;
        for (int right = 1; right < n; right++) {
            if (s[right] == s[right - 1] && ++same > 1) {
                for (left++; s[left] != s[left - 1]; left++) ;
                same = 1;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
