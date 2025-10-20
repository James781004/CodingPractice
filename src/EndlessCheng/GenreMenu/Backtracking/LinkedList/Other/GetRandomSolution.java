package EndlessCheng.GenreMenu.Backtracking.LinkedList.Other;

import EndlessCheng.ListNode;

public class GetRandomSolution {

    // https://leetcode.cn/problems/linked-list-random-node/solutions/1214128/tong-ge-lai-shua-ti-la-yi-ti-san-jie-sui-uqmv/
    static class Solution {

        ListNode head;

        public Solution(ListNode head) {
            this.head = head;
        }

        public int getRandom() {
            int i = 0;
            int pool = 0;
            ListNode cur = head;
            while (cur != null) {
                i++;
                int rand = (int) (Math.random() * i);
                if (rand == 0) {
                    pool = cur.val;
                }
                cur = cur.next;
            }
            return pool;
        }
    }

}
