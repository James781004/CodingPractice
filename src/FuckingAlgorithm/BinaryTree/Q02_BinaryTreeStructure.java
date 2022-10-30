package FuckingAlgorithm.BinaryTree;

import java.util.HashMap;

public class Q02_BinaryTreeStructure {
//    https://leetcode.cn/problems/maximum-binary-tree/
//    654. 最大二叉樹
//    給定一個不重復的整數數組 nums 。 最大二叉樹 可以用下面的算法從 nums 遞歸地構建:
//
//    創建一個根節點，其值為 nums 中的最大值。
//    遞歸地在最大值 左邊 的 子數組前綴上 構建左子樹。
//    遞歸地在最大值 右邊 的 子數組後綴上 構建右子樹。
//    返回 nums 構建的 最大二叉樹 。

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    TreeNode constructMaximumBinaryTree(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private TreeNode build(int[] nums, int lo, int hi) {
        // base case
        if (lo > hi) return null;

        // 找到數組中的最大值和對應的索引
        int index = -1, maxVal = Integer.MIN_VALUE;
        for (int i = lo; i <= hi; i++) {
            if (maxVal < nums[i]) {
                index = i;
                maxVal = nums[i];
            }
        }

        // 先構造出根節點
        TreeNode root = new TreeNode(maxVal);
        // 遞歸調用構造左右子樹
        root.left = build(nums, lo, index - 1);
        root.right = build(nums, index + 1, hi);

        return root;
    }


//    https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
//    105. 從前序與中序遍歷序列構造二叉樹
//    給定兩個整數數組 preorder 和 inorder ，其中 preorder 是二叉樹的先序遍歷， inorder 是同一棵樹的中序遍歷，請構造二叉樹並返回其根節點。

    // 存儲 inorder 中值到索引的映射
    HashMap<Integer, Integer> valToIndex = new HashMap<>();

    public TreeNode buildTreeLc105(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            valToIndex.put(inorder[i], i);
        }

        // 根據函數定義，用 preorder 和 inorder 構造二叉樹
        return buildTreeLc105(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1);
    }

    /*
    buildTree 函數的定義：
    若前序遍歷數組為 preorder[preStart..preEnd]，
    中序遍歷數組為 inorder[inStart..inEnd]，
    構造二叉樹，返回該二叉樹的根節點
    */
    TreeNode buildTreeLc105(int[] preorder, int preStart, int preEnd,
                            int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }

        // root 節點對應的值就是前序遍歷數組的第一個元素
        int rootVal = preorder[preStart];
        // rootVal 在中序遍歷數組中的索引
        int index = valToIndex.get(rootVal);

        int leftSize = index - inStart;

        TreeNode root = new TreeNode(index);
        root.left = buildTreeLc105(preorder, preStart + 1, preStart + leftSize,
                inorder, inStart, index - 1);
        root.right = buildTreeLc105(preorder, preStart + leftSize + 1, preEnd,
                inorder, index + 1, inEnd);
        return root;

    }


//    https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
//    106. 從中序與後序遍歷序列構造二叉樹
//    給定兩個整數數組 inorder 和 postorder ，其中 inorder 是二叉樹的中序遍歷， postorder 是同一棵樹的後序遍歷，請你構造並返回這顆 二叉樹 。

    // 沿用上面建立的
//    HashMap<Integer, Integer> valToIndex = new HashMap<>();

    TreeNode buildTreeLc106(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) {
            valToIndex.put(inorder[i], i);
        }
        return buildTreeLc106(inorder, 0, inorder.length - 1,
                postorder, 0, postorder.length - 1);
    }

    /* 
    build 函數的定義：
    後序遍歷數組為 postorder[postStart..postEnd]，
    中序遍歷數組為 inorder[inStart..inEnd]，
    構造二叉樹，返回該二叉樹的根節點 
*/
    TreeNode buildTreeLc106(int[] inorder, int inStart, int inEnd,
                            int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd) {
            return null;
        }

        // root 節點對應的值就是後序遍歷數組的最後一個元素
        int rootVal = postorder[postEnd];

        // rootVal 在中序遍歷數組中的索引
        int index = valToIndex.get(rootVal);

        // 左子樹的節點個數
        int leftSize = index - inStart;

        TreeNode root = new TreeNode(rootVal);

        // 遞歸構造左右子樹
        root.left = buildTreeLc106(inorder, inStart, index - 1,
                postorder, postStart, postStart + leftSize - 1);

        root.right = buildTreeLc106(inorder, index + 1, inEnd,
                postorder, postStart + leftSize, postEnd - 1);
        return root;
    }

//    https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
//    889. 根據前序和後序遍歷構造二叉樹
//    給定兩個整數數組，preorder 和 postorder ，其中 preorder 是一個具有 無重復 值的二叉樹的前序遍歷，postorder 是同一棵樹的後序遍歷，重構並返回二叉樹。
//
//    如果存在多個答案，您可以返回其中 任何 一個。

    // 存儲 postorder 中值到索引的映射
//    HashMap<Integer, Integer> valToIndex = new HashMap<>();

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        for (int i = 0; i < postorder.length; i++) {
            valToIndex.put(postorder[i], i);
        }
        return construct(preorder, 0, preorder.length - 1,
                postorder, 0, postorder.length - 1);
    }

    // 定義：根據 preorder[preStart..preEnd] 和 postorder[postStart..postEnd]
    // 構建二叉樹，並返回根節點。
    TreeNode construct(int[] preorder, int preStart, int preEnd,
                       int[] postorder, int postStart, int postEnd) {
        if (preStart > preEnd) {
            return null;
        }
        if (preStart == preEnd) {
            return new TreeNode(preorder[preStart]);
        }

        // 1、首先把前序遍歷結果的第一個元素或者後序遍歷結果的最後一個元素確定為根節點的值。
        // 2、然後把前序遍歷結果的第二個元素作為左子樹的根節點的值。
        // 3、在後序遍歷結果中尋找左子樹根節點的值，從而確定了左子樹的索引邊界，進而確定右子樹的索引邊界，遞歸構造左右子樹即可。

        // root 節點對應的值就是前序遍歷數組的第一個元素
        int rootVal = preorder[preStart];

        // root.left 的值是前序遍歷第二個元素
        // 通過前序和後序遍歷構造二叉樹的關鍵在於通過左子樹的根節點
        // 確定 preorder 和 postorder 中左右子樹的元素區間
        int leftRootVal = preorder[preStart + 1];

        // leftRootVal 在後序遍歷數組中的索引
        int index = valToIndex.get(leftRootVal);

        // 左子樹的元素個數
        int leftSize = index - postStart + 1;

        // 先構造出當前根節點
        TreeNode root = new TreeNode(rootVal);
        // 遞歸構造左右子樹
        // 根據左子樹的根節點索引和元素個數推導左右子樹的索引邊界
        root.left = construct(preorder, preStart + 1, preStart + leftSize,
                postorder, postStart, index);
        root.right = construct(preorder, preStart + leftSize + 1, preEnd,
                postorder, index + 1, postEnd - 1);

        return root;
    }
}
