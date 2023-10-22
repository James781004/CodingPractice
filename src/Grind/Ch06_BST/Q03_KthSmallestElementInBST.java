package Grind.Ch06_BST;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q03_KthSmallestElementInBST {
    // https://leetcode.cn/problems/kth-smallest-element-in-a-bst/solutions/1051120/gong-shui-san-xie-yi-ti-san-jie-pai-xu-y-8uah/
    // 遞歸
    public int kthSmallest(TreeNode root, int k) {
        // 利用 BST 的中序遍歷特性
        traverse(root, k);
        return res;
    }

    // 記錄結果
    int res = 0;
    // 記錄當前元素的排名
    int rank = 0;

    void traverse(TreeNode root, int k) {
        if (root == null) {
            return;
        }
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


    // 迭代
    public int kthSmallest2(TreeNode root, int k) {
        Deque<TreeNode> d = new ArrayDeque<>();
        while (root != null || !d.isEmpty()) {
            while (root != null) { // root 壓棧同時向左走，最後棧頂是最左節點
                d.addLast(root);
                root = root.left;
            }
            root = d.pollLast();
            if (--k == 0) return root.val; // 每次彈出棧頂--k
            root = root.right; // 接著向右走
        }
        return -1; // never
    }

}
