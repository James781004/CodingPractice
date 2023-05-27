package WeeklyContest;

import java.util.*;

public class Week_345 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2682.Find%20the%20Losers%20of%20the%20Circular%20Game/README.md
    public int[] circularGameLosers(int n, int k) {
        boolean[] vis = new boolean[n];
        int cnt = 0;
        for (int i = 0, p = 1; !vis[i]; ++p) {
            vis[i] = true; // 記錄每個朋友是否接到過球
            ++cnt;  // 計算贏家數量
            i = (i + p * k) % n;
        }
        int[] ans = new int[n - cnt];
        for (int i = 0, j = 0; i < n; ++i) {
            if (!vis[i]) {
                ans[j++] = i + 1;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2683.Neighboring%20Bitwise%20XOR/README.md
    //  original = a
    //  derived = b
    // ==> a[i + 1] = a[i] ^ b[i]
    // a[1] = b[0] ^ a[0]
    // a[2] = b[1] ^ a[1] = b[1] ^ b[0] ^ a[0]

    // a[3] = b[2] ^ a[2] = b[2] ^ b[1] ^ b[0] ^ a[0]
    // ====> a[3] = b[2] ^ b[1] ^ b[0] ^ a[0]
    // 又因為 a[3] ^ a[0] = b[3]
    // 左邊異或左邊、右邊異或右邊
    // ==> a[3] ^ a[3] ^ a[0] = b[3] ^ b[2] ^ b[1] ^ b[0] ^ a[0]
    // ==> 推導 b[3] ^ b[2] ^ b[1] ^ b[0] = 0
    // 只要派生數組的所有元素的異或和為 0，
    // 就一定存在一個滿足要求的原始二進制數組。
    public boolean doesValidArrayExist(int[] derived) {
        int s = 0;
        for (int x : derived) s ^= x;
        return s == 0;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2684.Maximum%20Number%20of%20Moves%20in%20a%20Grid/README.md
    public int maxMovesBFS(int[][] grid) {
        int[][] dirs = {{-1, 1}, {0, 1}, {1, 1}};
        int m = grid.length, n = grid[0].length;
        Deque<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < m; ++i) {
            q.offer(new int[]{i, 0}); // 將第一列的所有單元格 (i, 0) 加入隊列
        }
        int[][] dist = new int[m][n];  // dist[i][j] 表示到達單元格 (i, j) 的最大移動次數
        int ans = 0;
        while (!q.isEmpty()) {
            int[] p = q.poll();
            int i = p[0], j = p[1];
            for (int[] dir : dirs) {
                int x = i + dir[0], y = j + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] > grid[i][j] && dist[x][y] < dist[i][j] + 1) {
                    dist[x][y] = dist[i][j] + 1;  // 滿足題目條件，dist + 1
                    ans = Math.max(ans, dist[x][y]); // 更新答案
                    q.offer(new int[]{x, y}); // 下一格候選加入隊列
                }
            }
        }
        return ans;
    }


    // https://www.bilibili.com/video/BV1ka4y137ua/?spm_id_from=333.999.0.0
    // https://leetcode.cn/problems/maximum-number-of-moves-in-a-grid/solution/cong-ji-yi-hua-sou-suo-dao-di-tui-by-end-pgq3/
    // dfs
    private int n;
    private int m;
    private int max = 0;

    public int maxMovesDFS(int[][] grid) {
        n = grid.length;
        m = grid[0].length;
        // vis用於剪枝操作
        boolean[][] vis = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            dfs(i, 0, grid, vis);
            // 如果max為m-1，已經是能達到的最大值了，無須再考慮後面的情況了
            if (max == m - 1)
                break;
        }
        return max;
    }

    public void dfs(int i, int j, int[][] grid, boolean[][] vis) {
        // 如果j達到最大值
        if (j == m - 1) {
            // max即為該最大值
            max = m - 1;
            // 結束搜索
            return;
        }
        // 符合移動條件，且目標單元格還沒有搜索過
        if (grid[i][j + 1] > grid[i][j] && !vis[i][j + 1]) {
            // 繼續搜索
            dfs(i, j + 1, grid, vis);
            // 並標記目標單元格已經搜索，避免重復搜索
            vis[i][j + 1] = true;
        }
        // 下面同理
        if (i > 0 && grid[i - 1][j + 1] > grid[i][j] && !vis[i - 1][j + 1]) {
            dfs(i - 1, j + 1, grid, vis);
            vis[i - 1][j + 1] = true;
        }
        if (i < n - 1 && grid[i + 1][j + 1] > grid[i][j] && !vis[i + 1][j + 1]) {
            dfs(i + 1, j + 1, grid, vis);
            vis[i + 1][j + 1] = true;
        }
        // 全部搜索完畢後，若j值比max大，則保留該j值為最大值
        max = Math.max(max, j);
    }

    public int maxMovesDP(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] f = new int[m][n];
        for (int j = n - 2; j >= 0; j--)
            for (int i = 0; i < m; i++)
                for (int k = Math.max(i - 1, 0); k < Math.min(i + 2, m); k++)
                    if (grid[k][j + 1] > grid[i][j])
                        f[i][j] = Math.max(f[i][j], f[k][j + 1] + 1);
        int ans = 0;
        for (int i = 0; i < m; i++)
            ans = Math.max(ans, f[i][0]);
        return ans;
    }


    // https://leetcode.cn/problems/count-the-number-of-complete-components/solution/dfs-qiu-mei-ge-lian-tong-kuai-de-dian-sh-opg4/
    private List<Integer>[] g;
    private boolean vis[];
    private int v, e;  // v表示點數，e表示邊數

    public int countCompleteComponents(int n, int[][] edges) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
        }

        int ans = 0;
        vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                v = 0;
                e = 0;
                helper(i);
                // 在完全圖中，任意兩點之間都有邊，
                // 相當於從 v 個點中選 2 個點的方案數。
                // 所以有 e == v * (v - 1) / 2
                // 由於helper統計的時候，一條邊統計了兩次，所以代碼中的判斷條件是 e == v * (v - 1)
                if (e == v * (v - 1))
                    ans++;
            }
        }

        return ans;
    }

    // DFS 每個連通塊，統計當前連通塊的點數 v 和邊數 e
    private void helper(int x) {
        vis[x] = true;
        v++; // 每訪問一個點，就把 v 加一
        e += g[x].size(); // e 加上點 v 的鄰居個數。注意這樣一條邊會統計兩次
        for (int y : g[x]) {
            if (!vis[y]) helper(y);
        }
    }
}

