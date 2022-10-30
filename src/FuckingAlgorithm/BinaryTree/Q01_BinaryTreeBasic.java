package FuckingAlgorithm.BinaryTree;

public class Q01_BinaryTreeBasic {
//    https://leetcode.cn/problems/invert-binary-tree/
//    226. 翻轉二叉樹
//    給你一棵二叉樹的根節點 root ，翻轉這棵二叉樹，並返回其根節點。

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    TreeNode invertTree(TreeNode root) {
        // 遍歷二叉樹，交換每個節點的子節點
        traverse(root);
        return root;
    }

    private void traverse(TreeNode root) {
        if (root == null) return;

        /**** 前序位置 ****/
        // 每一個節點需要做的事就是交換它的左右子節點
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        // 遍歷框架，去遍歷左右子樹的節點
        traverse(root.left);
        traverse(root.right);
    }


    // 定義：將以 root 為根的這棵二叉樹翻轉，返回翻轉後的二叉樹的根節點
    TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }
        // 利用函數定義，先翻轉左右子樹
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        // 然後交換左右子節點
        root.left = right;
        root.right = left;

        // 和定義邏輯自恰：以 root 為根的這棵二叉樹已經被翻轉，返回 root
        return root;
    }


//    https://leetcode.cn/problems/populating-next-right-pointers-in-each-node/
//    116. 填充每個節點的下一個右側節點指針
//    給定一個 完美二叉樹 ，其所有葉子節點都在同一層，每個父節點都有兩個子節點。二叉樹定義如下：
//
//    struct Node {
//        int val;
//        Node *left;
//        Node *right;
//        Node *next;
//    }
//    填充它的每個 next 指針，讓這個指針指向其下一個右側節點。如果找不到下一個右側節點，則將 next 指針設置為 NULL。
//
//    初始狀態下，所有 next 指針都被設置為 NULL。

    class Node {
        int val;
        Node left;
        Node right;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    // 主函數
    Node connect(Node root) {
        if (root == null) return null;
        // 遍歷「三叉樹」，連接相鄰節點
        traverseNode(root.left, root.right);
        return root;
    }

    private void traverseNode(Node node1, Node node2) {
        if (node1 == null || node2 == null) return;

        /**** 前序位置 ****/
        // 將傳入的兩個節點穿起來
        node1.next = node2;

        // 連接相同父節點的兩個子節點
        traverseNode(node1.left, node1.right);
        traverseNode(node2.left, node2.right);

        // 連接跨越父節點的兩個子節點
        traverseNode(node1.right, node2.left);
    }


//    https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/
//    114. 二叉樹展開為鏈表
//    給你二叉樹的根結點 root ，請你將它展開為一個單鏈表：
//
//    展開後的單鏈表應該同樣使用 TreeNode ，其中 right 子指針指向鏈表中下一個結點，而左子指針始終為 null 。
//    展開後的單鏈表應該與二叉樹 先序遍歷 順序相同。

    // 定義：將以 root 為根的樹拉平為鏈表
    void flatten(TreeNode root) {
        if (root == null) return;

        // 利用定義，把左右子樹拉平
        flatten(root.left);
        flatten(root.right);

        /**** 後序遍歷位置 ****/
        // 1、左右子樹已經被拉平成一條鏈表
        TreeNode left = root.left;
        TreeNode right = root.right;

        // 2、將左子樹作為右子樹
        root.left = null;
        root.right = left;

        // 3、將原先的右子樹接到當前右子樹的末端
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;
    }
}
