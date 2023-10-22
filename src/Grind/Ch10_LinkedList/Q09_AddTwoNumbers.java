package Grind.Ch10_LinkedList;

public class Q09_AddTwoNumbers {
    // https://leetcode.cn/problems/add-two-numbers/solutions/2327008/dong-hua-jian-ji-xie-fa-cong-di-gui-dao-oe0di/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
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


    // 遞歸
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        return addTwo(l1, l2, 0);
    }

    // l1 和 l2 為當前遍歷的節點，carry 為進位
    private ListNode addTwo(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null) // 遞歸邊界：l1 和 l2 都是空節點
            return carry != 0 ? new ListNode(carry) : null; // 如果進位了，就額外創建一個節點
        if (l1 == null) { // 如果 l1 是空的，那麼此時 l2 一定不是空節點
            l1 = l2;
            l2 = null; // 交換 l1 與 l2，保證 l1 非空，從而簡化代碼
        }
        carry += l1.val + (l2 != null ? l2.val : 0); // 節點值和進位加在一起
        l1.val = carry % 10; // 每個節點保存一個數位
        l1.next = addTwo(l1.next, (l2 != null ? l2.next : null), carry / 10); // 進位
        return l1;
    }

}
