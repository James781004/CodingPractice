package EndlessCheng.Basic.Bitwise;

import EndlessCheng.TreeNode;

public class pseudoPalindromicPaths {

    // https://leetcode.cn/problems/pseudo-palindromic-paths-in-a-binary-tree/solutions/2540903/yi-bu-bu-you-hua-cong-shu-zu-dao-wei-yun-hu0b/
    public int pseudoPalindromicPaths(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode root, int mask) {
        if (root == null) {
            return 0;
        }
        mask ^= 1 << root.val; // 修改 root.val 出現次數的奇偶性
        if (root.left == root.right) { // root 是葉子節點
            // 刪除最小元素方法: (mask & (mask - 1)) == 0
            // 成立則說明 mask 中要麼只有一個 1，要麼全為 0
            return (mask & (mask - 1)) == 0 ? 1 : 0;
        }
        return dfs(root.left, mask) + dfs(root.right, mask);
    }


}
