package FuckingAlgorithm.LinkedList;

public class Q06_DetectCycle {
//    判斷鏈表是否包含環

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }


    boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head; // 快慢指針初始化指向 head
        while (fast != null && fast.next != null) { // 快指針走到末尾時停止
            slow = slow.next;
            fast = fast.next.next;
            // 快慢指針相遇，說明含有環
            if (slow == fast) {
                return true;
            }
        }

        // fast走完鏈表，表示不包含環
        return false;
    }

    // 環的起點
    ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            // 快慢指針相遇，說明含有環，跳出迴圈
            if (slow == fast) break;
        }

        // fast 遇到空指針說明沒有環
        if (fast != null || fast.next != null) return null;

        // 重新指向頭結點
        slow = head;

        // 快慢指針同步前進，相交點就是環起點
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }

}
