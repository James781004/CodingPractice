package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

public class MaxSumBST {

    // https://leetcode.cn/problems/maximum-sum-bst-in-binary-tree/solutions/2276783/hou-xu-bian-li-pythonjavacgo-by-endlessc-gll3/
    private int ans; // 二叉搜索樹可以為空

    public int maxSumBST(TreeNode root) {
        dfs(root);
        return ans;
    }

    // 每棵子樹返回:
    // 這棵子樹的最小節點值。
    // 這棵子樹的最大節點值。
    // 這棵子樹的所有節點值之和。
    private int[] dfs(TreeNode node) {
        if (node == null)
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0};

        int[] left = dfs(node.left); // 遞歸左子樹
        int[] right = dfs(node.right); // 遞歸右子樹
        int x = node.val;

        // 如果左子樹的最大值 ≥ 當前節點值，或者右子樹的最小值 ≤ 當前節點值，都不符合二叉搜索樹的定義
        if (x <= left[1] || x >= right[0])
            return new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0};

        int s = left[2] + right[2] + x; // 這棵子樹的所有節點值之和
        ans = Math.max(ans, s);

        return new int[]{Math.min(left[0], x), Math.max(right[1], x), s};
    }


}
