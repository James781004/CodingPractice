package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class replaceValueInTree {

    // https://leetcode.cn/problems/cousins-in-binary-tree-ii/solutions/2229010/bfssuan-liang-ci-pythonjavacgo-by-endles-b72a/
    public TreeNode replaceValueInTree(TreeNode root) {
        root.val = 0;
        List<TreeNode> q = List.of(root);
        while (!q.isEmpty()) {

            // 雙 list 層序遍歷
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
            for (TreeNode node : tmp) {
                int childrenSum = (node.left != null ? node.left.val : 0) +
                        (node.right != null ? node.right.val : 0); // 當前節點的左右子節點和

                // 「下一層的節點值之和」減去「當前節點的左右子節點和」，就是題目要求的「堂兄弟節點值的和」
                // 這邊直接替換掉當前節點的左右子節點值
                if (node.left != null) node.left.val = nextLevelSum - childrenSum;
                if (node.right != null) node.right.val = nextLevelSum - childrenSum;
            }
        }
        return root;
    }

}
