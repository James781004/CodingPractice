package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_437 {

    // https://leetcode.cn/problems/find-special-substring-of-length-k/solutions/3076644/yi-ci-bian-li-jian-ji-xie-fa-pythonjavac-3oc3/
    public boolean hasSpecialSubstring(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt++;
            if (i == n - 1 || s[i] != s[i + 1]) {
                if (cnt == k) {
                    return true;
                }
                cnt = 0;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/eat-pizzas/solutions/3076629/tan-xin-pythonjavacgo-by-endlesscheng-fpjx/
    public long maxWeight(int[] pizzas) {
        Arrays.sort(pizzas);
        int n = pizzas.length;
        int days = n / 4;
        int odd = (days + 1) / 2;
        long ans = 0;
        for (int i = 0; i < odd; i++) {
            ans += pizzas[n - 1 - i];
        }
        for (int i = 0; i < days / 2; i++) {
            ans += pizzas[n - 2 - odd - i * 2];
        }
        return ans;
    }


    // https://leetcode.cn/problems/select-k-disjoint-special-substrings/solutions/
    public boolean maxSubstringLength(String s, int k) {
        if (k == 0) { // 提前返回
            return true;
        }

        int n = s.length();
        // 記錄每種字母的出現位置
        List<Integer>[] pos = new ArrayList[26];
        Arrays.setAll(pos, i -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            pos[s.charAt(i) - 'a'].add(i);
        }

        // 構建有向圖
        List<Integer>[] g = new ArrayList[26];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < 26; i++) {
            if (pos[i].isEmpty()) {
                continue;
            }
            List<Integer> p = pos[i];
            int l = p.get(0);
            int r = p.get(p.size() - 1);
            for (int j = 0; j < 26; j++) {
                if (j == i) {
                    continue;
                }
                List<Integer> q = pos[j];
                int qi = lowerBound(q, l);
                // [l, r] 包含第 j 個小寫字母
                if (qi < q.size() && q.get(qi) <= r) {
                    g[i].add(j);
                }
            }
        }

        // 遍歷有向圖
        List<int[]> intervals = new ArrayList<>();
        boolean[] vis = new boolean[26];
        for (int i = 0; i < 26; i++) {
            if (pos[i].isEmpty()) {
                continue;
            }
            // 如果要包含第 i 個小寫字母，最終得到的區間是什麼？
            Arrays.fill(vis, false);
            l = n;
            r = 0;
            dfs(i, pos, g, vis);
            // 不能選整個 s，即區間 [0, n-1]
            if (l > 0 || r < n - 1) {
                intervals.add(new int[]{l, r});
            }
        }

        return maxNonoverlapIntervals(intervals) >= k;
    }

    private int l, r;

    private void dfs(int x, List<Integer>[] pos, List<Integer>[] g, boolean[] vis) {
        vis[x] = true;
        List<Integer> p = pos[x];
        l = Math.min(l, p.get(0)); // 合並區間
        r = Math.max(r, p.get(p.size() - 1));
        for (int y : g[x]) {
            if (!vis[y]) {
                dfs(y, pos, g, vis);
            }
        }
    }

    // 435. 無重疊區間
    // 直接計算最多能選多少個區間
    private int maxNonoverlapIntervals(List<int[]> intervals) {
        intervals.sort((a, b) -> (a[1] - b[1]));
        int ans = 0;
        int preR = -1;
        for (int[] p : intervals) {
            if (p[0] > preR) {
                ans++;
                preR = p[1];
            }
        }
        return ans;
    }

    // 開區間寫法
    // 請看 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(List<Integer> a, int target) {
        // 開區間 (left, right)
        int left = -1;
        int right = a.size();
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // a[left] < target
            // a[right] >= target
            int mid = (left + right) >>> 1;
            if (a.get(mid) >= target) {
                right = mid; // 范圍縮小到 (left, mid)
            } else {
                left = mid; // 范圍縮小到 (mid, right)
            }
        }
        return right; // right 是最小的滿足 a[right] >= target 的下標
    }


    // https://leetcode.cn/problems/length-of-longest-v-shaped-diagonal-segment/solutions/3076747/ji-yi-hua-sou-suo-pythonjavacgo-by-endle-jrjj/
    private static final int[][] DIRS = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};

    public int lenOfVDiagonal(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 開太多維度影響效率，這裡把後面三個狀態壓縮成一個 int
        int[][][] memo = new int[m][n][1 << 4];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < 4; k++) { // 枚舉起始方向
                        ans = Math.max(ans, dfs(i, j, k, 1, 2, grid, memo) + 1);
                    }
                }
            }
        }
        return ans;
    }

    private int dfs(int i, int j, int k, int canTurn, int target, int[][] grid, int[][][] memo) {
        i += DIRS[k][0];
        j += DIRS[k][1];
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] != target) {
            return 0;
        }
        int mask = (k << 2) | (canTurn << 1) | (target / 2);
        if (memo[i][j][mask] > 0) { // 之前計算過
            return memo[i][j][mask];
        }
        int res = dfs(i, j, k, canTurn, 2 - target, grid, memo); // 直行
        if (canTurn == 1) {
            res = Math.max(res, dfs(i, j, (k + 1) % 4, 0, 2 - target, grid, memo)); // 右轉
        }
        return memo[i][j][mask] = res + 1; // 記憶化
    }


}









