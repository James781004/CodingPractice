package EndlessCheng.GenreMenu.Backtracking.NormalTree.BottomUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindSubtreeSizes {

    // https://leetcode.cn/problems/find-subtree-sizes-after-changes/solutions/2966800/liang-ci-dfszi-ding-xiang-xia-zi-di-xian-k4zj/
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            g[parent[i]].add(i);
        }

        int[] size = new int[n];
        int[] ancestor = new int[26];
        Arrays.fill(ancestor, -1);
        dfs(0, g, s.toCharArray(), ancestor, size);
        return size;
    }

    private void dfs(int x, List<Integer>[] g, char[] s, int[] ancestor, int[] size) {
        size[x] = 1;
        int sx = s[x] - 'a';
        int old = ancestor[sx];
        ancestor[sx] = x; // 遞歸到節點 x 時，更新 ancestor[s[x]]=x
        for (int y : g[x]) {
            dfs(y, g, s, ancestor, size);
            int anc = ancestor[s[y] - 'a'];

            // 如果 y 沒有對應的祖先，把 size[x] 增加 size[y]。
            // 如果 y 有對應的祖先 anc，把 size[anc] 增加 size[y]。
            size[anc < 0 ? x : anc] += size[y];
        }
        ancestor[sx] = old; // 恢復現場
    }


}
