package GuChengAlgorithm.Ch01_BasicDataStructure.LinkedList;

import java.util.HashMap;
import java.util.Map;

public class Q06_RemoveLinkedListElements {

    // LC 203
    public ListNode removeElements1(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;
        while (cur.next != null) {
            if (cur.next.val == val) cur.next = cur.next.next;
            else cur = cur.next;
        }
        return dummy.next;
    }

    public ListNode removeElements2(ListNode head, int val) {
        if (head == null) return null;
        head.next = removeElements2(head.next, val);
        return head.val == val ? head.next : head;
    }


    // LC 83
    public ListNode deleteDuplicates1(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            while (cur.next != null && cur.val == cur.next.val) {
                cur.next = cur.next.next;
            }
            cur = cur.next;
        }
        return head;
    }


    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) return head;
        head.next = deleteDuplicates2(head.next);
        return head.val == head.next.val ? head.next : head;
    }


    // LC 82
    public ListNode deleteDuplicatesII(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy, cur = head;
        while (cur != null || cur.next != null) {
            if (cur.val == cur.next.val) { // 發現cur與後面節點有重複狀況時
                int temp = cur.val;
                while (cur != null && temp == cur.val) cur = cur.next; // cur後移到第一個不等值的節點
                pre.next = cur; // pre作為前驅節點，接上後移完畢的cur
            } else {
                pre = pre.next;
                cur = cur.next;
            }
        }
        return dummy.next;
    }


    // LC 19
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy, second = dummy;
        for (int i = 0; i <= n + 1; i++) {
            first = first.next;
        }

        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;

        return dummy.next;
    }


    public ListNode removeNthFromEndOnePass(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int length = 0;
        ListNode first = head;
        while (first != null) {
            length++;
            first = first.next;
        }

        length -= n;  // 鏈表總長減去n，表示first從dummy要移動到目標前驅節點的總距離
        first = dummy; // first移動回dummy
        while (length > 0) {
            length--;
            first = first.next;
        }
        first.next = first.next.next;

        return dummy.next;
    }


    // LC 1171
    public ListNode removeZeroSumSubLists(ListNode head) {
        int preFix = 0;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        Map<Integer, ListNode> map = new HashMap<>();
        map.put(0, dummy); // 初始化前綴表，方便處理第一組符合條件的preFix
        for (ListNode i = dummy; i != null; i = i.next) {
            preFix += i.val;
            map.put(preFix, i);  // 如果有相同preFix，表示兩點之間sub array和為0，這邊保留較後面的位置
        }

        preFix = 0;
        for (ListNode i = dummy; i != null; i = i.next) {
            preFix += i.val;
            i.next = map.get(preFix).next; // 跳過sub array和為0的區間，直接和後面的位置連接
        }

        return dummy.next;
    }


    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }
}
