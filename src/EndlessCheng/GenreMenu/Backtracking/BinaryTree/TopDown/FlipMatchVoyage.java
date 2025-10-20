package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class FlipMatchVoyage {

    // https://leetcode.cn/problems/flip-binary-tree-to-match-preorder-traversal/solutions/3583/fan-zhuan-er-cha-shu-yi-pi-pei-xian-xu-bian-li-by-/
    /**
     * 1. 確定使用前序遍歷遍歷二叉樹
     * 2. 判斷根節點是否相等
     * 3. 判斷左子樹的值是否等於數組中的下一個值
     * - 如果左子樹相等就不需要換，否則就交換，繼續遞歸左右子樹
     */
    List<Integer> res;
    int index = 0;

    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
        res = new ArrayList<>();
        if (!helper(root, voyage)) {
            res.clear();
            res.add(-1);
        }
        return res;
    }

    public boolean helper(TreeNode root, int[] voyage) {
        if (root == null)
            return true;
        if (root.val != voyage[index++])
            return false;
        // 如果不等於左子樹的值交換左右子樹
        if (root.left != null && root.left.val != voyage[index]) {
            res.add(root.val);
            return helper(root.right, voyage) && helper(root.left, voyage);
        }
        return helper(root.left, voyage) && helper(root.right, voyage);
    }
}
