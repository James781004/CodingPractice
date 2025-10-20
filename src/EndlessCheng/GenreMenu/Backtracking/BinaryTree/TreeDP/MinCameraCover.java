package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeDP;

import EndlessCheng.TreeNode;

public class MinCameraCover {

    // https://leetcode.cn/problems/binary-tree-cameras/solutions/2452795/shi-pin-ru-he-si-kao-shu-xing-dpgai-chen-uqsf/
    public int minCameraCover(TreeNode root) {
        int[] res = dfs(root);

        // 0：選擇當前節點為攝像頭  1：被父節點監控  2：至少有一個子節點被監控
        return Math.min(res[0], res[2]);
    }

    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{Integer.MAX_VALUE / 2, 0, 0}; // 除 2 防止加法溢出
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        // int choose = Math.min(left[0], Math.min(left[1],left[2])) + Math.min(right[0],Math.min(right[1],right[2])) + 1;
        // 靈神原視頻應為上述做法，但因為當該節點設置為攝像頭，那麼最優解其字節點一定不會是攝像頭，所以不要考慮left[2]和right[2]
        int choose = Math.min(left[0], left[1]) + Math.min(right[0], right[1]) + 1;
        int byFa = Math.min(left[0], left[2]) + Math.min(right[0], right[2]);
        int byChildren = Math.min(Math.min(left[0] + right[2], left[2] + right[0]), left[0] + right[0]);
        return new int[]{choose, byFa, byChildren};
    }


}
