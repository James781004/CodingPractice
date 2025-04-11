package EndlessCheng.Basic.LinkedList;


import EndlessCheng.ListNode;

public class reverseList {
    public ListNode reverseList(ListNode head) {
        // 邊緣條件判斷
        if (head == null) return null;
        if (head.next == null) return head;

        // 遞歸調用，翻轉第二個節點開始往後的鏈表
        ListNode last = reverseList(head.next);
        // 翻轉頭節點與第二個節點的指向
        head.next.next = head;
        // 此時的 head 節點為尾節點，next 需要指向 NULL
        head.next = null;
        return last;
    }


    public ListNode reverseList2(ListNode head) {
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
