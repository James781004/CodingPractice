package Grind.Ch07_BinaryTree;

public class Q10_BinaryTreeMaximumPathSum {
    // https://leetcode.cn/problems/binary-tree-maximum-path-sum/solutions/962707/javadi-gui-zi-ding-xiang-xia-bang-zhu-ji-8bmv/
    // 樹形DP: https://www.bilibili.com/video/BV17o4y187h1/
    int pathSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        helper(root);
        return pathSum;
    }

    // dfs 返回以該節點為端點的最大路徑和
    public int helper(TreeNode node) {
        if (node == null) return 0;

        // 樹形DP: 向左右子樹要資料
        int left = helper(node.left);
        int right = helper(node.right);

        // 當前節點有四個選擇：
        // 1）獨立成線，直接返回自己的值
        // 2）跟左子節點合成一條路徑
        // 3）跟右子節點合成一條路徑
        int ret = Math.max(node.val, node.val + Math.max(left, right));

        // 4）以自己為橋梁，跟左、右子節點合並成一條路徑 (注意這是是記錄 path 用，不能返回給上層)
        // 因為它不符合我們對遞歸所期望返回值的定義（因為此時該子節點並不是擁有最大路徑和路徑的起點（端點））
        pathSum = Math.max(pathSum, Math.max(ret, node.val + left + right));

        // 返回以該節點為端點的最大路徑和給上層
        return ret;
    }

}
