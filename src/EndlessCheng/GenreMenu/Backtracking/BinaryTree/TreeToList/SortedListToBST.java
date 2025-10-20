package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeToList;

import EndlessCheng.ListNode;
import EndlessCheng.TreeNode;

public class SortedListToBST {

    // https://leetcode.cn/problems/convert-sorted-list-to-binary-search-tree/solutions/379262/jian-dan-di-gui-bi-xu-miao-dong-by-sweetiee-3/
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return new TreeNode(head.val);
        }

        // 快慢指針找中心節點
        ListNode p = head, q = head, pre = null;
        while (q != null && q.next != null) {
            pre = p;
            p = p.next;
            q = q.next.next;
        }
        pre.next = null;

        // 以升序鏈表的中間元素作為根節點 root，遞歸的構建 root 的左子樹與右子樹。
        TreeNode root = new TreeNode(p.val);
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(p.next);
        return root;
    }


}
