package LeetcodeMaster.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class Q12_LeftBottomValue {
//    513.找樹左下角的值
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0513.%E6%89%BE%E6%A0%91%E5%B7%A6%E4%B8%8B%E8%A7%92%E7%9A%84%E5%80%BC.md
//
//    給定一個二叉樹，在樹的最後一行找到最左邊的值。

    private int Deep = -1;
    private int value = 0;

    public int findLeftBottomValue(TreeNode root) {
        value = root.value;
        findLeftValue(root, 0);
        return value;
    }

    private void findLeftValue(TreeNode root, int curDepth) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            if (curDepth > Deep) { // 深度有增加時，保存第一個碰到的葉節點，因為在前序遍歷的情況下，他就是當前樹的最左節點
                value = root.value;
                Deep = curDepth;
            }
        }
        if (root.left != null) findLeftValue(root.left, curDepth + 1); // 回溯隱藏
        if (root.right != null) findLeftValue(root.right, curDepth + 1);
    }

    public int findLeftBottomValueByLevel(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int res = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) { // 每次loop循環都是一個新的level
                TreeNode poll = queue.poll();
                if (i == 0) {
                    res = poll.value; // 深度有變動時，保存第一個碰到的節點，因為他就是當前樹的最左葉節點
                }
                if (poll.left != null) queue.offer(poll.left);
                if (poll.right != null) queue.offer(poll.right);
            }
        }
        return res;
    }
}
