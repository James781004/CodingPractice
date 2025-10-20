package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

public class SortedArrayToBST {

    // https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/solutions/2927064/ru-men-di-gui-cong-er-cha-shu-kai-shi-py-inu6/
    public TreeNode sortedArrayToBST(int[] nums) {
        return dfs(nums, 0, nums.length);
    }

    // 把 nums[left] 到 nums[right-1] 轉成平衡二叉搜索樹
    private TreeNode dfs(int[] nums, int left, int right) {
        if (left == right) {
            return null;
        }
        int m = (left + right) >>> 1;
        return new TreeNode(nums[m], dfs(nums, left, m), dfs(nums, m + 1, right));
    }


}
