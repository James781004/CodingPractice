package FuckingAlgorithm.LinkedList;

public class Q05_FindMiddleNode {
//    https://leetcode.cn/problems/middle-of-the-linked-list/
//    876. 鏈表的中間結點
//    給定一個頭結點為 head 的非空單鏈表，返回鏈表的中間結點。
//    如果有兩個中間結點，則返回第二個中間結點。

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }


    ListNode middleNode(ListNode head) {
        // 快慢指針初始化指向 head
        ListNode slow = head, fast = head;

        // 快指針走到末尾時停止
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 慢指針指向中點
        // 需要注意的是，如果鏈表長度為偶數，也就是說中點有兩個的時候，
        // 這個解法返回的節點是靠後的那個節點。
        return slow;
    }

}
