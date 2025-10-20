package EndlessCheng.GenreMenu.Backtracking.BinaryTree.InsertDeleteTreeNode;

import EndlessCheng.TreeNode;

public class DeleteNode {

    // https://leetcode.cn/problems/delete-node-in-a-bst/solutions/2900339/javapython3cer-fen-cha-zhao-dfsgai-bian-nsz60/
    public TreeNode deleteNode(TreeNode root, int key) {
        return dfs(root, key);
    }

    /**
     * 二分查找節點值為key的節點
     * 如果找到，將這個節點的左子樹拼接到右子樹的最左節點上
     *
     * @param node: 當前處理節點
     * @param key:  查找值
     * @return: 處理後的節點
     */
    private TreeNode dfs(TreeNode node, int key) {
        if (node == null) return node;                // 空節點直接返回
        if (node.val < key) {
            node.right = dfs(node.right, key);    // 當前值較小，往右子樹更大的值查找，並更新右節點
        } else if (node.val > key) {
            node.left = dfs(node.left, key);      // 當前值較大，往左子樹更小的值查找，並更新左節點
        } else {
            // 找到目標值節點
            if (node.right == null) {
                return node.left;      // 右子節點不存在，直接返回左子節點代替這個節點作為這個節點的父節點的子節點
            }
            // 否則找到該節點右子樹上的最左子節點
            TreeNode node2 = node.right;
            while (node2.left != null) {
                node2 = node2.left;
            }
            node2.left = node.left;   // 把要刪除的節點（node）左子樹作為最左子節點的左子樹
            return node.right;         // 返回右子節點代替這個節點作為這個節點的父節點的子節點
        }
        return node;    // 非刪除節點，返回節點本身，相當不變
    }

}
