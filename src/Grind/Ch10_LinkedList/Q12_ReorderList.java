package Grind.Ch10_LinkedList;

public class Q12_ReorderList {
    // https://leetcode.cn/problems/reorder-list/solutions/1999276/mei-xiang-ming-bai-yi-ge-shi-pin-jiang-t-u66q/
    public void reorderList(ListNode head) {
        ListNode mid = middleNode(head);
        ListNode head2 = reverseList(mid);
        while (head2.next != null) {
            // 1. 保存next節點
            ListNode nxt = head.next;
            ListNode nxt2 = head2.next;

            // 2. 將右鏈表的第一個節點插入到左鏈表中
            // 左鏈表：1->2->3 右鏈表：5->4
            // 合並後的左鏈表：1->5->2->3
            head.next = head2;
            head2.next = nxt;

            // 3. 移動left和right指針
            // 左鏈表變為：2->3 右鏈表變為：4，然後繼續接下來循環
            head = nxt;
            head2 = nxt2;
        }
    }

    // 876. 鏈表的中間節點
    private ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 206. 反轉鏈表
    private ListNode reverseList(ListNode head) {
        ListNode pre = null, cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

}
