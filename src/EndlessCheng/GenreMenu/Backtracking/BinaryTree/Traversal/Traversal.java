package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Traversal;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Traversal {

    // https://leetcode.cn/problems/binary-tree-postorder-traversal/solutions/367420/bang-ni-dui-er-cha-shu-bu-zai-mi-mang-che-di-chi-t/
    public List<Integer> preOrderReverse(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        preOrder(root, result);
        return result;
    }

    void preOrder(TreeNode root, ArrayList<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.val);           // 注意這一句
        preOrder(root.left, result);
        preOrder(root.right, result);
    }


    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    void inorder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        inorder(root.left, list);
        list.add(root.val);             // 注意這一句
        inorder(root.right, list);
    }


    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        postorder(root, res);
        return res;
    }

    void postorder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        postorder(root.left, list);
        postorder(root.right, list);
        list.add(root.val);             // 注意這一句
    }

}
