package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

public class doubleIt {
    // https://leetcode.cn/problems/double-a-number-represented-as-a-linked-list/solutions/2385962/o1-kong-jian-zuo-fa-kan-cheng-shi-head-y-1dco/
    public ListNode doubleIt(ListNode head) {
        head = reverseList(head);
        ListNode res = double2(head); // 反轉後，就變成【2. 兩數相加】了
        return reverseList(res);
    }

    // 206. 反轉鏈表
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

    // 2. 兩數相加：自己和自己相加
    // 題解 https://leetcode.cn/problems/add-two-numbers/solution/dong-hua-jian-ji-xie-fa-cong-di-gui-dao-oe0di/
    private ListNode double2(ListNode l1) {
        ListNode dummy = new ListNode(); // 哨兵節點，作為新鏈表的頭節點的前一個節點
        ListNode cur = dummy;
        int carry = 0; // 進位
        while (l1 != null) {
            carry += l1.val * 2; // 節點值和進位加在一起
            cur.next = new ListNode(carry % 10); // 每個節點保存一個數位
            carry /= 10; // 新的進位
            cur = cur.next; // 下一個節點
            l1 = l1.next; // 下一個節點
        }
        if (carry != 0) {
            cur.next = new ListNode(carry);
        }
        return dummy.next; // 哨兵節點的下一個節點就是頭節點
    }


    public ListNode doubleIt2(ListNode head) {
        if (head.val > 4)
            head = new ListNode(0, head); // 如果鏈表頭的值大於 4，那麼需要在前面插入一個新的節點
        for (var cur = head; cur != null; cur = cur.next) {
            cur.val = cur.val * 2 % 10; // 如果不考慮進位，就是每個節點的值乘以 2
            if (cur.next != null && cur.next.val > 4)
                cur.val++; // 只有下一個節點大於 4 的時候，才會因為進位多加一
        }
        return head;
    }
}
