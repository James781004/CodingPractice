package FuckingAlgorithm.BinaryTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Q04_BinaryTreePost {
//    https://leetcode.cn/problems/find-duplicate-subtrees/
//    652. 尋找重復的子樹
//    給你一棵二叉樹的根節點 root ，返回所有 重復的子樹 。
//
//    對於同一類的重復子樹，你只需要返回其中任意 一棵 的根結點即可。
//
//    如果兩棵樹具有 相同的結構 和 相同的結點值 ，則認為二者是 重復 的。

    class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }

    // 記錄所有子樹以及出現的次數
    HashMap<String, Integer> memo = new HashMap<>();
    // 記錄重復的子樹根節點
    LinkedList<TreeNode> res = new LinkedList<>();

    /* 主函數 */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        traverse(root);
        return res;
    }

    private String traverse(TreeNode root) {
        if (root == null) {
            return "#";
        }

        String left = traverse(root.left);
        String right = traverse(root.right);

        String subTree = left + "," + right + "," + root.val;

        int freq = memo.getOrDefault(subTree, 0);
        // 多次重復也只會被加入結果集一次
        if (freq == 1) {
            res.add(root);
        }
        // 給子樹對應的出現次數加一
        memo.put(subTree, freq + 1);
        return subTree;
    }
}
