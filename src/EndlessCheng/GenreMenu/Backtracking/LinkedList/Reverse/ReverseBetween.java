package EndlessCheng.GenreMenu.Backtracking.LinkedList.Reverse;

import EndlessCheng.ListNode;

public class ReverseBetween {

    // https://leetcode.cn/problems/reverse-linked-list-ii/solutions/1992226/you-xie-cuo-liao-yi-ge-shi-pin-jiang-tou-teqq/
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 1. 初始化一個虛擬頭節點，它的 next 是 head。
        ListNode dummy = new ListNode(0, head);

        ListNode cur = dummy;   // 操作指針

        // 2. 移動操作指針 left - 1 次，實際上是到了要反轉位置的前一個位置
        for (int i = 0; i < left - 1; i++) {
            cur = cur.next;
        }

        // 3. 第三部分：正常的一維反轉邏輯，但是 pre 節點不能到達 head 尾部了，而是到達反轉區域的下一個節點的位置。比如要反轉 [2,4]，那就移動到 5，方便原鏈表節點連接反轉後的鏈表。
        ListNode pre = cur.next;
        ListNode tail = null;

        for (int i = 0; i < right - left + 1; i++) {    // 原版的是while(pre != null)
            ListNode t = pre.next;
            pre.next = tail;
            tail = pre;
            pre = t;
        }

        // 4. 第四部分：拼接上反轉後的鏈表.
        // - cur 此時還在反轉區域的前一個節點，用它進行連接即可。
        // - 此時pre 節點在反轉區域的後面一個節點。tail 就在反轉區域的最後一個節點
        // 拼接：cur.next.next，實際上是操作反轉區域的第一個節點的 next 到 pre。然後是cur.next表示換成 tail節點，就拼好了。
        cur.next.next = pre;
        cur.next = tail;

        return dummy.next;
    }
}
