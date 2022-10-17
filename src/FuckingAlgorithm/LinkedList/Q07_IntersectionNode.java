package FuckingAlgorithm.LinkedList;

public class Q07_IntersectionNode {
//    https://leetcode.cn/problems/intersection-of-two-linked-lists/
//            160. 相交鏈表
//    給你兩個單鏈表的頭節點 headA 和 headB ，請你找出並返回兩個單鏈表相交的起始節點。如果兩個鏈表不存在相交節點，返回 null 。
//
//    圖示兩個鏈表在節點 c1 開始相交：
//
//
//
//    題目數據 保證 整個鏈式結構中不存在環。
//
//    注意，函數返回結果後，鏈表必須 保持其原始結構 。

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 0, lenB = 0;

        // 計算兩條鏈表的長度
        for (ListNode p1 = headA; p1 != null; p1 = p1.next) lenA++;
        for (ListNode p2 = headA; p2 != null; p2 = p2.next) lenB++;

        // 讓 p1 和 p2 到達尾部的距離相同
        ListNode p1 = headA, p2 = headB;
        if (lenA > lenB) {
            for (int i = 0; i < lenA - lenB; i++) {
                p1 = p1.next;
            }
        } else {
            for (int i = 0; i < lenB - lenA; i++) {
                p2 = p2.next;
            }
        }

        // 看兩個指針是否會相同，p1 == p2 時有兩種情況：
        // 1、要麼是兩條鏈表不相交，他倆同時走到尾部空指針
        // 2、要麼是兩條鏈表相交，他倆走到兩條鏈表的相交點
        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }

        return p1;
    }
}
