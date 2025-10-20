package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class Partition {

    // https://leetcode.cn/problems/partition-list/solutions/2362068/86-fen-ge-lian-biao-shuang-zhi-zhen-qing-hha7/
    public ListNode partition(ListNode head, int x) {
        ListNode smlDummy = new ListNode(0), bigDummy = new ListNode(0);
        ListNode sml = smlDummy, big = bigDummy;
        while (head != null) {
            if (head.val < x) {
                sml.next = head;
                sml = sml.next;
            } else {
                big.next = head;
                big = big.next;
            }
            head = head.next;
        }
        sml.next = bigDummy.next;
        big.next = null;
        return smlDummy.next;
    }

}
