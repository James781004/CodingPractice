package EndlessCheng.GenreMenu.Backtracking.LinkedList.Insert;

import EndlessCheng.ListNode;

public class InsertGreatestCommonDivisors {

    // https://leetcode.cn/problems/insert-greatest-common-divisors-in-linked-list/solutions/2592797/jian-ji-xie-fa-pythonjavacgojsrust-by-en-i1rn/
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        for (ListNode cur = head; cur.next != null; cur = cur.next.next) {
            // 當前節點 cur 後面插入 gcd 節點，同時 gcd 節點指向 cur 的下一個節點
            // 插入後，cur 更新為 cur.next.next，也就是 cur 原來的下一個節點，開始下一輪循環
            cur.next = new ListNode(gcd(cur.val, cur.next.val), cur.next);
        }
        return head;
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int temp = a;
            a = b % a;
            b = temp;
        }
        return b;
    }

}
