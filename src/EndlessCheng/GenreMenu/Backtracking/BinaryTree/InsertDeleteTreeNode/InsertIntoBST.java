package EndlessCheng.GenreMenu.Backtracking.BinaryTree.InsertDeleteTreeNode;

import EndlessCheng.TreeNode;

public class InsertIntoBST {

    // https://leetcode.cn/problems/insert-into-a-binary-search-tree/solutions/2028240/by-carlsun-2-8a9r/
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // 找到空位置插入新節點
        if (root == null) return new TreeNode(val);
        // if (root.val == val)
        //     BST 中一般不會插入已存在元素
        if (root.val < val)
            root.right = insertIntoBST(root.right, val);
        if (root.val > val)
            root.left = insertIntoBST(root.left, val);
        return root;
    }
}
