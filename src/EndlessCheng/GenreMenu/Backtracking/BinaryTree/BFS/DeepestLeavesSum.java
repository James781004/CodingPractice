package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class DeepestLeavesSum {

    // https://leetcode.cn/problems/deepest-leaves-sum/solutions/1754227/by-ac_oier-srst/
    public int deepestLeavesSum(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        Deque<TreeNode> d = new ArrayDeque<>();
        d.addLast(root);
        int depth = 0;
        while (!d.isEmpty()) {
            int sz = d.size();
            while (sz-- > 0) { // BFS 進行樹的遍歷，記錄最大深度 depth 以及使用哈希表記錄每層元素和
                TreeNode node = d.pollFirst();
                map.put(depth, map.getOrDefault(depth, 0) + node.val);
                if (node.left != null) d.addLast(node.left);
                if (node.right != null) d.addLast(node.right);
            }
            depth++;
        }
        return map.get(depth - 1);
    }


}
