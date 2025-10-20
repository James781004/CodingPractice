package EndlessCheng.GenreMenu.Backtracking.LinkedList.Comprehensive;

import EndlessCheng.ListNode;

import java.util.HashMap;
import java.util.Map;

public class RemoveZeroSumSublists {

    // https://leetcode.cn/problems/remove-zero-sum-consecutive-nodes-from-linked-list/solutions/2305659/javapython3qian-zhui-he-ha-xi-biao-yi-ci-6nya/
    public ListNode removeZeroSumSublists(ListNode head) {
        Map<Integer, ListNode> preSumMap = new HashMap<>();     // 哈希表記錄前綴和和節點映射
        ListNode dummy = new ListNode(0, head);     // 偽節點，接在頭節點之前
        ListNode node = dummy;  // 從偽節點開始遍歷
        int preSum = 0;     // 記錄的前綴和
        int removepreSum;   // 要移除的前綴和
        while (node != null) {
            preSum += node.val;     // 累加前綴和
            if (preSumMap.containsKey(preSum)) {  // 前綴和已存在
                removepreSum = preSum;  // 記錄要移除的前綴和
                for (ListNode removeNode = preSumMap.get(preSum).next; removeNode != node; removeNode = removeNode.next) {
                    // 從記錄的前綴和對應的節點的下一個節點開始遍歷，直到當前節點；移除這些節點的前綴和映射
                    removepreSum += removeNode.val;     // 累加要移除的前綴和
                    preSumMap.remove(removepreSum);     // 從哈希表中移除前綴和
                }
                preSumMap.get(preSum).next = node.next;  // 修改這個前綴和指向的節點的next為當前節點的next
            } else {
                preSumMap.put(preSum, node);    // 前綴和不存在，加入哈希表
            }
            node = node.next;
        }
        return dummy.next;  // 返回偽節點的下一個節點，即為鏈表的頭節點
    }

}
