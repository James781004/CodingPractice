package GuChengAlgorithm.Ch01_BasicDataStructure.LinkedList;

import java.util.Stack;

public class Q03_AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1), cur = dummy;
        int carry = 0, sum = 0;
        while (l1 != null || l2 != null) {
            sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            carry = sum / 10;
        }

        if (carry > 0) cur.next = new ListNode(carry);
        return dummy.next;
    }

    // recursive helper
    private void helper(ListNode l1, ListNode l2, ListNode cur, int carry) {
        if (l1 == null && l2 == null) {
            if (carry > 0) cur.next = new ListNode(carry);
            return;
        }

        if (l1 != null) {
            carry += l1.val;
            l1 = l1.next;
        }

        if (l2 != null) {
            carry += l2.val;
            l1 = l2.next;
        }

        cur.next = new ListNode(carry % 10);
        helper(l1, l2, cur.next, carry / 10);
    }


    // LC 445
    // 按位相加，用stack處理
    // stack處理的部份也可以直接把list整個反轉處理
    public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
        Stack<Integer> stack1 = new Stack<>();
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }

        Stack<Integer> stack2 = new Stack<>();
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }

        ListNode res = null;
        int carry = 0, sum = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int val1 = stack1.isEmpty() ? 0 : stack1.pop();
            int val2 = stack2.isEmpty() ? 0 : stack2.pop();
            sum = carry + val1 + val2;
            ListNode cur = new ListNode(sum % 10);
            carry = sum / 10;
            cur.next = res; // 這裡處理好的新數字是從頭加入鏈表
            res = cur;
        }

        return res;
    }


    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }
}
