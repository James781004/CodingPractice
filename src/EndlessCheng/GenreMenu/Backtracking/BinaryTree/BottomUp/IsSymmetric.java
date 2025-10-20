package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class IsSymmetric {

    // https://leetcode.cn/problems/symmetric-tree/solutions/46560/dong-hua-yan-shi-101-dui-cheng-er-cha-shu-by-user7/
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        //調用遞歸函數，比較左節點，右節點
        return dfs(root.left, root.right);
    }

    boolean dfs(TreeNode left, TreeNode right) {
        //遞歸的終止條件是兩個節點都為空
        //或者兩個節點中有一個為空
        //或者兩個節點的值不相等
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        //再遞歸的比較 左節點的左孩子 和 右節點的右孩子
        //以及比較  左節點的右孩子 和 右節點的左孩子
        return dfs(left.left, right.right) && dfs(left.right, right.left);
    }

}
