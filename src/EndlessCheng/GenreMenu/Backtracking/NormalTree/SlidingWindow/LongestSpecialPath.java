package EndlessCheng.GenreMenu.Backtracking.NormalTree.SlidingWindow;

import java.util.*;

public class LongestSpecialPath {

    // https://leetcode.cn/problems/longest-special-path/solutions/3051377/shu-shang-hua-chuang-pythonjavacgo-by-en-rh5m/
    private int maxLen = -1;
    private int minNodes = 0;

    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        List<int[]>[] g = new ArrayList[nums.length];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            int w = e[2];
            g[x].add(new int[]{y, w});
            g[y].add(new int[]{x, w});
        }

        List<Integer> dis = new ArrayList<>();
        dis.add(0);
        // 顏色 -> 該顏色最近一次出現的深度 +1，注意這裡已經 +1 了
        Map<Integer, Integer> lastDepth = new HashMap<>();
        dfs(0, -1, 0, g, nums, dis, lastDepth);
        return new int[]{maxLen, minNodes};
    }

    private void dfs(int x, int fa, int topDepth, List<int[]>[] g, int[] nums, List<Integer> dis, Map<Integer, Integer> lastDepth) {
        int color = nums[x];
        int oldDepth = lastDepth.getOrDefault(color, 0);
        topDepth = Math.max(topDepth, oldDepth);

        int disX = dis.get(dis.size() - 1);
        int len = disX - dis.get(topDepth); // 路徑長度：根到當前節點的距離，減去根到路徑最上面節點的距離
        int nodes = dis.size() - topDepth; // 路徑節點個數：當前節點的深度加一，減去 topDepth。前者是當前 dis 的大小
        if (len > maxLen || len == maxLen && nodes < minNodes) {
            maxLen = len;
            minNodes = nodes;
        }

        lastDepth.put(color, dis.size());
        for (int[] e : g[x]) {
            int y = e[0];
            if (y != fa) { // 避免訪問父節點
                dis.add(disX + e[1]);
                dfs(y, x, topDepth, g, nums, dis, lastDepth);
                dis.remove(dis.size() - 1); // 恢復現場
            }
        }
        lastDepth.put(color, oldDepth); // 恢復現場
    }


}
