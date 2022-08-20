package LeetcodeMaster.BinaryTree;

public class Q24_DeleteBST {
//    450.
//    刪除二叉搜索樹中的節點
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0450.%E5%88%A0%E9%99%A4%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E4%B8%AD%E7%9A%84%E8%8A%82%E7%82%B9.md
//
//    給定一個二叉搜索樹的根節點 root
//    和一個值 key，
//    刪除二叉搜索樹中的 key
//    對應的節點，並保證二叉搜索樹的性質不變。返回二叉搜索樹（有可能被更新）的根節點的引用。
//
//    一般來說，刪除節點可分為兩個步驟：
//
//    首先找到需要刪除的節點；如果找到了，刪除它。說明：
//
//    要求算法時間覆雜度為 $O(h)$，
//    h 為樹的高度。


    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return root;

        if (root.value == key) {
            if (root.left == null) { // 左節點為空，直接返回右節點
                return root.right;
            } else if (root.right == null) { // 右節點為空，直接返回左節點
                return root.left;
            } else {
                // 左右節點都不為空，則將刪除節點的左子樹放到刪除節點的右子樹的最左面節點的左孩子的位置
                // 並返回刪除節點右孩子為新的根節點。
                TreeNode cur = root.right;
                while (cur.left != null) {
                    cur = cur.left;
                }
                cur.left = root.left; // 把要刪除的節點（root）左子樹放在cur的左孩子的位置
                root = root.right; // 返回舊root的右孩子作為新root
                return root;
            }
        }

        if (root.value > key) root.left = deleteNode(root.left, key); // 重建左子樹
        if (root.value < key) root.right = deleteNode(root.right, key); // 重建右子樹
        return root;
    }


    public TreeNode deleteNode2(TreeNode root, int key) {
        root = process(root, key);
        return root;
    }

    private TreeNode process(TreeNode root, int key) {
        if (root == null) return null;
        if (root.value > key) {
            root.left = process(root.left, key);
        } else if (root.value < key) {
            root.right = process(root.right, key);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            TreeNode tmp = root.right;
            while (tmp.left != null) {
                tmp = tmp.left;
            }
            root.value = tmp.value;
            root.right = process(root.right, tmp.value);
        }

        return root;
    }
}
