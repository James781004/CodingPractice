package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

public class CountNodes {

    // https://leetcode.cn/problems/count-complete-tree-nodes/solutions/1812445/by-carlsun-2-bwlp/

    /**
     * 針對完全二叉樹的解法
     * 滿二叉樹的結點數為：2^depth - 1
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = getDepth(root.left);
        int rightDepth = getDepth(root.right);
        if (leftDepth == rightDepth) {// 左子樹是滿二叉樹
            // 2^leftDepth其實是 （2^leftDepth - 1） + 1 ，左子樹 + 根結點
            return (1 << leftDepth) + countNodes(root.right);
        } else {// 右子樹是滿二叉樹
            return (1 << rightDepth) + countNodes(root.left);
        }
    }

    private int getDepth(TreeNode root) {
        int depth = 0;
        while (root != null) {
            root = root.left;
            depth++;
        }
        return depth;
    }


}
