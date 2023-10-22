package Grind.Ch06_BST;

public class Q02_ValidateBST {
    // https://leetcode.cn/problems/validate-binary-search-tree/solutions/232885/zhong-xu-bian-li-qing-song-na-xia-bi-xu-miao-dong-/
    // 最小值初始化為中序序列的第一個節點值
    Integer pre;

    public boolean isValidBST(TreeNode root) {
        // 結束條件：如果root為null，返回true。
        // 因為空的二叉搜索樹也是符合條件的
        if (root == null) return true;

        // 中序遍歷：左根右
        // 遞歸左子樹，獲取結果
        boolean left = isValidBST(root.left);

        // 如果當前節點的值大於等於中序序列中上一個值，證明不是二叉搜索樹
        // 如果pre為null，證明pre還沒有初始化所以首先將pre初始化
        if (pre != null && root.val <= pre) {
            return false;
        }

        // 如果當前節點小於中序序列上一個值，證明符合條件，更新pre為
        // 當前值，繼續判斷下一個值，
        pre = root.val;

        // 遞歸右子樹，獲取結果
        boolean right = isValidBST(root.right);

        //    左右子樹都是二叉搜索樹，則返回true，否則返回false
        return left && right;
    }


    public boolean isValidBST2(TreeNode root) {
        if (root == null) return true;
        return dfs(root, null, null);
    }

    public static boolean dfs(TreeNode root, Integer min, Integer max) {
        if (root == null) return true;
        if (min != null && root.val <= min) return false;
        if (max != null && root.val >= max) return false;
        return dfs(root.left, min, root.val) && dfs(root.right, root.val, max);
    }
}
