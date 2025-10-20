package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class SufficientSubset {

    // https://leetcode.cn/problems/insufficient-nodes-in-root-to-leaf-paths/solutions/2278769/jian-ji-xie-fa-diao-yong-zi-shen-pythonj-64lf/
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        limit -= root.val;
        if (root.left == root.right) // root 是葉子
            // 如果 limit > 0 說明從根到葉子的路徑和小於 limit，刪除葉子，否則不刪除
            return limit > 0 ? null : root;
        if (root.left != null) root.left = sufficientSubset(root.left, limit);
        if (root.right != null) root.right = sufficientSubset(root.right, limit);
        // 如果兒子都被刪除，就刪 root，否則不刪 root
        return root.left == null && root.right == null ? null : root;
    }

}
