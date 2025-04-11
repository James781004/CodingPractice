package EndlessCheng.Basic.DP;

import EndlessCheng.TreeNode;

public class diameterOfBinaryTree {

    // https://leetcode.cn/problems/diameter-of-binary-tree/solutions/2227017/shi-pin-che-di-zhang-wo-zhi-jing-dpcong-taqma/
    private int ans;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int dfs(TreeNode node) {
        if (node == null)
            return -1; // 下面 +1 後，對於葉子節點就剛好是 0
        int lLen = dfs(node.left) + 1; // 左子樹最大鏈長+1
        int rLen = dfs(node.right) + 1; // 右子樹最大鏈長+1
        ans = Math.max(ans, lLen + rLen); // 兩條鏈拼成路徑
        return Math.max(lLen, rLen); // 當前子樹最大鏈長
    }

}
