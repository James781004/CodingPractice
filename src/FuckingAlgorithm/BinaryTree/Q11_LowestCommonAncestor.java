package FuckingAlgorithm.BinaryTree;

public class Q11_LowestCommonAncestor {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }


//    https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
//    236. 二叉樹的最近公共祖先
//    給定一個二叉樹, 找到該樹中兩個指定節點的最近公共祖先。
//
//    最近公共祖先的定義為：“對於有根樹 T 的兩個節點 p、q，
//    最近公共祖先表示為一個節點 x，滿足 x 是 p、q 的祖先且 x 的深度盡可能大（一個節點也可以是它自己的祖先）。”

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) return right;
        if (right == null) return left;
        return root;
    }
}
