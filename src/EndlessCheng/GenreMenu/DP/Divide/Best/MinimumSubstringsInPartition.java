package EndlessCheng.GenreMenu.DP.Divide.Best;

import java.util.Arrays;

public class MinimumSubstringsInPartition {

    // https://leetcode.cn/problems/minimum-substring-partition-of-equal-character-frequency/solutions/2775377/hua-fen-xing-dpji-yi-hua-sou-suo-di-tui-s1nq0/
    public int minimumSubstringsInPartition(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] memo = new int[n];
        return dfs(n - 1, s, memo);
    }

    // 當剩余字符串是 s[0] 到 s[i] 時，最少能分割出多少個平衡子串
    private int dfs(int i, char[] s, int[] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i] > 0) { // 之前計算過
            return memo[i];
        }
        int res = Integer.MAX_VALUE;
        int[] cnt = new int[26];
        int k = 0, maxCnt = 0;

        // 枚舉最後一段從 s[j] 到 s[i]
        for (int j = i; j >= 0; j--) {
            // 倒序枚舉 j 的同時，用一個哈希表（或者數組）統計每個字符的出現次數。
            // 如果子串中每個字母的出現次數都相等，那麼子串是平衡的。
            // 設子串中有 k 種字母，字母出現次數的最大值為 maxCnt。
            // 子串是平衡的，當且僅當子串長度 i−j+1 等於 k⋅maxCnt
            k += cnt[s[j] - 'a']++ == 0 ? 1 : 0;
            maxCnt = Math.max(maxCnt, cnt[s[j] - 'a']);

            // 如果這個子串是平衡的，
            // 那麼接下來要解決的問題是：當剩余字符串是 s[0] 到 s[j−1] 時，最少能分割出多少個平衡子串
            // 即 dfs(j−1)
            if (i - j + 1 == k * maxCnt) {
                res = Math.min(res, dfs(j - 1, s, memo) + 1);
            }
        }
        memo[i] = res; // 記憶化
        return res;
    }


    public int minimumSubstringsInPartitionDP(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] f = new int[n + 1];
        Arrays.fill(f, Integer.MAX_VALUE);
        f[0] = 0;
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            Arrays.fill(cnt, 0);
            int k = 0;
            int maxCnt = 0;
            for (int j = i; j >= 0; j--) {
                k += cnt[s[j] - 'a']++ == 0 ? 1 : 0;
                maxCnt = Math.max(maxCnt, cnt[s[j] - 'a']);
                if (i - j + 1 == k * maxCnt) {
                    f[i + 1] = Math.min(f[i + 1], f[j] + 1);
                }
            }
        }
        return f[n];
    }


}
