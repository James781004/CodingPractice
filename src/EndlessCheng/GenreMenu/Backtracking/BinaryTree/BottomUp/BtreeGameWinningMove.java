package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class BtreeGameWinningMove {

    // https://leetcode.cn/problems/binary-tree-coloring-game/solutions/2089813/mei-you-si-lu-yi-zhang-tu-miao-dong-pyth-btav/
    private int x, lsz, rsz;

    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        this.x = x;
        dfs(root);

        // 設 n2 為二號玩家最多可以染的節點個數
        // 左子樹的大小為 lsz，右子樹的大小為 rsz，
        // 那麼父節點子樹的大小就是 n−1−lsz−rsz
        // 因此 n2 = max(lsz,rsz,n−1−lsz−rsz)
        // 一號玩家染的節點個數為 n−n2，獲勝條件為 n2>n−n2，即 2⋅n2>n。
        return Math.max(Math.max(lsz, rsz), n - 1 - lsz - rsz) * 2 > n;
    }

    // 計算子樹大小
    // 以 x 為根，它的三個鄰居（左兒子、右兒子和父節點）就對應著三棵子樹
    // 哪棵子樹最大，二號玩家就選哪棵
    private int dfs(TreeNode node) {
        if (node == null)
            return 0;
        int ls = dfs(node.left);
        int rs = dfs(node.right);

        // 找到 x 根，記錄下左右子樹大小
        if (node.val == x) {
            lsz = ls;
            rsz = rs;
        }

        // 統計目前樹大小
        return ls + rs + 1;
    }

}
