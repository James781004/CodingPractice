package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class buildTree2 {

    // https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/solutions/2647794/tu-jie-cong-on2-dao-onpythonjavacgojsrus-w8ny/
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length;
        Map<Integer, Integer> index = new HashMap<>(n); // 預分配空間
        for (int i = 0; i < n; i++) {
            index.put(inorder[i], i);
        }
        return dfs(0, postorder, 0, n, index); // 左閉右開區間
    }

    private TreeNode dfs(int inL, int[] postorder, int postL, int postR, Map<Integer, Integer> index) {
        if (postL == postR) { // 空節點
            return null;
        }
        int leftSize = index.get(postorder[postR - 1]) - inL; // 左子樹的大小
        TreeNode left = dfs(inL, postorder, postL, postL + leftSize, index);
        TreeNode right = dfs(inL + leftSize + 1, postorder, postL + leftSize, postR - 1, index);
        return new TreeNode(postorder[postR - 1], left, right);
    }


}
