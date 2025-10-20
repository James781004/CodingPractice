package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class RemoveLeafNodes {

    // https://leetcode.cn/problems/delete-leaves-with-a-given-value/solutions/1342336/yi-zhao-by-xi-li-niu-kou-t8ps/
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        // 遞歸終止條件
        if (root == null) {
            return null;
        }
        // 每次遞歸的任務
        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);
        // 遞歸返回值
        if (root.left == null && root.right == null && root.val == target) {
            return null;
        }
        return root;
    }

}
