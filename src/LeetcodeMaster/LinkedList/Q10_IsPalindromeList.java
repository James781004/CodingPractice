package LeetcodeMaster.LinkedList;

public class Q10_IsPalindromeList {
//    234.回文鏈表
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0234.%E5%9B%9E%E6%96%87%E9%93%BE%E8%A1%A8.md
//
//    請判斷一個鏈表是否為回文鏈表。
//
//    示例 1:
//
//    輸入: 1->2
//    輸出: false
//    示例 2:
//
//    輸入: 1->2->2->1
//    輸出: true


    class ListNode {
        private ListNode next;
        private int val;

        public ListNode(int val) {
            this.val = val;
        }
    }

    // 方法一，使用數組
    public boolean isPalindrome(ListNode head) {
        int len = 0;
        // 統計鏈表長度
        ListNode cur = head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        cur = head;
        int[] res = new int[len];
        // 將元素加到數組之中
        for (int i = 0; i < res.length; i++) {
            res[i] = cur.val;
            cur = cur.next;
        }
        // 比較回文
        for (int i = 0, j = len - 1; i < j; i++, j--) {
            if (res[i] != res[j]) {
                return false;
            }
        }
        return true;
    }


    // 方法二，快慢指針
    public boolean isPalindrome2(ListNode head) {
        // 如果為空或者僅有一個節點，返回true
        if (head == null && head.next == null) return true;
        ListNode slow = head;
        ListNode fast = head;
        ListNode pre = head;
        while (fast != null && fast.next != null) {
            pre = slow;  // 記錄slow的前一個結點
            slow = slow.next;
            fast = fast.next.next;
        }
        pre.next = null;  // 分割兩個鏈表
        // 前半部分
        ListNode cur1 = head;
        // 後半部分。這里使用了反轉鏈表
        ListNode cur2 = reverseList(slow);

        while (cur1 != null) {
            if (cur1.val != cur2.val) return false;

            // 注意要移動兩個結點
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return true;
    }

    ListNode reverseList(ListNode head) {
        // 反轉鏈表
        ListNode tmp = null;
        ListNode pre = null;
        while (head != null) {
            tmp = head.next;
            head.next = pre;
            pre = head;
            head = tmp;
        }
        return pre;
    }
}
