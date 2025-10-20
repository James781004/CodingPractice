package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

import java.util.Stack;

public class RecoverFromPreorder {

    // https://leetcode.cn/problems/recover-a-tree-from-preorder-traversal/solutions/293104/javashi-yong-zhan-jie-jue-by-sdwwld/
    public TreeNode recoverFromPreorder(String S) {
        Stack<TreeNode> stack = new Stack<>();
        for (int i = 0; i < S.length(); ) {
            // 查看在第幾層，從0開始的，根節點是第0層
            int level = 0;
            while (S.charAt(i) == '-') {
                level++;
                i++;
            }

            // 查看當前數字
            int val = 0;
            while (i < S.length() && S.charAt(i) != '-') {
                val = val * 10 + (S.charAt(i) - '0');
                i++;
            }

            // 找到新結點的父節點
            while (stack.size() > level) {
                stack.pop();
            }
            // 創建結點
            TreeNode node = new TreeNode(val);
            if (!stack.isEmpty()) {
                // 如果節點只有一個子節點，那麼保證該子節點為左子節點。
                if (stack.peek().left == null) {
                    stack.peek().left = node;
                } else {
                    stack.peek().right = node;
                }
            }
            // 入棧
            stack.add(node);
        }
        // 除了根節點，其他子節點全部出棧
        while (stack.size() > 1) {
            stack.pop();
        }
        // 返回根節點
        return stack.pop();
    }


}
