package EndlessCheng.Basic.Graph;

public class TreeAncestor {

    // https://leetcode.cn/problems/kth-ancestor-of-a-tree-node/solutions/2305895/mo-ban-jiang-jie-shu-shang-bei-zeng-suan-v3rw/
    private int[][] pa;

    public TreeAncestor(int n, int[] parent) {
        int m = 32 - Integer.numberOfLeadingZeros(n); // n 的二進制長度
        pa = new int[n][m];
        for (int i = 0; i < n; i++)
            pa[i][0] = parent[i]; // pa[x][0]=parent[x]，即父節點

        // 先枚舉 i，再枚舉 x。相當於先算出所有爺爺節點，再算出所有爺爺節點的爺爺節點，依此類推
        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                // pa[x][i+1]=pa[pa[x][i]][i]，
                // 表示 x 的第 2^i 個祖先節點的第 2^i 個祖先節點就是 x 的第 2^(i+1) 個祖先節點。
                // 特別地，如果 pa[x][i]=−1 則 pa[x][i+1]=−1
                int p = pa[x][i];
                pa[x][i + 1] = p < 0 ? -1 : pa[p][i];
            }
        }
    }

    public int getKthAncestor(int node, int k) {
        int m = 32 - Integer.numberOfLeadingZeros(k); // k 的二進制長度
        for (int i = 0; i < m; i++) {
            if (((k >> i) & 1) > 0) { // k 的二進制從低到高第 i 位是 1
                node = pa[node][i]; // 將 node 更新為 pa[node][i]。如果 node=−1 則說明第 k 個祖先節點不存在
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
