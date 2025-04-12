package EndlessCheng.GenreMenu.Graph.Pseudotree;

import java.util.*;

public class ChaseGame {

    // https://leetcode.cn/problems/Za25hA/solutions/543590/java-bfsyou-bei-zhu-by-berli-ypwh/
    private int[] e, ne, h;
    private int idx;

    private List<Integer> ls = new ArrayList<>();

    public int chaseGame(int[][] edges, int startA, int startB) {
        int n = edges.length;
        if (startA < 1 || startA > n || startB < 1 || startB > n) return -1;
        h = new int[n + 1];
        Arrays.fill(h, -1);
        e = new int[2 * n];
        ne = new int[2 * n];

        Set<Integer> set = getSet(edges);//構建圖 + 返回環中的點集合

        for (int i = h[startA]; i != -1; i = ne[i]) if (e[i] == startB) return 1;//相鄰 直接返回

        if (set.size() > 3 && set.contains(startB)) return -1;//如果B在環中 且環長度大於3 永遠抓不到
        int max_step = -0x3f3f3f3f;

        int[] A_dists = getDist(startA, n);//存儲到各個點的距離 以後就不用求了
        int[] B_dists = getDist(startB, n);

        if (set.size() <= 3) {//當環小於4時候 一定能追上 但是追上時候不一定是死胡同 所以要枚舉所有點
            //枚舉一下所有的的點
            for (int i = 1; i <= n; i++) {
                int ad = A_dists[i];
                int bd = B_dists[i];
                if (ad - bd > 1) max_step = Math.max(max_step, ad);
            }
            return max_step;
        }
        // 找到B 進入環的最近點
        boolean[] st = new boolean[n + 1];
        Queue<Integer> qu = new LinkedList<>();
        qu.offer(startB);
        st[startB] = true;
        int B_in = 0;
        while (!qu.isEmpty()) {
            int cur = qu.poll();
            for (int i = h[cur]; i != -1; i = ne[i]) {
                int t = e[i];
                if (set.contains(t)) B_in = t;
                if (!st[t]) {
                    st[t] = true;
                    qu.offer(t);
                }
            }
            if (B_in != 0) break;
        }
        int A_dist = A_dists[B_in];
        int B_dist = B_dists[B_in];
        if (A_dist > B_dist + 1) return -1;//B能在A抓到之前 進入環 以後就住不到了
        // System.out.println(A_dist+" "+B_dist);
        // System.out.println(B_in);
        for (int t : ls) {//枚舉所有的死胡同 一定在死胡同被抓
            int dist_B = B_dists[t];
            int dist_A = A_dists[t];
            if (dist_A - dist_B > 1) max_step = Math.max(max_step, dist_A);
        }
        return max_step;

    }

    private int[] getDist(int A, int n) {
        boolean[] st = new boolean[n + 1];
        int[] dist = new int[n + 1];
        Arrays.fill(dist, 0x3f3f3f3f);
        Queue<Integer> qu = new LinkedList<>();
        qu.offer(A);
        dist[A] = 0;
        st[A] = true;
        while (!qu.isEmpty()) {
            int cur = qu.poll();
            for (int i = h[cur]; i != -1; i = ne[i]) {
                int t = e[i];
                if (!st[t]) {
                    st[t] = true;
                    qu.offer(t);
                    dist[t] = dist[cur] + 1;
                }
            }
        }
        return dist;
    }


    private void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    private Set<Integer> getSet(int[][] edges) {
        int n = edges.length;
        int[] degrees = new int[n + 1];

        for (int[] e : edges) {
            int a, b;
            a = e[0];
            b = e[1];
            degrees[a]++;
            degrees[b]++;
            add(a, b);
            add(b, a);
        }

        for (int i = 1; i <= n; i++) if (degrees[i] == 1) ls.add(i);
        Set<Integer> set = new HashSet<>();
        boolean[] st = new boolean[n + 1];
        Queue<Integer> qu = new LinkedList<>();
        for (int e : ls) {
            qu.offer(e);
            st[e] = true;
        }
        while (!qu.isEmpty()) {
            int cur = qu.poll();
            degrees[cur]--;
            for (int i = h[cur]; i != -1; i = ne[i]) {
                int t = e[i];
                degrees[t]--;
                if (!st[t] && degrees[t] == 1) {
                    qu.offer(t);
                    st[t] = true;
                }
            }
        }
        for (int i = 1; i <= n; i++) if (degrees[i] == 2) set.add(i);
        return set;
    }


}
