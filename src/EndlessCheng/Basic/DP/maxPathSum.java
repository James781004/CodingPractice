package EndlessCheng.Basic.DP;

import EndlessCheng.TreeNode;

public class maxPathSum {

    // https://leetcode.cn/problems/binary-tree-maximum-path-sum/solutions/2227021/shi-pin-che-di-zhang-wo-zhi-jing-dpcong-n9s91/
    private int ans = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int dfs(TreeNode node) {
        if (node == null) {
            return 0; // 沒有節點，和為 0
        }
        int lVal = dfs(node.left); // 左子樹最大鏈和
        int rVal = dfs(node.right); // 右子樹最大鏈和
        ans = Math.max(ans, lVal + rVal + node.val); // 兩條鏈拼成路徑
        return Math.max(Math.max(lVal, rVal) + node.val, 0); // 當前子樹最大鏈和
    }
}
