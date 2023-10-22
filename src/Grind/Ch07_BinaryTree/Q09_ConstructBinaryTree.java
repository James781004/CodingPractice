package Grind.Ch07_BinaryTree;

public class Q09_ConstructBinaryTree {
    // https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/solutions/867923/dai-ma-sui-xiang-lu-dai-ni-xue-tou-er-ch-g155/
    // 105.從前序與中序遍歷序列構造二叉樹
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public TreeNode helper(int[] preorder, int preLeft, int preRight,
                           int[] inorder, int inLeft, int inRight) {
        // 遞歸終止條件
        if (inLeft > inRight || preLeft > preRight) return null;

        // val 為前序遍歷第一個的值，也即是根節點的值
        // idx 為根據根節點的值來找中序遍歷的下標
        int idx = inLeft, val = preorder[preLeft];
        TreeNode root = new TreeNode(val);
        for (int i = inLeft; i <= inRight; i++) {
            if (inorder[i] == val) {
                idx = i;
                break;
            }
        }

        // 根據 idx 來遞歸找左右子樹
        root.left = helper(preorder, preLeft + 1, preLeft + (idx - inLeft),
                inorder, inLeft, idx - 1);
        root.right = helper(preorder, preLeft + (idx - inLeft) + 1, preRight,
                inorder, idx + 1, inRight);
        return root;
    }


    // 106.從中序與後序遍歷序列構造二叉樹
    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        return buildTree1(inorder, 0, inorder.length, postorder, 0, postorder.length);
    }

    public TreeNode buildTree1(int[] inorder, int inLeft, int inRight,
                               int[] postorder, int postLeft, int postRight) {
        // 沒有元素了
        if (inRight - inLeft < 1) {
            return null;
        }
        // 只有一個元素了
        if (inRight - inLeft == 1) {
            return new TreeNode(inorder[inLeft]);
        }
        // 後序數組postorder裡最後一個即為根節點
        int rootVal = postorder[postRight - 1];
        TreeNode root = new TreeNode(rootVal);
        int rootIndex = 0;
        // 根據根節點的值找到該值在中序數組inorder裡的位置
        for (int i = inLeft; i < inRight; i++) {
            if (inorder[i] == rootVal) {
                rootIndex = i;
                break;
            }
        }
        // 根據rootIndex劃分左右子樹
        root.left = buildTree1(inorder, inLeft, rootIndex,
                postorder, postLeft, postLeft + (rootIndex - inLeft));
        root.right = buildTree1(inorder, rootIndex + 1, inRight,
                postorder, postLeft + (rootIndex - inLeft), postRight - 1);
        return root;
    }
}
