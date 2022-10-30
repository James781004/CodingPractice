package FuckingAlgorithm.BinaryTree;

public class Q07_BSTBasic {

    class TreeNode {
        int val;
        // 以該節點為根的樹的節點總數
        int size;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    //    https://leetcode.cn/problems/validate-binary-search-tree/
//    98. 驗證二叉搜索樹
//    給你一個二叉樹的根節點 root ，判斷其是否是一個有效的二叉搜索樹。
//
//    有效 二叉搜索樹定義如下：
//
//    節點的左子樹只包含 小於 當前節點的數。
//    節點的右子樹只包含 大於 當前節點的數。
//    所有左子樹和右子樹自身必須也是二叉搜索樹。

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    /* 限定以 root 為根的子樹節點必須滿足 max.val > root.val > min.val */
    private boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        // base case
        if (root == null) return true;
        // 若 root.val 不符合 max 和 min 的限制，說明不是合法 BST
        if (min != null && root.val <= min.val) return false;
        if (max != null && root.val >= max.val) return false;
        // 限定左子樹的最大值是 root.val，右子樹的最小值是 root.val
        return isValidBST(root.left, min, root)
                && isValidBST(root.right, root, max);
    }


    //    https://leetcode.cn/problems/search-in-a-binary-search-tree/
//    700. 二叉搜索樹中的搜索
//    給定二叉搜索樹（BST）的根節點 root 和一個整數值 val。
//
//    你需要在 BST 中找到節點值等於 val 的節點。 返回以該節點為根的子樹。 如果節點不存在，則返回 null 。
    public TreeNode searchBST(TreeNode root, int target) {
        if (root == null) return null;
        if (root.val > target) return searchBST(root.left, target);
        if (root.val < target) return searchBST(root.right, target);
        return root;
    }

//    https://leetcode.cn/problems/insert-into-a-binary-search-tree/
//    701. 二叉搜索樹中的插入操作
//    給定二叉搜索樹（BST）的根節點 root 和要插入樹中的值 value ，將值插入二叉搜索樹。 
//    返回插入後二叉搜索樹的根節點。 輸入數據 保證 ，新值和原始二叉搜索樹中的任意節點值都不同。
//
//    注意，可能存在多種有效的插入方式，只要樹在插入後仍保持為二叉搜索樹即可。 你可以返回 任意有效的結果 。

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (root.val < val) {
            root.right = insertIntoBST(root.right, val);
        }
        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        }

        return root;
    }


//    https://leetcode.cn/problems/delete-node-in-a-bst/
//    450. 刪除二叉搜索樹中的節點
//    給定一個二叉搜索樹的根節點 root 和一個值 key，刪除二叉搜索樹中的 key 對應的節點，並保證二叉搜索樹的性質不變。
//    返回二叉搜索樹（有可能被更新）的根節點的引用。
//
//    一般來說，刪除節點可分為兩個步驟：
//
//    首先找到需要刪除的節點；
//    如果找到了，刪除它。

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (root.val == key) {
            // 這兩個 if 把情況 1 和 2 都正確處理了
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            // 處理情況 3
            // 獲得右子樹最小的節點
            TreeNode minNode = getMin(root.right);
            // 刪除右子樹最小的節點
            root.right = deleteNode(root.right, minNode.val);
            // 用右子樹最小的節點替換 root 節點
            minNode.left = root.left;
            minNode.right = root.right;
            root = minNode;
        } else if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        }
        return root;
    }

    TreeNode getMin(TreeNode node) {
        // BST 最左邊的就是最小的
        while (node.left != null) node = node.left;
        return node;
    }
}
