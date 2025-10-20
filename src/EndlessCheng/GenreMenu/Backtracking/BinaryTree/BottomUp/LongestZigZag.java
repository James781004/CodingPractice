package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class LongestZigZag {

    // https://leetcode.cn/problems/longest-zigzag-path-in-a-binary-tree/solutions/192930/javazi-di-xiang-shang-shu-xing-dp-by-jackie-tien/
    private int maxPath = 0;

    public int longestZigZag(TreeNode root) {
        dfs(root);
        return maxPath;
    }


    private int[] dfs(TreeNode root) {
        // res[0]表示當前節點下一步向左走帶來的最大收益
        // res[1]表示當前節點下一步向右走帶來的最大收益
        int[] res = new int[2];
        if (root == null) {
            res[0] = -1;
            res[1] = -1;
            return res;
        }
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);

        // res[0]=1+left[1] 當前節點下一步向左走帶來的最大收益等於左子節點向右走的最大收益+1
        res[0] = 1 + left[1];

        // res[1]=1+right[0] 當前節點下一步向右走帶來的最大收益等於右子節點向左走的最大收益+1
        res[1] = 1 + right[0];

        // 全局變量maxPath，每次遍歷某一節點時，更新它
        maxPath = Math.max(maxPath, Math.max(res[0], res[1]));
        return res;
    }


}
