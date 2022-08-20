package LeetcodeMaster.BinaryTree;

import java.util.Stack;

public class Q19_MinDifference {
//    530.二叉搜索樹的最小絕對差
//     https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0530.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E7%9A%84%E6%9C%80%E5%B0%8F%E7%BB%9D%E5%AF%B9%E5%B7%AE.md
//
//    給你一棵所有節點為非負值的二叉搜索樹，請你計算樹中任意兩節點的差的絕對值的最小值。


    TreeNode pre;
    int result = Integer.MAX_VALUE;

    public int getMinDifference(TreeNode root) {
        if (root == null) return 0;
        traversal(root);
        return result;
    }

    private void traversal(TreeNode root) {
        if (root == null) return;

        // 左
        traversal(root.left);

        // 中，處理比較邏輯
        if (pre != null) {
            result = Math.min(result, root.value - pre.value);
        }

        pre = root; // pre賦值，作為下一循環的前驅節點使用

        // 右
        traversal(root.right);
    }


    public int getMinDifferenceByStack(TreeNode root) {
        if (root == null) return 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null && !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left; // 左
            } else {
                cur = stack.pop(); // 中，處理比較邏輯
                if (pre != null) {
                    result = Math.min(result, cur.value - pre.value);
                }
                pre = cur;  // pre賦值，作為下一循環的前驅節點使用
                cur = cur.right;  // 右
            }
        }
        return result;
    }
}
