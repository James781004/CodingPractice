package EndlessCheng.GenreMenu.Backtracking.BinaryTree.NTree;

import EndlessCheng.Node;

import java.util.ArrayList;
import java.util.List;

public class Postorder {

    // https://leetcode.cn/problems/n-ary-tree-postorder-traversal/solutions/2645191/jian-dan-dfspythonjavacgojs-by-endlessch-ytdk/
    public List<Integer> postorder(Node root) {
        List<Integer> ans = new ArrayList<>();
        dfs(root, ans);
        return ans;
    }

    private void dfs(Node node, List<Integer> ans) {
        if (node == null) {
            return;
        }
        for (Node c : node.children) {
            dfs(c, ans);
        }
        ans.add(node.val);
    }


}
