package Grind.Ch07_BinaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Q11_PathSumII {
    // https://leetcode.cn/problems/path-sum-ii/solutions/867902/dai-ma-sui-xiang-lu-dai-ni-xue-tou-er-ch-sbm3/
    // LC 113
    List<List<Integer>> result;
    LinkedList<Integer> path;

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        result = new LinkedList<>();
        path = new LinkedList<>();
        traversal(root, targetSum);
        return result;
    }

    private void traversal(TreeNode root, int count) {
        if (root == null) return;
        path.offer(root.val);
        count -= root.val;
        if (root.left == null && root.right == null && count == 0) {
            result.add(new LinkedList<>(path));
        }
        traversal(root.left, count);
        traversal(root.right, count);
        path.removeLast(); // 回溯
    }


    // 另一種寫法
    public void preorderdfs(TreeNode root, int targetsum, List<List<Integer>> res, List<Integer> path) {
        path.add(root.val); // 本層開始先加入當前節點

        // 遇到了葉子節點
        if (root.left == null && root.right == null) {
            // 找到了和為 targetsum 的路徑
            if (targetsum - root.val == 0) {
                res.add(new ArrayList<>(path));
            }
            return; // 如果和不為 targetsum，返回
        }

        if (root.left != null) {
            preorderdfs(root.left, targetsum - root.val, res, path);
            path.remove(path.size() - 1); // 回溯
        }
        if (root.right != null) {
            preorderdfs(root.right, targetsum - root.val, res, path);
            path.remove(path.size() - 1); // 回溯
        }
    }

    // LC 112
    public boolean haspathsum(TreeNode root, int targetsum) {
        if (root == null) {
            return false;
        }
        targetsum -= root.val;
        // 葉子結點
        if (root.left == null && root.right == null) {
            return targetsum == 0;
        }
        if (root.left != null) {
            boolean left = haspathsum(root.left, targetsum);
            if (left) {// 已經找到
                return true;
            }
        }
        if (root.right != null) {
            boolean right = haspathsum(root.right, targetsum);
            if (right) {// 已經找到
                return true;
            }
        }
        return false;
    }


    // LC 112 簡潔方法
    public boolean haspathsum2(TreeNode root, int targetsum) {

        if (root == null) return false; // 為空退出

        // 葉子節點判斷是否符合
        if (root.left == null && root.right == null) return root.val == targetsum;

        // 求兩側分支的路徑和
        return haspathsum(root.left, targetsum - root.val) || haspathsum(root.right, targetsum - root.val);
    }

    // 迭代
    public boolean haspathsumStack(TreeNode root, int targetsum) {
        if (root == null) return false;
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        stack1.push(root);
        stack2.push(root.val);
        while (!stack1.isEmpty()) {
            int size = stack1.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = stack1.pop();
                int sum = stack2.pop();
                // 如果該節點是葉子節點了，同時該節點的路徑數值等於sum，那麼就返回true
                if (node.left == null && node.right == null && sum == targetsum) return true;
                // 右節點，壓進去一個節點的時候，將該節點的路徑數值也記錄下來
                if (node.right != null) {
                    stack1.push(node.right);
                    stack2.push(sum + node.right.val);
                }
                // 左節點，壓進去一個節點的時候，將該節點的路徑數值也記錄下來
                if (node.left != null) {
                    stack1.push(node.left);
                    stack2.push(sum + node.left.val);
                }
            }
        }
        return false;
    }
}
