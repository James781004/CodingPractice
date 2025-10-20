package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class GetTargetCopy {

    // https://leetcode.cn/problems/find-a-corresponding-node-of-a-binary-tree-in-a-clone-of-that-tree/solutions/2721724/jian-ji-xie-fa-pythonjavacjs-by-endlessc-3vri/
    public TreeNode getTargetCopy(TreeNode original, TreeNode cloned, TreeNode target) {
        if (original == null || original == target) {
            return cloned;
        }
        TreeNode leftRes = getTargetCopy(original.left, cloned.left, target);
        if (leftRes != null) {
            return leftRes; // 已經找到 target，無需遞歸右子樹
        }
        return getTargetCopy(original.right, cloned.right, target);
    }

}
