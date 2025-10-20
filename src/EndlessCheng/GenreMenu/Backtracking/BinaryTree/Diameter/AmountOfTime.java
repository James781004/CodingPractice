package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Diameter;

import EndlessCheng.TreeNode;

public class AmountOfTime {

    // https://leetcode.cn/problems/amount-of-time-for-binary-tree-to-be-infected/solutions/2753470/cong-liang-ci-bian-li-dao-yi-ci-bian-li-tmt0x/
    private int ans;

    public int amountOfTime(TreeNode root, int start) {
        dfs(root, start);
        return ans;
    }

    private int[] dfs(TreeNode node, int start) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] leftRes = dfs(node.left, start);
        int[] rightRes = dfs(node.right, start);
        int lLen = leftRes[0], lFound = leftRes[1];
        int rLen = rightRes[0], rFound = rightRes[1];
        if (node.val == start) {
            // 自底向上第一次遇見start,初始化最大路徑長度
            // 注意這裡 max 後面沒有 +1，所以算出的也是最大深度
            ans = Math.max(lLen, rLen);
            return new int[]{1, 1}; // 找到了 start
        }
        if (lFound == 1 || rFound == 1) {
            // 只有在左子樹或右子樹包含 start 時，才能更新答案
            ans = Math.max(ans, lLen + rLen); // 兩條鏈拼成直徑
            // 保證 start 是直徑端點
            return new int[]{(lFound == 1 ? lLen : rLen) + 1, 1};
        }

        // 左右子樹都不包含start, 只更新最大高度
        return new int[]{Math.max(lLen, rLen) + 1, 0};
    }

}
