package Grind.Ch10_LinkedList;

public class Q03_ReverseLinkedList {
    // https://leetcode.cn/problems/reverse-linked-list/solutions/1718107/by-carlsun-2-uh1f/
    // 雙指針
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode temp = null;
        while (cur != null) {
            temp = cur.next;// 保存下一個節點
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }

    // 遞歸
    public ListNode reverseList2(ListNode head) {
        return reverse(null, head);
    }

    private ListNode reverse(ListNode prev, ListNode cur) {
        if (cur == null) {
            return prev;
        }
        ListNode temp = null;
        temp = cur.next;// 先保存下一個節點
        cur.next = prev;// 反轉
        // 更新prev、cur位置
        // prev = cur;
        // cur = temp;
        return reverse(cur, temp);
    }


    // 從後向前遞歸
    ListNode reverseList3(ListNode head) {
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

}
