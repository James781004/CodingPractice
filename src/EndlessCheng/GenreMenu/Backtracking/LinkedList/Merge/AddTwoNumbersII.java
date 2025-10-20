package EndlessCheng.GenreMenu.Backtracking.LinkedList.Merge;

import EndlessCheng.ListNode;

public class AddTwoNumbersII {

    // https://leetcode.cn/problems/add-two-numbers-ii/solutions/2328330/fan-zhuan-lian-biao-liang-shu-xiang-jia-okw6q/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverseList(l1);
        l2 = reverseList(l2); // l1 和 l2 反轉後，就變成【2. 兩數相加】了
        ListNode l3 = addTwo(l1, l2, 0);
        return reverseList(l3);
    }

    private ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head; // 把下一個節點指向自己
        head.next = null; // 斷開指向下一個節點的連接，保證最終鏈表的末尾節點的 next 是空節點
        return newHead;
    }

    // l1 和 l2 為當前遍歷的節點，carry 為進位
    private ListNode addTwo(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null) { // 遞歸邊界：l1 和 l2 都是空節點
            return carry != 0 ? new ListNode(carry) : null; // 如果進位了，就額外創建一個節點
        }
        if (l1 == null) { // 如果 l1 是空的，那麼此時 l2 一定不是空節點
            l1 = l2;
            l2 = null; // 交換 l1 與 l2，保證 l1 非空，從而簡化代碼
        }
        carry += l1.val + (l2 != null ? l2.val : 0); // 節點值和進位加在一起
        l1.val = carry % 10; // 每個節點保存一個數位
        l1.next = addTwo(l1.next, (l2 != null ? l2.next : null), carry / 10); // 進位
        return l1;
    }

}
