package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class Tree2str {

    // https://leetcode.cn/problems/construct-string-from-binary-tree/solutions/1349148/by-tong-zhu-g5au/
    public String tree2str(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb);
        return sb.toString();
    }

    private void dfs(TreeNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }

        // 當前值
        sb.append(node.val);

        // 左右子節點都為空，直接返回
        if (node.left == null && node.right == null) {
            return;
        }

        
        // 只要有右子節點，左子節點都需要處理
        sb.append("(");
        dfs(node.left, sb);
        sb.append(")");

        // 右子節點不為空的時候才需要處理
        if (node.right != null) {
            sb.append("(");
            dfs(node.right, sb);
            sb.append(")");
        }
    }

}
