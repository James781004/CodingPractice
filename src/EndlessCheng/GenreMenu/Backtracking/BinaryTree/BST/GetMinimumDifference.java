package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

public class GetMinimumDifference {

    // https://leetcode.cn/problems/minimum-absolute-difference-in-bst/solutions/3038197/zhong-xu-bian-li-jian-ji-xie-fa-pythonja-3pwf/
    private int ans = Integer.MAX_VALUE;
    private int pre = Integer.MIN_VALUE / 2; // 防止減法溢出

    public int getMinimumDifference(TreeNode root) {
        dfs(root);
        return ans;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        ans = Math.min(ans, node.val - pre);
        pre = node.val;
        dfs(node.right);
    }

}
