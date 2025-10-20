package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class MergeTrees {

    // https://leetcode.cn/problems/merge-two-binary-trees/solutions/2387255/kan-dao-di-gui-jiu-yun-dai-ni-li-jie-di-leixm/
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;
        return new TreeNode(root1.val + root2.val,
                mergeTrees(root1.left, root2.left),    // 合並左子樹
                mergeTrees(root1.right, root2.right)); // 合並右子樹
    }

    public TreeNode mergeTrees2(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;
        root1.val += root2.val;
        root1.left = mergeTrees2(root1.left, root2.left);    // 合並左子樹
        root1.right = mergeTrees2(root1.right, root2.right); // 合並右子樹
        return root1;
    }

}
