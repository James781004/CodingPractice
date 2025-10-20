package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class CountPairs {

    // https://leetcode.cn/problems/number-of-good-leaf-nodes-pairs/solutions/2573863/java-dfs-qia-dang-zhu-shi-by-somnia1337-5xj7/
    private int d, ans = 0;

    public int countPairs(TreeNode root, int distance) {
        d = distance;
        dfs(root);
        return ans;
    }

    private int[] dfs(TreeNode node) {
        // count[i] 表示以 node 為根結點的子樹中到 node 距離為 i 的葉結點數量
        // count 開到 d+1, 因為考慮的最大距離為 d, 且沒有距離為 0 的葉結點
        int[] count = new int[d + 1];
        if (node == null) return count;
        if (node.left == null && node.right == null) {
            // 葉結點, 到 par 的距離為 1
            count[1] = 1;
            return count;
        }

        int[] L = dfs(node.left), R = dfs(node.right);
        // 累加左右子樹中到 node 的距離之和 <=d 的葉結點對
        for (int d1 = 1; d1 <= d; d1++) {
            for (int d2 = 1; d1 + d2 <= d; d2++) ans += L[d1] * R[d2];
        }

        // 根據左右子樹的 count 計算自身 count, 各葉結點深度 +1
        for (int i = 2; i <= d; i++) count[i] = L[i - 1] + R[i - 1];
        return count;
    }

}
