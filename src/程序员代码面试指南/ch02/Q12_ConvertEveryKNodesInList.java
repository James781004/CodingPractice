package 程序员代码面试指南.ch02;

import java.util.Stack;

public class Q12_ConvertEveryKNodesInList {
//    【题目描述】
//    给定一个单链表的头节点head, 实现一个调整单链表的函数，使得每K个节点之间逆序，如果最后不够K个节点一组，则不调整最后几个节点。
//
//    例如：
//
//    链表:1->2->3->4->5->6->7->8->null, K = 3。
//
//    调整后：3->2->1->6->5->4->7->8->null。其中 7，8不调整，因为不够一组。
//
//            【要求】
//    如果链表的长度为 N, 时间复杂度达到 O(N)。

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node reverseKNodes1(Node head, int K) {
        if (K < 2) return head;
        Stack<Node> stack = new Stack<Node>();
        Node newHead = head;
        Node cur = head;
        Node pre = null, next = null;

        while (cur != null) {
            next = cur.next;
            stack.push(cur);
            if (stack.size() == K) {
                pre = resign1(stack, pre, next);
                newHead = newHead == head ? cur : newHead;  // 第一組轉換要注意換頭問題: 第一組的cur會變成新翻轉鏈表的頭
            }
            cur = next;
        }

        return newHead;
    }

    private static Node resign1(Stack<Node> stack, Node left, Node right) {
        Node cur = stack.pop();
        if (left != null) {
            left.next = cur;
        }
        Node next = null;
        while (!stack.isEmpty()) {
            next = stack.pop();
            cur.next = next;
            cur = next;
        }
        cur.next = right;
        return cur;
    }

    public static Node reverseKNodes2(Node head, int K) {
        if (K < 2) return head;
        Node cur = head;
        Node start = null;
        Node pre = null;
        Node next = null;
        int count = 1;

        while (cur != null) {

            // count滿足條件，開始翻轉部分鏈表
            // 先用next指針記下當前位置cur的下一位
            // pre位置此時代表翻轉鏈表的前軀節點，pre的next就是真正起點
            // start: 翻轉鏈表的頭，如果是最開始第一組翻轉(pre == null)，設定為head，否則就是pre下一位
            // cur位置此時代表翻轉鏈表的尾節點，下一位的next就是翻轉鏈表的後驅節點
            next = cur.next;
            if (count == K) {
                start = pre == null ? head : pre.next;
                head = pre == null ? cur : head; // 第一組轉換要注意換頭問題: 第一組的cur會變成新翻轉鏈表的頭
                resign2(pre, start, cur, next);
                pre = start; // start位置翻轉後變成尾節點，pre指針移動過去作為下一組的前軀節點
                count = 0; // 重置count
            }

            count++;
            cur = next;
        }

        return head;
    }

    private static void resign2(Node left, Node start, Node end, Node right) {
        Node pre = start; // 目前節點的前軀節點
        Node cur = start.next; // 目前節點
        Node next = null; // 預定存放目前節點下一位的指針

        while (cur != right) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        // left是原本start之前的前軀點
        // 所以翻轉完成後要和新的頭，也就是end連接
        // 第一組轉換要注意換頭問題 (原始頭節點之前會是空值)
        if (left != null) {
            left.next = end;
        }

        // right是原本end之後的後軀點
        // 所以翻轉完成後要和新的尾，也就是start連接
        start.next = right;
    }

    // 遞歸解法
    public static Node reverseKNodes3(Node head, int k) {
        if (head == null) return null;
        if (k < 2) return head;

        // 區間 [a, b) 包含 k 個待反轉元素
        Node a = head, b = head;
        for (int i = 0; i < k; i++) {
            if (b == null) return head; // 不足 k 個，不需要反轉
            b = b.next;
        }

        // 反轉前 k 個元素，用newHead保存完成反轉的鏈表
        // 原本是 a->...->b 變成 newHead(b)->...->a
        Node newHead = reverse(a, b);

        // 遞歸反轉後續鏈表並連接起來
        // 原本是 a->...->b 變成 b->...->a，所以是a連接後後續鏈表
        a.next = reverseKNodes3(b, k);
        return newHead;
    }

    private static Node reverse(Node a, Node b) {
        Node pre = null, cur = a, next = null;
        while (cur != b) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
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
        int K = 3;
        printLinkedList(head);
        head = reverseKNodes1(head, K);
        printLinkedList(head);
        head = reverseKNodes2(head, K);
        printLinkedList(head);
        System.out.println("=======================");

        head = new Node(1);
        K = 3;
        printLinkedList(head);
        head = reverseKNodes1(head, K);
        printLinkedList(head);
        head = reverseKNodes2(head, K);
        printLinkedList(head);
        System.out.println("=======================");

        head = new Node(1);
        head.next = new Node(2);
        K = 2;
        printLinkedList(head);
        head = reverseKNodes1(head, K);
        printLinkedList(head);
        head = reverseKNodes2(head, K);
        printLinkedList(head);
        System.out.println("=======================");

        head = new Node(1);
        head.next = new Node(2);
        K = 3;
        printLinkedList(head);
        head = reverseKNodes1(head, K);
        printLinkedList(head);
        head = reverseKNodes2(head, K);
        printLinkedList(head);
        System.out.println("=======================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        K = 2;
        printLinkedList(head);
        head = reverseKNodes1(head, K);
        printLinkedList(head);
        head = reverseKNodes2(head, K);
        printLinkedList(head);
        System.out.println("=======================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);
        head.next.next.next.next.next.next = new Node(7);
        head.next.next.next.next.next.next.next = new Node(8);
        K = 3;
        printLinkedList(head);
        head = reverseKNodes1(head, K);
        printLinkedList(head);
        head = reverseKNodes2(head, K);
        printLinkedList(head);
        head = reverseKNodes3(head, K);
        printLinkedList(head);
        System.out.println("=======================");

    }
}
