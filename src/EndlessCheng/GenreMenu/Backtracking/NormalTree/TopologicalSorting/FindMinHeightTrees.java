package EndlessCheng.GenreMenu.Backtracking.NormalTree.TopologicalSorting;

import java.util.*;

public class FindMinHeightTrees {

    // https://leetcode.cn/problems/minimum-height-trees/solutions/2691752/python3javacgotypescript-yi-ti-yi-jie-tu-4aet/
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return List.of(0);
        }
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, k -> new ArrayList<>());
        int[] degree = new int[n];
        for (int[] e : edges) {
            int a = e[0], b = e[1];
            g[a].add(b);
            g[b].add(a);
            ++degree[a];
            ++degree[b];
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; ++i) {
            if (degree[i] == 1) {
                q.offer(i);
            }
        }

        // 利用拓撲排序，從外向內剝離葉子節點
        List<Integer> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            ans.clear();
            for (int i = q.size(); i > 0; --i) {
                int a = q.poll();
                ans.add(a);
                for (int b : g[a]) {
                    if (--degree[b] == 1) {
                        q.offer(b);
                    }
                }
            }
        }

        // 到達最後一層的時候，剩下的節點就可作為最小高度樹的根節點
        return ans;
    }


}
