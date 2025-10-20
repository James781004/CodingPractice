package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class MinDepth {

    // https://leetcode.cn/problems/minimum-depth-of-binary-tree/solutions/2730984/liang-chong-fang-fa-zi-ding-xiang-xia-zi-0sxz/
    private int ans = Integer.MAX_VALUE;

    public int minDepth(TreeNode root) {
        dfs(root, 0);
        return root != null ? ans : 0;
    }

    private void dfs(TreeNode node, int cnt) {
        if (node == null || ++cnt >= ans) { // 最優性剪枝
            return;
        }
        cnt++;

        // 力扣創建數據的時候，每個節點都是 new 出來的，
        // 用 == 比較的時候，比的是內存地址，不是值
        if (node.left == node.right) { // node 是葉子
            ans = Math.min(ans, cnt);
            return;
        }
        dfs(node.left, cnt);
        dfs(node.right, cnt);
    }


    // 自底向上
    public int minDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

}
