package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class RotateRight {

    // https://leetcode.cn/problems/rotate-list/solutions/3590205/bi-huan-fa-jie-jue-xuan-zhuan-lian-biao-625ws/
    public ListNode rotateRight(ListNode head, int k) {
        ListNode s = head;

        if (head == null || head.next == null) { //先判斷鏈表等於0或者1的情況
            return head;
        }
        int size = 1;
        while (s.next != null) {//閉環的同時計算鏈表長度
            s = s.next;
            size++;
        }
        s.next = head;

        k = size - k % size;//計算移動後新的頭指針在環形鏈表中的索引（從原鏈表頭指針開始的索引）
        for (int i = 0; i < k - 1; i++) {//移動頭指針到新頭指針的前一個節點
            head = head.next;
        }
        s = head.next;//取信頭指針的值
        head.next = null;//斷開環
        return s;


    }
}
