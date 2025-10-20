package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

import java.util.TreeSet;

public class GetNumber {

    // https://leetcode.cn/problems/QO5KpG/solutions/1425924/by-makersy-j19t/
    // 保存的是沒有操作過的節點
    TreeSet<Integer> set;

    public int getNumber(TreeNode root, int[][] ops) {
        if (root == null) return 0;

        set = new TreeSet<>();
        build(root);

        int res = 0;

        for (int i = ops.length - 1; i >= 0; i--) {
            while (true) {
                // 找到第一個大於x的節點
                Integer upper = set.higher(ops[i][1] - 1);
                if (upper == null || upper > ops[i][2]) break;
                // 刪除操作過的節點
                set.remove(upper);
                // 如果是染紅，記錄紅色節點數
                if (ops[i][0] == 1) res++;
            }

        }

        return res;
    }

    private void build(TreeNode root) {
        if (root == null) return;
        build(root.left);
        set.add(root.val);
        build(root.right);
    }


}
