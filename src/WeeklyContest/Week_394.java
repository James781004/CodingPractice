package WeeklyContest;

import java.util.*;

public class Week_394 {
    // https://leetcode.cn/problems/count-the-number-of-special-characters-i/solutions/2749356/yi-ci-bian-li-by-sleepy-herschelouf-p10a/
    public int numberOfSpecialChars(String word) {
        int[] mp = new int[300];
        for (char x : word.toCharArray()) {
            mp[x]++;
        }
        int ans = 0;
        for (int i = 65; i <= 90; ++i) {
            if (mp[i] > 0 && mp[i + 32] > 0) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-the-number-of-special-characters-ii/solutions/2749308/jian-dan-qing-xi-shuang-mapshi-xian-by-l-yi3m/
    // 特殊字母要求：
    // 同時出現某個字母 c 的小寫形式和大寫形式;
    // 並且 每個小寫形式的 c 都出現在第一個大寫形式的 c 之前。
    // 使用兩個Map，小寫字母map記錄小寫字母最後一次出現索引，大寫字母Map記錄大寫字母第一次出現索引。
    // 遍歷大寫字母map，如果對應小寫字母出現，並且最後出現索引小於大寫字母第一次出現索引，則特殊字母加1
    public int numberOfSpecialChars2(String word) {
        int ans = 0;
        Map<Character, Integer> lowerMap = new HashMap<>();
        Map<Character, Integer> upperCaseMap = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (c >= 'a' && c <= 'z') {
                lowerMap.put(c, i);
            } else if (c >= 'A' && c <= 'Z' && !upperCaseMap.containsKey(c)) {
                upperCaseMap.put(c, i);
            }
        }
        for (Map.Entry<Character, Integer> entry : upperCaseMap.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            char c = (char) (key + 32);
            if (lowerMap.containsKey(c) && lowerMap.get(c) < value) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-number-of-operations-to-satisfy-conditions/solutions/2749283/ji-yi-hua-sou-suo-pythonjavacgo-by-endle-8i0e/
    public int minimumOperations(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] cnt = new int[n][10];
        for (int[] row : grid) {
            for (int j = 0; j < n; j++) {
                cnt[j][row[j]]++;
            }
        }
        int[][] memo = new int[n][11];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return m * n - dfs(n - 1, 10, cnt, memo);
    }

    private int dfs(int i, int j, int[][] cnt, int[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res = 0;
        for (int k = 0; k < 10; ++k) {
            if (k != j) {
                res = Math.max(res, dfs(i - 1, k, cnt, memo) + cnt[i][k]);
            }
        }
        return memo[i][j] = res; // 記憶化
    }


    // https://leetcode.cn/problems/find-edges-in-shortest-paths/solutions/2749274/dijkstra-zui-duan-lu-dfsbfs-zhao-bian-py-yf48/
    public boolean[] findAnswer(int n, int[][] edges) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int[] e = edges[i];
            int x = e[0], y = e[1], w = e[2];
            g[x].add(new int[]{y, w, i});
            g[y].add(new int[]{x, w, i});
        }

        long[] dis = new long[n];
        Arrays.fill(dis, Long.MAX_VALUE);
        dis[0] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 0});
        while (!pq.isEmpty()) {
            long[] dxPair = pq.poll();
            long dx = dxPair[0];
            int x = (int) dxPair[1];
            if (dx > dis[x]) {
                continue;
            }
            for (int[] t : g[x]) {
                int y = t[0];
                int w = t[1];
                long newDis = dx + w;
                if (newDis < dis[y]) {
                    dis[y] = newDis;
                    pq.offer(new long[]{newDis, y});
                }
            }
        }

        boolean[] ans = new boolean[edges.length];
        // 圖不連通
        if (dis[n - 1] == Long.MAX_VALUE) {
            return ans;
        }

        // 從終點出發 BFS
        boolean[] vis = new boolean[n];
        dfs(n - 1, g, dis, ans, vis);
        return ans;
    }

    private void dfs(int y, List<int[]>[] g, long[] dis, boolean[] ans, boolean[] vis) {
        vis[y] = true;
        for (int[] t : g[y]) {
            int x = t[0];
            int w = t[1];
            int i = t[2];
            if (dis[x] + w != dis[y]) {
                continue;
            }
            ans[i] = true;
            if (!vis[x]) {
                dfs(x, g, dis, ans, vis);
            }
        }
    }
}


