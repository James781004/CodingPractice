package LeetcodeMaster.BinaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Q13_PathSum {
//    112. 路徑總和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0112.%E8%B7%AF%E5%BE%84%E6%80%BB%E5%92%8C.md
//
//    給定一個二叉樹和一個目標和，判斷該樹中是否存在根節點到葉子節點的路徑，這條路徑上所有節點值相加等於目標和。
//
//    說明: 葉子節點是指沒有子節點的節點。

    // LC 112
    public boolean hasPathSum(TreeNode root, int target) {
        if (root == null) return false;
        target -= root.value; // 扣除當前節點值

        // 找到葉節點就驗證目前target是否已經扣完
        if (root.left == null && root.right == null) return target == 0;

        if (root.left != null) {
            boolean left = hasPathSum(root.left, target);
            if (left) return true;
        }

        if (root.right != null) {
            boolean right = hasPathSum(root.right, target);
            if (right) return true;
        }
        return false;
    }

    // 簡潔寫法
    public boolean hasPathSum1(TreeNode root, int target) {
        if (root == null) return false;

        // 找到葉節點就驗證目前target是否可以消耗完
        if (root.left == null && root.right == null) return target == root.value;

        // 隱藏回溯，進入遞歸之前target才進行扣除
        return hasPathSum(root.left, target - root.value) || hasPathSum(root.right, target - root.value);
    }


    public boolean hasPathSumStack(TreeNode root, int target) {
        if (root == null) return false;
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        stack1.push(root);
        stack2.push(root.value);

        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            int sum = stack2.pop();

            // 找到葉節點就驗證目前target是否可以消耗完
            if (node.left == null && node.right == null) return target == sum;

            // 壓入節點的同時，sum也必須累加後壓入
            if (node.right != null) {
                stack1.push(node.right);
                stack2.push(sum + node.right.value);
            }

            if (node.left != null) {
                stack1.push(node.left);
                stack2.push(sum + node.left.value);
            }
        }
        return false;
    }

//    113. 路徑總和ii
//
//    給定一個二叉樹和一個目標和，找到所有從根節點到葉子節點路徑總和等於給定目標和的路徑。
//
//    說明: 葉子節點是指沒有子節點的節點。
    
    // LC 113
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        List<Integer> path = new LinkedList<>();
        process(root, target, res, path);
        return res;
    }

    private void process(TreeNode root, int target, List<List<Integer>> res, List<Integer> path) {
        path.add(root.value); // 這邊先加上root值，後面就要記得回溯

        // 找到葉節點
        if (root.left == null && root.right == null) {
            // 驗證目前target是否可以消耗完，可以消耗完表示找到一條路徑
            if (target - root.value == 0) {
                res.add(new ArrayList<>(path));
            }
            return; // 如果和不為target，直接返回
        }

        if (root.left != null) {
            process(root.left, target - root.value, res, path);
            path.remove(path.size() - 1); // 回溯移除之前加上的root值
        }

        if (root.right != null) {
            process(root.right, target - root.value, res, path);
            path.remove(path.size() - 1); // 回溯移除之前加上的root值
        }
    }


    // 另一種解法
    List<List<Integer>> result;
    List<Integer> curPath;

    public List<List<Integer>> pathSum2(TreeNode root, int target) {
        result = new LinkedList<>();
        curPath = new LinkedList<>();
        travesal(root, target);
        return result;
    }

    private void travesal(TreeNode root, int target) {
        if (root == null) return;

        curPath.add(root.value);  // 這邊先加上root值，後面就要記得回溯
        target -= root.value;

        // 找到葉節點
        if (root.left == null && root.right == null && target == 0) {
            result.add(new LinkedList<>(curPath));
        }

        travesal(root.left, target);
        travesal(root.right, target);
        curPath.remove(curPath.size() - 1); // 回溯移除之前加上的root值
    }
}
