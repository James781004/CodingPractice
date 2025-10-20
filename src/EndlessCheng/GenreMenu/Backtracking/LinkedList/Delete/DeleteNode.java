package EndlessCheng.GenreMenu.Backtracking.LinkedList.Delete;

import EndlessCheng.ListNode;

public class DeleteNode {

    // https://leetcode.cn/problems/delete-node-in-a-linked-list/solutions/2004056/ru-he-shan-chu-jie-dian-liu-fen-zhong-ga-x3kn/
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

}
