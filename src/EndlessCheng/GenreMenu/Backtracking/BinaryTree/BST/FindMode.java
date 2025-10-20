package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

import java.util.ArrayList;

public class FindMode {

    // https://leetcode.cn/problems/find-mode-in-binary-search-tree/solutions/425776/501-er-cha-sou-suo-shu-zhong-de-zhong-shu-bao-li-t/
    ArrayList<Integer> mode = new ArrayList<>();
    TreeNode prev = null;
    // 當前元素的重復次數
    int curCount = 0;
    // 全局的最長相同序列長度
    int maxCount = 0;

    public int[] findMode(TreeNode root) {
        // 執行中序遍歷
        traverse(root);

        int[] res = new int[mode.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = mode.get(i);
        }
        return res;
    }

    void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        traverse(root.left);

        // 中序遍歷位置
        if (prev == null) {
            // 初始化
            curCount = 1;
            maxCount = 1;
            mode.add(root.val);
        } else {
            if (root.val == prev.val) {
                // root.val 重復的情況
                curCount++;
                if (curCount == maxCount) {
                    // root.val 是眾數
                    mode.add(root.val);
                } else if (curCount > maxCount) {
                    // 更新眾數
                    mode.clear();
                    maxCount = curCount;
                    mode.add(root.val);
                }
            }

            if (root.val != prev.val) {
                // root.val 不重復的情況
                curCount = 1;
                if (curCount == maxCount) {
                    mode.add(root.val);
                }
            }
        }
        // 別忘了更新 prev
        prev = root;

        traverse(root.right);
    }

}