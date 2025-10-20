package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

public class DistributeCoins {

    // https://leetcode.cn/problems/distribute-coins-in-binary-tree/solutions/2343262/tu-jie-mei-you-si-lu-jin-lai-miao-dong-p-vrni/
    private int ans;

    public int distributeCoins(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int[] dfs(TreeNode node) {
        if (node == null)
            return new int[]{0, 0};
        var left = dfs(node.left);
        var right = dfs(node.right);
        int coins = left[0] + right[0] + node.val; // 子樹硬幣個數
        int nodes = left[1] + right[1] + 1; // 子樹節點數
        ans += Math.abs(coins - nodes);
        return new int[]{coins, nodes};
    }

}
