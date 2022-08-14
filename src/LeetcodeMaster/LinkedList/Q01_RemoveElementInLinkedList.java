package LeetcodeMaster.LinkedList;

public class Q01_RemoveElementInLinkedList {
    // 203.移除鏈表元素
    // https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0203.%E7%A7%BB%E9%99%A4%E9%93%BE%E8%A1%A8%E5%85%83%E7%B4%A0.md

    // 題意：刪除鏈表中等於給定值 val 的所有節點。

    // 示例 1：
    // 輸入：head = [1,2,6,3,4,5,6], val = 6
    // 輸出：[1,2,3,4,5]

    // 示例 2：
    // 輸入：head = [], val = 1
    // 輸出：[]

    // 示例 3：
    // 輸入：head = [7,7,7,7], val = 7
    // 輸出：[]

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public Node removeElements(Node head, int val) {
        if (head == null) return head;
        Node dummy = new Node(-1); // 因為刪除可能涉及到頭節點，所以設置dummy節點，統一操作
        Node pre = dummy;
        Node cur = head;
        while (cur != null) {
            if (cur.value == val) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }

        return dummy.next; // dummy節點後方開始才是我們真正需要的鏈表
    }

}
