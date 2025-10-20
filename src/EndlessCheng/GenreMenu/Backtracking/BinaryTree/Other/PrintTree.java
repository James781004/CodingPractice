package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class PrintTree {

    // https://leetcode.cn/problems/print-binary-tree/solutions/15389/java-di-gui-by-zxy0917-13/
    public List<List<String>> printTree(TreeNode root) {
        //1.求出root的高度
        int maxDepth = getDepth(root);
        //2.求出輸出List的寬度
        int width = 0, count = maxDepth;
        while (count-- > 0) {
            width = width * 2 + 1;
        }
        //對結果集初始化
        List<List<String>> res = new ArrayList<>(maxDepth);
        for (int i = 0; i < maxDepth; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                list.add("");
            }
            res.add(list);
        }
        //3.前序遍歷，首先在結果集中填充左子樹，然後填充右子樹
        helper(root, 1, 0, width, res);
        return res;
    }

    private void helper(TreeNode root, int depth, int start, int end, List<List<String>> res) {
        if (root == null || start > end) return;
        //獲取當前節點需要插入List的位置
        int insert = start + (end - start) / 2;
        //根據當前層數獲得對應的List
        //插入根節點
        for (int i = start; i <= end; i++) {
            if (i == insert) {
                res.get(depth - 1).set(i, root.val + "");
                break;
            }
        }
        //遞歸打印左子樹
        helper(root.left, depth + 1, start, insert - 1, res);
        helper(root.right, depth + 1, insert + 1, end, res);
    }

    private int getDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(getDepth(root.left), getDepth(root.right)) + 1;
    }


}
