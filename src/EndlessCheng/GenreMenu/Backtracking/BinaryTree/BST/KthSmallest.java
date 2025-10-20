package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

public class KthSmallest {

    // https://leetcode.cn/problems/kth-smallest-element-in-a-bst/solutions/2952810/zhong-xu-bian-li-pythonjavaccgojsrust-by-wc02/
    private int ans;
    private int k;

    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        dfs(root);
        return ans;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left); // 左
        if (--k == 0) {
            ans = node.val; // 根
            return;
        }
        dfs(node.right); // 右
    }


}
