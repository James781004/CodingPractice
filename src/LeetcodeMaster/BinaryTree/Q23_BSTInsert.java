package LeetcodeMaster.BinaryTree;

public class Q23_BSTInsert {
//    701.二叉搜索樹中的插入操作
//     https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0701.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E4%B8%AD%E7%9A%84%E6%8F%92%E5%85%A5%E6%93%8D%E4%BD%9C.md
//
//    給定二叉搜索樹（BST）的根節點和要插入樹中的值，將值插入二叉搜索樹。 返回插入後二叉搜索樹的根節點。 輸入數據保證，新值和原始二叉搜索樹中的任意節點值都不同。
//
//    注意，可能存在多種有效的插入方式，只要樹在插入後仍保持為二叉搜索樹即可。 你可以返回任意有效的結果。

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        TreeNode node = root;
        TreeNode pre = root; // 使用pre找尋適合進行插入操作的前驅節點
        while (root != null) {
            pre = root;
            if (root.value > val) {
                root = root.left;
            } else if (root.value < val) {
                root = root.right;
            }
        }

        // 這階段root會走到null，pre會停留在值最接近val的位置，根據BST規則插入新節點即可
        if (pre.value > val) {
            pre.left = new TreeNode(val);
        } else {
            pre.right = new TreeNode(val);
        }

        return node;
    }

    public TreeNode insertIntoBST2(TreeNode root, int val) {
        if (root == null) return new TreeNode(val); // 如果當前節點為空，也就意味著val找到了合適的位置，此時創建節點直接返回。
        if (root.value < val) {
            root.right = insertIntoBST2(root.right, val); // 遞歸創建右子樹
        } else if (root.value > val) {
            root.left = insertIntoBST(root.left, val); // 遞歸創建左子樹
        }

        return root;
    }

}
