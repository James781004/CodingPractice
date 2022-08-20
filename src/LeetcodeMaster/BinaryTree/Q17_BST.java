package LeetcodeMaster.BinaryTree;

import java.util.Stack;

public class Q17_BST {
//    700.二叉搜索樹中的搜索
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0700.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E4%B8%AD%E7%9A%84%E6%90%9C%E7%B4%A2.md
//
//    給定二叉搜索樹（BST）的根節點和一個值。 你需要在BST中找到節點值等於給定值的節點。 
//    返回以該節點為根的子樹。 如果節點不存在，則返回 NULL。

    // 遞歸，普通二叉樹
    public TreeNode searchTree(TreeNode root, int val) {
        if (root == null || root.value == val) return root;

        TreeNode left = searchTree(root.left, val);
        if (left != null) return left;

        return searchTree(root.right, val);
    }

    // 迭代，普通二叉樹
    public TreeNode searchTree2(TreeNode root, int val) {
        if (root == null || root.value == val) return root;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.value == val) return node;
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        return null;
    }

    // 遞歸，利用二叉搜索樹特點，優化
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.value == val) return root;
        if (val < root.value) {
            return searchBST(root.left, val);
        } else {
            return searchBST(root.right, val);
        }
    }

    // 迭代，利用二叉搜索樹特點，優化，可以不需要棧
    public TreeNode searchBST2(TreeNode root, int val) {
        while (root != null) {
            if (val < root.value) {
                root = root.left;
            } else if (val > root.value) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }

}
