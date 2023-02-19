package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.*;

public class Q19_Tree {
    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_6_513
    class ConstructBinaryTree {
        int[] preorder, inorder;
        int preOrderIndex;
        Map<Integer, Integer> map = new HashMap<>();

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            this.preorder = preorder;
            this.inorder = inorder;
            preOrderIndex = 0;
            int N = preorder.length;
            for (int i = 0; i < N; i++) {
                map.put(inorder[i], i);
            }
            return helper(0, N - 1);
        }

        private TreeNode helper(int inStart, int inEnd) {
            if (inStart > inEnd) return null;
            TreeNode root = new TreeNode(preorder[preOrderIndex++]);
            int index = map.get(root.val);
            root.left = helper(inStart, index - 1);
            root.right = helper(index + 1, inEnd);
            return root;
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_7_233
    class DeleteNodeBST {
        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) return null;
            if (key < root.val) root.left = deleteNode(root.left, key);
            else if (key > root.val) root.right = deleteNode(root.right, key);
            else if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            else {
//                root.val = findMin(root.right);
//                root.right = deleteNode(root.right, root.val);

                // 如果不允許alter value
                TreeNode cur = root;
                root = min(root.right);
                root.right = deleteMin(cur.right);
                root.left = cur.left;
            }
            return root;
        }


        private int findMin(TreeNode node) {
            while (node.left != null) node = node.left;
            return node.val;
        }


        private TreeNode min(TreeNode root) {
            if (root.left == null) return root.right;
            return min(root.left);
        }


        private TreeNode deleteMin(TreeNode root) {
            if (root.left == null) return root.right;
            root.left = deleteMin(root.left);
            return root;
        }

    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_7_178
    class ValidateBST {
        public boolean isValidBST(TreeNode root) {
            return isValidBST(root, null, null);
        }

        private boolean isValidBST(TreeNode root, Integer max, Integer min) {
            if (root == null) return true;
            if (max != null && root.val >= max) return false;
            if (min != null && root.val <= min) return false;
            return isValidBST(root.left, root.val, min) && isValidBST(root.right, max, root.val);
        }

        private long prev = Long.MIN_VALUE;

        public boolean isValidBST2(TreeNode root) {
            if (root == null) return true;
            boolean left = isValidBST2(root.left);
            if (!left) return false;
            if (root.val <= prev) return false;
            prev = root.val;
            return isValidBST2(root.right);
        }


        public boolean isValidBST3(TreeNode root) {
            TreeNode pre = null;
            Stack<TreeNode> stack = new Stack<>();
            while (root != null || !stack.isEmpty()) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                if (pre != null && root.val <= pre.val) return false;
                pre = root;
                root = root.right;
            }
            return true;
        }

    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_9_0
    class BSTIterator {
        Stack<TreeNode> stack;

        public BSTIterator(TreeNode root) {
            stack = new Stack<>();
            pushAllLeft(root);
        }

        private void pushAllLeft(TreeNode root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public int next() {
            TreeNode node = stack.pop();
            pushAllLeft(node.right);
            return node.val;
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_9_102
    class LCA {
        public TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || root == p || root == q) return root;
            TreeNode left = lca(root.left, p, q);
            TreeNode right = lca(root.right, p, q);
            if (left != null && right != null) return root;
            if (left != null) return left;
            if (right != null) return right;
            return null;
        }

        Map<TreeNode, TreeNode> parent = new HashMap<>();

        public TreeNode lca2(TreeNode root, TreeNode p, TreeNode q) {
            dfs(null, root);
            Set<TreeNode> ancestors = new HashSet<>();
            while (p != null) {
                ancestors.add(p);
                p = parent.get(p);
            }
            while (!ancestors.contains(q)) q = parent.get(q);
            return q;
        }

        private void dfs(TreeNode parentNode, TreeNode cur) {
            if (cur == null) return;
            parent.put(cur, parentNode);
            dfs(cur, cur.left);
            dfs(cur, cur.right);
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_9_217
    class BinaryTreePaths {
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> res = new ArrayList<>();
            if (root != null) dfs(root, String.valueOf(root.val), res);
            return res;
        }

        // 自上而下記錄path
        private void dfs(TreeNode root, String path, List<String> res) {
            if (root.left == null && root.right == null) res.add(path);
            if (root.left != null) dfs(root.left, path + "->" + root.left.val, res);
            if (root.right != null) dfs(root.right, path + "->" + root.right.val, res);
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_9_323
    class MaxPathSum {
        int maxvalue = Integer.MIN_VALUE;

        public int maxPathSum(TreeNode root) {
            helper(root);
            return maxvalue;
        }

        // 自下而上傳遞data
        private int helper(TreeNode root) {
            if (root == null) return 0;
            int left = Math.max(0, helper(root.left));
            int right = Math.max(0, helper(root.right));
            maxvalue = Math.max(maxvalue, left + right + root.val);
            return Math.max(left, right) + root.val;
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_9_270
    class ZigZag {
        int max;

        public int longestZigZag(TreeNode root) {
            dfs(root);
            return max == 0 ? 0 : max - 1;
        }

        private int[] dfs(TreeNode root) {
            int[] res = new int[2];  // [leftSum, rightSum]
            if (root == null) return res;
            int[] left = dfs(root.left);
            int[] right = dfs(root.right);
            res[0] = left[1] + 1;  // rightSum of left tree
            res[1] = right[0] + 1;  // leftSum of right tree
            max = Math.max(max, Math.max(res[0], res[1]));
            return res;
        }
    }


    class TreeNode {
        int val;
        TreeNode left, right;

        public TreeNode(int v) {
            val = v;
        }
    }
}
