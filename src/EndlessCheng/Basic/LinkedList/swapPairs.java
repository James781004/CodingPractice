package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

public class swapPairs {
    // https://leetcode.cn/problems/swap-nodes-in-pairs/solutions/2374872/tu-jie-die-dai-di-gui-yi-zhang-tu-miao-d-51ap/
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode node1 = head;
        ListNode node2 = head.next;
        ListNode node3 = node2.next;

        node1.next = swapPairs(node3); // 1 指向遞歸返回的鏈表頭
        node2.next = node1; // 2 指向 1

        return node2; // 返回交換後的鏈表頭節點
    }


    public ListNode swapPairs2(ListNode head) {
        ListNode dummy = new ListNode(0, head); // 用哨兵節點簡化代碼邏輯
        ListNode node0 = dummy;
        ListNode node1 = head;
        while (node1 != null && node1.next != null) { // 至少有兩個節點
            ListNode node2 = node1.next;
            ListNode node3 = node2.next;

            node0.next = node2; // 0 -> 2
            node2.next = node1; // 2 -> 1
            node1.next = node3; // 1 -> 3

            node0 = node1; // 下一輪交換，0 是 1
            node1 = node3; // 下一輪交換，1 是 3
        }
        return dummy.next; // 返回新鏈表的頭節點
    }

}
