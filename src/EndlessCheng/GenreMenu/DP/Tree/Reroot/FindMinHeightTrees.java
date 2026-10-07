package EndlessCheng.GenreMenu.DP.Tree.Reroot;

import java.util.ArrayList;
import java.util.List;

public class FindMinHeightTrees {

    // https://leetcode.cn/problems/minimum-height-trees/solutions/2692188/javapython3chuan-gen-dong-gui-dfsfu-jie-9x4zw/
    private int[] rootHeights;    // rootHeights[i]表示以節點i為根的樹的高度
    private int[] h;  // h[i]表示以節點i為根的子樹的高度
    List<Integer>[] neighbors;       // neighbors[i] 表示節點i的鄰接點列表
    int minHeight;      // 最小高度

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        // 生成鄰居節點矩陣
        neighbors = new ArrayList[n];
        for (int i = 0; i < n; i++) neighbors[i] = new ArrayList<>();
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            neighbors[x].add(y);
            neighbors[y].add(x);
        }
        // 生成每個節點為根的子樹的高度
        h = new int[n];
        dfs_zeroRoot(0, -1);
        // 生成每個節點為根的樹的高度
        rootHeights = new int[n];
        minHeight = n;
        dfs_changeRoot(0, -1);
        // 找到高度為最小高度的根節點
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (rootHeights[i] == minHeight) res.add(i);
        }
        return res;
    }

    /**
     * 以節點0為根進行DFS，生成節點node為根的子樹的高度
     */
    private int dfs_zeroRoot(int node, int pa) {
        int height = 1;     // 初始高度為1，表示節點本身
        for (int child : neighbors[node]) {
            if (child == pa) continue;    // 跳過父節點
            height = Math.max(height, dfs_zeroRoot(child, node) + 1);     // 以節點為根的子樹高度 = 所有子節點子樹的最大高度 + 1
        }
        h[node] = height;   // 記錄這個值
        return height;
    }

    /**
     * 換根動規DFS，生成以節點node為根的樹的高度
     */
    private void dfs_changeRoot(int node, int pa) {
        // 統計當前節點node的所有子樹的最大高度和次大高度
        int maxH = 0;
        int secondMaxH = 0;
        for (int child : neighbors[node]) {
            if (h[child] > maxH) {
                secondMaxH = maxH;
                maxH = h[child];
            } else if (h[child] > secondMaxH) {
                secondMaxH = h[child];
            }
        }
        rootHeights[node] = maxH + 1;   // 根節點node的高度就等於最大子樹高度 + 1
        minHeight = Math.min(minHeight, rootHeights[node]);      // 更新最小高度
        // 換根，換到子節點為根的樹
        for (int child : neighbors[node]) {
            if (child == pa) continue;
            h[node] = (h[child] == maxH ? secondMaxH : maxH) + 1;   // 換根後，當前節點變成子節點的子節點，更新其為根的子樹高度
            dfs_changeRoot(child, node);
        }
    }

}
