package EndlessCheng.GenreMenu.Backtracking.LinkedList.Comprehensive;

import EndlessCheng.ListNode;

import java.util.ArrayDeque;

public class NextLargerNodes {

    // https://leetcode.cn/problems/next-greater-node-in-linked-list/solutions/2217563/tu-jie-dan-diao-zhan-liang-chong-fang-fa-v9ab/
    private int n;

    public int[] nextLargerNodes(ListNode head) {
        head = reverseList(head);
        var ans = new int[n];
        var st = new ArrayDeque<Integer>(); // 單調棧（節點值）
        for (var cur = head; cur != null; cur = cur.next) {
            while (!st.isEmpty() && st.peek() <= cur.val)
                st.pop(); // 彈出無用數據
            ans[--n] = st.isEmpty() ? 0 : st.peek();
            st.push(cur.val);
        }
        return ans;
    }

    // 206. 反轉鏈表
    public ListNode reverseList(ListNode head) {
        ListNode pre = null, cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
            ++n;
        }
        return pre;
    }

}
