package EndlessCheng.GenreMenu.Backtracking.LinkedList.Traverse;

import EndlessCheng.ListNode;

public class MergeNodes {

    // https://leetcode.cn/problems/merge-nodes-in-between-zeros/solutions/1278727/jian-ji-xie-fa-by-endlesscheng-c4gf/
    public ListNode mergeNodes(ListNode head) {
        ListNode tail = head; // 把 head 當作答案鏈表的頭節點。一開始 head 也是答案鏈表的末尾節點 tail
        for (ListNode cur = head.next; cur.next != null; cur = cur.next) { // 從 head.next 開始遍歷鏈表
            if (cur.val != 0) { // 如果當前節點的值不為 0，則把節點值加到 tail.val 中
                tail.val += cur.val;
            } else { // 如果當前節點的值等於 0，則更新 tail 為 tail.next，然後把 tail.val 置為 0。
                tail = tail.next;
                tail.val = 0;
            }
        }
        tail.next = null; // 把 tail.next 置為空，以確保答案鏈表不包含原鏈表中多余的節點
        return head;
    }

}
