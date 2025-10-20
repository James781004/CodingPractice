package EndlessCheng.GenreMenu.Backtracking.LinkedList.Delete;

import EndlessCheng.ListNode;

import java.util.HashSet;
import java.util.Set;

public class ModifiedList {

    // https://leetcode.cn/problems/delete-nodes-from-linked-list-present-in-array/solutions/2843071/shao-bing-jie-dian-yi-ci-bian-li-pythonj-imre/
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> set = new HashSet<>(nums.length); // 預分配空間
        for (int x : nums) {
            set.add(x);
        }
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        while (cur.next != null) {
            if (set.contains(cur.next.val)) {
                cur.next = cur.next.next; // 刪除
            } else {
                cur = cur.next; // 向後移動
            }
        }
        return dummy.next;
    }

}
