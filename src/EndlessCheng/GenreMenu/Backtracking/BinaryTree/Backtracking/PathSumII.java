package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Backtracking;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PathSumII {

    // https://leetcode.cn/problems/path-sum-ii/solutions/3061294/hui-su-fu-chang-jian-wen-ti-ji-qi-jie-da-g8im/
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(root, targetSum, path, ans);
        return ans;
    }

    private void dfs(TreeNode node, int remain, List<Integer> path, List<List<Integer>> ans) {
        if (node == null) {
            return;
        }
        path.add(node.val);
        remain -= node.val;
        // node.remain == node.right 相當於判斷左右節點是否均為 null
        if (node.left == node.right && remain == 0) {
            ans.add(new ArrayList<>(path));
        } else {
            dfs(node.left, remain, path, ans);
            dfs(node.right, remain, path, ans);
        }
        path.remove(path.size() - 1);
    }

}
