package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeToList;

import EndlessCheng.ListNode;
import EndlessCheng.TreeNode;

public class IsSubPath {

    // https://leetcode.cn/problems/linked-list-in-binary-tree/solutions/3034003/dan-di-gui-xie-fa-pythonjavacgo-by-endle-00js/
    private ListNode head;

    public boolean isSubPath(ListNode head, TreeNode root) {
        this.head = head;
        return dfs(head, root);
    }

    private boolean dfs(ListNode s, TreeNode t) {
        if (s == null) { // 整個鏈表匹配完畢
            return true;
        }
        // 否則需要繼續匹配
        if (t == null) { // 無法繼續匹配
            return false;
        }
        // 節點值相同則繼續匹配，否則從 head 開始重新匹配
        return s.val == t.val && (dfs(s.next, t.left) || dfs(s.next, t.right)) ||
                s == head && (dfs(head, t.left) || dfs(head, t.right));
    }


}
