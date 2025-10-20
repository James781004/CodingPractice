package EndlessCheng.GenreMenu.Backtracking.LinkedList.Traverse;

import EndlessCheng.ListNode;

public class NodesBetweenCriticalPoints {

    // https://leetcode.cn/problems/find-the-minimum-and-maximum-number-of-nodes-between-critical-points/solutions/2612349/2058-zhao-chu-lin-jie-dian-zhi-jian-de-z-i2az/
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        // minDistance 和 maxDistance 分別表示任意兩個不同臨界點之間的最小距離和最大距離
        int minDistance = Integer.MAX_VALUE, maxDistance = 0;

        int firstCriticalIndex = -1; // 首個臨界點下標，−1表示尚未遇到臨界點
        int prevCriticalIndex = -1; // 上一個臨界點下標
        int currCriticalIndex = -1; // 當前臨界點下標
        int index = 1;
        ListNode prev = head, curr = head.next; // 臨界點一定不是鏈表的頭結點和尾結點，因此從鏈表的下標 1 的結點開始遍歷
        while (curr.next != null) {
            ListNode next = curr.next; // 得到 curr 的下一個結點 next

            // 將 curr 的結點值與 prev 和 next 的結點值比較
            // 1. 如果 firstCriticalIndex<0，則 curr 是首個臨界點，將 firstCriticalIndex 的值更新為 index
            // 2. 將 prevCriticalIndex 的值更新為 currCriticalIndex，將 currCriticalIndex 的值更新為 index
            // 3. 如果 prevCriticalIndex≥0，則在當前臨界點之前已經有臨界點，
            // 將 minDistance 的值更新為 min(minDistance,currCriticalIndex−prevCriticalIndex)
            // 將 maxDistance 的值更新為 max(maxDistance,currCriticalIndex−firstCriticalIndex)
            if ((curr.val > prev.val && curr.val > next.val) || (curr.val < prev.val && curr.val < next.val)) {
                if (firstCriticalIndex < 0) {
                    firstCriticalIndex = index;
                }
                prevCriticalIndex = currCriticalIndex;
                currCriticalIndex = index;
                if (prevCriticalIndex >= 0) {
                    minDistance = Math.min(minDistance, currCriticalIndex - prevCriticalIndex);
                    maxDistance = Math.max(maxDistance, currCriticalIndex - firstCriticalIndex);
                }
            }

            // 將 prev 的值更新為 curr，將 curr 的值更新為 next，將 index 的值加 1，表示移動到下一個結點
            prev = curr;
            curr = next;
            index++;
        }
        if (minDistance <= maxDistance) {
            return new int[]{minDistance, maxDistance};
        } else {
            return new int[]{-1, -1};
        }
    }

}
