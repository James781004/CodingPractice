package LeetcodeMaster.LinkedList;

public class Q04_ReversePartLinkedList {
//    24. 兩兩交換鏈表中的節點
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0024.%E4%B8%A4%E4%B8%A4%E4%BA%A4%E6%8D%A2%E9%93%BE%E8%A1%A8%E4%B8%AD%E7%9A%84%E8%8A%82%E7%82%B9.md
//
//    給定一個鏈表，兩兩交換其中相鄰的節點，並返回交換後的鏈表。
//
//    你不能只是單純的改變節點內部的值，而是需要實際的進行節點交換。

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public Node swapPairs(Node head) {

        // base case 退出提交
        if (head == null || head.next == null) return head;

        // 獲取當前節點的下一個節點
        Node next = head.next;

        // 進行遞歸
        Node newNode = swapPairs(next.next);

        // 這里進行交換
        next.next = head;
        head.next = newNode;

        return next;
    }

    // 虛擬頭結點
    public Node swapPairs2(Node head) {
        Node dummyNode = new Node(-1);
        dummyNode.next = head;
        Node pre = dummyNode;
        while (pre.next != null || pre.next.next != null) {
            Node temp = head.next.next;    // 緩存 head.next.next
            pre.next = head.next;          // 將 pre 的 next 改為 head 的 next
            head.next.next = head;         // 將 head.next(pre.next) 的next，指向 head
            head.next = temp;              // 將head 的 next 接上緩存的temp
            pre = head;                    // 步進1位
            head = head.next;              // 步進1位
        }

        return dummyNode.next;
    }

}
