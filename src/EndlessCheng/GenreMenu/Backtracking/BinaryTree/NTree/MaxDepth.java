package EndlessCheng.GenreMenu.Backtracking.BinaryTree.NTree;

import EndlessCheng.Node;

public class MaxDepth {

    // https://leetcode.cn/problems/maximum-depth-of-n-ary-tree/solutions/1111562/pythonjavajavascriptgo-di-gui-by-himymbe-lu2y/
    public int maxDepth(Node root) {
        if (root == null)
            return 0;
        int ans = 0;
        for (Node child : root.children)
            ans = Math.max(ans, maxDepth(child));
        return ans + 1;
    }


}
