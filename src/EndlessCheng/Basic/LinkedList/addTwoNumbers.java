package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

public class addTwoNumbers {
    // https://leetcode.cn/problems/add-two-numbers-ii/solutions/2328330/fan-zhuan-lian-biao-liang-shu-xiang-jia-okw6q/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverseList(l1);
        l2 = reverseList(l2); // l1 和 l2 反轉後，就變成【2. 兩數相加】了
        ListNode l3 = addTwo(l1, l2);
        return reverseList(l3);
    }

    // 視頻講解 https://www.bilibili.com/video/BV1sd4y1x7KN/
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

    private ListNode addTwo(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(); // 哨兵節點
        ListNode cur = dummy;
        int carry = 0; // 進位
        while (l1 != null || l2 != null || carry != 0) { // 有一個不是空節點，或者還有進位，就繼續迭代
            if (l1 != null) carry += l1.val; // 節點值和進位加在一起
            if (l2 != null) carry += l2.val; // 節點值和進位加在一起
            cur = cur.next = new ListNode(carry % 10); // 每個節點保存一個數位
            carry /= 10; // 新的進位
            if (l1 != null) l1 = l1.next; // 下一個節點
            if (l2 != null) l2 = l2.next; // 下一個節點
        }
        return dummy.next; // 哨兵節點的下一個節點就是頭節點
    }

}
