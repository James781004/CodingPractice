package EndlessCheng.GenreMenu.Backtracking.BinaryTree.NTree;

import EndlessCheng.Node;

import java.util.ArrayList;
import java.util.List;

public class LevelOrder {

    // https://leetcode.cn/problems/n-ary-tree-level-order-traversal/solutions/2642410/liang-chong-bfs-xie-fa-shuang-shu-zu-dui-a4hd/
    public List<List<Integer>> levelOrder(Node root) {
        if (root == null) return List.of();
        List<List<Integer>> ans = new ArrayList<>();
        List<Node> cur = List.of(root);
        while (!cur.isEmpty()) {
            List<Node> nxt = new ArrayList<>();
            List<Integer> vals = new ArrayList<>(cur.size()); // 預分配空間
            for (Node node : cur) {
                vals.add(node.val);
                nxt.addAll(node.children);
            }
            ans.add(vals);
            cur = nxt;
        }
        return ans;
    }


}
