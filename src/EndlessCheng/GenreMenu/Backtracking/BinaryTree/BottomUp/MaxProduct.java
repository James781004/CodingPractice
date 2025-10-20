package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class MaxProduct {

    // https://leetcode.cn/problems/maximum-product-of-splitted-binary-tree/solutions/84161/java-jian-dan-yi-dong-de-dai-ma-by-william-43/
    double ans = Double.MIN_VALUE;
    double allSum;
    double nodeSum;

    public int maxProduct(TreeNode root) {
        allSum = sum(root);
        dfs(root);
        return (int) (ans % (int) (1e9 + 7));
    }

    // 求整個二叉樹的和
    public double sum(TreeNode node) {
        if (node == null) return 0;
        return node.val + sum(node.left) + sum(node.right);
    }

    // 遍歷所有子樹和，求最大值
    public double dfs(TreeNode node) {
        if (node == null) return 0;
        nodeSum = node.val + dfs(node.left) + dfs(node.right);
        ans = Math.max(ans, (allSum - nodeSum) * nodeSum);
        return nodeSum;
    }

}
