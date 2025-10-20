package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class SumNumbers {

    // https://leetcode.cn/problems/sum-root-to-leaf-numbers/solutions/2730644/jian-ji-xie-fa-pythonjavacgojsrust-by-en-gbu9/
    private int ans;

    public int sumNumbers(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    private void dfs(TreeNode node, int x) {
        if (node == null) {
            return;
        }
        x = x * 10 + node.val;

        // 力扣創建數據的時候，每個節點都是 new 出來的，
        // 用 == 比較的時候，比的是內存地址，不是值
        if (node.left == node.right) { // node 是葉子節點
            ans += x;
            return;
        }

        dfs(node.left, x);
        dfs(node.right, x);
    }


    // 自底向上
    public int sumNumbers2(TreeNode root) {
        return bottomUp(root, 0);
    }

    private int bottomUp(TreeNode node, int x) {
        if (node == null) {
            return 0;
        }
        x = x * 10 + node.val;
        if (node.left == node.right) { // node 是葉子節點
            return x;
        }
        return bottomUp(node.left, x) + bottomUp(node.right, x);
    }

}
