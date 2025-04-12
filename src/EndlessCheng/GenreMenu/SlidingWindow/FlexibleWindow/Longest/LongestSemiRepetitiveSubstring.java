package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class LongestSemiRepetitiveSubstring {

    // https://leetcode.cn/problems/find-the-longest-semi-repetitive-substring/solutions/2304713/shuang-zhi-zhen-hua-chuang-pythonjavacgo-nurf/
    public int longestSemiRepetitiveSubstring(String S) {
        var s = S.toCharArray();
        int ans = 1, left = 0, same = 0, n = s.length;
        for (int right = 1; right < n; right++) { // 移動右指針 right，並統計相鄰相同的情況出現了多少次，記作 same

            // 如果 same>1，則不斷移動左指針 left 直到 s[left]=s[left−1]，
            // 此時將一對相同的字符移到窗口之外。然後將 same 置為 1。
            if (s[right] == s[right - 1] && ++same > 1) {
                for (left++; s[left] != s[left - 1]; left++) ;
                same = 1;
            }
            
            ans = Math.max(ans, right - left + 1); // 統計子串長度 right−left+1 的最大值
        }
        return ans;
    }


}
