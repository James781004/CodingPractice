package EndlessCheng.GenreMenu.Backtracking.LinkedList.DivideAndConquer;

import EndlessCheng.ListNode;

public class SortList {

    // https://leetcode.cn/problems/sort-list/solutions/2993518/liang-chong-fang-fa-fen-zhi-die-dai-mo-k-caei/
    public ListNode sortList(ListNode head) {
        // 如果鏈表為空或者只有一個節點，無需排序
        if (head == null || head.next == null) {
            return head;
        }
        // 找到中間節點 head2，並斷開 head2 與其前一個節點的連接
        // 比如 head=[4,2,1,3]，那麼 middleNode 調用結束後 head=[4,2] head2=[1,3]
        ListNode head2 = middleNode(head);
        // 分治
        head = sortList(head);
        head2 = sortList(head2);
        // 合並
        return mergeTwoLists(head, head2);
    }

    // 876. 鏈表的中間結點（快慢指針）
    private ListNode middleNode(ListNode head) {
        ListNode pre = head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            pre = slow; // 記錄 slow 的前一個節點
            slow = slow.next;
            fast = fast.next.next;
        }
        pre.next = null; // 斷開 slow 的前一個節點和 slow 的連接
        return slow;
    }

    // 21. 合並兩個有序鏈表（雙指針）
    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(); // 用哨兵節點簡化代碼邏輯
        ListNode cur = dummy; // cur 指向新鏈表的末尾
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                cur.next = list1; // 把 list1 加到新鏈表中
                list1 = list1.next;
            } else { // 注：相等的情況加哪個節點都是可以的
                cur.next = list2; // 把 list2 加到新鏈表中
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 != null ? list1 : list2; // 拼接剩余鏈表
        return dummy.next;
    }

   
}
