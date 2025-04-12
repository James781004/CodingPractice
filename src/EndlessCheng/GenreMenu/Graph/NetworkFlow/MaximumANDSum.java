package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.*;

public class MaximumANDSum {

    // https://leetcode.cn/problems/maximum-and-sum-of-array/solutions/1262849/on3-xiong-ya-li-suan-fa-km-suan-fa-fu-ja-j3v1/
    public int maximumANDSum(int[] nums, int numSlots) {
        // 建立籃子列表，每個籃子最多可存放 2 個數字
        List<Integer> slots = new ArrayList<>();
        for (int i = 1; i <= numSlots; i++) {
            slots.add(i);
            slots.add(i);
        }

        int n = nums.length; // 數字個數
        int s = numSlots * 2; // 總共可用的籃子數
        int[][] scope = new int[s][s]; // 權重矩陣

        // 計算每個數字與籃子之間的 AND 值作為權重
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < s; j++) {
                scope[i][j] = nums[i] & slots.get(j);
            }
        }

        // 使用 Km 演算法計算最大權匹配
        KmAlgo kmAlgo = new KmAlgo(s, scope);
        return kmAlgo.getMaximumWeight();
    }

    private static class KmAlgo {
        private final int n; // 節點數 (2 * numSlots)
        private final int[] matchX, matchY; // X, Y 集合的匹配點
        private final int[] pre; // BFS 過程中的增廣路徑
        private final boolean[] visX, visY; // 拜訪記錄 (X 集合 & Y 集合)
        private final int[] lx, ly; // 頂標數組
        private final int[][] graph; // 權重圖
        private final int[] slack; // 記錄 BFS 過程中的最小 delta
        private static final int INF = Integer.MAX_VALUE; // 無窮大
        private final Queue<Integer> queue; // BFS 隊列

        public KmAlgo(int n, int[][] graph) {
            this.n = n;
            this.graph = graph;
            this.queue = new LinkedList<>();
            this.matchX = new int[n];
            Arrays.fill(matchX, -1);
            this.matchY = new int[n];
            Arrays.fill(matchY, -1);
            this.pre = new int[n];
            this.visX = new boolean[n];
            this.visY = new boolean[n];
            this.lx = new int[n];
            Arrays.fill(lx, -INF);
            this.ly = new int[n];
            this.slack = new int[n];
        }

        public int getMaximumWeight() {
            // 初始化左集合頂標 (每個節點取其最大權重值)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    lx[i] = Math.max(lx[i], graph[i][j]);
                }
            }

            // 進行匹配
            for (int i = 0; i < n; i++) {
                Arrays.fill(slack, INF);
                Arrays.fill(visX, false);
                Arrays.fill(visY, false);
                bfs(i);
            }

            // 計算總權重
            int res = 0;
            for (int i = 0; i < n; i++) {
                res += graph[i][matchX[i]];
            }
            return res;
        }

        private boolean check(int v) {
            visY[v] = true;
            if (matchY[v] != -1) {
                queue.add(matchY[v]);
                visX[matchY[v]] = true;
                return false;
            }

            // 找到增廣路，更新匹配關係
            while (v != -1) {
                matchY[v] = pre[v];
                int tmp = matchX[pre[v]];
                matchX[pre[v]] = v;
                v = tmp;
            }
            return true;
        }

        private void bfs(int i) {
            queue.clear();
            queue.add(i);
            visX[i] = true;
            while (true) {
                while (!queue.isEmpty()) {
                    int u = queue.remove();
                    for (int v = 0; v < n; v++) {
                        if (!visY[v]) {
                            int delta = lx[u] + ly[v] - graph[u][v];
                            if (slack[v] >= delta) {
                                pre[v] = u;
                                if (delta > 0) {
                                    slack[v] = delta;
                                } else if (check(v)) {
                                    return;
                                }
                            }
                        }
                    }
                }

                // 調整頂標
                int a = INF;
                for (int j = 0; j < n; j++) {
                    if (!visY[j]) {
                        a = Math.min(a, slack[j]);
                    }
                }
                for (int j = 0; j < n; j++) {
                    if (visX[j]) lx[j] -= a;
                    if (visY[j]) ly[j] += a;
                    else slack[j] -= a;
                }

                for (int j = 0; j < n; j++) {
                    if (!visY[j] && slack[j] == 0 && check(j)) {
                        return;
                    }
                }
            }
        }
    }


}
