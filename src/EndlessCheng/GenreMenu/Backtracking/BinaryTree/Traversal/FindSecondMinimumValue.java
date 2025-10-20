package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Traversal;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class FindSecondMinimumValue {

    // https://leetcode.cn/problems/second-minimum-node-in-a-binary-tree/solutions/898485/gong-shui-san-xie-yi-ti-shuang-jie-shu-d-eupu/
    Set<Integer> set = new HashSet<>();

    public int findSecondMinimumValue(TreeNode root) {
        dfs(root);
//        bfs(root);
        if (set.size() < 2) return -1;
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int i : set) {
            if (i <= first) {
                second = first;
                first = i;
            } else if (i <= second) {
                second = i;
            }
        }
        return second;
    }

    void dfs(TreeNode root) {
        if (root == null) return;
        set.add(root.val);
        dfs(root.left);
        dfs(root.right);
    }

    void bfs(TreeNode root) {
        Deque<TreeNode> d = new ArrayDeque<>();
        d.addLast(root);
        while (!d.isEmpty()) {
            TreeNode poll = d.pollFirst();
            set.add(poll.val);
            if (poll.left != null) d.addLast(poll.left);
            if (poll.right != null) d.addLast(poll.right);
        }
    }

}
