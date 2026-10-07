package EndlessCheng.GenreMenu.DP.Tree.Diameter;

import EndlessCheng.GenreMenu.DP.Tree.TreeNode;

public class DiameterOfBinaryTree {

    // https://leetcode.cn/problems/diameter-of-binary-tree/solutions/2227017/shi-pin-che-di-zhang-wo-zhi-jing-dpcong-taqma/
    private int ans;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return ans;
    }

    // 返回 node 子樹的最大鏈長
    private int dfs(TreeNode node) {
        if (node == null) {
            return -1; // 對於葉子來說，鏈長就是 -1+1=0
        }
        int lLen = dfs(node.left) + 1; // 左子樹最大鏈長+1
        int rLen = dfs(node.right) + 1; // 右子樹最大鏈長+1
        ans = Math.max(ans, lLen + rLen); // 兩條鏈拼成路徑
        return Math.max(lLen, rLen); // 當前子樹最大鏈長
    }


}
