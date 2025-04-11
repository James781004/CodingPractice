package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class delNodes {

    // https://leetcode.cn/problems/delete-nodes-and-return-forest/solutions/2289131/he-shi-ji-lu-da-an-pythonjavacgo-by-endl-lpcd/
    public List<TreeNode> delNodes(TreeNode root, int[] toDelete) {
        List<TreeNode> ans = new ArrayList<>();
        Set<Integer> s = new HashSet<>();
        for (int x : toDelete) s.add(x);
        if (dfs(ans, s, root) != null) ans.add(root); // 如果根節點沒被刪除，把根節點加入答案
        return ans;
    }

    private TreeNode dfs(List<TreeNode> ans, Set<Integer> s, TreeNode node) {
        if (node == null) return null;

        // 取得左右子樹返回值
        node.left = dfs(ans, s, node.left);
        node.right = dfs(ans, s, node.right);

        // 如果當前節點沒被刪除，就返回當前節點
        if (!s.contains(node.val)) return node;

        // 如果當前節點被刪除，那麼就檢查左右子樹是否被刪除，如果沒被刪除，就加入答案。
        if (node.left != null) ans.add(node.left);
        if (node.right != null) ans.add(node.right);

        // 當前節點被刪除
        return null;
    }

}
