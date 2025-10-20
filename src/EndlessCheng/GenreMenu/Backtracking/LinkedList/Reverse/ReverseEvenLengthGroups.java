package EndlessCheng.GenreMenu.Backtracking.LinkedList.Reverse;

import EndlessCheng.ListNode;

public class ReverseEvenLengthGroups {

    // https://leetcode.cn/problems/reverse-nodes-in-even-length-groups/solutions/1784473/jian-dan-mo-ni-ji-ke-by-yu-le-1-ow4v/
    public ListNode reverseEvenLengthGroups(ListNode head) {
        ListNode dummyNode = new ListNode(0, head);
        int group = 1; // 表示鏈表組數
        // head 表示每一組的頭結點, tail表示每一組尾節點,
        // pre表示每一組頭結點的前驅(前一組的尾節點), cur表示遍歷指針
        ListNode cur = head, pre = dummyNode, tail = dummyNode;
        while (head != null) {
            int count = 0; // 表示每一組的節點個數
            while (count < group && cur != null) {
                tail = cur;
                cur = cur.next;
                count++;
            }
            // cur剛好每次遍歷到下一組的頭結點
            if (count % 2 == 0) { // 處理偶數組或最後一組有偶數個節點
                ListNode newHead = reverse(head, cur);
                // 反轉之後： head變為尾節點，newHead變為頭結點
                pre.next = newHead;
                head.next = cur;
                pre = head;
            } else { // 奇數組或最後一組有奇數個節點
                pre = tail;
            }
            head = cur;
            group++;
        }
        return dummyNode.next;
    }

    // [start, end)
    private ListNode reverse(ListNode start, ListNode end) {
        ListNode cur = start, pre = null;
        while (cur != end) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

}
