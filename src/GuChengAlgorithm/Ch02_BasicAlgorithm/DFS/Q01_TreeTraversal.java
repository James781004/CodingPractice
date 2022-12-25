package GuChengAlgorithm.Ch02_BasicAlgorithm.DFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q01_TreeTraversal {
    // https://docs.google.com/presentation/d/1pU6V3tGvldbAXk_qrcNOqE85vfv9Ty-raBP2XcDacyo/edit#slide=id.g9de9a01eda_0_0
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    private void helper(TreeNode root, List<Integer> res) {
        if (root == null) return;
        helper(root.left, res);
        res.add(root.val);
        helper(root.right, res);
    }


    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {  // 先把左邊節點全部入棧
                stack.push(root);
                root = root.left;
            }
            root = stack.pop(); // 左節點沒了，彈出棧頭作為指向為當前根節點
            res.add(root.val); // 加入根節點
            root = root.right; // 開始找根節點的右子樹
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
