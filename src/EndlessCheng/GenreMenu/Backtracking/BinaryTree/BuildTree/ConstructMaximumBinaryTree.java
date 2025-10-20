package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

public class ConstructMaximumBinaryTree {

    // https://leetcode.cn/problems/maximum-binary-tree/solutions/1761534/by-tong-zhu-btly/
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return dfs(nums, 0, nums.length - 1);
    }

    private TreeNode dfs(int[] nums, int from, int to) {
        // 遞歸的終止條件
        if (to < from) {
            return null;
        }

        // 遞歸的處理過程
        // 找到最大值，構造根節點，並以此位置分割成左右子樹
        int max = from;
        for (int i = from + 1; i <= to; i++) {
            if (nums[i] > nums[max]) {
                max = i;
            }
        }

        TreeNode node = new TreeNode(nums[max]);
        node.left = dfs(nums, from, max - 1);
        node.right = dfs(nums, max + 1, to);

        return node;
    }


}
