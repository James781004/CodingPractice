package EndlessCheng.GenreMenu.Backtracking.LinkedList.Traverse;

import EndlessCheng.ListNode;

public class GetDecimalValue {

    // https://leetcode.cn/problems/convert-binary-number-in-a-linked-list-to-integer/solutions/2863325/321290javajie-ti-by-serializable-w-hpql/
    public int getDecimalValue(ListNode head) {
        int sum = 0;
        while (head != null) {
            sum = (sum << 1) | head.val; // 將左移一位後的sum與當前節點的值head.val做按位或運算。
            // 這個操作會將head.val添加到sum的最右邊一位。
            head = head.next;
        }
        return sum; // sum被認為是二進制數，直接返回即為十進制數
    }

}
