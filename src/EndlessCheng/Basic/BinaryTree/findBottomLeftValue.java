package EndlessCheng.Basic.BinaryTree;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class findBottomLeftValue {

    // https://leetcode.cn/problems/find-bottom-left-tree-value/solutions/2049776/bfs-wei-shi-yao-yao-yong-dui-lie-yi-ge-s-f34y/
    // BFS 這棵二叉樹，先把右兒子入隊，再把左兒子入隊，
    // 這樣最後一個出隊的節點就是左下角的節點了
    public int findBottomLeftValue(TreeNode root) {
        TreeNode node = root;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            node = q.poll();
            if (node.right != null) q.add(node.right);
            if (node.left != null) q.add(node.left);
        }
        return node.val;
    }


    // DFS 方法
    // 如果采用DFS，則我們遍歷的第一個深度最大的結點就是最底層第一個結點
    // 因為我們每次遍歷（先序、中序、後序）都是先遍歷左節點，再遍歷右節點
    private int ans = 0;
    private int maxHeight = 0;

    public int findBottomLeftValue2(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    public void dfs(TreeNode root, int height) {
        if (root == null) {
            return;
        }
        //深度+1
        height++;
        dfs(root.left, height);
        dfs(root.right, height);
        //當前深度大於目前最大深度 也就是記錄當前深度（層）的第一個左節點
        if (height > maxHeight) {
            maxHeight = height;
            ans = root.val;
        }
    }

}
