package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class FlipEquiv {

    // https://leetcode.cn/problems/flip-equivalent-binary-trees/solutions/2890601/ling-shen-ke-hou-ti-951-fan-zhuan-deng-j-5xwi/
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        return dfs(root1, root2);
    }

    private boolean dfs(TreeNode p, TreeNode q) {
        if (p == null || q == null)
            return p == q;
        if (p.val != q.val)
            return false;
        boolean forward = dfs(p.left, q.left) && dfs(p.right, q.right);
        boolean reverse = dfs(p.left, q.right) && dfs(p.right, q.left);
        return forward || reverse;
    }
}
