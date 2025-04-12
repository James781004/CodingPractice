package EndlessCheng.GenreMenu.Graph.NetworkFlow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MaxCompatibilitySum {

    // https://leetcode.cn/problems/maximum-compatibility-score-sum/solutions/1262945/on3-xiong-ya-li-suan-fa-km-suan-fa-fu-ja-o19j/
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        // n 個問題
        int n = students[0].length;
        // m 名學生和 m 名導師
        int m = students.length;
        // 預處理
        // score[i][j] 代表下標為 i 的學生與下標為 j 的老師的 兼容性評分
        int[][] score = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    if (students[i][k] == mentors[j][k]) {
                        score[i][j] += 1;
                    }
                }
            }
        }
        KmAlgo kmAlgo = new KmAlgo(m, score);
        return kmAlgo.getMaximumWeight();
    }

    // 匈牙利算法又稱為 KM 算法，可以在 O(n^3) 時間內求出二分圖的 最大權完美匹配
    private static class KmAlgo {
        private final int n;
        // 左集合對應的匹配點
        private final int[] matchX;
        // 右集合對應的匹配點
        private final int[] matchY;
        // 連接右集合的左點
        private final int[] pre;
        // 拜訪數組 左
        private final boolean[] visX;
        // 拜訪數組 右
        private final boolean[] visY;
        // 可行頂標 給每個節點 i 分配一個權值 l(i)，對於所有邊 (u,v) 滿足 w(u,v) <= l(u) + l(v)。
        private final int[] lx;
        private final int[] ly;
        private final int[][] graph;
        private final int[] slack;
        private static final int INF = Integer.MAX_VALUE;
        private final Queue<Integer> queue;

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
            // 初始頂標
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    lx[i] = Math.max(lx[i], graph[i][j]);
                }
            }
            for (int i = 0; i < n; i++) {
                Arrays.fill(slack, INF);
                Arrays.fill(visX, false);
                Arrays.fill(visY, false);
                bfs(i);
            }
            // custom
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
                // in S
                visX[matchY[v]] = true;
                return false;
            }
            // 找到新的未匹配點 更新匹配點 pre 數組記錄著"非匹配邊"上與之相連的點
            while (v != -1) {
                matchY[v] = pre[v];
                // swap(v, matchx[pre[v]]);
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
                                    // delta=0 代表有機會加入相等子圖 找增廣路
                                    // 找到就return 重建交錯樹
                                    return;
                                }
                            }
                        }
                    }
                }
                // 沒有增廣路 修改頂標
                int a = INF;
                for (int j = 0; j < n; j++) {
                    if (!visY[j]) {
                        a = Math.min(a, slack[j]);
                    }
                }
                for (int j = 0; j < n; j++) {
                    // S
                    if (visX[j]) {
                        lx[j] -= a;
                    }
                    // T
                    if (visY[j]) {
                        ly[j] += a;
                    }
                    // T'
                    else {
                        slack[j] -= a;
                    }
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
