package LeetcodeMaster.BinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q09_BinaryTreePaths {
//    257. 二叉樹的所有路徑
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0257.%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E6%89%80%E6%9C%89%E8%B7%AF%E5%BE%84.md
//
//    給定一個二叉樹，返回所有從根節點到葉子節點的路徑。
//
//    說明: 葉子節點是指沒有子節點的節點。

    public List<String> binaryTreePath1(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;
        List<Integer> paths = new ArrayList<>();
        traversal(root, paths, res);
        return res;
    }

    private void traversal(TreeNode root, List<Integer> paths, List<String> res) {
        paths.add(root.value); // 如果要繼續往更深處走，後面必須回溯

        // 找到葉節點
        if (root.left == null && root.right == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paths.size(); i++) {
                sb.append(paths.get(i)).append("->");
            }
            sb.append(paths.get(paths.size() - 1));
            res.add(sb.toString());
            return;
        }

        if (root.left != null) {
            traversal(root.left, paths, res);
            paths.remove(paths.size() - 1); // 回溯
        }

        if (root.right != null) {
            traversal(root.right, paths, res);
            paths.remove(paths.size() - 1); // 回溯
        }
    }

    public List<String> binaryTreePath2(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;
        StringBuilder path = new StringBuilder();
        traversal1(root, path, res);
        return res;
    }

    // 隱形回溯
    private void traversal1(TreeNode root, StringBuilder path, List<String> res) {
        path.append(root.value);

        // 找到葉節點
        if (root.left == null && root.right == null) {
            res.add(path.toString());
            return;
        }

        if (root.left != null) {
            traversal1(root.left, path.append("->"), res);
        }

        if (root.right != null) {
            traversal1(root.right, path.append("->"), res);
        }
    }

    public List<String> binaryTreePathStack(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;
        Stack<Object> stack = new Stack<>();

        // 節點和路徑同時進入stack
        stack.push(root);
        stack.push(root.value + "");

        while (!stack.isEmpty()) {
            String path = (String) stack.pop();
            TreeNode node = (TreeNode) stack.pop();

            // 找到葉節點
            if (node.left == null && node.right == null) {
                res.add(path);
            }

            // 因為使用stack，所以進入stack順序先右後左
            if (node.right != null) {
                stack.push(node.right);
                stack.push(path + "->" + node.right.value);
            }

            if (node.left != null) {
                stack.push(node.left);
                stack.push(path + "->" + node.left.value);
            }
        }
        return res;
    }
}
