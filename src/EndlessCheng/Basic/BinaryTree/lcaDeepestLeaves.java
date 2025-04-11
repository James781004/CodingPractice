package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

public class lcaDeepestLeaves {

    // https://leetcode.cn/problems/lowest-common-ancestor-of-deepest-leaves/solutions/2428724/liang-chong-di-gui-si-lu-pythonjavacgojs-xxnk/
    private TreeNode ans;
    private int maxDepth = -1; // 全局最大深度

    public TreeNode lcaDeepestLeaves(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    private int dfs(TreeNode node, int depth) {
        if (node == null) {
            maxDepth = Math.max(maxDepth, depth); // 維護全局最大深度
            return depth;
        }
        int leftMaxDepth = dfs(node.left, depth + 1); // 獲取左子樹最深葉節點的深度
        int rightMaxDepth = dfs(node.right, depth + 1); // 獲取右子樹最深葉節點的深度

        // 如果 leftMaxDepth = rightMaxDepth = maxDepth，那麼更新答案為當前節點。
        // 注意這並不代表我們找到了答案，如果後面發現了更深的葉節點，那麼答案還會更新。
        if (leftMaxDepth == rightMaxDepth && leftMaxDepth == maxDepth) {
            ans = node;
        }

        return Math.max(leftMaxDepth, rightMaxDepth); // 當前子樹最深葉節點的深度
    }

    // 方法2：把每棵子樹都看成是一個「子問題」，即對於每棵子樹，我們需要知道：
    // 這棵子樹最深葉結點的深度。這裡是指葉子在這棵子樹內的深度，而不是在整棵二叉樹的視角下的深度。相當於這棵子樹的高度。
    // 這棵子樹的最深葉結點的最近公共祖先 lca。
//    public TreeNode lcaDeepestLeaves2(TreeNode root) {
//        return dfs(root).getValue();
//    }
//
//    private Pair<Integer, TreeNode> dfs(TreeNode node) {
//        if (node == null)
//            return new Pair<>(0, null);
//        var left = dfs(node.left);
//        var right = dfs(node.right);
//        if (left.getKey() > right.getKey()) // 左子樹更高
//            return new Pair<>(left.getKey() + 1, left.getValue());
//        if (left.getKey() < right.getKey()) // 右子樹更高
//            return new Pair<>(right.getKey() + 1, right.getValue());
//        return new Pair<>(left.getKey() + 1, node); // 一樣高
//    }


}
