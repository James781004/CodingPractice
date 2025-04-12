package EndlessCheng.GenreMenu.Graph.Pseudotree;

import java.util.*;

public class MaximumInvitations {

    // https://leetcode.cn/problems/maximum-employees-to-be-invited-to-a-meeting/solutions/1187830/nei-xiang-ji-huan-shu-tuo-bu-pai-xu-fen-c1i1b/
    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] deg = new int[n];
        for (int f : favorite) {
            deg[f]++; // 統計基環樹每個節點的入度
        }

        List<Integer>[] rg = new List[n]; // 反圖
        Arrays.setAll(rg, e -> new ArrayList<>());
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (deg[i] == 0) {
                q.add(i);
            }
        }
        while (!q.isEmpty()) { // 拓撲排序，剪掉圖上所有樹枝
            int x = q.poll();
            int y = favorite[x]; // x 只有一條出邊
            rg[y].add(x);
            if (--deg[y] == 0) {
                q.add(y);
            }
        }

        int maxRingSize = 0, sumChainSize = 0;
        for (int i = 0; i < n; i++) {
            if (deg[i] == 0) continue;

            // 遍歷基環上的點
            deg[i] = 0; // 將基環上的點的入度標記為 0，避免重復訪問
            int ringSize = 1; // 基環長度
            for (int x = favorite[i]; x != i; x = favorite[x]) {
                deg[x] = 0; // 將基環上的點的入度標記為 0，避免重復訪問
                ringSize++;
            }

            if (ringSize == 2) { // 基環長度為 2
                sumChainSize += rdfs(i, rg) + rdfs(favorite[i], rg); // 累加兩條最長鏈的長度
            } else {
                maxRingSize = Math.max(maxRingSize, ringSize); // 取所有基環長度的最大值
            }
        }
        return Math.max(maxRingSize, sumChainSize);
    }

    // 通過反圖 rg 尋找樹枝上最深的鏈
    private int rdfs(int x, List<Integer>[] rg) {
        int maxDepth = 1;
        for (int son : rg[x]) {
            maxDepth = Math.max(maxDepth, rdfs(son, rg) + 1);
        }
        return maxDepth;
    }


}
