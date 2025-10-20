package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class ReplaceValueInTree {

    // https://leetcode.cn/problems/cousins-in-binary-tree-ii/solutions/2229010/bfssuan-liang-ci-pythonjavacgo-by-endles-b72a/
    public TreeNode replaceValueInTree(TreeNode root) {
        root.val = 0;
        List<TreeNode> q = List.of(root);
        while (!q.isEmpty()) {
            List<TreeNode> tmp = q;
            q = new ArrayList<>();

            // 計算下一層的節點值之和
            int nextLevelSum = 0;
            for (TreeNode node : tmp) {
                if (node.left != null) {
                    q.add(node.left);
                    nextLevelSum += node.left.val;
                }
                if (node.right != null) {
                    q.add(node.right);
                    nextLevelSum += node.right.val;
                }
            }

            // 再次遍歷，更新下一層的節點值
            // 對於一個節點 x 來說，它的所有堂兄弟節點值的和，
            // 等於 x 這一層的所有節點值之和 (nextLevelSum) 減去 x 及其兄弟節點的值 (childrenSum) 之和
            for (TreeNode node : tmp) {
                int childrenSum = (node.left != null ? node.left.val : 0) +
                        (node.right != null ? node.right.val : 0);
                if (node.left != null) node.left.val = nextLevelSum - childrenSum;
                if (node.right != null) node.right.val = nextLevelSum - childrenSum;
            }
        }
        return root;
    }

}
