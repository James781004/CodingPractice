package LeetcodeMaster.LinkedList;

public class Q05_DeleteLastNthNodeInLinkedList {
//    19.刪除鏈表的倒數第N個節點
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0019.%E5%88%A0%E9%99%A4%E9%93%BE%E8%A1%A8%E7%9A%84%E5%80%92%E6%95%B0%E7%AC%ACN%E4%B8%AA%E8%8A%82%E7%82%B9.md
//
//    給你一個鏈表，刪除鏈表的倒數第 n 個結點，並且返回鏈表的頭結點。
//
//    進階：你能嘗試使用一趟掃描實現嗎？

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public Node removeNthFromEnd(Node head, int n) {
        Node dummy = new Node(-1);
        dummy.next = head;
        Node slow = dummy;
        Node fast = dummy;

        while (n-- > 0) {
            fast = fast.next;
        }

        // 記住 待刪除節點slow 的上一節點
        Node pre = null;
        while (fast != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next;
        }

        // 不使用pre指針的話，fast再提前走一步，
        // 因為需要讓slow指向刪除節點的上一個節點
        // 之後讓slow.next = slow.next.next，即可完成刪除
//        fast = fast.next;

        // 上一節點的next指針繞過 待刪除節點slow 直接指向slow的下一節點
        pre.next = slow.next;

        // 釋放 待刪除節點slow 的next指針, 這句刪掉也能AC
        slow.next = null;

        return dummy.next;
    }

}
