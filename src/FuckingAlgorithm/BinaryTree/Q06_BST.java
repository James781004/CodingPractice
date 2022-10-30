package FuckingAlgorithm.BinaryTree;

public class Q06_BST {
//    https://leetcode.cn/problems/kth-smallest-element-in-a-bst/
//    230. 二叉搜索樹中第K小的元素
//    給定一個二叉搜索樹的根節點 root ，和一個整數 k ，請你設計一個算法查找其中第 k 個最小元素（從 1 開始計數）。

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

    class kthSmallest {
        // 記錄結果
        int res = 0;
        // 記錄當前元素的排名
        int rank = 0;

        int kthSmallest(TreeNode root, int k) {
            // 利用 BST 的中序遍歷特性
            traverse(root, k);
            return res;
        }

        void traverse(TreeNode root, int k) {
            if (root == null) return;

            traverse(root.left, k);

            /* 中序遍歷代碼位置 */
            rank++;
            if (k == rank) {
                // 找到第 k 小的元素
                res = root.val;
                return;
            }
            /*****************/

            traverse(root.right, k);
        }
    }


//    https://leetcode.cn/problems/convert-bst-to-greater-tree/
//    538. 把二叉搜索樹轉換為累加樹
//    給出二叉 搜索 樹的根節點，該樹的節點值各不相同，請你將其轉換為累加樹（Greater Sum Tree），使每個節點 node 的新值等於原樹中大於或等於 node.val 的值之和。
//
//    提醒一下，二叉搜索樹滿足下列約束條件：
//
//    節點的左子樹僅包含鍵 小於 節點鍵的節點。
//    節點的右子樹僅包含鍵 大於 節點鍵的節點。
//    左右子樹也必須是二叉搜索樹。
//    注意：本題和 1038: https://leetcode-cn.com/problems/binary-search-tree-to-greater-sum-tree/ 相同

    class convertBST {
        TreeNode convertBST(TreeNode root) {
            traverse(root);
            return root;
        }

        // 記錄累加和
        int sum = 0;

        void traverse(TreeNode root) {
            if (root == null) return;
            traverse(root.right);
            // 維護累加和
            sum += root.val;
            // 將 BST 轉化成累加樹
            root.val = sum;
            traverse(root.left);
        }
    }
}
