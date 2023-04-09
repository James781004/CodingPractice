package WeeklyContest;

import java.util.*;

public class Week_322 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2490.Circular%20Sentence/README.md
    public boolean isCircularSentence(String sentence) {
        if (sentence.charAt(0) != sentence.charAt(sentence.lastIndexOf(-1))) return false;
        String[] ss = sentence.split(" ");
        for (int i = 1; i < ss.length; i++) {
            if (ss[i].charAt(0) != ss[i - 1].charAt(ss[i - 1].length() - 1)) {
                return false;
            }
        }
        return true;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2491.Divide%20Players%20Into%20Teams%20of%20Equal%20Skill/README.md
    public long dividePlayers(int[] skill) {
        Arrays.sort(skill);
        int n = skill.length;
        int t = skill[0] + skill[n - 1];
        long ans = 0;
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            if (skill[i] + skill[j] != t) return -1;
            ans += (long) skill[i] * skill[j];
        }
        return ans;
    }

    public long dividePlayers2(int[] skill) {
        int s = Arrays.stream(skill).sum();
        int m = skill.length >> 1;
        if (s % m != 0) {
            return -1;
        }
        int t = s / m;
        int[] d = new int[1010];
        long ans = 0;
        for (int v : skill) {
            if (d[t - v] > 0) {
                ans += (long) v * (t - v);
                --d[t - v];
                --m;
            } else {
                ++d[v];
            }
        }
        return m == 0 ? ans : -1;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2492.Minimum%20Score%20of%20a%20Path%20Between%20Two%20Cities/README.md
    class MinScore {
        private List<int[]>[] g;
        private boolean[] vis;
        private int ans = 1 << 30;

        public int minScoreDFS(int n, int[][] roads) {
            g = new List[n];
            vis = new boolean[n];
            Arrays.setAll(g, k -> new ArrayList<>());
            for (int[] e : roads) {
                int a = e[0] - 1, b = e[1] - 1, d = e[2];
                g[a].add(new int[]{b, d});
                g[b].add(new int[]{a, d});
            }
            dfs(0);
            return ans;
        }

        private void dfs(int i) {
            for (int[] nxt : g[i]) {
                int j = nxt[0], d = nxt[1];
                ans = Math.min(ans, d);
                if (!vis[j]) {
                    vis[j] = true;
                    dfs(j);
                }
            }
        }

        public int minScoreBFS(int n, int[][] roads) {
            List<int[]>[] g = new List[n];
            boolean[] vis = new boolean[n];
            Arrays.setAll(g, k -> new ArrayList<>());
            for (int[] e : roads) {
                int a = e[0] - 1, b = e[1] - 1, d = e[2];
                g[a].add(new int[]{b, d});
                g[b].add(new int[]{a, d});
            }
            Deque<Integer> q = new ArrayDeque<>();
            q.offer(0);
            vis[0] = true;
            int ans = 1 << 30;
            while (!q.isEmpty()) {
                for (int k = q.size(); k > 0; k--) {
                    int i = q.pollFirst();
                    for (int[] nxt : g[i]) {
                        int j = nxt[0], d = nxt[1];
                        ans = Math.min(ans, d);
                        if (!vis[j]) {
                            vis[j] = true;
                            q.offer(j);
                        }
                    }
                }
            }
            return ans;
        }
    }


    // https://www.bilibili.com/video/BV15d4y147YF/
    // https://leetcode.cn/problems/divide-nodes-into-the-maximum-number-of-groups/solution/mei-ju-qi-dian-pao-bfs-by-endlesscheng-s5bu/
    class MagnificentSets {
        private List<Integer>[] g;
        private final List<Integer> nodes = new ArrayList<>();
        private int[] time, color; // time 充當 vis 數組的作用（避免在 BFS 內部重復創建 vis 數組）
        private int clock;

        public int magnificentSets(int n, int[][] edges) {
            g = new ArrayList[n];
            Arrays.setAll(g, e -> new ArrayList<>());
            for (int[] e : edges) {
                int x = e[0], y = e[1];
                g[x].add(y);
                g[y].add(x);
            }

            time = new int[n];
            color = new int[n];
            int ans = 0;
            for (int i = 0; i < n; i++) {
                if (color[i] != 0) continue;
                nodes.clear();
                if (!isBipartite(i, 1)) return -1; // 如果不是二分圖（有奇環），則無法分組
                // 否則一定可以分組
                int maxDepth = 0;
                for (int x : nodes) // 枚舉連通塊的每個點，作為起點 BFS，求最大深度
                    maxDepth = Math.max(maxDepth, bfs(x));
                ans += maxDepth;
            }
            return ans;
        }

        // 二分圖判定
        // 0 表示没有染色，1 表示红色，-1 表示蓝色
        private boolean isBipartite(int x, int c) {
            nodes.add(x);
            color[x] = c;
            for (int y : g[x]) {
                if (color[y] == c || color[y] == 0 && !isBipartite(y, -c)) return false;
            }
            return true;
        }

        // 返回從 start 出發的最大深度
        private int bfs(int start) {
            int depth = 0;
            time[start] = ++clock;
            List<Integer> q = new ArrayList<>();
            q.add(start);
            while (!q.isEmpty()) {
                List<Integer> tmp = q;
                q = new ArrayList<>();
                for (int x : tmp) {
                    for (int y : g[x]) {
                        if (time[y] != clock) { // 沒有在同一次 BFS 中訪問過
                            time[y] = clock;
                            q.add(y);
                        }
                    }
                }
                depth++;
            }
            return depth;
        }
    }
}
