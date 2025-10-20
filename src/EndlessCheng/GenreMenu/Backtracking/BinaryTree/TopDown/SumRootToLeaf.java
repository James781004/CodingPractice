package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class SumRootToLeaf {

    // https://leetcode.cn/problems/sum-of-root-to-leaf-binary-numbers/solutions/1526069/by-ac_oier-1905/
    int ans = 0;

    public int sumRootToLeaf(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    void dfs(TreeNode root, int cur) {
        int ncur = (cur << 1) + root.val; // 根據題意，我們需要先將 cur 進行整體左移（騰出最後一位），然後將節點 x 的值放置最低位來得到新的值，並繼續進行遞歸。
        if (root.left != null) dfs(root.left, ncur);
        if (root.right != null) dfs(root.right, ncur);
        if (root.left == null && root.right == null) ans += ncur;
    }


}
