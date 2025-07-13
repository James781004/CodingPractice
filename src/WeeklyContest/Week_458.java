package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_458 {

    // https://leetcode.cn/problems/process-string-with-special-operations-i/solutions/3722476/mo-ni-pythonjavacgo-by-endlesscheng-6hid/
    String processStr(String s) {
        StringBuilder ans = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '*') {
                if (!ans.isEmpty()) {
                    ans.setLength(ans.length() - 1);
                }
            } else if (c == '#') {
                ans.append(ans);
            } else if (c == '%') {
                ans.reverse();
            } else {
                ans.append(c);
            }
        }
        return ans.toString();
    }

    // https://leetcode.cn/problems/minimize-maximum-component-cost/solutions/3722472/cong-xiao-dao-da-he-bing-pythonjavacgo-b-s72b/
    int minCost(int n, int[][] edges, int k) {
        if (k == n) {
            return 0;
        }

        Arrays.sort(edges, (a, b) -> a[2] - b[2]);
        UnionFind u = new UnionFind(n);
        for (int[] e : edges) {
            u.merge(e[0], e[1]);
            if (u.cc <= k) {
                return e[2];
            }
        }
        throw new RuntimeException("impossible");
    }


    class UnionFind {
        private final int[] fa; // 代表元
        public int cc; // 連通塊個數

        UnionFind(int n) {
            // 一開始有 n 個集合 {0}, {1}, ..., {n-1}
            // 集合 i 的代表元是自己，大小為 1
            fa = new int[n];
            for (int i = 0; i < n; i++) {
                fa[i] = i;
            }
            cc = n;
        }

        // 返回 x 所在集合的代表元
        // 同時做路徑壓縮，也就是把 x 所在集合中的所有元素的 fa 都改成代表元
        public int find(int x) {
            // 如果 fa[x] == x，則表示 x 是代表元
            if (fa[x] != x) {
                fa[x] = find(fa[x]); // fa 改成代表元
            }
            return fa[x];
        }

        // 把 from 所在集合合並到 to 所在集合中
        public void merge(int from, int to) {
            int x = find(from);
            int y = find(to);
            if (x == y) { // from 和 to 在同一個集合，不做合並
                return;
            }
            fa[x] = y; // 合並集合。修改後就可以認為 from 和 to 在同一個集合了
            cc--; // 成功合並，連通塊個數減一
        }
    }


    // https://leetcode.cn/problems/process-string-with-special-operations-ii/solutions/3722462/ni-xiang-si-wei-pythonjavacgo-by-endless-26al/
    char processStr(String S, long k) {
        char[] s = S.toCharArray();
        int n = s.length;
        long[] size = new long[n];
        long sz = 0;
        for (int i = 0; i < n; i++) {
            char c = s[i];
            if (c == '*') {
                sz = Math.max(sz - 1, 0);
            } else if (c == '#') {
                sz *= 2; // 題目保證 sz <= 1e15
            } else if (c != '%') { // c 是字母
                sz++;
            }
            size[i] = sz;
        }

        if (k >= size[n - 1]) { // 下標越界
            return '.';
        }

        // 迭代
        for (int i = n - 1; ; i--) {
            char c = s[i];
            sz = size[i];
            if (c == '#') {
                if (k >= sz / 2) { // k 在復制後的右半邊
                    k -= sz / 2;
                }
            } else if (c == '%') {
                k = sz - 1 - k;
            } else if (c != '*' && k == sz - 1) { // 找到答案
                return c;
            }
        }
    }

    // https://leetcode.cn/problems/longest-palindromic-path-in-graph/solutions/3722469/zhong-xin-kuo-zhan-fa-zhuang-ya-dp-by-en-ai9s/
    public int maxLen(int n, int[][] edges, String label) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        int[][][] memo = new int[n][n][1 << n];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }

        char[] s = label.toCharArray();
        int ans = 0;
        for (int x = 0; x < n; x++) {
            // 奇回文串，x 作為回文中心
            ans = Math.max(ans, dfs(x, x, 1 << x, g, s, memo) + 1);
            // 偶回文串，x 和 x 的鄰居 y 作為回文中心
            for (int y : g[x]) {
                // 保證 x < y，減少狀態個數和計算量
                if (x < y && s[x] == s[y]) {
                    ans = Math.max(ans, dfs(x, y, 1 << x | 1 << y, g, s, memo) + 2);
                }
            }
        }
        return ans;
    }

    // 計算從 x 和 y 向兩側擴展，最多還能訪問多少個節點（不算 x 和 y）
    private int dfs(int x, int y, int vis, List<Integer>[] g, char[] label, int[][][] memo) {
        if (memo[x][y][vis] >= 0) { // 之前計算過
            return memo[x][y][vis];
        }
        int res = 0;
        for (int v : g[x]) {
            if ((vis >> v & 1) > 0) { // v 在路徑中
                continue;
            }
            for (int w : g[y]) {
                if ((vis >> w & 1) == 0 && w != v && label[w] == label[v]) {
                    // 保證 v < w，減少狀態個數和計算量
                    int r = dfs(Math.min(v, w), Math.max(v, w), vis | 1 << v | 1 << w, g, label, memo);
                    res = Math.max(res, r + 2);
                }
            }
        }
        return memo[x][y][vis] = res; // 記憶化
    }


}









