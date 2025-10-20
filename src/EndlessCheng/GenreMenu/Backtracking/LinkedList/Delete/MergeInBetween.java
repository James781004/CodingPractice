package EndlessCheng.GenreMenu.Backtracking.LinkedList.Delete;

import EndlessCheng.ListNode;

public class MergeInBetween {

    // https://leetcode.cn/problems/merge-in-between-linked-lists/solutions/2082137/by-lcbin-0kxs/
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode p = list1, q = list1;
        while (--a > 0) {
            p = p.next;
        }
        while (b-- > 0) {
            q = q.next;
        }
        p.next = list2;
        while (p.next != null) {
            p = p.next;
        }
        p.next = q.next;
        q.next = null;
        return list1;
    }

}
