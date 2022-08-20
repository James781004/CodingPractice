package LeetcodeMaster.BinaryTree;

import java.util.Stack;

public class Q16_MergeTree {
//    617. 合併二叉樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0617.%E5%90%88%E5%B9%B6%E4%BA%8C%E5%8F%89%E6%A0%91.md
//
//    給定兩個二叉樹，想象當你將它們中的一個覆蓋到另一個上時，兩個二叉樹的一些節點便會重疊。
//
//    你需要將他們合併為一個新的二叉樹。合並的規則是如果兩個節點重疊，那麽將他們的值相加作為節點合並後的新值，
//    否則不為 NULL 的節點將直接作為新二叉樹的節點。


    // 遞歸
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;

        // 重新定義新的節點，不修改原有兩個樹的結構
        TreeNode root = new TreeNode(0);
        root.value = root1.value + root2.value;

        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root;
    }

    // 使用棧迭代
    public TreeNode mergeTreeStack(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root2);
        stack.push(root1);
        while (!stack.isEmpty()) {
            TreeNode node1 = stack.pop();
            TreeNode node2 = stack.pop();
            node1.value += node2.value;

            // 先壓右節點
            if (node2.right != null && node1.right != null) {
                stack.push(node2.right);
                stack.push(node1.right);
            } else {
                if (node1.right == null) {
                    node1.right = node2.right;
                }
            }

            // 後壓左節點
            if (node2.left != null && node1.left != null) {
                stack.push(node2.left);
                stack.push(node1.left);
            } else {
                if (node1.left == null) {
                    node1.left = node2.left;
                }
            }
        }
        return root1;
    }
}
