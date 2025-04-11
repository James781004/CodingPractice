package EndlessCheng.Basic.MonoStack;

import EndlessCheng.ListNode;

import java.util.ArrayDeque;

public class nextLargerNodes {

    // https://leetcode.cn/problems/next-greater-node-in-linked-list/solutions/2217563/tu-jie-dan-diao-zhan-liang-chong-fang-fa-v9ab/
    public int[] nextLargerNodes(ListNode head) {
        int n = 0;
        for (var cur = head; cur != null; cur = cur.next)
            ++n; // 確定返回值的長度
        var ans = new int[n];
        var st = new ArrayDeque<int[]>(); // 單調棧（節點值，節點下標）
        int i = 0;
        for (var cur = head; cur != null; cur = cur.next) {
            while (!st.isEmpty() && st.peek()[0] < cur.val)
                ans[st.pop()[1]] = cur.val; // 用當前節點值更新答案
            st.push(new int[]{cur.val, i++});
        }
        return ans;
    }


}
