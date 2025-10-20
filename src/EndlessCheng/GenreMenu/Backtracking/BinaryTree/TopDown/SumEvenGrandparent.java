package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class SumEvenGrandparent {

    // https://leetcode.cn/problems/sum-of-nodes-with-even-valued-grandparent/solutions/282559/java-dfszui-jian-ji-xie-fa-by-yukong/
    int res;

    public int sumEvenGrandparent(TreeNode root) {

        res = 0;
        helper(null, null, root);
        return res;
    }

    private void helper(TreeNode grandP, TreeNode father, TreeNode node) {
        if (node == null) return;

        if (grandP != null && grandP.val % 2 == 0) {
            res += node.val;
        }

        helper(father, node, node.left);
        helper(father, node, node.right);
    }

}
