package WeeklyContest;

import java.util.*;

public class Week_426 {

    // https://leetcode.cn/problems/smallest-number-with-all-set-bits/solutions/3006337/o1-zuo-fa-pythonjavacgo-by-endlesscheng-kbqc/
    public int smallestNumber(int n) {
        int m = 32 - Integer.numberOfLeadingZeros(n);
        return (1 << m) - 1;
    }


    // https://leetcode.cn/problems/identify-the-largest-outlier-in-an-array/solutions/3006328/mei-ju-yi-chang-zhi-pythonjavacgo-by-end-wb3g/
    public int getLargestOutlier(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int total = 0;
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum); // cnt[x]++
            total += x;
        }

        int ans = Integer.MIN_VALUE;
        for (int x : nums) {
            cnt.merge(x, -1, Integer::sum);
            if ((total - x) % 2 == 0 && cnt.getOrDefault((total - x) / 2, 0) > 0) {
                ans = Math.max(ans, x);
            }
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximize-the-number-of-target-nodes-after-connecting-trees-i/solutions/3006334/nao-jin-ji-zhuan-wan-bao-li-mei-ju-pytho-ua6k/
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int max2 = 0;
        if (k > 0) {
            List<Integer>[] g = buildTree(edges2);

            // 暴力枚舉第二棵樹的節點 i，用 DFS 計算距離 i 不超過 k−1 的節點個數 (這裡傳 k-1，因為新添加的邊也算在距離中)
            // 所有 cnt 取最大值，記作 max2。新添加的邊就連到 max2對應的節點上
            for (int i = 0; i < edges2.length + 1; i++) {
                max2 = Math.max(max2, dfs(i, -1, 0, g, k - 1)); // 注意這裡傳的是 k-1
            }
        }

        List<Integer>[] g = buildTree(edges1);
        int[] ans = new int[edges1.length + 1];

        // 暴力枚舉第一棵樹的節點 i，用 DFS 計算距離 i 不超過 k 的節點個數
        for (int i = 0; i < ans.length; i++) {
            ans[i] = dfs(i, -1, 0, g, k) + max2;
        }
        return ans;
    }

    private List<Integer>[] buildTree(int[][] edges) {
        List<Integer>[] g = new ArrayList[edges.length + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        return g;
    }

    private int dfs(int x, int fa, int d, List<Integer>[] g, int k) {
        if (d > k) {
            return 0;
        }
        int cnt = 1;
        for (int y : g[x]) {
            if (y != fa) {
                cnt += dfs(y, x, d + 1, g, k);
            }
        }
        return cnt;
    }


    // https://leetcode.cn/problems/maximize-the-number-of-target-nodes-after-connecting-trees-ii/solutions/3006331/an-qi-ou-fen-lei-pythonjavacgo-by-endles-dweg/
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {

        // 對於第二棵樹，把其中的節點分成兩個集合：
        // 集合 A：到節點 0 的距離是偶數的點。其大小記作 cnt2[0]。
        // 集合 B：到節點 0 的距離是奇數的點。其大小記作 cnt2[1]。
        List<Integer>[] g2 = buildTree(edges2);
        int[] cnt2 = new int[2];
        dfs(0, -1, 0, g2, cnt2);

        // 分類討論：
        // 如果 cnt2[0] > cnt2[1]，那麼第一棵樹的節點 i 應當連到集合 B 中的任意節點，
        // 這樣節點 i 在第二棵樹中的目標節點為 cnt2[0]
        // 否則，第一棵樹的節點 i 應當連到集合 A 中的任意節點，這樣節點 i 在第二棵樹中的目標節點為 cnt2[1]。
        // 所以節點 i 在第二棵樹中的目標節點，至多為 Math.max(cnt2[0], cnt2[1])
        int max2 = Math.max(cnt2[0], cnt2[1]);

        // 對於第一棵樹，把其中的節點分成兩個集合：
        // 集合 A：到節點 0 的距離是偶數的點。其大小記作 cnt2[0]。
        // 集合 B：到節點 0 的距離是奇數的點。其大小記作 cnt2[1]。
        List<Integer>[] g1 = buildTree(edges1);
        int[] cnt1 = new int[2];
        dfs(0, -1, 0, g1, cnt1);

        // 分類討論：
        // 如果節點 i 在集合 A 中，那麼它的目標節點也必然在集合 A 中。
        // 如果節點 i 在集合 B 中，那麼它的目標節點也必然在集合 B 中。
        // 所以 answer[i] 等於節點 i 所屬集合的大小，加上 max2
        int[] ans = new int[g1.length];
        Arrays.fill(ans, max2);
        dfs1(0, -1, 0, g1, cnt1, ans);
        return ans;
    }


    private void dfs(int x, int fa, int d, List<Integer>[] g, int[] cnt) {
        cnt[d]++;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x, d ^ 1, g, cnt); // d ^ 1 表示奇偶數切換
            }
        }
    }

    private void dfs1(int x, int fa, int d, List<Integer>[] g, int[] cnt1, int[] ans) {
        ans[x] += cnt1[d];
        for (int y : g[x]) {
            if (y != fa) {
                dfs1(y, x, d ^ 1, g, cnt1, ans); // d ^ 1 表示奇偶數切換
            }
        }
    }
}









