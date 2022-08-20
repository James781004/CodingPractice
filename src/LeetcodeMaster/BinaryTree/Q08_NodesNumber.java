package LeetcodeMaster.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class Q08_NodesNumber {
//    222.完全二叉樹的節點個數
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0222.%E5%AE%8C%E5%85%A8%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E8%8A%82%E7%82%B9%E4%B8%AA%E6%95%B0.md
//
//    給出一個完全二叉樹，求出該樹的節點個數。
//
//    示例 1：
//
//    輸入：root = [1,2,3,4,5,6]
//    輸出：6
//    示例 2：
//
//    輸入：root = []
//    輸出：0
//    示例 3：
//
//    輸入：root = [1]
//    輸出：1
//    提示：
//
//    樹中節點的數目範圍是[0, 5 * 10^4]
//            0 <= Node.val <= 5 * 10^4
//    題目數據保證輸入的樹是 完全二叉樹

    // 通用遞歸解法
    public int countNodes1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return countNodes1(root.left) + countNodes1(root.right) + 1;
    }

    // 叠代法
    public int countNodes2(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int res = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                res++;
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
        }
        return res;
    }

    /**
     * 針對完全二叉樹的解法
     * 滿二叉樹的結點數為：2^depth - 1
     */
    public int countNodes3(TreeNode root) {
        if (root == null) return 0;

        int leftDepth = getDepth(root.left);
        int rightDepth = getDepth(root.right);

        // 完全二叉樹只有兩種情況，情況一：就是滿二叉樹，情況二：最後一層葉子節點沒有滿。
        // 對於情況一，可以直接用 2^樹深度 - 1 來計算，注意這里根節點深度為1。
        // 對於情況二，分別遞歸左孩子，和右孩子，遞歸到某一深度一定會有左孩子或者右孩子為滿二叉樹，然後依然可以按照情況1來計算。
        if (leftDepth == rightDepth) {// 左子樹是滿二叉樹
            // 2^leftDepth其實是 （2^leftDepth - 1） + 1 ，左子樹 + 根結點
            return (1 << leftDepth) + countNodes3(root.right);
        } else { // 右子樹是滿二叉樹
            return (1 << rightDepth) + countNodes3(root.left);
        }
    }

    // 完全二叉樹最深的點一定在最左邊
    private int getDepth(TreeNode root) {
        int depth = 0;
        while (root != null) {
            root = root.left;
            depth++;
        }
        return depth;
    }

}
