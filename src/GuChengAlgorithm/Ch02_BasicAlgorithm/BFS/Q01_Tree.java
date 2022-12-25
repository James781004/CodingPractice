package GuChengAlgorithm.Ch02_BasicAlgorithm.BFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q01_Tree {
    // https://docs.google.com/presentation/d/1R8rHF7l3C5DEOI0GTwSwSzkmyRmscu1KwjVIVpL4tgQ/edit#slide=id.g99a2da5d10_4_1

    public int minDepthBFS(TreeNode root) {
        if (root == null) return 0;
        int depth = 1;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();
                if (cur.left == null && cur.right == null) return depth;
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            depth++;
        }
        return depth;
    }

    public int minDepthDFS(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return 1 + minDepthDFS(root.right);
        if (root.right == null) return 1 + minDepthDFS(root.left);
        return Math.min(minDepthDFS(root.left), minDepthDFS(root.right)) + 1;
    }


    // https://docs.google.com/presentation/d/1R8rHF7l3C5DEOI0GTwSwSzkmyRmscu1KwjVIVpL4tgQ/edit#slide=id.g99a2da5d10_4_13
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        if (root != null) q.offer(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();
                level.add(cur.val);
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            res.add(level);
        }
        return res;
    }

    public List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        levelDFS(root, res, 0);
        return res;
    }

    private void levelDFS(TreeNode root, List<List<Integer>> res, int height) {
        if (root == null) return;
        if (height >= res.size()) res.add(new ArrayList<>()); // 首次抵達新階層，新增一個空list
        res.get(height).add(root.val); // 在當前階層的list新增value
        if (root.left != null) levelDFS(root.left, res, height + 1);  // 進入下一個階層，height + 1
        if (root.right != null) levelDFS(root.right, res, height + 1);
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

}
