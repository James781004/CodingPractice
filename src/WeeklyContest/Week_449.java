package WeeklyContest;

import java.util.*;

public class Week_449 {

    // https://leetcode.cn/problems/minimum-deletions-for-at-most-k-distinct-characters/solutions/3673973/cong-xiao-dao-da-tan-xin-jian-ji-xie-fa-lm9it/
    public int minDeletion(String s, int k) {
        int[] cnt = new int[26];
        for (char b : s.toCharArray()) {
            cnt[b - 'a']++;
        }

        Arrays.sort(cnt);
        int ans = 0;
        for (int i = 0; i < 26 - k; i++) {
            ans += cnt[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/equal-sum-grid-partition-i/solutions/3673985/mei-ju-fen-ge-xian-de-wei-zhi-pythonjava-6y75/
    public boolean canPartitionGrid(int[][] grid) {
        long total = 0;
        for (int[] row : grid) {
            for (int x : row) {
                total += x;
            }
        }

        // 水平分割 or 垂直分割
        return check(grid, total) || check(rotate(grid), total);
    }

    // 能否水平分割
    private boolean check(int[][] a, long total) {
        long s = 0;
        for (int i = 0; i < a.length - 1; i++) {
            for (int x : a[i]) {
                s += x;
            }
            if (s * 2 == total) {
                return true;
            }
        }
        return false;
    }

    // 順時針旋轉矩陣 90°
    private int[][] rotate(int[][] a) {
        int m = a.length, n = a[0].length;
        int[][] b = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[j][m - 1 - i] = a[i][j];
            }
        }
        return b;
    }


    // https://leetcode.cn/problems/maximum-sum-of-edge-values-in-a-graph/solutions/3673945/tan-xin-san-tiao-fa-ze-pythonjavacgo-by-0viop/
    private int cntV, cntE, cur;

    public long maxScore(int n, int[][] edges) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        List<Integer> cycle = new ArrayList<>();
        List<Integer> chain = new ArrayList<>();
        boolean[] vis = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (vis[i]) {
                continue;
            }
            cntV = 0;
            cntE = 0;
            dfs(i, g, vis);
            if (cntV * 2 == cntE) { // 環
                cycle.add(cntV);
            } else if (cntE > 0) { // 鏈，但不考慮孤立點
                chain.add(cntV);
            }
        }

        long ans = 0;
        cur = n;

        cycle.sort(null);
        for (int sz : cycle) {
            ans += calc(sz, true);
        }

        chain.sort(Comparator.reverseOrder());
        for (int sz : chain) {
            ans += calc(sz, false);
        }

        return ans;
    }

    private void dfs(int x, List<Integer>[] g, boolean[] vis) {
        vis[x] = true;
        cntV++;
        cntE += g[x].size();
        for (int y : g[x]) {
            if (!vis[y]) {
                dfs(y, g, vis);
            }
        }
    }

    private long calc(int sz, boolean isCycle) {
        long res = 0;
        int l = cur - sz + 1;
        int r = cur;
        for (int i = l; i < r - 1; i++) {
            res += (long) i * (i + 2);
        }
        res += (long) r * (r - 1);
        if (isCycle) {
            res += (long) l * (l + 1);
        }
        cur -= sz;
        return res;
    }


    // https://leetcode.cn/problems/equal-sum-grid-partition-ii/solutions/3673963/shi-zi-bian-xing-fen-lei-tao-lun-pythonj-2613/
    public boolean canPartitionGridII(int[][] grid) {
        long total = 0;
        for (int[] row : grid) {
            for (int x : row) {
                total += x;
            }
        }
        return calc(grid, total) || calc(rotateII(grid), total);
    }

    private boolean calc(int[][] a, long total) {
        if (checkII(a, total)) {
            return true;
        }
        reverse(a);
        return checkII(a, total);
    }

    private boolean checkII(int[][] a, long total) {
        int m = a.length, n = a[0].length;
        Set<Long> st = new HashSet<>();
        st.add(0L); // 0 對應不刪除數字
        long s = 0;
        for (int i = 0; i < m - 1; i++) {
            int[] row = a[i];
            for (int j = 0; j < n; j++) {
                int x = row[j];
                s += x;
                // 第一行，不能刪除中間元素
                if (i > 0 || j == 0 || j == n - 1) {
                    st.add((long) x);
                }
            }
            // 特殊處理只有一列的情況，此時只能刪除第一個數或者分割線上那個數
            if (n == 1) {
                if (s * 2 == total || s * 2 - total == a[0][0] || s * 2 - total == row[0]) {
                    return true;
                }
                continue;
            }
            if (st.contains(s * 2 - total)) {
                return true;
            }
            // 如果分割到更下面，那麼可以刪第一行的元素
            if (i == 0) {
                for (int x : row) {
                    st.add((long) x);
                }
            }
        }
        return false;
    }

    // 順時針旋轉矩陣 90°
    private int[][] rotateII(int[][] a) {
        int m = a.length, n = a[0].length;
        int[][] b = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[j][m - 1 - i] = a[i][j];
            }
        }
        return b;
    }

    private void reverse(int[][] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int[] tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

}









