package EndlessCheng.Basic.Bitwise;

import EndlessCheng.TreeNode;

public class FindElements {

    // https://leetcode.cn/problems/find-elements-in-a-contaminated-binary-tree/solutions/2681672/liang-chong-fang-fa-ha-xi-biao-wei-yun-s-6m7w/
    private TreeNode root;

    public FindElements(TreeNode root) {
        this.root = root;
    }

    public boolean find(int target) {
        target++;
        TreeNode cur = root; // 從根節點出發

        // int 32位 32-b(前導0個數) 得到 target 的二進制位數長度 x，x-1 得最高位下標 j (取 x-1 原因是從 0 開始計算)，
        // 最高位下標 -1 得次高位下標 i => i = 32-b-1-1 => i = 30-b
        for (int i = 30 - Integer.numberOfLeadingZeros(target); i >= 0; i--) { // 從次高位開始枚舉
            int bit = (target >> i) & 1; // target 第 i 位的比特值
            cur = bit == 0 ? cur.left : cur.right;
            if (cur == null) { // 走到空節點，說明 target 不在二叉樹中
                return false;
            }
        }
        return true; // 沒有走到空節點，說明 target 在二叉樹中
    }


    // 基本解法
//    private final Set<Integer> s = new HashSet<>();
//
//    public FindElements(TreeNode root) {
//        dfs(root, 0);
//    }
//
//    public boolean find(int target) {
//        return s.contains(target);
//    }
//
//    private void dfs(TreeNode node, int val) {
//        if (node == null) {
//            return;
//        }
//        s.add(val);
//        dfs(node.left, val * 2 + 1);
//        dfs(node.right, val * 2 + 2);
//    }


}
