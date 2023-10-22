package Grind.Ch07_BinaryTree;

public class Q04_LCA {
    // https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/solutions/625063/236-er-cha-shu-de-zui-jin-gong-gong-zu-x-tl5b/
    // 1. 求最小公共祖先，需要從底向上遍歷，那麼二叉樹，只能通過後序遍歷（即：回溯）實現從底向上的遍歷方式
    // 2. 在回溯的過程中，必然要遍歷整棵二叉樹，即使已經找到結果了，依然要把其他節點遍歷完，因為要使用遞歸函數的返回值（也就是代碼中的left和right）做邏輯判斷
    // 3. 要理解如果返回值left為空，right不為空為什麼要返回right，為什麼可以用返回right傳給上一層結果
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            // 只要當前根節點是p和q中的任意一個，就返回（因為不能比這個更深了，再深p和q中的一個就沒了）
            return root;
        }
        // 根節點不是p和q中的任意一個，那麼就繼續分別往左子樹和右子樹找p和q
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // p和q都沒找到，那就沒有
        if (left == null && right == null) {
            return null;
        }

        // 左子樹沒有p也沒有q，就返回右子樹的結果
        if (left == null) {
            return right;
        }
        
        // 右子樹沒有p也沒有q就返回左子樹的結果
        if (right == null) {
            return left;
        }

        // 左右子樹都找到p和q了，那就說明p和q分別在左右兩個子樹上，所以此時的最近公共祖先就是root
        return root;
    }
}
