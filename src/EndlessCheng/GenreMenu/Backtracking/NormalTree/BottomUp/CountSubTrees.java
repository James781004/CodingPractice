package EndlessCheng.GenreMenu.Backtracking.NormalTree.BottomUp;

import java.util.ArrayList;
import java.util.List;

public class CountSubTrees {

    // https://leetcode.cn/problems/number-of-nodes-in-the-sub-tree-with-the-same-label/solutions/337258/leetcode-zi-shu-zhong-biao-qian-xiang-tong-de-jie-/
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        List<Integer>[] points = new List[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ArrayList<>();
        }
        for (int[] p : edges) {
            points[p[0]].add(p[1]);
            points[p[1]].add(p[0]);
        }

        int[] ls = new int[n];
        for (int i = 0; i < n; i++) {
            ls[i] = labels.charAt(i) - 'a';
        }

        res = new int[n];
        visited = new boolean[n];
        visited[0] = true;
        dfs(0, points, ls);
        return res;
    }

    int[] res;
    boolean[] visited;

    private int[] dfs(int i, List<Integer>[] points, int[] ls) {
        int[] curLs = new int[26];
        //添加自身節點
        curLs[ls[i]]++;
        for (int child : points[i]) {
            /*
                判斷是否已經遍歷過該節點，如果遍歷過，那麼跳過
                因為這是無向圖, 1 可以到 2，2 也可以到 1，因此，當 1 到 2 的時候，我們需要記錄 1 已經訪問
                這樣，從 2 出發，就不會再到 1 了
            */
            if (visited[child]) {
                continue;
            }
            visited[child] = true;
            int[] childLs = dfs(child, points, ls);
            for (int k = 0; k < 26; k++) {
                curLs[k] += childLs[k];
            }
        }
        res[i] = curLs[ls[i]];
        return curLs;
    }


}
