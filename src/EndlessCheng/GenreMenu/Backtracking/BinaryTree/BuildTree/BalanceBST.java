package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BalanceBST {

    // https://leetcode.cn/problems/balance-a-binary-search-tree/solutions/457827/1382-jiang-er-cha-sou-suo-shu-bian-ping-heng-gou-z/
    List<Integer> res = new ArrayList<Integer>();

    public TreeNode balanceBST(TreeNode root) {
        travesal(root);
        return getTree(res, 0, res.size() - 1);
    }

    // 有序樹轉成有序數組
    private void travesal(TreeNode cur) {
        if (cur == null) return;
        travesal(cur.left);
        res.add(cur.val);
        travesal(cur.right);
    }

    // 有序數組轉成平衡二叉樹
    private TreeNode getTree(List<Integer> nums, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums.get(mid));
        root.left = getTree(nums, left, mid - 1);
        root.right = getTree(nums, mid + 1, right);
        return root;
    }

}
