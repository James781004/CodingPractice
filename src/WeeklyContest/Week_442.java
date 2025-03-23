package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Week_442 {

    // https://leetcode.cn/problems/maximum-containers-on-a-ship/solutions/3624237/o1-gong-shi-pythonjavacgo-by-endlesschen-n8j0/
    public int maxContainers(int n, int w, int maxWeight) {
        return Math.min(maxWeight / w, n * n);
    }


    // https://leetcode.cn/problems/properties-graph/solutions/3624345/bing-cha-ji-pythonjavacgo-by-endlesschen-xi0d/
    public int numberOfComponents(int[][] properties, int k) {
        int n = properties.length;
        int m = properties[0].length;
        Set<Integer>[] sets = new HashSet[n];
        Arrays.setAll(sets, i -> new HashSet<>(m));
        for (int i = 0; i < n; i++) {
            for (int x : properties[i]) {
                sets[i].add(x);
            }
        }

        UnionFind u = new UnionFind(n);

        // 暴力枚舉所有 properties[i] 和 properties[j]
        for (int i = 0; i < n; i++) {
            Set<Integer> a = sets[i];
            for (int j = 0; j < i; j++) {
                int cnt = 0;
                for (int x : sets[j]) {
                    if (a.contains(x)) { // 交集
                        cnt++;
                    }
                }
                if (cnt >= k) { // 交集大小 ≥k，那麼用並查集合並 i 和 j
                    u.merge(i, j);
                }
            }
        }
        return u.cc;
    }

    class UnionFind {
        private final int[] fa;
        public int cc; // 連通塊個數

        UnionFind(int n) {
            fa = new int[n];
            cc = n;
            for (int i = 0; i < n; i++) {
                fa[i] = i;
            }
        }

        public int find(int x) {
            if (fa[x] != x) {
                fa[x] = find(fa[x]);
            }
            return fa[x];
        }

        public void merge(int from, int to) {
            int x = find(from);
            int y = find(to);
            if (x == y) {
                return;
            }
            fa[x] = y;
            cc--; // 初始連通塊個數 cc=n，每成功合並一次，就把 cc 減一
        }
    }


    // https://leetcode.cn/problems/find-the-minimum-amount-of-time-to-brew-potions/solutions/3624232/zheng-fan-liang-ci-sao-miao-pythonjavacg-5fz9/
    public long minTime(int[] skill, int[] mana) {
        int n = skill.length;
        long[] lastFinish = new long[n]; // 第 i 名巫師完成上一瓶藥水的時間
        for (int m : mana) {
            // 按題意模擬
            long sumT = 0;
            for (int i = 0; i < n; i++) {
                sumT = Math.max(sumT, lastFinish[i]) + skill[i] * m;
            }
            // 倒推：如果釀造藥水的過程中沒有停頓，那麼 lastFinish[i] 應該是多少
            lastFinish[n - 1] = sumT;
            for (int i = n - 2; i >= 0; i--) {
                sumT -= skill[i + 1] * m;
                lastFinish[i] = sumT;
            }
        }
        return lastFinish[n - 1];
    }


    // https://leetcode.cn/problems/minimum-operations-to-make-array-elements-zero/solutions/3624312/o1-gong-shi-pythonjavacgo-by-endlesschen-2gos/
    public long minOperations(int[][] queries) {
        long ans = 0;
        for (int[] q : queries) {
            int l = q[0], r = q[1];
            long totOp = f(r) - f(l - 1);
            long maxOp = (33 - Integer.numberOfLeadingZeros(r)) / 2;
            ans += Math.max((totOp + 1) / 2, maxOp);
        }
        return ans;
    }

    // 返回 [1,n] 的單個元素的操作次數之和
    private long f(int n) {
        int m = 32 - Integer.numberOfLeadingZeros(n);
        long res = 0;
        for (int i = 1; i < m; i++) {
            res += (long) (i + 1) / 2 << (i - 1);
        }
        return res + (long) (m + 1) / 2 * (n + 1 - (1 << m >> 1));
    }


}









