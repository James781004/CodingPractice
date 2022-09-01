package LeetcodeMaster.DP;

import LeetcodeMaster.BinaryTree.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class Q23_HouseRob3 {
//    337.打家劫舍 III
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0337.%E6%89%93%E5%AE%B6%E5%8A%AB%E8%88%8DIII.md
//
//    在上次打劫完一條街道之後和一圈房屋後，小偷又發現了一個新的可行竊的地區。
//    這個地區只有一個入口，我們稱之為“根”。
//    除了“根”之外，每棟房子有且只有一個“父“房子與之相連。
//    一番偵察之後，聰明的小偷意識到“這個地方的所有房屋的排列類似於一棵二叉樹”。 如果兩個直接相連的房子在同一天晚上被打劫，房屋將自動報警。
//
//    計算在不觸動警報的情況下，小偷一晚能夠盜取的最高金額。


    // 1.遞歸去偷，超時
    public int rob(TreeNode root) {
        if (root == null) return 0;
        int money = root.value;
        if (root.left != null) money += rob(root.left.left) + rob(root.left.right);
        if (root.right != null) money += rob(root.right.left) + rob(root.right.right);
        return Math.max(money, rob(root.left) + rob(root.right));
    }


    // 2.遞歸去偷，記錄狀態
    // 執行用時：3 ms , 在所有 Java 提交中擊敗了 56.24% 的用戶
    public int rob1(TreeNode root) {
        Map<TreeNode, Integer> memo = new HashMap<>();
        return robAction(root, memo);
    }

    private int robAction(TreeNode root, Map<TreeNode, Integer> memo) {
        if (root == null) return 0;
        if (memo.containsKey(root)) return memo.get(root);

        int money = root.value;
        if (root.left != null) money += rob(root.left.left) + rob(root.left.right);
        if (root.right != null) money += rob(root.right.left) + rob(root.right.right);
        int res = Math.max(money, rob(root.left) + rob(root.right));
        memo.put(root, res);
        return res;
    }


    // 3.狀態標記遞歸
    // 執行用時：0 ms , 在所有 Java 提交中擊敗了 100% 的用戶
    // 不偷：Max(左孩子不偷，左孩子偷) + Max(又孩子不偷，右孩子偷)
    // root[0] = Math.max(rob(root.left)[0], rob(root.left)[1]) +
    // Math.max(rob(root.right)[0], rob(root.right)[1])
    // 偷：左孩子不偷+ 右孩子不偷 + 當前節點偷
    // root[1] = rob(root.left)[0] + rob(root.right)[0] + root.val;
    public int rob2(TreeNode root) {
        int[] res = robAction1(root);
        return Math.max(res[0], res[1]);
    }

    private int[] robAction1(TreeNode root) {
        int[] res = new int[2]; // 長度為2的數組，0：不偷，1：偷
        if (root == null) return res;
        int[] left = robAction1(root.left);
        int[] right = robAction1(root.right);
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]); // 不偷root，那麽可以偷也可以不偷左右節點，取較大的情況
        res[1] = root.value + left[0] + right[0]; // 偷root，那麽就不能偷左右節點。
        return res;
    }
}
