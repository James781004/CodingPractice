package LeetcodeMaster.BinaryTree;

import java.util.HashMap;
import java.util.Map;

public class Q14_BuildTree {
//    106.從中序與後序遍歷序列構造二叉樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0106.%E4%BB%8E%E4%B8%AD%E5%BA%8F%E4%B8%8E%E5%90%8E%E5%BA%8F%E9%81%8D%E5%8E%86%E5%BA%8F%E5%88%97%E6%9E%84%E9%80%A0%E4%BA%8C%E5%8F%89%E6%A0%91.md
//
//    根據一棵樹的中序遍歷與後序遍歷構造二叉樹。
//
//    注意: 你可以假設樹中沒有重覆的元素。

//    105.從前序與中序遍歷序列構造二叉樹
//
//    根據一棵樹的前序遍歷與中序遍歷構造二叉樹。
//
//    注意: 你可以假設樹中沒有重覆的元素。


    Map<Integer, Integer> map;

    // LC 106
    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i); // 用map保存中序序列的數值對應位置
        }

        return process(inorder, 0, inorder.length, postorder, 0, postorder.length); // 前閉後開
    }

    private TreeNode process(int[] inorder, int inBegin, int inEnd, int[] postorder, int postBegin, int postEnd) {
        if (inBegin >= inEnd || postBegin >= postEnd) return null;  // 不滿足左閉右開，說明沒有元素，返回空樹

        int rootIndex = map.get(postorder[postEnd - 1]); // 找到後序遍歷的最後一個元素在中序遍歷中的位置
        TreeNode root = new TreeNode(inorder[rootIndex]); // 構造頭節點
        int leftLength = rootIndex - inBegin;  // 保存中序左子樹個數，用來確定後序數列的個數

        root.left = process(inorder, inBegin, rootIndex,
                postorder, postBegin, postBegin + leftLength);
        root.right = process(inorder, rootIndex + 1, inEnd,
                postorder, postBegin + leftLength, postEnd - 1);
        return root;
    }

    // LC 105
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i); // 用map保存中序序列的數值對應位置
        }

        return findNode(preorder, 0, preorder.length, inorder, 0, inorder.length);  // 前閉後開
    }

    private TreeNode findNode(int[] preorder, int preBegin, int preEnd, int[] inorder, int inBegin, int inEnd) {
        // 參數里的範圍都是前閉後開
        if (preBegin >= preEnd || inBegin >= inEnd) {  // 不滿足左閉右開，說明沒有元素，返回空樹
            return null;
        }
        int rootIndex = map.get(preorder[preBegin]);  // 找到前序遍歷的第一個元素在中序遍歷中的位置
        TreeNode root = new TreeNode(inorder[rootIndex]);  // 構造頭節點
        int leftLength = rootIndex - inBegin;  // 保存中序左子樹個數，用來確定前序數列的個數

        root.left = findNode(preorder, preBegin + 1, preBegin + leftLength + 1,
                inorder, inBegin, rootIndex);
        root.right = findNode(preorder, preBegin + leftLength + 1, preEnd,
                inorder, rootIndex + 1, inEnd);

        return root;
    }
}
