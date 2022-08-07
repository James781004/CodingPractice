package TeacherZuoCodingInterviewGuide.ch02;

public class Q05_ReversePartList {

    // 反轉部分鏈表問題
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node reversePart(Node head, int from, int to) {

        // 找到from位置的前一個節點 和 找到to位置的後一個節點
        int len = 0;
        Node node1 = head;
        Node fPre = null;
        Node tPos = null;
        while (node1 != null) {
            len++;
            fPre = len == from - 1 ? node1 : fPre;
            tPos = len == to + 1 ? node1 : tPos;
            node1 = node1.next;
        }

        // 校驗輸入數據的合法性
        if (from > to || from < 1 || to > len) {
            return head;
        }

        // 反轉from到to位置的鏈表，注意：要判斷是否存在"換頭問題"，即fPre是否為null。
        node1 = fPre == null ? head : fPre.next;
        Node node2 = node1.next;
        node1.next = tPos;
        Node next = null;
        while (node2 != tPos) {
            next = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = next;
        }

        // 不存在"換頭問題"
        if (fPre != null) {
            fPre.next = node1; // pre就是反轉鏈表的第一個節點，目的：將反轉後的鏈表，頭部同整個鏈表關聯起來
            return head;
        }

        // 存在"換頭問題"
        return node1;
    }


    // 使用dummy node 的方法比較簡潔，可以避免換頭問題
    public Node reverseBetween(Node head, int start, int end) {
        // 定義一個dummyHead, 方便處理
        Node dummyHead = new Node(0);
        dummyHead.next = head;
        Node pre = dummyHead;
        // 找到前驅節點
        for (int i = 0; i < start - 1; i++) {
            // 假如 start == 2, i = 0 走1次， pre 指向第一個元素，剛剛好是 start == 2 的前一個
            pre = pre.next;
        }

        // 找到左節點
        Node leftNode = pre.next;
        Node next;

        // 頭插法， 把 start 後面的元素插入pre 後面。
        for (int i = 0; i < end - start; i++) {
            next = leftNode.next; // 臨時保存一下，別丟棄了
            leftNode.next = next.next; // 跳過next , 相當於刪除了後一個元素。
            next.next = pre.next; // 把 start 後面的元素插入pre 後面， 這個時候指向pre.next
            pre.next = next; // 把pre的後置指針改變位置， 指向next ,完成一次 頭插，，start後面的元素插入pre後面了。
        }

        return dummyHead.next;
    }


    public static void printLinkedList(Node head) {
        System.out.print("Linked List: ");
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        printLinkedList(head);
        head = reversePart(head, 1, 1);
        printLinkedList(head);

        head = new Node(1);
        printLinkedList(head);
        head = reversePart(head, 1, 1);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        head = reversePart(head, 1, 2);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        head = reversePart(head, 2, 3);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        head = reversePart(head, 1, 3);
        printLinkedList(head);

    }
}
