package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RightSideView {

    // https://leetcode.cn/problems/binary-tree-right-side-view/solutions/214871/jian-dan-bfsdfs-bi-xu-miao-dong-by-sweetiee/
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (i == size - 1) {  // 將當前層的最後一個節點放入結果列表
                    res.add(node.val);
                }
            }
        }
        return res;
    }


    // DFS
    List<Integer> res = new ArrayList<>();

    public List<Integer> rightSideViewDFS(TreeNode root) {
        dfs(root, 0); // 從根節點開始訪問，根節點深度是0
        return res;
    }

    private void dfs(TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        // 先訪問 當前節點，再遞歸地訪問 右子樹 和 左子樹。
        if (depth == res.size()) {   // 如果當前節點所在深度還沒有出現在res裡，說明在該深度下當前節點是第一個被訪問的節點，因此將當前節點加入res中。
            res.add(root.val);
        }
        depth++;
        dfs(root.right, depth);
        dfs(root.left, depth);
    }


}
