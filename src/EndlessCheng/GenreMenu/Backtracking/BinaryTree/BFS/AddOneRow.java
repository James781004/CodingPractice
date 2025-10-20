package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class AddOneRow {

    // https://leetcode.cn/problems/add-one-row-to-tree/solutions/1723308/by-ac_oier-sc34/
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (depth == 1) return new TreeNode(val, root, null);
        Deque<TreeNode> d = new ArrayDeque<>();
        d.addLast(root);
        int cur = 1;
        while (!d.isEmpty()) {
            int sz = d.size();
            while (sz-- > 0) {
                TreeNode t = d.pollFirst();
                if (cur == depth - 1) { // 到達第 depth - 1 層，則進行加點操作
                    TreeNode a = new TreeNode(val), b = new TreeNode(val);
                    a.left = t.left; // t 原來的左子樹應該是新的左子樹根的左子樹
                    b.right = t.right; // t 原來的右子樹應該是新的右子樹根的右子樹
                    t.left = a; // t 接上新的左右子樹根
                    t.right = b;
                } else {
                    if (t.left != null) d.addLast(t.left);
                    if (t.right != null) d.addLast(t.right);
                }
            }
            cur++;
        }
        return root;
    }


}
