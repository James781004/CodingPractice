package EndlessCheng.GenreMenu.DP.OtherLinear.SubArrays;

import java.util.Arrays;

public class UniqueLetterString {

    // https://leetcode.cn/problems/count-unique-characters-of-all-substrings-of-a-given-string/solutions/1804144/by-endlesscheng-ko4z/
    public int uniqueLetterString(String s) {
        int ans = 0, total = 0;
        int[] last0 = new int[26], last1 = new int[26];
        Arrays.fill(last0, -1);
        Arrays.fill(last1, -1);
        for (var i = 0; i < s.length(); i++) {
            var c = s.charAt(i) - 'A';
            total += i - 2 * last0[c] + last1[c];
            ans += total;
            last1[c] = last0[c];
            last0[c] = i;
        }
        return ans;
    }


}
