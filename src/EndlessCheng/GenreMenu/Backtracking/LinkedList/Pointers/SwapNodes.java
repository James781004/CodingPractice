package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class SwapNodes {
    
    // https://leetcode.cn/problems/swapping-nodes-in-a-linked-list/solutions/809613/java-zhi-jiao-huan-he-jie-dian-jiao-huan-dmbm/
    public ListNode swapNodes(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;// 因為頭結點可能會發生交換，所以要構造一個啞結點
        ListNode pre1 = dummy;// pre1指向第k個節點的前一個節點
        ListNode left = dummy.next;// 第k個節點
        ListNode pre2 = dummy;// pre2指向倒數第k個節點的前一個節點
        ListNode right = dummy.next;// 倒數第k個節點
        for (int i = 1; i < k; i++) {
            pre1 = pre1.next;
            left = left.next;
        }
        ListNode cur = left;
        ListNode temp = left.next;// 第k個節點的後一個節點
        while (cur.next != null) {
            pre2 = pre2.next;
            right = right.next;
            cur = cur.next;
        }
        if (right == pre1) {// 特殊情況，倒數第k個節點在第k個節點的左側
            right.next = temp;
            left.next = right;
            pre2.next = left;
        } else {
            left.next = right.next;
            if (pre2 == left) {
                right.next = left;
            }// 特殊情況，第k個節點在倒數第k個節點的左側
            else {
                pre2.next = left;
                right.next = temp;
            }
            pre1.next = right;
        }
        return dummy.next;
    }


}
