package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TopDown;

import EndlessCheng.TreeNode;

public class LongestZigZag {

    // https://leetcode.cn/problems/longest-zigzag-path-in-a-binary-tree/solutions/2892767/javapython3cshen-du-you-xian-sou-suo-xi-scqgi/
    private int longest = 0;    // 記錄最長路徑

    public int longestZigZag(TreeNode root) {
        dfs(root, 0, true);     // 從根節點開始，到達根節點時的長度為0，是否為左子節點不影響結果
        return longest;
    }

    /**
     * 遞歸搜索到達節點node時的交錯路徑長度length
     *
     * @param node：當前節點
     * @param length：到達該節點的交錯路徑長度
     * @param isLeft：轉移方向，該節點是否為其父節點的左子節點
     */
    private void dfs(TreeNode node, int length, boolean isLeft) {
        if (node == null) return;                    // 空節點直接返回
        longest = Math.max(longest, length);     // 更新最長路徑
        // 向左遞歸，如果當前節點為父節點的左子節點，那麼向左遞歸就是開辟一條新的交錯路徑，到達其左子節點長度為1
        // 否則就是接著之前的交錯路徑遞歸，長度+1
        dfs(node.left, isLeft ? 1 : length + 1, true);
        // 同理向右遞歸，如果當前節點為父節點的右子節點，那麼向右遞歸就是開辟一條新的交錯路徑，到達其右子節點長度為1
        // 否則就是接著之前的交錯路徑遞歸，長度+1
        dfs(node.right, !isLeft ? 1 : length + 1, false);
    }

}
