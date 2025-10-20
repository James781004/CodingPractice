package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class IsEvenOddTree {

    // https://leetcode.cn/problems/even-odd-tree/solutions/1175601/tong-ge-lai-shua-ti-la-yi-ti-liang-jie-c-2m9r/
    public boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // 初始為偶數層
        boolean even = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            // 上一個節點的值，針對偶數層和奇數層設置不一樣的初始值
            int last = even ? -1 : 1000001;
            // 遍歷一層
            while (size-- > 0) {
                TreeNode node = queue.poll();

                // 檢查函數
                if (!check(even, node.val, last)) {
                    return false;
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                last = node.val;
            }
            even = !even;
        }
        return true;
    }

    private boolean check(boolean even, int val, int last) {
        // 偶數層：全部為奇數且嚴格遞增
        // 奇數層：全部為偶數且嚴格遞減
        return even ? val % 2 == 1 && last < val : val % 2 == 0 && last > val;
    }

}
