package EndlessCheng.Basic.Other;

public class countSubstrings {

    // https://leetcode.cn/problems/count-substrings-that-differ-by-one-character/solutions/2192600/tu-jie-fei-bao-li-onm-suan-fa-pythonjava-k5og/
    public int countSubstrings(String S, String T) {
        char[] s = S.toCharArray(), t = T.toCharArray();
        int ans = 0, n = s.length, m = t.length;

        // 外層循環枚舉 s、t 字符串中被選取的子串的結束下標之差值，即 d = i - j
        // 由於 i ∈ [0, n) , j ∈ [0, m)，故 d ∈ [1 - m, n)
        for (int d = 1 - m; d < n; ++d) { // d=i-j, j=i-d

            // 此處首先根據枚舉的差值 d 確定 i，當 d 為 負值時， i 在下標區間要求下，取 0；
            // 否則，i 應為 d
            int i = Math.max(d, 0);

            // 枚舉 i, 維護 k0 和 k1， 累加 k0 - k1 到答案中，由於一開始 k0 和 k1 不存在， 應初始化成 i - 1
            // 為什麼 k0 和 k1 初始化為 i - 1？ 一、初始化後，未遇到不同字符前，累加的是 0； 二、遇到不同字符後，更新 k0 和 k1，累加的 k1 - k0，對應於 [可以作為滿足條件的子串的起點的個數]。
            // 這種累加法，等同於 以不同字符作為中心點，計算滿足條件的子串的 起點個數*終點個數。
            for (int k0 = i - 1, k1 = k0; i < n && i - d < m; ++i) { // 枚舉所考察子串的終點 i ，根據差值計算另一子串的終點  j = i - d
                if (s[i] != t[i - d]) { // 檢查終點處的字符，不同時更新
                    k0 = k1; // 上上一個不同
                    k1 = i;  // 上一個不同
                }
                ans += k1 - k0; // 對於每個確定的終點，累加符合條件的起點個數——即，符合條件的子串個數
            }
        }
        return ans;
    }


}
