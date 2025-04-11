package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

public class minDepth {

    // https://leetcode.cn/problems/minimum-depth-of-binary-tree/solutions/2730984/liang-chong-fang-fa-zi-ding-xiang-xia-zi-0sxz/
    private int ans = Integer.MAX_VALUE;

    public int minDepth(TreeNode root) {
        dfs(root, 0);
        return root != null ? ans : 0;
    }

    private void dfs(TreeNode node, int cnt) {
        if (node == null) {
            return;
        }
        cnt++;
        if (node.left == node.right) { // node 是葉子
            ans = Math.min(ans, cnt);
            return;
        }
        dfs(node.left, cnt);
        dfs(node.right, cnt);
    }


    public int minDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.right == null) {
            return minDepth2(root.left) + 1;
        }
        if (root.left == null) {
            return minDepth2(root.right) + 1;
        }
        return Math.min(minDepth2(root.left), minDepth2(root.right)) + 1;
    }

}
