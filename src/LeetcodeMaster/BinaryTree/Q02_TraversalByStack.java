package LeetcodeMaster.BinaryTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Q02_TraversalByStack {
//    二叉樹的遞歸遍歷
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E9%80%92%E5%BD%92%E9%81%8D%E5%8E%86.md

//    每次寫遞歸，都按照這三要素來寫，可以保證大家寫出正確的遞歸算法！
//
//    1. 確定遞歸函數的參數和返回值： 確定哪些參數是遞歸的過程中需要處理的，那麼就在遞歸函數里加上這個參數，
//       並且還要明確每次遞歸的返回值是什麼進而確定遞歸函數的返回類型。
//
//    2. 確定終止條件： 寫完了遞歸算法, 運行的時候，經常會遇到棧溢出的錯誤，就是沒寫終止條件或者終止條件寫的不對，
//       操作系統也是用一個棧的結構來保存每一層遞歸的信息，如果遞歸沒有終止，操作系統的內存棧必然就會溢出。
//
//    3. 確定單層遞歸的邏輯： 確定每一層遞歸需要處理的信息。在這里也就會重覆調用自己來實現遞歸的過程。


    // 前序遍歷順序：中-左-右，入棧順序：中-右-左
    public List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur;
        stack.push(root);
        while (!stack.isEmpty()) {
            cur = stack.pop();
            res.add(cur.value);
            if (cur.right != null) {
                stack.push(cur.right);
            }

            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        return res;
    }

    // 中序遍歷順序: 左-中-右 入棧順序： 左-右
    public List<Integer> inOrderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        stack.push(root);
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                res.add(cur.value);
                cur = cur.right;
            }
        }
        return res;
    }

    // 後序遍歷順序 左-右-中 入棧順序：中-左-右 出棧順序：中-右-左， 最後翻轉結果
    public List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur;
        stack.push(root);
        while (!stack.isEmpty()) {
            cur = stack.pop();
            result.add(cur.value);

            if (cur.left != null) {
                stack.push(cur.left);
            }

            if (cur.right != null) {
                stack.push(cur.right);
            }
        }
        Collections.reverse(result);
        return result;
    }
}
