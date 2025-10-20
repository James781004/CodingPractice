package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class SubtreeWithAllDeepest {

    // https://leetcode.cn/problems/smallest-subtree-with-all-the-deepest-nodes/solutions/2428730/liang-chong-on-di-gui-xie-fa-pythonjavac-hac3/
    private TreeNode ans;
    private int maxDepth = -1; // 全局最大深度

    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    private int dfs(TreeNode node, int depth) {
        if (node == null) {
            maxDepth = Math.max(maxDepth, depth); // 維護全局最大深度
            return depth;
        }
        int leftMaxDepth = dfs(node.left, depth + 1); // 獲取左子樹最深葉節點的深度
        int rightMaxDepth = dfs(node.right, depth + 1); // 獲取右子樹最深葉節點的深度
        if (leftMaxDepth == rightMaxDepth && leftMaxDepth == maxDepth)
            ans = node;
        return Math.max(leftMaxDepth, rightMaxDepth); // 當前子樹最深葉節點的深度
    }

}
