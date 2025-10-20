package EndlessCheng.GenreMenu.Backtracking.BinaryTree.LCA;

import EndlessCheng.TreeNode;

public class LowestCommonAncestor {

    // https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/solutions/2023873/zui-jin-gong-gong-zu-xian-yi-ge-shi-pin-8h2zc/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int x = root.val;
        if (p.val < x && q.val < x) { // p 和 q 都在左子樹
            return lowestCommonAncestor(root.left, p, q);
        }
        if (p.val > x && q.val > x) { // p 和 q 都在右子樹
            return lowestCommonAncestor(root.right, p, q);
        }
        return root; // 其它
    }

}
