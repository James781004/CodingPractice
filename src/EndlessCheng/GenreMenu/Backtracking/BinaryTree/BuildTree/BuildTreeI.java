package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class BuildTreeI {

    // https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/solutions/2646359/tu-jie-cong-on2-dao-onpythonjavacgojsrus-aob8/
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        Map<Integer, Integer> index = new HashMap<>(n); // 預分配空間
        for (int i = 0; i < n; i++) {
            index.put(inorder[i], i);
        }
        return dfs(preorder, 0, n, 0, n, index); // 左閉右開區間
    }

    private TreeNode dfs(int[] preorder, int preL, int preR, int inL, int inR, Map<Integer, Integer> index) {
        if (preL == preR) { // 空節點
            return null;
        }
        int leftSize = index.get(preorder[preL]) - inL; // 左子樹的大小
        TreeNode left = dfs(preorder, preL + 1, preL + 1 + leftSize, inL, inL + leftSize, index);
        TreeNode right = dfs(preorder, preL + 1 + leftSize, preR, inL + 1 + leftSize, inR, index);
        return new TreeNode(preorder[preL], left, right);
    }

}
