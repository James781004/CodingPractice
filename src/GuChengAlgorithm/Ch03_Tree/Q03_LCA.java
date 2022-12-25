package GuChengAlgorithm.Ch03_Tree;

import java.util.HashSet;
import java.util.Set;

public class Q03_LCA {
    // https://docs.google.com/presentation/d/1Ck_j8cwoOlLFkDzUGzN6p4EdFrM6PMlQyF0ssHJK8R4/edit#slide=id.gd3515bba61_0_59
    public TreeNode lcabst(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val > p.val && root.val > q.val) lcabst(root.left, p, q);
        if (root.val < p.val && root.val < q.val) lcabst(root.right, p, q);
        return root;
    }

    public TreeNode lcabstIterative(TreeNode root, TreeNode p, TreeNode q) {
        while (true) {
            if (root.val > p.val && root.val > q.val) root = root.left;
            else if (root.val < p.val && root.val < q.val) root = root.right;
            else return root;
        }
    }


    // https://docs.google.com/presentation/d/1Ck_j8cwoOlLFkDzUGzN6p4EdFrM6PMlQyF0ssHJK8R4/edit#slide=id.gd3515bba61_0_71
    public TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);
        if (left != null && right != null) return root;
        if (left == null) return right;
        if (right == null) return left;
        return null;
//        return left == null ? right : left;
    }


    // https://docs.google.com/presentation/d/1Ck_j8cwoOlLFkDzUGzN6p4EdFrM6PMlQyF0ssHJK8R4/edit#slide=id.gd3515bba61_0_80
    public TreeNode lcaII(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode pNode = lcaIIdfs(root, p.val), qNode = lcaIIdfs(root, q.val);
        return (qNode == null || pNode == null) ? null : lcaII(root, pNode, qNode);
    }

    private TreeNode lcaIIdfs(TreeNode cur, int target) {
        if (cur == null) return null;
        if (cur.val == target) return cur;
        TreeNode left = lcaIIdfs(cur.left, target);
        TreeNode right = lcaIIdfs(cur.right, target);
        return left == null ? right : left;
    }


    public TreeNode lcaII2(TreeNode root, TreeNode p, TreeNode q) {
        int count = 0;
        TreeNode LCA = lcaHelper(root, p, q, count);
        return count == 2 ? LCA : null;
    }

    private TreeNode lcaHelper(TreeNode root, TreeNode p, TreeNode q, int count) {
        if (root == null) return null;
        TreeNode left = lcaHelper(root.left, p, q, count);
        TreeNode right = lcaHelper(root.right, p, q, count);
        if (root == p || root == q) {
            count++;
            return root;
        }
        return left == null ? right : right == null ? left : root;
    }


    // https://docs.google.com/presentation/d/1Ck_j8cwoOlLFkDzUGzN6p4EdFrM6PMlQyF0ssHJK8R4/edit#slide=id.gd3515bba61_0_89
    public Node lcaIII(Node p, Node q) {
        Set<Node> set = new HashSet<>();
        while (true) {
            if (p != null && !set.add(p)) return p;
            if (q != null && !set.add(q)) return q;
            if (p != null) p = p.parent;
            if (q != null) q = q.parent;
        }
    }

    public Node lcaIII2(Node p, Node q) {
        Node a = p, b = q;
        while (a != b) {  // 還狀鏈表解法，走到底就自動街上另一頭，直到兩點交會
            a = a == null ? q : a.parent;
            b = b == null ? p : b.parent;
        }
        return a;
    }


    // https://docs.google.com/presentation/d/1Ck_j8cwoOlLFkDzUGzN6p4EdFrM6PMlQyF0ssHJK8R4/edit#slide=id.gd3515bba61_0_101
    public TreeNode lcaIV(TreeNode root, TreeNode[] nodes) {
        Set<Integer> set = new HashSet<>();
        for (TreeNode t : nodes) set.add(t.val);
        return lcaIVDFS(root, set);
    }

    private TreeNode lcaIVDFS(TreeNode root, Set<Integer> set) {
        if (root == null) return null;
        if (set.contains(root.val)) return root; // 第一次遇到目標點，後面可以不用繼續掃
        TreeNode left = lcaIVDFS(root.left, set);
        TreeNode right = lcaIVDFS(root.right, set);
        if (left != null && right != null) return root;
        if (left != null) return left;
        if (right != null) return right;
        return null;
    }


    // https://docs.google.com/presentation/d/1Ck_j8cwoOlLFkDzUGzN6p4EdFrM6PMlQyF0ssHJK8R4/edit#slide=id.gd3515bba61_0_117
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        Pair p = getLca(root, 0);
        return p.node;
    }

    private Pair getLca(TreeNode root, int d) {
        if (root == null) return new Pair(null, d);
        Pair l = getLca(root.left, d + 1);
        Pair r = getLca(root.right, d + 1);
        if (l.d == r.d) {
            return new Pair(root, l.d);
        } else {
            return l.d > r.d ? l : r;
        }
    }


    class Pair {
        TreeNode node;
        int d;

        Pair(TreeNode node, int d) {
            this.node = node;
            this.d = d;
        }
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    class Node {
        int val;
        Node left;
        Node right;
        Node parent;
    }
}
