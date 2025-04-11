package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

public class sumNumbers {

    // https://leetcode.cn/problems/sum-root-to-leaf-numbers/solutions/2730644/jian-ji-xie-fa-pythonjavacgojsrust-by-en-gbu9/
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode root, int x) {
        if (root == null) {
            return 0;
        }
        x = x * 10 + root.val;
        if (root.left == root.right) { // root 是葉子節點
            return x;
        }
        return dfs(root.left, x) + dfs(root.right, x);
    }

}
