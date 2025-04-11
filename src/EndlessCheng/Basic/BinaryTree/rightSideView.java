package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class rightSideView {

    // https://leetcode.cn/problems/binary-tree-right-side-view/solutions/2015061/ru-he-ling-huo-yun-yong-di-gui-lai-kan-s-r1nc/
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        dfs(root, 0, ans);
        return ans;
    }

    private void dfs(TreeNode root, int depth, List<Integer> ans) {
        if (root == null) {
            return;
        }
        if (depth == ans.size()) { // 這個深度首次遇到
            ans.add(root.val);
        }
        dfs(root.right, depth + 1, ans); // 先遞歸右子樹，保證首次遇到的一定是最右邊的節點
        dfs(root.left, depth + 1, ans);
    }

}
