package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

public class sufficientSubset {

    // https://leetcode.cn/problems/insufficient-nodes-in-root-to-leaf-paths/solutions/2278769/jian-ji-xie-fa-diao-yong-zi-shen-pythonj-64lf/
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        limit -= root.val;

        // 先判斷當前 root 是否「已經是」葉子 (注意：== 比較的是地址，不是節點值)
        // if (root.left == null && root.right == null) 也是可以通過的
        if (root.left == root.right) {
            // 如果 limit > 0 說明從根到葉子的路徑和小於 limit，刪除葉子，否則不刪除
            return limit > 0 ? null : root;
        }

        // 獲取左右子樹，注意獲取的流程可能造成左右子樹被刪除
        if (root.left != null) root.left = sufficientSubset(root.left, limit);
        if (root.right != null) root.right = sufficientSubset(root.right, limit);

        // 判斷當前 root 是否「變成」葉子，即左右子樹被上面流程刪除的狀況
        // 如果兒子都被刪除，就刪 root，否則不刪 root
        return root.left == null && root.right == null ? null : root;
    }

}
