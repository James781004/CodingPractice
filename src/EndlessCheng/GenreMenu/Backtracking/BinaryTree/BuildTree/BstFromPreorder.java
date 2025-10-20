package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

public class BstFromPreorder {


    // https://leetcode.cn/problems/construct-binary-search-tree-from-preorder-traversal/solutions/2945592/shang-xian-fa-by-tian-xing-jian-6-mhtm/
    int i = 0;  // 全局索引變量，用於遍歷前序數組

    // 主函數，從給定的前序遍歷數組構建二叉搜索樹
    public TreeNode bstFromPreorder(int[] preorder) {
        // 調用輔助函數，初始最大值設為Integer.MAX_VALUE
        return insert(preorder, Integer.MAX_VALUE);
    }

    // 輔助函數，遞歸地將節點插入到二叉搜索樹中
    private TreeNode insert(int[] preorder, int max) {
        // 如果當前索引已經超出數組長度，返回null
        if (i == preorder.length) {
            return null;
        }
        // 獲取當前索引處的值
        int val = preorder[i];
        // 如果當前值大於允許的最大值，返回null
        if (val > max) {
            return null;
        }
        // 創建當前節點
        TreeNode node = new TreeNode(val);
        // 移動全局索引
        i++;
        // 遞歸構建左子樹，當前節點值作為新的最大值
        node.left = insert(preorder, val);
        // 遞歸構建右子樹，保持原來的最大值
        node.right = insert(preorder, max);
        // 返回當前節點
        return node;
    }

}
