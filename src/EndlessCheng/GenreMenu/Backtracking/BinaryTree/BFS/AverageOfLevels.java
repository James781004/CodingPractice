package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class AverageOfLevels {

    // https://leetcode.cn/problems/average-of-levels-in-binary-tree/solutions/859789/dai-ma-sui-xiang-lu-wo-yao-da-shi-ge-er-cbxt1/

    /**
     * 解法：隊列，迭代。
     * 每次返回每層的最後一個字段即可。
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> list = new ArrayList<>();
        Deque<TreeNode> que = new LinkedList<>();

        if (root == null) {
            return list;
        }

        que.offerLast(root);
        while (!que.isEmpty()) {
            TreeNode peek = que.peekFirst();

            int levelSize = que.size();
            double levelSum = 0.0;
            for (int i = 0; i < levelSize; i++) {
                TreeNode poll = que.pollFirst();

                levelSum += poll.val;

                if (poll.left != null) {
                    que.addLast(poll.left);
                }
                if (poll.right != null) {
                    que.addLast(poll.right);
                }
            }
            list.add(levelSum / levelSize);
        }
        return list;
    }


}
