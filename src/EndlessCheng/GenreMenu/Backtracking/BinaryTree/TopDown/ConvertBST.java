package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class ConvertBST {

    // https://leetcode.cn/problems/convert-bst-to-greater-tree/solutions/2552803/jian-ji-xie-fa-li-yong-er-cha-sou-suo-sh-lz3i/
    private int s = 0;

    public TreeNode convertBST(TreeNode root) {
        dfs(root);
        return root;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.right); // 遞歸右子樹
        s += node.val;
        node.val = s; // 此時 s 就是 >= node.val 的所有數之和
        dfs(node.left); // 遞歸左子樹
    }

}
