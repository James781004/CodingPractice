package LeetcodeMaster.BinaryTree;

public class Q22_LowestCommonAncestorBST {
//    235. 二叉搜索樹的最近公共祖先
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0235.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E7%9A%84%E6%9C%80%E8%BF%91%E5%85%AC%E5%85%B1%E7%A5%96%E5%85%88.md
//
//    給定一個二叉搜索樹, 找到該樹中兩個指定節點的最近公共祖先。
//
//    最近公共祖先的定義為：“對於有根樹 T 的兩個結點 p、q，最近公共祖先表示為一個結點 x，滿足 x 是 p、q 的祖先且 x 的深度盡可能大（一個節點也可以是它自己的祖先）。”


    public TreeNode lcaBst(TreeNode root, TreeNode p, TreeNode q) {

        // BST特性左小右大，所以比較節點值的大小決定搜尋方向
        // 根節點值比兩目標都大，就往左樹搜尋
        if (root.value > p.value && root.value > q.value) return lcaBst(root.left, p, q);

        // 根節點值比兩目標都小，就往右樹搜尋
        if (root.value < p.value && root.value < q.value) return lcaBst(root.right, p, q);

        return root; // 根節點值在兩目標之間，root本身就是LCA
    }

    // 迭代法
    public TreeNode lcaBst2(TreeNode root, TreeNode p, TreeNode q) {

        // BST特性左小右大，所以比較節點值的大小決定搜尋方向
        while (true) {
            if (root.value > p.value && root.value > q.value) {
                root = root.left; // 根節點值比兩目標都大，就往左樹搜尋
            } else if (root.value < p.value && root.value < q.value) {
                root = root.right; // 根節點值比兩目標都小，就往右樹搜尋
            } else {
                break; // 根節點值在兩目標之間，root本身就是LCA
            }
        }
        return root;
    }
}
