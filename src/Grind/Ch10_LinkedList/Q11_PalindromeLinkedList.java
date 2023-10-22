package Grind.Ch10_LinkedList;

import java.util.Stack;

public class Q11_PalindromeLinkedList {
    // https://leetcode.cn/problems/palindrome-linked-list/solutions/457312/di-gui-zhan-deng-3chong-jie-jue-fang-shi-zui-hao-d/
    // 反轉後半部分鏈表
    public boolean isPalindrome(ListNode head) {
        ListNode fast = head, slow = head;
        // 通過快慢指針找到中點
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 如果fast不為空，說明鏈表的長度是奇數個
        if (fast != null) {
            slow = slow.next;
        }
        // 反轉後半部分鏈表
        slow = reverse(slow);

        fast = head;
        while (slow != null) {
            // 然後比較，判斷節點值是否相等
            if (fast.val != slow.val)
                return false;
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    // 反轉鏈表
    public ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    // 使用棧解決
    public boolean isPalindromeStack(ListNode head) {
        if (head == null)
            return true;
        ListNode temp = head;
        Stack<Integer> stack = new Stack();
        // 鏈表的長度
        int len = 0;
        // 把鏈表節點的值存放到棧中
        while (temp != null) {
            stack.push(temp.val);
            temp = temp.next;
            len++;
        }
        // len長度除以2
        len >>= 1;
        // 然後再出棧
        while (len-- >= 0) {
            if (head.val != stack.pop())
                return false;
            head = head.next;
        }
        return true;
    }


    // 遞歸方式解決
    ListNode temp;

    public boolean isPalindrome3(ListNode head) {
        temp = head;
        return check(head);
    }

    private boolean check(ListNode head) {
        if (head == null)
            return true;

        // 對鏈表逆序打印
        boolean res = check(head.next) && (temp.val == head.val);
        temp = temp.next;
        return res;
    }

}
