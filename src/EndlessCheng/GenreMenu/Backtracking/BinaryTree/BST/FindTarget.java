package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class FindTarget {

    // https://leetcode.cn/problems/two-sum-iv-input-is-a-bst/solutions/1354976/by-ac_oier-zr4o/
    // 中序遍歷+雙指針，利用二叉搜索樹的特性，中序遍歷是有序的
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        dfs(root, list);
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            int tmp = list.get(left) + list.get(right);
            if (tmp == k)
                return true;
            else if (tmp < k)
                left++;
            else
                right--;
        }
        return false;
    }

    private void dfs(TreeNode root, List<Integer> list) {
        if (root == null)
            return;
        dfs(root.left, list);
        list.add(root.val);
        dfs(root.right, list);
    }

}
