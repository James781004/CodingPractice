package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BottomUp;

import EndlessCheng.TreeNode;

public class IsSubtree {

    // https://leetcode.cn/problems/subtree-of-another-tree/solutions/2868217/cong-onm-dao-onpythonjavacgo-by-endlessc-uukp/
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        int hs = getHeight(subRoot);
        return dfs(root, subRoot, hs).getValue();
    }

    // 代碼邏輯同 104. 二叉樹的最大深度
    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftH = getHeight(root.left);
        int rightH = getHeight(root.right);
        return Math.max(leftH, rightH) + 1;
    }


    // 返回 node 的高度，以及是否找到了 subRoot
    private Pair<Integer, Boolean> dfs(TreeNode node, TreeNode subRoot, int hs) {
        if (node == null) {
            return new Pair<>(0, false);
        }
        Pair<Integer, Boolean> left = dfs(node.left, subRoot, hs);
        Pair<Integer, Boolean> right = dfs(node.right, subRoot, hs);
        if (left.getValue() || right.getValue()) {
            return new Pair<>(0, true); // 找到匹配時，算法就結束了
        }
        int nodeH = Math.max(left.getKey(), right.getKey()) + 1;

        // 只在高度相同時匹配
        return new Pair<>(nodeH, nodeH == hs && isSameTree(node, subRoot));
    }

    // 100. 相同的樹
    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            return p == q; // 必須都是 null
        }
        return p.val == q.val &&
                isSameTree(p.left, q.left) &&
                isSameTree(p.right, q.right);
    }


    class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
