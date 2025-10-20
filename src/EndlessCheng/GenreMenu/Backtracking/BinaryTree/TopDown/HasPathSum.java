package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class HasPathSum {

    // https://leetcode.cn/problems/path-sum/solutions/2731531/jian-ji-xie-fa-pythonjavacgojsrust-by-en-icwe/
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        targetSum -= root.val;

        // 力扣創建數據的時候，每個節點都是 new 出來的，
        // 用 == 比較的時候，比的是內存地址，不是值
        if (root.left == root.right) { // root 是葉子
            return targetSum == 0;
        }
        return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
    }

}
