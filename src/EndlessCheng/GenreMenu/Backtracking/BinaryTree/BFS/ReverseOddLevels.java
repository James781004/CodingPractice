package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class ReverseOddLevels {

    // https://leetcode.cn/problems/reverse-odd-levels-of-binary-tree/solutions/1831556/zhi-jie-jiao-huan-zhi-by-endlesscheng-o8ze/
    public TreeNode reverseOddLevels(TreeNode root) {
        List<TreeNode> que = new ArrayList<>();
        que.add(root);
        boolean odd = false;
        while (!que.isEmpty()) {
            List<TreeNode> tmp = new ArrayList<>(que);
            if (odd) {
                int left = 0, right = tmp.size() - 1;
                while (left < right) {
                    int tmpVal = tmp.get(left).val;
                    tmp.get(left).val = tmp.get(right).val;
                    tmp.get(right).val = tmpVal;
                    left++;
                    right--;
                }
            }
            que = new ArrayList<>();
            for (int i = 0; i < tmp.size(); i++) {
                TreeNode curr = tmp.get(i);
                if (curr.left != null)
                    que.add(curr.left);
                if (curr.right != null)
                    que.add(curr.right);
            }
            odd = !odd;
        }
        return root;
    }


    public TreeNode reverseOddLevelsDFS(TreeNode root) {
        if (root.left == null) return root;
        dfs(root.left, root.right, 1);
        return root;
    }

    public void dfs(TreeNode left, TreeNode right, int h) {
        if (h % 2 == 1) {
            int t = left.val;
            left.val = right.val;
            right.val = t;
        }
        if (left.left != null) {
            dfs(left.left, right.right, h + 1);
            dfs(left.right, right.left, h + 1);
        }
    }

}
