package GuChengAlgorithm.Ch03_Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q02_BST {
    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_118
    public int closestValue(TreeNode root, double target) {
        int res = 0;
        double min = Double.MAX_VALUE;
        closestValueHelper(root, target, min, res);
        return res;
    }

    private void closestValueHelper(TreeNode root, double target, double min, int res) {
        if (root == null) return;
        if (Math.abs(root.val - target) < min) {
            min = Math.abs(root.val - target);
            res = root.val;
        }
        if (root.val > target) closestValueHelper(root.left, target, min, res);
        else closestValueHelper(root.right, target, min, res);
    }


    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_56
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (key < root.val) root.left = deleteNode(root.left, key);
        else if (key > root.val) root.right = deleteNode(root.right, key);
        else if (root.left == null) return root.right;
        else if (root.right == null) return root.left;
        else {
            root.val = findMin(root.right);
            root.right = deleteNode(root.right, root.val);  // 刪除當前node
        }
        return root;
    }

    private int findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node.val;
    }


    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_67
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    private boolean isValidBST(TreeNode root, Integer max, Integer min) {
        if (root == null) return true;
        if (max != null && root.val >= max) return false;
        if (min != null && root.val <= min) return false;
        return isValidBST(root.left, root.val, min) && isValidBST(root.right, max, root.val);
    }


    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_81
    public class BSTIterator {
        Stack<TreeNode> stack;

        public BSTIterator(TreeNode root) {
            stack = new Stack<>();
            pushAllLeft(root);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public int next() {
            TreeNode node = stack.pop();
            pushAllLeft(node.right);
            return node.val;
        }

        private void pushAllLeft(TreeNode root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }
    }


    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_99
    public void recoverTree(TreeNode root) {
        TreeNode first = null, second = null;
        TreeNode prev = new TreeNode(Integer.MIN_VALUE);
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.val <= prev.val) {  // 發現出問題的2個node
                if (first == null) {  // first只更新一次
                    first = prev;
                    second = root;
                } else {
                    second = root;
                }
            }
            prev = root;
            root = root.right;
        }

        // swap first and second
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
    }


    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_110
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildBST(nums, 0, nums.length - 1);
    }

    private TreeNode buildBST(int[] nums, int start, int end) {
        if (start > end) return null;
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = buildBST(nums, start, mid - 1);
        root.right = buildBST(nums, mid + 1, end);
        return root;
    }


    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_191
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        balanceBSTHelper(root, res);
        return build(0, res.size() - 1, res);
    }

    private TreeNode build(int left, int right, List<Integer> res) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(res.get(mid));
        root.left = build(left, mid - 1, res);
        root.right = build(mid + 1, right, res);
        return root;

    }

    private void balanceBSTHelper(TreeNode root, List<Integer> res) {
        if (root == null) return;
        balanceBSTHelper(root.left, res);
        res.add(root.val);
        balanceBSTHelper(root.right, res);
    }


    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_200
    public int numTrees(int n) {
        Integer[] memo = new Integer[n + 1];
        return dfs(n, memo);
    }

    private int dfs(int n, Integer[] memo) {
        if (n == 0 || n == 1) return 1;
        if (memo[n] != null) return memo[n];
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += dfs(i - 1, memo) * dfs(n - i, memo);
        }
        return memo[n] = n;
    }

    public int numTreesDP(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }


    // https://docs.google.com/presentation/d/1eV6aR0lI1mcee9K5SUEhhxZQe7_2gaDFGSTjareAGfo/edit#slide=id.gce4af61fc6_0_213
    public List<TreeNode> generate(int n) {
        if (n == 0) return new ArrayList<TreeNode>();
        return helper(1, n);
    }

    private List<TreeNode> helper(int start, int end) {
        List<TreeNode> res = new ArrayList<>();
        if (start > end) res.add(null);
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftList = helper(start, i - 1);
            List<TreeNode> rightList = helper(i + 1, end);
            for (TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
            }
        }
        return res;
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
