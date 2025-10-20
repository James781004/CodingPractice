package EndlessCheng.GenreMenu.Backtracking.NormalTree.LCA;

public class TreeAncestor {

    // https://leetcode.cn/problems/kth-ancestor-of-a-tree-node/solutions/2305895/mo-ban-jiang-jie-shu-shang-bei-zeng-suan-v3rw/
    private final int[][] pa;

    public TreeAncestor(int n, int[] parent) {
        int m = 32 - Integer.numberOfLeadingZeros(n); // n 的二進制長度
        pa = new int[n][m];
        for (int i = 0; i < n; i++) {
            pa[i][0] = parent[i]; // 父節點
        }
        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                pa[x][i + 1] = p < 0 ? -1 : pa[p][i];
            }
        }
    }

    public int getKthAncestor(int node, int k) {
        int m = 32 - Integer.numberOfLeadingZeros(k); // k 的二進制長度
        for (int i = 0; i < m; i++) {
            if ((k >> i & 1) > 0) { // k 的二進制從低到高第 i 位是 1
                node = pa[node][i];
                if (node < 0) break;
            }
        }
        return node;
    }

    // 另一種寫法，不斷去掉 k 的最低位的 1
    public int getKthAncestor2(int node, int k) {
        for (; k > 0 && node != -1; k &= k - 1) {
            node = pa[node][Integer.numberOfTrailingZeros(k)];
        }
        return node;
    }


}
