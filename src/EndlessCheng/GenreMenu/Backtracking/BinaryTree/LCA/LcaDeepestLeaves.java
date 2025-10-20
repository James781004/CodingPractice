package EndlessCheng.GenreMenu.Backtracking.BinaryTree.LCA;

import EndlessCheng.TreeNode;

public class LcaDeepestLeaves {

    // https://leetcode.cn/problems/lowest-common-ancestor-of-deepest-leaves/solutions/2428724/liang-chong-di-gui-si-lu-pythonjavacgojs-xxnk/
    private TreeNode ans;
    private int maxDepth = -1; // 全局最大深度

    public TreeNode lcaDeepestLeaves(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    private int dfs(TreeNode node, int depth) {
        if (node == null) {
            maxDepth = Math.max(maxDepth, depth); // 維護全局最大深度
            return depth;
        }
        int leftMaxDepth = dfs(node.left, depth + 1); // 左子樹最深空節點的深度
        int rightMaxDepth = dfs(node.right, depth + 1); // 右子樹最深空節點的深度
        if (leftMaxDepth == rightMaxDepth && leftMaxDepth == maxDepth) { // 最深的空節點左右子樹都有
            ans = node;
        }
        return Math.max(leftMaxDepth, rightMaxDepth); // 當前子樹最深空節點的深度
    }

}
