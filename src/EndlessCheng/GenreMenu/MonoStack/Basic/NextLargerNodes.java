package EndlessCheng.GenreMenu.MonoStack.Basic;

import EndlessCheng.ListNode;

import java.util.ArrayDeque;

public class NextLargerNodes {

    // https://leetcode.cn/problems/next-greater-node-in-linked-list/solutions/2217563/tu-jie-dan-diao-zhan-liang-chong-fang-fa-v9ab/
    public int[] nextLargerNodes(ListNode head) {
        int n = 0;
        for (var cur = head; cur != null; cur = cur.next)
            ++n; // 確定返回值的長度
        var ans = new int[n];
        var st = new ArrayDeque<Integer>(); // 單調棧（節點下標）
        int i = 0;
        for (var cur = head; cur != null; cur = cur.next) {
            while (!st.isEmpty() && ans[st.peek()] < cur.val)
                ans[st.pop()] = cur.val; // ans[st.pop()] 後面不會再訪問到了
            st.push(i);
            ans[i++] = cur.val;
        }
        for (var idx : st)
            ans[idx] = 0; // 沒有下一個更大元素
        return ans;
    }


}
