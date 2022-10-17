package FuckingAlgorithm.LinkedList;

public class Q09_Palindrome {
//    https://leetcode.cn/problems/palindrome-linked-list/
//    234. 回文鏈表
//    給你一個單鏈表的頭節點 head ，請你判斷該鏈表是否為回文鏈表。如果是，返回 true ；否則，返回 false 。

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    boolean isPalindrome(ListNode head) {
        // 快慢指針找到鏈表的中點
        ListNode slow, fast;
        slow = fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow 指針現在指向鏈表中點
        // 如果fast指針沒有指向null，說明鏈表長度為奇數，slow還要再前進一步：
        if (fast != null) slow = slow.next;

        // 從slow開始反轉後面的鏈表，現在就可以開始比較回文串了：
        ListNode left = head;
        ListNode right = reverse(slow);
        while (right != null) {
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }

        return true;
    }

    private ListNode reverse(ListNode head) {
        ListNode pre = null, next = null, cur = head;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

}
