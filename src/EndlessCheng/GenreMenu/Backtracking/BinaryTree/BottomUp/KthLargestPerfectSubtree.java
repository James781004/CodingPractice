package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KthLargestPerfectSubtree {

    // https://leetcode.cn/problems/k-th-largest-perfect-subtree-size-in-binary-tree/solutions/2948931/you-di-you-gui-de-er-cha-shu-dfspythonja-qy5j/
    public int kthLargestPerfectSubtree(TreeNode root, int k) {
        List<Integer> hs = new ArrayList<>();
        dfs(root, hs);

        if (k > hs.size()) {
            return -1;
        }
        Collections.sort(hs);
        return (1 << hs.get(hs.size() - k)) - 1;
    }

    private int dfs(TreeNode node, List<Integer> hs) {
        if (node == null) {
            return 0;
        }
        int leftH = dfs(node.left, hs);
        int rightH = dfs(node.right, hs);
        if (leftH < 0 || leftH != rightH) {
            return -1; // 不合法
        }
        hs.add(leftH + 1);
        return leftH + 1;
    }

}
