package Grind.Ch07_BinaryTree;

public class Q06_DiameterBinaryTree {
    // https://leetcode.cn/problems/diameter-of-binary-tree/solutions/141166/java-shen-du-you-xian-bian-li-dfs-by-sugar-31/
    // 最大值不一定包含根節點，但是一定是：經過一個節點，該節點左右子樹的最大深度之和 +1（二叉樹的根節點深度為 0）
    // 可以使用 DFS，找出所有節點的最大直徑，取出最大值 res

    int res = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return res;
    }

    // 函數dfs的作用是：找到以root為根節點的二叉樹的最大深度
    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = dfs(root.left);
        int rigthDepth = dfs(root.right);

        // root 為跟節點的最大深度為 Math.max(leftDepth,rigthDepth) + 1
        // res 取值為以經過 root，左右子樹的最大深度之和 leftDepth + rigthDepth （不用加 1，是因為根節點的深度是 1）
        res = Math.max(res, leftDepth + rigthDepth);

        // 只能返回較長的一邊加上根節點的 1 給上層
        return Math.max(leftDepth, rigthDepth) + 1;
    }

}
