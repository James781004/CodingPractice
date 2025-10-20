package EndlessCheng.GenreMenu.Backtracking.LinkedList.Delete;

import EndlessCheng.ListNode;

public class RemoveElements {

    // https://leetcode.cn/problems/remove-linked-list-elements/solutions/2806456/tao-lu-ru-he-you-ya-di-shan-chu-lian-bia-ah8z/
    public ListNode removeElements(ListNode head, int val) {
        // 步驟1：創建虛擬頭節點，next指向真實鏈表頭（相當於保安亭）
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        // 步驟2：用curr指針從虛擬頭開始遍歷（保安開始檢查每個節點）
        ListNode curr = dummy;

        while (curr.next != null) { // 只要下一個節點存在就檢查
            if (curr.next.val == val) {
                // 發現要刪除的節點 → 跳過它（保安把違規者移出隊伍）
                curr.next = curr.next.next;
            } else {
                // 下一個節點安全 → 移動指針繼續檢查（保安向前走一步）
                curr = curr.next;
            }
        }

        // 步驟3：返回新鏈表的真實頭（保安亭的下一個才是隊伍起點）
        return dummy.next;
    }

}
