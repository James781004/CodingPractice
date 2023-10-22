package Grind.Ch06_BST;

public class Q01_LCA_BST {
    // https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/solutions/428739/er-cha-sou-suo-shu-de-zui-jin-gong-gong-zu-xian-3c/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //如果小於等於0，說明p和q位於root的兩側，直接返回即可
        if ((root.val - p.val) * (root.val - q.val) <= 0)
            return root;
        //否則，p和q位於root的同一側，就繼續往下找
        return lowestCommonAncestor(p.val < root.val ? root.left : root.right, p, q);
    }


    // 遞歸法：
    public TreeNode lowestCommonAncestor2(TreeNode cur, TreeNode p, TreeNode q) {
        if (cur == null || cur == p || cur == q) return cur;
        TreeNode left = lowestCommonAncestor2(cur.left, p, q);
        TreeNode right = lowestCommonAncestor2(cur.right, p, q);

        // 如果left為空，說明這兩個節點在cur結點的右子樹上，我們只需要返回右子樹查找的結果即可
        if (left == null) return right;

        // 同上
        if (right == null) return left;
        
        // 如果left和right都不為空，說明這兩個節點一個在cur的左子樹上一個在cur的右子樹上，
        // 我們只需要返回cur結點即可。
        return cur;
    }


    // 迭代法：
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        while (true) {
            if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else {
                break;
            }
        }
        return root;
    }
}
