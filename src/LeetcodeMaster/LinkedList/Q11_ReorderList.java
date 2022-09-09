package LeetcodeMaster.LinkedList;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Q11_ReorderList {
//    143.重排鏈表
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0143.%E9%87%8D%E6%8E%92%E9%93%BE%E8%A1%A8.md
//    https://leetcode.cn/problems/reorder-list/submissions/


    class ListNode {
        private ListNode next;
        private int val;

        public ListNode(int val) {
            this.val = val;
        }
    }


    // 方法一 Java實現，使用數組存儲節點
    public void reorderList(ListNode head) {
        // 雙指針的做法
        ListNode cur = head;
        // ArrayList底層是數組，可以使用下標隨機訪問
        List<ListNode> list = new ArrayList<>();
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }
        cur = head;  // 重新回到頭部
        int l = 1, r = list.size() - 1;  // 注意左邊是從1開始
        int count = 0;
        while (l <= r) {
            if (count % 2 == 0) {
                // 偶數
                cur.next = list.get(r);
                r--;
            } else {
                // 奇數
                cur.next = list.get(l);
                l++;
            }
            // 每一次指針都需要移動
            cur = cur.next;
            count++;
        }
        // 注意結尾要結束一波
        cur.next = null;

    }

    // 方法二：使用雙端隊列，簡化了數組的操作，代碼相對於前者更簡潔（避免一些邊界條件）
    public void reorderList2(ListNode head) {
        // 使用雙端隊列的方法來解決
        Deque<ListNode> de = new LinkedList<>();
        // 這里是取head的下一個節點，head不需要再入隊了，避免造成重覆
        ListNode cur = head.next;
        while (cur != null) {
            de.offer(cur);
            cur = cur.next;
        }
        cur = head;  // 回到頭部
        int count = 0;
        while (!de.isEmpty()) {
            if (count % 2 == 0) {
                // 偶數，取出隊列右邊尾部的值
                cur.next = de.pollLast();
            } else {
                // 奇數，取出隊列左邊頭部的值
                cur.next = de.poll();
            }
            cur = cur.next;
            count++;
        }
        cur.next = null;
    }

    // 方法三
    public void reorderList3(ListNode head) {
        ListNode fast = head, slow = head;
        // 求出中點
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // right就是右半部分 12345 就是45  1234 就是34
        ListNode right = slow.next;
        // 斷開左部分和右部分
        slow.next = null;
        // 反轉右部分 right就是反轉後右部分的起點
        right = reverseList(right);
        // 左部分的起點
        ListNode left = head;

        // 進行左右部分來回連接
        // 這里左部分的節點個數一定大於等於右部分的節點個數 因此只判斷right即可
        while (right != null) {
            ListNode curLeft = left.next;
            left.next = right;
            left = curLeft;

            ListNode curRight = right.next;
            right.next = left;
            right = curRight;
        }
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
