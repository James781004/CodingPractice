package Grind.Ch10_LinkedList;

public class Q10_SortList {
    // https://leetcode.cn/problems/sort-list/solutions/437400/pai-xu-lian-biao-di-gui-die-dai-xiang-jie-by-cherr/
    /* 知識點1：歸並排序的整體思想
     * 知識點2：找到一個鏈表的中間節點的方法
     * 知識點3：合並兩個已排好序的鏈表為一個新的有序鏈表
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head; // 慢指針
        ListNode fast = head.next; // 快指針

        while (fast != null && fast.next != null) { // 快慢指針找到鏈表中點
            slow = slow.next; // 慢指針走一步
            fast = fast.next.next; // 快指針走兩步
        }
        ListNode rightHead = slow.next; // 鏈表第二部分的頭節點
        slow.next = null; // cut 鏈表

        ListNode left = sortList(head); // 遞歸排序前一段鏈表
        ListNode right = sortList(rightHead); // 遞歸排序後一段鏈表
        return merge(left, right);
    }

    public ListNode merge(ListNode h1, ListNode h2) { // 合並兩個有序鏈表
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        while (h1 != null && h2 != null) {
            if (h1.val < h2.val) {
                p.next = h1;
                h1 = h1.next;
            } else {
                p.next = h2;
                h2 = h2.next;
            }
            p = p.next;
        }
        if (h1 != null) p.next = h1;
        else if (h2 != null) p.next = h2;
        return dummy.next;
    }


    // 迭代
    public ListNode sortList2(ListNode head) {
        int length = getLength(head);
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        for (int step = 1; step < length; step *= 2) { // 依次將鏈表分成1塊，2塊，4塊...
            // 每次變換步長，pre指針和cur指針都初始化在鏈表頭
            ListNode pre = dummy;
            ListNode cur = dummy.next;
            while (cur != null) {
                ListNode h1 = cur; // 第一部分頭 （第二次循環之後，cur為剩余部分頭，不斷往後把鏈表按照步長step分成一塊一塊...）
                ListNode h2 = split(h1, step);  // 第二部分頭
                cur = split(h2, step); // 剩余部分的頭
                ListNode temp = merge(h1, h2); // 將一二部分排序合並
                pre.next = temp; // 將前面的部分與排序好的部分連接
                while (pre.next != null) {
                    pre = pre.next; // 把pre指針移動到排序好的部分的末尾
                }
            }
        }
        return dummy.next;
    }

    public int getLength(ListNode head) {
        // 獲取鏈表長度
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    public ListNode split(ListNode head, int step) {
        // 斷鏈操作 返回第二部分鏈表頭
        if (head == null) return null;
        ListNode cur = head;
        for (int i = 1; i < step && cur.next != null; i++) {
            cur = cur.next;
        }
        ListNode right = cur.next;
        cur.next = null; // 切斷連接
        return right;
    }
}
