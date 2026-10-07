package EndlessCheng.GenreMenu.DP.OtherLinear.SubArrays;

import java.util.Arrays;

public class AppealSum {

    // https://leetcode.cn/problems/total-appeal-of-a-string/solutions/1461618/by-endlesscheng-g405/
    public long appealSum(String s) {
        int[] last = new int[26]; // 設 s[i] 上一次出現的下標為 last[s[i]]。如果不存在則為 −1
        Arrays.fill(last, -1);
        int n = s.length();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            // 子串的左端點可以從 last[s[i]]+1 到 i，子串的右端點可以從 i 到 n−1
            // 所以 s[i] 的貢獻是 (i−last[s[i]])⋅(n−i)
            ans += (long) (i - last[c]) * (n - i);
            last[c] = i;
        }
        return ans;
    }


    public long appealSum2(String s) {
        int[] last = new int[26];
        Arrays.fill(last, -1); // 初始化成 -1 可以讓提示 2-2 中的兩種情況合並成一個公式
        long ans = 0;
        int sumG = 0;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            sumG += i - last[c];
            ans += sumG;
            last[c] = i;
        }
        return ans;
    }


}
