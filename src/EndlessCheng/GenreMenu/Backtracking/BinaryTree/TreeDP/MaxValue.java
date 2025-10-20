package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeDP;

import EndlessCheng.TreeNode;

public class MaxValue {

    // https://leetcode.cn/problems/er-cha-shu-ran-se-UGC/solutions/974381/java-dong-tai-gui-hua-qing-xi-si-lu-yong-hjf2/
    public int maxValue(TreeNode root, int k) {
        int[] dp = getDP(root, k);
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i <= k; i++) {
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public int[] getDP(TreeNode root, int maxCount) {
        // 當前節點為空時直接返回全為0的數組
        int[] dp = new int[maxCount + 1];
        if (root == null) return dp;

        // 獲取左右子節點的dp結果
        int[] ldp = getDP(root.left, maxCount);
        int[] rdp = getDP(root.right, maxCount);

        // 當前節點不染色，最大值為左右子樹染色最大值的和
        int lMax = Integer.MIN_VALUE;
        int rMax = Integer.MIN_VALUE;
        for (int i = 0; i <= maxCount; i++) {
            lMax = Math.max(lMax, ldp[i]);
            rMax = Math.max(rMax, rdp[i]);
        }
        dp[0] = lMax + rMax;

        // 當前節點染色個數為i時，取左右子節點染色個數和為(i-1)的所有情況的最大值
        for (int i = 1; i <= maxCount; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] = Math.max(dp[i], root.val + ldp[j] + rdp[i - 1 - j]);
            }
        }
        return dp;
    }

}