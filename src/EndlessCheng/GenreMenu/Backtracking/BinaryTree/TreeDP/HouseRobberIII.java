package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeDP;

import EndlessCheng.TreeNode;

public class HouseRobberIII {

    // https://leetcode.cn/problems/house-robber-iii/solutions/2282018/shi-pin-ru-he-si-kao-shu-xing-dppythonja-a7t1/
    public int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]); // 根節點選或不選的最大值
    }

    private int[] dfs(TreeNode node) {
        if (node == null) { // 遞歸邊界
            return new int[]{0, 0}; // 沒有節點，怎麼選都是 0
        }
        int[] left = dfs(node.left); // 遞歸左子樹
        int[] right = dfs(node.right); // 遞歸右子樹
        int rob = left[1] + right[1] + node.val; // 選
        int notRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]); // 不選
        return new int[]{rob, notRob};
    }


}
