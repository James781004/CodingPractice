package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

public class ConstructFromPrePost {

    // https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/solutions/2649218/tu-jie-cong-on2-dao-onpythonjavacgojsrus-h0o5/
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        int n = preorder.length;
        int[] index = new int[n + 1];
        for (int i = 0; i < n; i++) {
            index[postorder[i]] = i;
        }
        return dfs(preorder, 0, n, 0, index); // 左閉右開區間
    }

    // 注意 postR 可以省略
    private TreeNode dfs(int[] preorder, int preL, int preR, int postL, int[] index) {
        if (preL == preR) { // 空節點
            return null;
        }
        if (preL + 1 == preR) { // 葉子節點
            return new TreeNode(preorder[preL]);
        }
        int leftSize = index[preorder[preL + 1]] - postL + 1; // 左子樹的大小
        TreeNode left = dfs(preorder, preL + 1, preL + 1 + leftSize, postL, index);
        TreeNode right = dfs(preorder, preL + 1 + leftSize, preR, postL + leftSize, index);
        return new TreeNode(preorder[preL], left, right);
    }


}
