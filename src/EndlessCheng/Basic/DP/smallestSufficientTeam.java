package EndlessCheng.Basic.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class smallestSufficientTeam {

    // https://leetcode.cn/problems/smallest-sufficient-team/solutions/2214387/zhuang-ya-0-1-bei-bao-cha-biao-fa-vs-shu-qode/
    private long all;
    private int[] mask;
    private long[][] memo;

    public int[] smallestSufficientTeam(String[] reqSkills, List<List<String>> people) {
        var sid = new HashMap<String, Integer>();
        int m = reqSkills.length;
        for (int i = 0; i < m; ++i)
            sid.put(reqSkills[i], i); // 字符串映射到下標

        int n = people.size();
        mask = new int[n];
        for (int i = 0; i < n; ++i)
            for (var s : people.get(i)) // 把 people[i] 壓縮成一個二進制數 mask[i]
                mask[i] |= 1 << sid.get(s); // 計算兩個集合 A,B 的並集 A∪B，即 A | B。例如 110 | 11 = 111

        int u = 1 << m;
        memo = new long[n][u];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示還沒有計算過
        all = (1L << n) - 1;
        long res = dfs(n - 1, u - 1);

        var ans = new int[Long.bitCount(res)];
        for (int i = 0, j = 0; i < n; ++i)
            if (((res >> i) & 1) > 0) // 判斷元素 x 是否在集合 A 中，即 ((A >> x) & 1) == 1
                ans[j++] = i; // 所有在 res 中的下標
        return ans;
    }

    private long dfs(int i, int j) {
        if (j == 0) return 0; // 背包已裝滿
        if (i < 0) return all; // 沒法裝滿背包，返回全集，這樣下面比較集合大小會取更小的
        if (memo[i][j] != -1) return memo[i][j];
        long res = dfs(i - 1, j); // 不選 mask[i]
        long res2 = dfs(i - 1, j & ~mask[i]) | (1L << i); // 選 mask[i]，從集合 A 中去掉在集合 B 中的元素，即 A & ~B。例如 110 & ~11 = 100
        return memo[i][j] = Long.bitCount(res) < Long.bitCount(res2) ? res : res2;
    }


    public int[] smallestSufficientTeamDP(String[] reqSkills, List<List<String>> people) {
        var sid = new HashMap<String, Integer>();
        int m = reqSkills.length;
        for (int i = 0; i < m; ++i)
            sid.put(reqSkills[i], i); // 字符串映射到下標

        int n = people.size(), u = 1 << m;
        // f[i+1][j] 表示從前 i 個集合中選擇一些集合，並集等於 j，需要選擇的最小集合
        var f = new long[n + 1][u];
        Arrays.fill(f[0], (1L << n) - 1); // 對應記憶化搜索中的 if (i < 0) return all;
        f[0][0] = 0;
        for (int i = 0; i < n; ++i) {
            int mask = 0;
            for (var s : people.get(i)) // 把 people[i] 壓縮成一個二進制數 mask
                mask |= 1 << sid.get(s);
            for (int j = 1; j < u; ++j) {
                long res = f[i][j]; // 不選 mask
                long res2 = f[i][j & ~mask] | (1L << i); // 選 mask
                f[i + 1][j] = Long.bitCount(res) < Long.bitCount(res2) ? res : res2;
            }
        }

        long res = f[n][u - 1];
        var ans = new int[Long.bitCount(res)];
        for (int i = 0, j = 0; i < n; ++i)
            if (((res >> i) & 1) > 0)
                ans[j++] = i; // 所有在 res 中的下標
        return ans;
    }

    public int[] smallestSufficientTeamDP2(String[] reqSkills, List<List<String>> people) {
        var sid = new HashMap<String, Integer>();
        int m = reqSkills.length;
        for (int i = 0; i < m; ++i)
            sid.put(reqSkills[i], i); // 字符串映射到下標

        int n = people.size(), u = 1 << m;
        var f = new long[u];
        Arrays.fill(f, (1L << n) - 1);
        f[0] = 0;
        for (int i = 0; i < n; ++i) {
            int mask = 0;
            for (var s : people.get(i)) // 把 people[i] 壓縮成一個二進制數 mask
                mask |= 1 << sid.get(s);
            for (int j = u - 1; j > 0; --j) {
                long res = f[j]; // 不選 mask
                long res2 = f[j & ~mask] | (1L << i); // 選 mask
                f[j] = Long.bitCount(res) < Long.bitCount(res2) ? res : res2;
            }
        }

        long res = f[u - 1];
        var ans = new int[Long.bitCount(res)];
        for (int i = 0, j = 0; i < n; ++i)
            if (((res >> i) & 1) > 0)
                ans[j++] = i; // 所有在 res 中的下標
        return ans;
    }


}
