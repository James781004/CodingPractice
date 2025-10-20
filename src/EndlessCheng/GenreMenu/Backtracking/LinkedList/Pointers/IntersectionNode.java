package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class IntersectionNode {

    // https://leetcode.cn/problems/intersection-of-two-linked-lists/solutions/2958778/tu-jie-yi-zhang-tu-miao-dong-xiang-jiao-m6tg1/
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p = headA;
        ListNode q = headB;
        while (p != q) {
            // 每次循環，p 和 q 各向後走一步。
            // 如果 p 不是空節點，那麼更新 p 為 p.next，否則更新 p 為 headB；
            // 如果 q 不是空節點，那麼更新 q 為 q.next，否則更新 q 為 headA
            p = p != null ? p.next : headB;
            q = q != null ? q.next : headA;
        }

        // 循環結束時，如果兩條鏈表相交，那麼此時 p 和 q 都在相交的起始節點處，返回 p；
        // 如果兩條鏈表不相交，那麼 p 和 q 都走到空節點，所以也可以返回 p，即空節點
        return p;
    }

}
