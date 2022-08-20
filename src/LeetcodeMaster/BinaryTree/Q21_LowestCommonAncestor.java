package LeetcodeMaster.BinaryTree;

public class Q21_LowestCommonAncestor {
//    236. 二叉樹的最近公共祖先
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0236.%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E6%9C%80%E8%BF%91%E5%85%AC%E5%85%B1%E7%A5%96%E5%85%88.md
//
//    給定一個二叉樹, 找到該樹中兩個指定節點的最近公共祖先。
//
//    最近公共祖先的定義為：“對於有根樹 T 的兩個結點 p、q，最近公共祖先表示為一個結點 x，
//    滿足 x 是 p、q 的祖先且 x 的深度盡可能大（一個節點也可以是它自己的祖先）。”

    public TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        // 後續遍歷本身就是從下往上找
        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);

//        if (left == null && right == null) {
//            return null;
//        } else if (left == null && right != null) {
//            return right;
//        } else if (left != null && right == null) {
//            return right;
//        } else {
//            return root;
//        }

        // 精簡寫法
        if (left != null && right != null) return root;
        if (left == null) {
            return right;
        } else {
            return left;
        }
    }
}
