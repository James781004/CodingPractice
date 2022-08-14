package LeetcodeMaster.LinkedList;

public class Q03_ReverseLinkedList {
    // 206.反轉鏈表
    // https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0206.%E7%BF%BB%E8%BD%AC%E9%93%BE%E8%A1%A8.md

    // 題意：反轉一個單鏈表。

    // 示例: 輸入: 1->2->3->4->5->NULL 輸出: 5->4->3->2->1->NULL

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public Node reverseList(Node head) {
        Node pre = null;
        Node cur = head;
        Node next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    public Node reverseList2(Node head) {
        return reverse(null, head);
    }

    private Node reverse(Node pre, Node cur) {
        if (cur == null) return pre;
        Node temp = null;
        temp = cur.next;
        cur.next = pre;
        return reverse(cur, temp);
    }


    public Node reverseList3(Node head) {
        if (head == null) return null;
        if (head.next == null) return head;

        // 遞歸調用，翻轉第二個節點開始往後的鏈表
        Node last = reverseList3(head.next);

        // 翻轉頭節點與第二個節點的指向
        head.next.next = head;

        // 此時的 head 節點為尾節點，next 需要指向 NULL
        head.next = null;
        return last;
    }

}
