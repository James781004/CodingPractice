package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class PairSum {

    // https://leetcode.cn/problems/maximum-twin-sum-of-a-linked-list/solutions/2873795/javapython3cliang-chong-zuo-fa-lian-biao-pzke/
    public int pairSum(ListNode head) {
        if (head == null) return 0;
        // 獲取中間靠前節點和中間靠後節點，斷開二者連接
        ListNode midFrontNode = getMidNode(head);
        ListNode midBackNode = midFrontNode.next;
        midFrontNode.next = null;
        // 反轉後半段鏈表
        ListNode reversed = reverseList(midBackNode);
        // 同時遍歷兩段鏈表，得到每一對孿生和
        int res = 0;
        while (head != null) {
            res = Math.max(res, head.val + reversed.val);
            head = head.next;
            reversed = reversed.next;
        }
        return res;
    }

    /**
     * 找到中間節點；偶數長度找到中間靠前的節點
     */
    private ListNode getMidNode(ListNode head) {
        if (head == null) return head;   // 空鏈表直接返回
        ListNode slow = head, fast = head.next;  // 慢指針指向頭節點，快指針指向頭節點下一位
        // 同時移動快慢指針，直到快指針移動不了
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;    // 當前慢指針就指向中間節點
    }

    /**
     * 反轉鏈表給定范圍
     */
    private ListNode reverseList(ListNode head) {
        ListNode last = null;   // 上一個節點，初始為空，因為頭節點沒有上一個節點
        ListNode curr = head;      // 當前節點，初始為頭節點
        while (curr != null) {
            ListNode next = curr.next;    // 暫存當前節點的下一個節點
            curr.next = last;              // 局部反轉，將當前節點鏈接到它的上一個節點
            last = curr;                    // 更新上一個節點
            curr = next;                    // 更新當前節點
        }
        return last;    // 最終last指向原鏈表的最後一個節點，即反轉後的鏈表的頭節點
    }

}
