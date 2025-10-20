package EndlessCheng.GenreMenu.Backtracking.BinaryTree.NTree;

import EndlessCheng.Node;

import java.util.ArrayList;
import java.util.List;

public class Preorder {

    // https://leetcode.cn/problems/n-ary-tree-preorder-traversal/solutions/2643719/jian-dan-dfspythonjavacgojsrust-by-endle-0sf4/
    public List<Integer> preorder(Node root) {
        List<Integer> ans = new ArrayList<>();
        dfs(root, ans);
        return ans;
    }

    private void dfs(Node node, List<Integer> ans) {
        if (node == null) {
            return;
        }
        ans.add(node.val);
        for (Node c : node.children) {
            dfs(c, ans);
        }
    }


}
