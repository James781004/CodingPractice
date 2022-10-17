package FuckingAlgorithm.LinkedList;

public class Q08_ReverseLinkedList {

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    //    https://leetcode.com/problems/reverse-linked-list/
    //    206. Reverse Linked List
    //    Given the head of a singly linked list, reverse the list, and return the reversed list.
    //    定義：輸入一個單鏈表頭節點，將該鏈表反轉，返回新的頭節點
    ListNode reverse(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode last = reverse(head.next); // 根據函數定義，reverse 函數會返回反轉之後的頭節點，我們用變量 last 接收了。
        head.next.next = head;
        head.next = null; // 鏈表遞歸反轉之後，新的頭節點是 last，而之前的 head 變成了最後一個節點，鏈表的末尾指向 null
        return last;
    }


    //    https://leetcode.cn/problems/reverse-linked-list-ii/
    //    92. 反轉鏈表 II
    //    給你單鏈表的頭指針 head 和兩個整數 left 和 right ，其中 left <= right 。請你反轉從位置 left 到位置 right 的鏈表節點，返回 反轉後的鏈表 。
    // 將鏈表的前 n 個節點反轉（n <= 鏈表長度）
    ListNode successor = null; // 後驅節點

    // 反轉以 head 為起點的 n 個節點，返回新的頭節點
    // 1、base case 變為 n == 1，反轉一個元素，就是它本身，同時要記錄後驅節點。
    // 2、剛才我們直接把 head.next 設置為 null，因為整個鏈表反轉後原來的 head 變成了整個鏈表的最後一個節點。
    //    但現在 head 節點在遞歸反轉之後不一定是最後一個節點了，所以要記錄後驅 successor（第 n + 1 個節點），反轉之後將 head 連接上
    ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            successor = head.next; // 記錄第 n + 1 個節點
            return head;
        }

        // 以 head.next 為起點，需要反轉前 n - 1 個節點
        ListNode last = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = successor; // 讓反轉之後的 head 節點和後面的節點連起來
        return last;
    }

    ListNode reverseBetween(ListNode head, int m, int n) {
        // base case
        // 如果 m == 1，就相當於反轉鏈表開頭的 n 個元素
        if (m == 1) {
            return reverseN(head, n);
        }
        // 前進到反轉的起點觸發 base case
        // 如果把 head 的索引視為 1，從第 m 個元素開始反轉
        // 如果把 head.next 的索引視為 1
        // 那麼相對於 head.next，反轉的區間應該是從第 m - 1 個元素開始的；那麼對於 head.next.next 呢……
        head.next = reverseBetween(head.next, m - 1, n - 1);
        return head;
    }


    // https://leetcode.cn/problems/reverse-nodes-in-k-group/
//    25. K 個一組翻轉鏈表
//    給你鏈表的頭節點 head ，每 k 個節點一組進行翻轉，請你返回修改後的鏈表。
//
//    k 是一個正整數，它的值小於或等於鏈表的長度。如果節點總數不是 k 的整數倍，那麼請將最後剩余的節點保持原有順序。
//
//    你不能只是單純的改變節點內部的值，而是需要實際進行節點交換。

    // 反轉以 a 為頭節點的鏈表
    ListNode reverse2(ListNode a) {
        ListNode pre, cur, nxt;
        pre = null;
        cur = a;
        nxt = a;
        while (cur != null) {
            nxt = cur.next;
            // 逐個節點反轉
            cur.next = pre;
            // 更新指針位置
            pre = cur;
            cur = nxt;
        }
        // 返回反轉後的頭節點
        return pre;
    }


    /**
     * 反轉區間 [a, b) 的元素，注意是左閉右開
     */
    ListNode reverseBetween2(ListNode a, ListNode b) {
        ListNode pre, cur, nxt;
        pre = null;
        cur = a;
        nxt = a;
        while (cur != b) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) return null;
        // 區間 [a, b) 包含 k 個待反轉元素
        ListNode a, b;
        a = b = head;
        for (int i = 0; i < k; i++) {
            if (b == null) return head; // 不足 k 個，不需要反轉，base case
            b = b.next;
        }

        // 反轉前 k 個元素
        ListNode newHead = reverseBetween2(a, b);
        // 遞歸反轉後續鏈表並連接起來
        a.next = reverseKGroup(b, k);
        return newHead;

    }

}
