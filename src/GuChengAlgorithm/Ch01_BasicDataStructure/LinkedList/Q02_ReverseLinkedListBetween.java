package GuChengAlgorithm.Ch01_BasicDataStructure.LinkedList;

public class Q02_ReverseLinkedListBetween {

    public ListNode reverseKGroup1(ListNode head, int k) {
        ListNode node = head;
        int count = 0;
        while (count < k) {
            if (node == null) return head;
            node = node.next;
            count++;
        }

        ListNode pre = reverseKGroup1(node, k);  // 遞歸處理後續剩下的
        ListNode next = null;

        // 直接反轉當前區間k個
        while (count-- > 0) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }


    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode node = head;
        int count = 0;
        while (count < k) {
            if (node == null) return head;
            node = node.next;
            count++;
        }

        ListNode newHead = reverse(head, k);  // 直接先反轉前k個
        head.next = reverseKGroup2(node, k);  // 遞歸處理後續剩下的
        return newHead;
    }

    private ListNode reverse(ListNode head, int k) {
        ListNode pre = null, cur = head, next = null;
        while (k-- > 0) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        int N = countLength(head);
        int i = 1;
        while (i + k < N + 1) {
            head = reverseBetween(head, i, i + k - 1);
            i += k;
        }
        return head;
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode cur = dummy.next;
        int i = 1;

        while (i < m) {
            pre = cur;
            cur = cur.next;
            i++;
        }

        ListNode node = pre;
        while (i++ <= n) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        node.next.next = cur;
        node.next = pre;
        return dummy.next;
    }


    private int countLength(ListNode head) {
        int count = 0;
        while (head != null) {
            head = head.next;
            count++;
        }
        return count;
    }


    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }
}
