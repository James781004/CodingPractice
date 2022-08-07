package 程序员代码面试指南.ch02;

public class Q18_InsertNumToCircularList {
//    描述
//    一个环形单链表从头节点 head 开始不降序，同时由最后的节点指回头节点。
//    给定这样一个环形单链表的头节点 head 和 一个整数 num，
//    请生成节点值为 num 的新节点，并插入到这个环形链表中，保证调整后的链表依然有序。
//    输入描述：
//    环形单链表的头节点 head 和 一个整数 num。
//    输出描述：
//    在给定的函数内返回新的环形单链表的头指针。


    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node insertNum(Node head, int num) {
        Node node = new Node(num);
        if (head == null) {
            node.next = node;
            return node;
        }

        // pre指針記錄當前頭節點，cur指針記錄頭節點的next
        Node pre = head;
        Node cur = head.next;

        // pre跟上cur，cur往後走，直到cur指針繞回頭節點為止，尋找合適條件，找到就break loop
        // 如果沒有break，表示環狀鏈表裡面每個元素都比新節點小或大，沒有中間區間，新節點必須插入尾端
        while (cur != head) {
            if (pre.value <= num && cur.value >= num) break;
            pre = cur;
            cur = cur.next;
        }

        // 連接pre -> node -> cur
        pre.next = node;
        node.next = cur;
        return head.value < num ? head : node;  // 最小節點當頭
    }


    public static void printCircularList(Node head) {
        if (head == null) {
            return;
        }
        System.out.print("Circular List: " + head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

    public static void main(String[] args) {
        Node head = null;
        head = insertNum(head, 2);
        printCircularList(head);
        head = insertNum(head, 1);
        printCircularList(head);
        head = insertNum(head, 4);
        printCircularList(head);
        head = insertNum(head, 3);
        printCircularList(head);
        head = insertNum(head, 5);
        printCircularList(head);
        head = insertNum(head, 0);
        printCircularList(head);

    }
}
