package Grind.Ch06_BST;

public class Q05_ConvertSortedArrayBST {
    // https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/solutions/313508/jian-dan-di-gui-bi-xu-miao-dong-by-sweetiee/
    public TreeNode sortedArrayToBST(int[] nums) {
        return dfs(nums, 0, nums.length - 1);
    }

    private TreeNode dfs(int[] nums, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        // 以升序數組的中間元素作為根節點 root。
        int mid = lo + (hi - lo) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        // 遞歸的構建 root 的左子樹與右子樹。
        root.left = dfs(nums, lo, mid - 1);
        root.right = dfs(nums, mid + 1, hi);
        return root;
    }

}
