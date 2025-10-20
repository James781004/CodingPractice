package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

public class RecoverTree {

    // https://leetcode.cn/problems/recover-binary-search-tree/solutions/10397/zhong-xu-bian-li-by-powcai/
    // pre保留前一個節點的值，node1保留被交換的節點1，node2保留被交換的節點2
    TreeNode pre, node1, node2;

    public void recoverTree(TreeNode root) {
        /*
        BST的中序遍歷是嚴格升序序列
        因此通過中序遍歷的遞降對，可以定位兩個交換的節點(第一個遞降對前一個節點、第二個遞降對後面的節點)
        如果沒有第二個遞降對就直接取第一個遞降對的前後節點
        然後直接進行值交換即可！
         */
        dfs(root);
        //
        if (node1 != null && node2 != null) {
            int t = node1.val;
            node1.val = node2.val;
            node2.val = t;
        }
    }


    private void dfs(TreeNode root) {
        if (root == null) return;
        dfs(root.left);
        // 找到遞降對並求node1與node2，以下算法適合緊挨和相隔的情形
        if (pre != null && pre.val > root.val) {
            node2 = root;
            if (node1 == null) node1 = pre;
        }
        pre = root; // pre指針變更
        dfs(root.right);
    }

}
