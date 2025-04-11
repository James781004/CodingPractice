package EndlessCheng.Basic.DP;

import EndlessCheng.TreeNode;

public class longestUnivaluePath {

    // https://leetcode.cn/problems/longest-univalue-path/solutions/2227160/shi-pin-che-di-zhang-wo-zhi-jing-dpcong-524j4/


    private int ans;

    public int longestUnivaluePath(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int dfs(TreeNode node) {
        if (node == null)
            return -1; // 下面 +1 後，對於葉子節點就剛好是 0
        int lLen = dfs(node.left) + 1; // 左子樹最大鏈長+1
        int rLen = dfs(node.right) + 1; // 右子樹最大鏈長+1
        if (node.left != null && node.left.val != node.val) lLen = 0; // 鏈長視作 0
        if (node.right != null && node.right.val != node.val) rLen = 0; // 鏈長視作 0
        ans = Math.max(ans, lLen + rLen); // 兩條鏈拼成路徑
        return Math.max(lLen, rLen); // 當前子樹最大鏈和
    }
}

