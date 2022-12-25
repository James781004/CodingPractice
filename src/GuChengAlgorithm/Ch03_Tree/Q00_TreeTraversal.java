package GuChengAlgorithm.Ch03_Tree;

import java.util.*;

public class Q00_TreeTraversal {
    // https://docs.google.com/presentation/d/14yDoFTsJ-wuhsgfNGWXOk1y76gue4cocFIgBeQWZaB4/edit#slide=id.gc52206fef5_0_3
    public List<Integer> preOrder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            res.add(root.val);
            if (root.right != null) stack.push(root.right);
            if (root.left != null) stack.push(root.left);
        }
        return res;
    }

    public List<Integer> preOrder2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null && !stack.isEmpty()) {
            if (cur != null) {
                res.add(cur.val); // root
                stack.push(cur);
                cur = cur.left; // left
            } else {
                cur = stack.pop();
                cur = cur.right; // right
            }
        }
        return res;
    }

    public void preOrderHelper(TreeNode root, List<Integer> res) {
        if (root == null) return;
        res.add(root.val);
        preOrderHelper(root.left, res);
        preOrderHelper(root.right, res);
    }


    // https://docs.google.com/presentation/d/14yDoFTsJ-wuhsgfNGWXOk1y76gue4cocFIgBeQWZaB4/edit#slide=id.gc52206fef5_0_15
    public List<Integer> inOrder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null && !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }

    public List<Integer> inOrder2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null && !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left; // left
            } else {
                cur = stack.pop();
                res.add(cur.val); // root
                cur = cur.right; // right
            }
        }
        return res;
    }


    public void inOrderHelper(TreeNode root, List<Integer> res) {
        if (root == null) return;
        inOrderHelper(root.left, res);
        res.add(root.val);
        inOrderHelper(root.right, res);
    }


    // https://docs.google.com/presentation/d/14yDoFTsJ-wuhsgfNGWXOk1y76gue4cocFIgBeQWZaB4/edit#slide=id.gc52206fef5_0_23
    public List<Integer> postOrder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            res.add(0, root.val);  // 頭插法，從list頭部開始新增元素
            if (root.left != null) stack.push(root.left);  // 注意這邊開始是從left先放進stack，以達到root - right - left的棧順序
            if (root.right != null) stack.push(root.right);
        }

        return res;
    }


    public List<Integer> postOrder2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> resStack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                resStack.push(cur); // root
                stack.push(cur);
                cur = cur.right; // right
            } else {
                cur = stack.pop();
                cur = cur.left;  // left
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!resStack.isEmpty()) res.add(resStack.pop().val);
        return res;
    }


    public void postOrderHelper(TreeNode root, List<Integer> res) {
        if (root == null) return;
        postOrderHelper(root.left, res);
        postOrderHelper(root.right, res);
        res.add(root.val);
    }


    // https://docs.google.com/presentation/d/14yDoFTsJ-wuhsgfNGWXOk1y76gue4cocFIgBeQWZaB4/edit#slide=id.gc52206fef5_0_37
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


    public List<List<Integer>> levelOrderRecursive(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(root, res, 0);
        return res;
    }

    private void dfs(TreeNode root, List<List<Integer>> res, int height) {
        if (root == null) return;
        if (height == res.size()) res.add(new ArrayList<Integer>());
        res.get(height).add(root.val);
        if (root.left != null) dfs(root.left, res, height + 1);
        if (root.right != null) dfs(root.right, res, height + 1);
    }


    // res舊指針方法
    public List<TreeNode> levelOrder2(TreeNode root) {
        List<TreeNode> res = new ArrayList<>();
        int slow = 0;
        res.add(root);
        while (slow < res.size()) {
            TreeNode cur = res.get(slow++);
            if (cur.left != null) res.add(cur.left);
            if (cur.right != null) res.add(cur.right);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/14yDoFTsJ-wuhsgfNGWXOk1y76gue4cocFIgBeQWZaB4/edit#slide=id.gc52206fef5_0_48
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        if (root != null) q.offer(root);
        while (!q.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < q.size(); i++) {
                TreeNode cur = q.poll();
                level.add(cur.val);
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            res.add(0, level);  // 這邊進行反向插入list
        }
        return res;
    }


    // https://docs.google.com/presentation/d/14yDoFTsJ-wuhsgfNGWXOk1y76gue4cocFIgBeQWZaB4/edit#slide=id.gc52206fef5_0_56
    public List<List<Integer>> levelOrderZigZag(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        if (root != null) q.offer(root);
        while (!q.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < q.size(); i++) {
                TreeNode cur = q.poll();
                if (res.size() % 2 == 0) level.add(cur.val);
                else level.add(0, cur.val);
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            res.add(level);
        }
        return res;
    }

    public List<List<Integer>> levelOrderZigZag2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        zigZagHelper(root, res, 0);
        return res;
    }

    private void zigZagHelper(TreeNode root, List<List<Integer>> res, int height) {
        if (root == null) return;
        if (res.size() <= height) res.add(new ArrayList<Integer>());
        if (height % 2 == 0) res.get(height).add(root.val);
        else res.get(height).add(0, root.val);
        zigZagHelper(root.left, res, height + 1);
        zigZagHelper(root.right, res, height + 1);
    }


    // https://docs.google.com/presentation/d/14yDoFTsJ-wuhsgfNGWXOk1y76gue4cocFIgBeQWZaB4/edit#slide=id.gc52206fef5_0_65
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<Integer>> colToNode = new HashMap<>();
        Map<TreeNode, Integer> position = new HashMap<>();
        Queue<TreeNode> q = new LinkedList<>();
        if (root != null) q.offer(root);
        position.put(root, 0);
        int min = 0;
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            int col = position.get(cur);  // 取得當前節點座標
            colToNode.computeIfAbsent(col, x -> new ArrayList<>()).add(cur.val);
            if (cur.left != null) {
                q.add(cur.left);
                position.put(cur.left, col - 1);  // 左節點座標-1
            }
            if (cur.right != null) {
                q.add(cur.right);
                position.put(cur.right, col + 1);  // 右節點座標+1
            }
            min = Math.min(min, col);  // 記錄最小座標
        }

        // 從最小座標開始加入答案
        while (colToNode.containsKey(min)) res.add(colToNode.get(min++));
        return res;
    }


    public List<List<Integer>> verticalOrder2(TreeNode root) {
        Map<Integer, List<int[]>> colToNode = new TreeMap<>();
        verticalHelper(root, 0, 0, colToNode);
        List<List<Integer>> res = new ArrayList<>();
        for (List<int[]> nodes : colToNode.values()) {
            Collections.sort(nodes, (node1, node2) -> node1[0] - node2[0]);
            List<Integer> tmp = new LinkedList<>();
            for (int[] node : nodes) tmp.add(node[1]);
            res.add(tmp);
        }
        return res;
    }

    private void verticalHelper(TreeNode root, int depth, int offset, Map<Integer, List<int[]>> colToNode) {
        if (root == null) return;
        if (!colToNode.containsKey(offset)) colToNode.put(offset, new LinkedList<>());
        colToNode.get(offset).add(new int[]{depth, root.val});  // 根據當前座標加入
        verticalHelper(root.left, depth + 1, offset - 1, colToNode);  // 左節點座標-1
        verticalHelper(root.right, depth + 1, offset + 1, colToNode);  // 右節點座標+1
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
