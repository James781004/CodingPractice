package EndlessCheng.GenreMenu.Backtracking.NormalTree.SlidingWindow;

import java.util.*;

public class LongestSpecialPathII {

    // https://leetcode.cn/problems/longest-special-path-ii/solutions/3613693/shu-shang-hua-chuang-zhi-xu-zai-3425-de-xqm73/
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
        dfs(0, -1, 0, 0, g, nums, dis, lastDepth);
        return new int[]{maxLen, minNodes};
    }

    private void dfs(int x, int fa, int topDepth, int last1, List<int[]>[] g, int[] nums, List<Integer> dis, Map<Integer, Integer> lastDepth) {
        int color = nums[x];
        int last2 = lastDepth.getOrDefault(color, 0);
        // 相較 3425 題，維護窗口左端點 topDepth 的邏輯變了
        topDepth = Math.max(topDepth, Math.min(last1, last2));

        int disX = dis.get(dis.size() - 1);
        int len = disX - dis.get(topDepth);
        int nodes = dis.size() - topDepth;
        if (len > maxLen || len == maxLen && nodes < minNodes) {
            maxLen = len;
            minNodes = nodes;
        }

        lastDepth.put(color, dis.size());
        for (int[] e : g[x]) {
            int y = e[0];
            if (y != fa) {
                dis.add(disX + e[1]);
                // 相較 3425 題，額外維護 last1
                dfs(y, x, topDepth, Math.max(last1, last2), g, nums, dis, lastDepth);
                dis.remove(dis.size() - 1);
            }
        }
        lastDepth.put(color, last2);
    }


}
