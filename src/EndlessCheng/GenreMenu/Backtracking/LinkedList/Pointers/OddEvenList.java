package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class OddEvenList {

    // https://leetcode.cn/problems/odd-even-linked-list/solutions/2864482/javapython3cshuang-zhi-zhen-fen-bie-wei-aocox/
    public ListNode oddEvenList(ListNode head) {
        if (head == null) return head;   // 空鏈表，直接返回
        ListNode oddList = head, evenList = head.next;   // 奇數索引節點鏈表oddList初始化為頭節點，偶數索引節點鏈表evenList初始化為次節點
        ListNode evenHead = evenList;      // 記錄偶數索引節點鏈表頭節點
        // 當前偶數節點不為空且當前偶數節點之後還有節點
        while (evenList != null && evenList.next != null) {
            oddList.next = evenList.next;     // 下一個奇數索引節點是當前偶數索引節點的下一個節點
            oddList = oddList.next;            // 更新當前奇數索引節點
            evenList.next = oddList.next;     // 下一個偶數索引節點是新的奇數索引節點的下一個節點
            evenList = evenList.next;          // 更新偶數索引節點
        }
        oddList.next = evenHead;   // 最後一個奇數索引節點和首個偶數索引節點拼接起來
        return head;
    }

}
