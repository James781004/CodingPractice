package EndlessCheng.GenreMenu.Backtracking.LinkedList.Traverse;

import EndlessCheng.ListNode;

public class SplitListToParts {

    // https://leetcode.cn/problems/split-linked-list-in-parts/solutions/1010236/gong-shui-san-xie-jing-dian-lian-biao-ju-9yj4/
    public ListNode[] splitListToParts(ListNode head, int k) {
        // 掃描鏈表，得到總長度 cnt
        int cnt = 0;
        ListNode tmp = head;
        while (tmp != null && ++cnt > 0) tmp = tmp.next;
        // 理論最小分割長度
        int per = cnt / k;
        // 將鏈表分割為 k 份（sum 代表已經被處理的鏈表長度為多少）
        ListNode[] ans = new ListNode[k];
        for (int i = 0, sum = 1; i < k; i++, sum++) {
            ans[i] = head;
            tmp = ans[i];
            // 每次首先分配 per 的長度
            int u = per;
            while (u-- > 1 && ++sum > 0) tmp = tmp.next;
            // 當「已處理的鏈表長度 + 剩余待分配份數 * per < cnt」，再分配一個單位長度
            int remain = k - i - 1;
            if (per != 0 && sum + per * remain < cnt && ++sum > 0) tmp = tmp.next;
            head = tmp != null ? tmp.next : null;
            if (tmp != null) tmp.next = null;
        }
        return ans;
    }


}
