package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class MaxAncestorDiff {

    // https://leetcode.cn/problems/maximum-difference-between-node-and-ancestor/solutions/2232367/liang-chong-fang-fa-zi-ding-xiang-xia-zi-wj9v/
    private int ans;

    public int maxAncestorDiff(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int[] dfs(TreeNode node) {
        if (node == null) { // 需要保證空節點不影響 mn 和 mx
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        }
        int[] p = dfs(node.left);
        int[] q = dfs(node.right);
        int mn = Math.min(node.val, Math.min(p[0], q[0]));
        int mx = Math.max(node.val, Math.max(p[1], q[1]));
        ans = Math.max(ans, Math.max(node.val - mn, mx - node.val));
        return new int[]{mn, mx};
    }


}
