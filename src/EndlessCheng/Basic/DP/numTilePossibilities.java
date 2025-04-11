package EndlessCheng.Basic.DP;

import java.util.Arrays;
import java.util.HashMap;

public class numTilePossibilities {

    // https://leetcode.cn/problems/letter-tile-possibilities/solutions/2275356/on2-ji-shu-dppythonjavacgo-by-endlessche-hmez/
    int res = 0;
    int[] used;

    public int numTilePossibilities(String tiles) {
        used = new int[tiles.length()];
        char[] c = tiles.toCharArray();
        Arrays.sort(c);// 使用used去重一定记得先排序
        dfs(c);
        return res;
    }

    // 求排列数，在每个结点都可以收集结果
    void dfs(char[] c) {
        for (int i = 0; i < c.length; i++) {
            // 树层去重，前一位没被选上且后一位与前一位相同
            if (i > 0 && used[i - 1] == 0 && c[i] == c[i - 1]) continue;
            // 自己不能被选两次
            if (used[i] == 1) continue;
            res++;
            used[i] = 1;
            dfs(c);
            used[i] = 0;
        }
    }


    // 計數 DP
    private static final int MX = 8;
    private static final int[][] c = new int[MX][MX]; // 表示用前 i 種字符構造長為 j 的序列的方案數

    static {
        for (int i = 0; i < MX; i++) {
            c[i][0] = c[i][i] = 1;
            for (int j = 1; j < i; j++)
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j]; // 預處理組合數
        }
    }

    public int numTilePossibilitiesDP(String tiles) {
        var counts = new HashMap<Character, Integer>(); // 統計每個字母的出現次數
        for (var c : tiles.toCharArray())
            counts.merge(c, 1, Integer::sum); // counts[c]++
        int m = counts.size(), n = tiles.length();
        var f = new int[m + 1][n + 1];
        f[0][0] = 1; // 構造空序列的方案數
        int i = 1;
        for (var cnt : counts.values()) { // 枚舉第 i 種字母
            for (int j = 0; j <= n; j++) // 枚舉序列長度 j
                for (int k = 0; k <= j && k <= cnt; k++) // 枚舉第 i 種字母選了 k 個
                    f[i][j] += f[i - 1][j - k] * c[j][k];
            i++;
        }
        int ans = 0;
        for (int j = 1; j <= n; j++)
            ans += f[m][j];
        return ans;
    }


}
