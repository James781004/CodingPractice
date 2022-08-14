package LeetcodeMaster.TwoPointer;

public class Q06_DeleteLastNthNodeInLinkedList {
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
        Node slow = dummy;  // 慢指針從虛擬頭開始頭
        Node fast = head; // 快指針從頭開始走，因為需要讓slow指向刪除節點的上一個節點，所以起點提前走一步

        while (n-- > 0) {
            fast = fast.next;
        }

        // slow最後會走到待刪除節點的上一節點
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }

}
