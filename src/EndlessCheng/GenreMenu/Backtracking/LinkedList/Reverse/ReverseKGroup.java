package EndlessCheng.GenreMenu.Backtracking.LinkedList.Reverse;

import EndlessCheng.ListNode;

public class ReverseKGroup {

    // https://leetcode.cn/problems/reverse-nodes-in-k-group/solutions/1992228/you-xie-cuo-liao-yi-ge-shi-pin-jiang-tou-plfs/
    public ListNode reverseKGroup(ListNode head, int k) {
        // 鏈表的長度
        int n = 0;
        for (ListNode cur = head; cur != null; cur = cur.next) {
            n++;
        }

        // 哨兵頭
        ListNode dummyHead = new ListNode(0, head);
        ListNode p0 = dummyHead;

        // 反轉鏈表起手式
        ListNode cur = head;
        ListNode pre = null;

        // 長度無需所謂常量保存，參與運算
        // 此條件等價於 重復整數次，該整數為 與k相乘不大於n的整數
        while (n >= k) {
            n -= k;

            // 對每個 以k個鏈表組成的組進行反轉
            for (int i = 0; i < k; i++) {
                ListNode tmp = cur.next;
                cur.next = pre;
                pre = cur;
                cur = tmp;
            }

            // 每次組反轉結束後 cur指向下一個組的頭結點 pre指向本組頭節點（反轉後的） 也即是反轉前該組尾結點
            // 而p0是 進行反轉操作的組的頭結點的上一個節點（組的上一個節點）
            // 所以 p0.next（進行反轉操作的組的頭結點，在反轉結束後是尾結點） 要指向 下個組的頭結點cur =>p0.next.next=cur;
            // p0 此時依然是進行反轉操作的組的頭結點的上一個節點，我們希望該節點（也是上一個進行反轉操作的組的尾結點）指向
            // 反轉後本組頭節點（這樣就連上了）
            // 最後，保持p0的本質，更新p0為此次反轉操作結束、下個待反轉操作的組的頭結點的上一個節點
            ListNode nxt = p0.next;
            p0.next.next = cur;
            p0.next = pre;
            p0 = nxt;

        }
        return dummyHead.next;
    }
}
