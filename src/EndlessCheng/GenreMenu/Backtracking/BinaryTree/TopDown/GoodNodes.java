package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class GoodNodes {

    // https://leetcode.cn/problems/count-good-nodes-in-binary-tree/solutions/2403705/python3javacgotypescript-yi-ti-yi-jie-df-p1mz/
    private int ans = 0;

    public int goodNodes(TreeNode root) {
        dfs(root, -1000000);
        return ans;
    }

    private void dfs(TreeNode root, int mx) {
        if (root == null) {
            return;
        }
        if (mx <= root.val) {
            ++ans;
            mx = root.val;
        }
        dfs(root.left, mx);
        dfs(root.right, mx);
    }


}
