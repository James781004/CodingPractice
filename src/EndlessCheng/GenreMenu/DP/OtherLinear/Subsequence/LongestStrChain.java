package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestStrChain {

    // https://leetcode.cn/problems/longest-string-chain/solutions/2247269/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-wdkm/
    private Map<String, Integer> ws = new HashMap<>();

    public int longestStrChain(String[] words) {
        for (String s : words) {
            ws.put(s, 0); // 0 表示未被計算
        }
        int ans = 0;
        for (String s : ws.keySet()) {
            ans = Math.max(ans, dfs(s));
        }
        return ans;
    }

    private int dfs(String s) {
        int res = ws.get(s);
        if (res > 0) {
            return res; // 之前計算過
        }
        for (int i = 0; i < s.length(); i++) { // 枚舉去掉 s[i]
            var t = s.substring(0, i) + s.substring(i + 1);
            if (ws.containsKey(t)) { // t 在 words 中
                res = Math.max(res, dfs(t));
            }
        }
        ws.put(s, res + 1); // 記憶化
        return res + 1;
    }


    public int longestStrChainDP(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int ans = 0;
        Map<String, Integer> f = new HashMap<>();
        for (String s : words) {
            int res = 0;
            for (int i = 0; i < s.length(); i++) { // 枚舉去掉 s[i]
                String t = s.substring(0, i) + s.substring(i + 1);
                res = Math.max(res, f.getOrDefault(t, 0));
            }
            f.put(s, res + 1);
            ans = Math.max(ans, res + 1);
        }
        return ans;
    }

   
}
