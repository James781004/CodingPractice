package EndlessCheng.GenreMenu.DP.OtherLinear.SubArrays;

public class FindSubstringInWraproundString {

    // https://leetcode.cn/problems/unique-substrings-in-wraparound-string/solutions/3025674/fen-zu-xun-huan-zhi-ling-shen-gong-shi-b-tbcp/
    public int findSubstringInWraproundString(String S) {
        int[] cnt = new int[26];//選出當前字符結尾的最長子串
        char[] s = S.toCharArray();
        int i = 0, n = s.length, ans = 0;
        while (i < n) {
            int k = 1;
            cnt[s[i] - 'a'] = Math.max(cnt[s[i] - 'a'], k);
            i++;
            while (i < n && (s[i] - s[i - 1] == 1 || s[i] - s[i - 1] == -25)) {
                k++;
                cnt[s[i] - 'a'] = Math.max(cnt[s[i] - 'a'], k);
                i++;
            }
        }
        for (int t : cnt) {
            ans += t;
        }
        return ans;
    }


}
