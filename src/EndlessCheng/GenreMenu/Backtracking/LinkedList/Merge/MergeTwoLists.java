package EndlessCheng.GenreMenu.Backtracking.LinkedList.Merge;

import EndlessCheng.ListNode;

public class MergeTwoLists {

    // https://leetcode.cn/problems/merge-two-sorted-lists/solutions/2373691/liang-chong-fang-fa-die-dai-di-gui-pytho-wf75/
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
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


    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) return list2; // 注：如果都為空則返回空
        if (list2 == null) return list1;
        if (list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        }
        list2.next = mergeTwoLists(list1, list2.next);
        return list2;
    }

}
