package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

public class RangeSumBST {

    // https://leetcode.cn/problems/range-sum-of-bst/solutions/2653989/jian-ji-xie-fa-pythonjavacgojsrust-by-en-7jw4/
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        }
        int x = root.val;
        if (x > high) { // 右子樹沒有節點在范圍內，只需遞歸左子樹
            return rangeSumBST(root.left, low, high);
        }
        if (x < low) { // 左子樹沒有節點在范圍內，只需遞歸右子樹
            return rangeSumBST(root.right, low, high);
        }
        return x + rangeSumBST(root.left, low, high) +
                rangeSumBST(root.right, low, high);
    }

}
