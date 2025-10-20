package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DistanceK {

    // https://leetcode.cn/problems/all-nodes-distance-k-in-binary-tree/solutions/3087719/xi-you-yuan-su-863-er-cha-shu-zhong-suo-qfx6h/
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {

        // 先用樹來建圖，DFS遍歷樹，把父節點和子節點作為鄰接節點
        List<List<Integer>> graph = new ArrayList<>(501);
        for (int i = 0; i < 501; i++) {
            graph.add(new ArrayList<>());
        }
        dfs(root, null, graph);

        // 以「target」為起點，用BFS遍歷圖，計算路徑長度，當到達「k」時停止遍歷
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[501];
        queue.offer(target.val);
        visited[target.val] = true;
        while (k-- > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int u = queue.poll();
                for (int v : graph.get(u)) {
                    if (visited[v]) {
                        continue;
                    }
                    queue.offer(v);
                    visited[v] = true;
                }
            }
        }

        // 隊列中剩余的節點就是符合要求的節點
        List<Integer> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            ans.add(queue.poll());
        }
        return ans;
    }

    private void dfs(TreeNode node, TreeNode parent, List<List<Integer>> graph) {
        if (node == null) {
            return;
        }
        if (parent != null) {
            graph.get(node.val).add(parent.val);
        }
        if (node.left != null) {
            graph.get(node.val).add(node.left.val);
            dfs(node.left, node, graph);
        }
        if (node.right != null) {
            graph.get(node.val).add(node.right.val);
            dfs(node.right, node, graph);
        }
    }

}
