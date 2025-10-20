package EndlessCheng.GenreMenu.DP.Knapsack.Multiple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PossibleStringCount {

    // https://leetcode.cn/problems/find-the-original-typed-string-ii/solutions/2966856/zheng-nan-ze-fan-qian-zhui-he-you-hua-dp-5mi9/
    public int possibleStringCount(String word, int k) {
        int n = word.length();
        if (n < k) { // 無法滿足要求
            return 0;
        }

        final int MOD = 1_000_000_007;
        List<Integer> cnts = new ArrayList<>();
        long ans = 1;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt++;
            if (i == n - 1 || word.charAt(i) != word.charAt(i + 1)) {
                // 如果 cnt = 1，這組字符串必選，無需參與計算
                if (cnt > 1) {
                    if (k > 0) {
                        cnts.add(cnt - 1);
                    }
                    ans = ans * cnt % MOD;
                }
                k--; // 注意這裡把 k 減小了
                cnt = 0;
            }
        }

        if (k <= 0) {
            return (int) ans;
        }

        int m = cnts.size();
        int[][] f = new int[m + 1][k];
        Arrays.fill(f[0], 1);
        int[] s = new int[k + 1];
        for (int i = 0; i < m; i++) {
            // 計算 f[i] 的前綴和數組 s
            for (int j = 0; j < k; j++) {
                s[j + 1] = (s[j] + f[i][j]) % MOD;
            }
            int c = cnts.get(i);
            // 計算子數組和
            for (int j = 0; j < k; j++) {
                f[i + 1][j] = (s[j + 1] - s[Math.max(j - c, 0)]) % MOD;
            }
        }

        return (int) ((ans - f[m][k - 1] + MOD) % MOD); // 保證結果非負
    }


    public int possibleStringCount2(String word, int k) {
        int n = word.length();
        if (n < k) { // 無法滿足要求
            return 0;
        }

        final int MOD = 1_000_000_007;
        List<Integer> cnts = new ArrayList<>();
        long ans = 1;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt++;
            if (i == n - 1 || word.charAt(i) != word.charAt(i + 1)) {
                // 如果 cnt = 1，這組字符串必選，無需參與計算
                if (cnt > 1) {
                    if (k > 0) { // 保證空間復雜度為 O(k)
                        cnts.add(cnt - 1);
                    }
                    ans = ans * cnt % MOD;
                }
                k--; // 注意這裡把 k 減小了
                cnt = 0;
            }
        }

        if (k <= 0) {
            return (int) ans;
        }

        int[] f = new int[k];
        Arrays.fill(f, 1);
        for (int c : cnts) {
            // 原地計算 f 的前綴和
            for (int j = 1; j < k; j++) {
                f[j] = (f[j] + f[j - 1]) % MOD;
            }
            // 計算子數組和
            for (int j = k - 1; j > c; j--) {
                f[j] = (f[j] - f[j - c - 1]) % MOD;
            }
        }

        return (int) ((ans - f[k - 1] + MOD) % MOD); // 保證結果非負
    }


}
