package EndlessCheng.GenreMenu.Backtracking.NormalTree.TopologicalSorting;

import java.util.*;

public class CollectTheCoins {

    // https://leetcode.cn/problems/collect-coins-in-a-tree/solutions/2191371/tuo-bu-pai-xu-ji-lu-ru-dui-shi-jian-pyth-6uli/
    public int collectTheCoins(int[] coins, int[][] edges) {
        int n = coins.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        int[] deg = new int[n];
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
            deg[x]++;
            deg[y]++; // 統計每個節點的度數（鄰居個數）
        }

        int leftEdges = n - 1; // 剩余邊數
        // 拓撲排序，去掉沒有金幣的子樹
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (deg[i] == 1 && coins[i] == 0) { // 沒有金幣的葉子
                q.add(i);
            }
        }
        while (!q.isEmpty()) {
            leftEdges--; // 刪除節點到其父節點的邊
            for (int y : g[q.poll()]) {
                if (--deg[y] == 1 && coins[y] == 0) { // 沒有金幣的葉子
                    q.add(y);
                }
            }
        }

        // 再次拓撲排序
        for (int i = 0; i < n; i++) {
            // 有金幣的葉子（判斷 coins[i] 是避免把沒有金幣的葉子也算進來）
            if (deg[i] == 1 && coins[i] == 1) {
                q.add(i);
            }
        }
        leftEdges -= q.size(); // 刪除所有葉子（到其父節點的邊）
        for (int x : q) { // 遍歷所有葉子
            for (int y : g[x]) {
                if (--deg[y] == 1) { // y 現在是葉子了
                    leftEdges--; // 刪除 y（到其父節點的邊）
                }
            }
        }
        return Math.max(leftEdges * 2, 0);
    }


}
