package Grind.Ch10_LinkedList;

public class Q13_RotateList {
    // https://leetcode.cn/problems/rotate-list/solutions/1008999/xuan-zhuan-lian-biao-tu-jie-lian-biao-zu-ku33/
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0) return head;
        int n = 0;              // 鏈表的長度
        ListNode tail = null;   // 尾節點
        for (ListNode p = head; p != null; p = p.next) {
            tail = p;
            n++;
        }
        k %= n; // 求出「有效步數」，就不用使用環形鏈表多次旋轉
        ListNode p = head;
        for (int i = 0; i < n - k - 1; i++) p = p.next;   // 找到鏈表的第n-k個節點
        
        tail.next = head; // 將鏈表的後k個節點和前 n - k個節點拼接到一塊
        head = p.next;    // 讓head指向新的頭節點
        p.next = null;    // 新的尾節點即p節點的next指針指向null
        return head;      // 返回新的頭節點
    }
}
