package LeetcodeMaster.BinaryTree;

public class Q25_TrimBST {
//    669.
//    修剪二叉搜索樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0669.%E4%BF%AE%E5%89%AA%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91.md
//
//    給定一個二叉搜索樹，
//    同時給定最小邊界L 和最大邊界
//    R。通過修剪二叉搜索樹，使得所有節點的值在[L,R]
//
//    中(R>=L) 。你可能需要改變樹的根節點，所以結果應當返回修剪好的二叉搜索樹的新的根節點。


    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) return null;

        // 尋找符合區間[low, high]的節點
        if (root.value < low) {
            return trimBST(root.right, low, high);
        }

        if (root.value > high) {
            return trimBST(root.left, low, high);
        }

        root.left = trimBST(root.left, low, high); // root->left接入符合條件的左孩子
        root.right = trimBST(root.right, low, high);  // root->right接入符合條件的右孩子
        return root;
    }


}
