package EndlessCheng.GenreMenu.Backtracking.BinaryTree.LCA;

import EndlessCheng.TreeNode;

public class GetDirections {

    // https://leetcode.cn/problems/step-by-step-directions-from-a-binary-tree-node-to-another/solutions/1139430/bfs-zuo-fa-by-endlesscheng-dfcf/
    public String getDirections(TreeNode root, int startValue, int destValue) {
        //找到最近公共祖先節點
        TreeNode lca = dfs(root, startValue, destValue);

        // 2️⃣ 從 LCA 到 startValue 的路徑
        StringBuilder pathToStart = new StringBuilder();
        findPath(lca, startValue, pathToStart);

        // 3️⃣ 從 LCA 到 destValue 的路徑
        StringBuilder pathToDest = new StringBuilder();
        findPath(lca, destValue, pathToDest);

        // 4️⃣ 計算最終路徑
        // pathToStart 需要變成 "U"（上移），然後拼接 pathToDest
        return "U".repeat(pathToStart.length()) + pathToDest.toString();
    }

    private TreeNode dfs(TreeNode node, int startValue, int destValue) {
        if (node == null || node.val == startValue || node.val == destValue) {
            return node;
        }

        TreeNode left = dfs(node.left, startValue, destValue);
        TreeNode right = dfs(node.right, startValue, destValue);

        if (left != null && right != null) {
            return node;
        }

        return left != null ? left : right;
    }

    // 🔍 遞歸查找目標節點，並記錄路徑
    private boolean findPath(TreeNode node, int target, StringBuilder path) {
        if (node == null) return false; // 遍歷到底，返回 false
        if (node.val == target) return true; // 找到目標，返回 true

        // 先嘗試左子樹
        path.append("L");
        if (findPath(node.left, target, path)) return true;
        path.deleteCharAt(path.length() - 1); // 回溯

        // 再嘗試右子樹
        path.append("R");
        if (findPath(node.right, target, path)) return true;
        path.deleteCharAt(path.length() - 1); // 回溯

        return false; // 目標不在該子樹
    }
}
