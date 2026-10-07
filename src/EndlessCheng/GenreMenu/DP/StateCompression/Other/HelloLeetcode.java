package EndlessCheng.GenreMenu.DP.StateCompression.Other;

import java.util.*;

public class HelloLeetcode {

    // https://leetcode.cn/problems/rMeRt2/solutions/1876097/z-by-endlesscheng-6ver/
    Map<Character, int[]> hmap;
    int[][] dp;
    int len;
    List<Map<Integer, Integer>> costs;

    public int Leetcode(String[] words) {

        len = words.length;
        hmap = new HashMap<>();   // 字符-位置，數量，位掩碼
        hmap.put('c', new int[]{10, 1, 1});
        hmap.put('d', new int[]{9, 1, 1});
        hmap.put('e', new int[]{0, 4, 7});
        hmap.put('h', new int[]{8, 1, 1});
        hmap.put('l', new int[]{3, 3, 3});
        hmap.put('o', new int[]{5, 2, 3});
        hmap.put('t', new int[]{7, 1, 1});

        // 求每個單詞的mask和cost
        costs = new ArrayList<>();
        for (String word : words) {
            Map<Integer, Integer> hp = new HashMap<>();
            dfs(hp, word, 0, 0);
            costs.add(hp);
        }

        dp = new int[len][1 << 11];
        for (int i = 0; i < len; i++) Arrays.fill(dp[i], -1);
        int ans = dfs2(0, 0);
        return ans == 0x3f3f3f3f ? -1 : ans;
        // return 0;
    }

    void dfs(Map<Integer, Integer> hp, String str, int mask, int tot) {
        if (!hp.containsKey(mask) || hp.get(mask) > tot) hp.put(mask, tot);
        if ("".equals(str)) return;
        int n = str.length();
        for (int i = 0; i < n; i++) {
            if (!hmap.containsKey(str.charAt(i))) continue;
            int[] pnm = hmap.get(str.charAt(i));
            int cnt = (mask >> pnm[0]) & pnm[2];
            if (cnt < pnm[1]) { // 可以添加字母
                dfs(hp, str.substring(0, i) + str.substring(i + 1, n), mask + (1 << pnm[0]), tot + i * (n - i - 1));
            }
        }
    }

    int merge(int mask, int add) {
        for (int[] pnm : hmap.values()) {
            int c1 = (mask >> pnm[0]) & pnm[2];
            int c2 = (add >> pnm[0]) & pnm[2];
            if (c1 + c2 > pnm[1]) return -1;
            mask += c2 << pnm[0];
        }
        return mask;
    }

    // 記憶化搜索 dp[wi][mask]表示 從第wi個字符串開始，當前狀態為mask時還需要付出的最小代價
    int dfs2(int wi, int mask) {
        if (wi == len) return mask == 2012 ? 0 : 0x3f3f3f3f;
        if (dp[wi][mask] != -1) return dp[wi][mask];
        int res = 0x3f3f3f3f;
        Map<Integer, Integer> hp = costs.get(wi);
        for (Map.Entry<Integer, Integer> e : hp.entrySet()) {
            int m = merge(mask, e.getKey());
            if (m >= 0) {
                res = Math.min(res, dfs2(wi + 1, m) + e.getValue());
            }
        }
        dp[wi][mask] = res;
        return res;
    }

}
