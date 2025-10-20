package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

public class SearchBST {

    // https://leetcode.cn/problems/search-in-a-binary-search-tree/solutions/2899483/javapython3cer-fen-cha-zhao-dfser-cha-sh-d0d3/
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val)
            return root;                      // 空節點返回null 或 找到節點值等於val的節點
        if (root.val < val)
            return searchBST(root.right, val);  // 當前節點值過小，往值更大的方向查找
        return searchBST(root.left, val);      // 當前節點值過大，往值更小的方向查找
    }

}
