package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class FindTilt {

    // https://leetcode.cn/problems/binary-tree-tilt/solutions/1108333/tong-ge-lai-shua-ti-la-jian-dan-dfs-by-t-6fp0/
    int ans = 0;

    public int findTilt(TreeNode root) {
        dfs(root);
        return ans;
    }

    // 對於每一個節點，計算出它的坡度，貢獻給最後的結果即可
    // 坡度的公式: 左子樹之和減去右子樹之和
    private int dfs(TreeNode node) {
        if (node == null) {
            // 空節點返回0
            return 0;
        }

        // 左子樹之和
        int left = dfs(node.left);
        // 右子樹之和
        int right = dfs(node.right);

        // 總坡度存儲在全局變量 ans 中
        ans += Math.abs(left - right);

        // 返回值是子樹之和
        return left + right + node.val;
    }

}
