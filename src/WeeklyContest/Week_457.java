package WeeklyContest;

import java.util.*;

public class Week_457 {

    // https://leetcode.cn/problems/coupon-code-validator/solutions/3716401/mo-ni-pythonjavacgo-by-endlesscheng-xqv4/
    private static final Map<String, Integer> BUSINESS_LINE_TO_CATEGORY = Map.of(
            "electronics", 0,
            "grocery", 1,
            "pharmacy", 2,
            "restaurant", 3
    );

    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        List<String>[] groups = new ArrayList[4];
        Arrays.setAll(groups, i -> new ArrayList<>());
        for (int i = 0; i < code.length; i++) {
            String s = code[i];
            Integer category = BUSINESS_LINE_TO_CATEGORY.get(businessLine[i]);
            if (!s.isEmpty() && category != null && isActive[i] && isValid(s)) {
                groups[category].add(s);
            }
        }

        List<String> ans = new ArrayList<>();
        for (List<String> g : groups) {
            Collections.sort(g);
            ans.addAll(g);
        }
        return ans;
    }

    // 判斷是否只包含下劃線或字母數字
    private boolean isValid(String s) {
        for (char c : s.toCharArray()) {
            if (c != '_' && !Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/power-grid-maintenance/solutions/3716402/dfs-lan-shan-chu-dui-pythonjavacgo-by-en-17gb/
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        List<Integer>[] g = new ArrayList[c + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : connections) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        int[] belong = new int[c + 1];
        Arrays.fill(belong, -1);
        List<PriorityQueue<Integer>> heaps = new ArrayList<>();
        PriorityQueue<Integer> pq;
        for (int i = 1; i <= c; i++) {
            if (belong[i] >= 0) {
                continue;
            }
            pq = new PriorityQueue<>();
            dfs(i, g, belong, heaps.size(), pq);
            heaps.add(pq);
        }

        int ansSize = 0;
        for (int[] q : queries) {
            if (q[0] == 1) {
                ansSize++;
            }
        }

        int[] ans = new int[ansSize];
        int idx = 0;
        boolean[] offline = new boolean[c + 1];
        for (int[] q : queries) {
            int x = q[1];
            if (q[0] == 2) {
                offline[x] = true;
                continue;
            }
            if (!offline[x]) {
                ans[idx++] = x;
                continue;
            }
            pq = heaps.get(belong[x]);
            // 懶刪除：取堆頂的時候，如果離線，才刪除
            while (!pq.isEmpty() && offline[pq.peek()]) {
                pq.poll();
            }
            ans[idx++] = pq.isEmpty() ? -1 : pq.peek();
        }
        return ans;
    }

    private void dfs(int x, List<Integer>[] g, int[] belong, int compId, PriorityQueue<Integer> pq) {
        belong[x] = compId; // 記錄節點 x 在哪個堆
        pq.offer(x);
        for (int y : g[x]) {
            if (belong[y] < 0) {
                dfs(y, g, belong, compId, pq);
            }
        }
    }


    // https://leetcode.cn/problems/minimum-time-for-k-connected-components/solutions/3716407/bing-cha-ji-cong-da-dao-xiao-he-bing-pyt-03qz/
    public int minTime(int n, int[][] edges, int k) {
        Arrays.sort(edges, (a, b) -> b[2] - a[2]); // 按照 time 降序排列

        UnionFind u = new UnionFind(n);
        for (int[] e : edges) {
            u.merge(e[0], e[1]);
            if (u.cc < k) { // 這條邊不能留，即移除所有 time <= e[2] 的邊
                return e[2];
            }
        }
        return 0; // 無需移除任何邊
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


    // https://leetcode.cn/problems/minimum-moves-to-reach-target-in-grid/solutions/3716440/ni-xiang-si-wei-fen-lei-tao-lun-yan-ge-z-m5cc/
    public int minMoves(int sx, int sy, int x, int y) {
        int ans = 0;
        for (; x != sx || y != sy; ans++) {
            if (x < sx || y < sy) {
                return -1;
            }
            if (x == y) {
                if (sy > 0) {
                    x = 0;
                } else {
                    y = 0;
                }
                continue;
            }
            // 保證 x > y
            if (x < y) {
                int tmp = x;
                x = y;
                y = tmp;

                tmp = sx;
                sx = sy;
                sy = tmp;
            }
            if (x > y * 2) {
                if (x % 2 > 0) {
                    return -1;
                }
                x /= 2;
            } else {
                x -= y;
            }
        }
        return ans;
    }


}









