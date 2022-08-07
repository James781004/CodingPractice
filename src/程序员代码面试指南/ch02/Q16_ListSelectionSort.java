package 程序员代码面试指南.ch02;

public class Q16_ListSelectionSort {
//    描述
//    给定一个无序单链表，实现单链表的选择排序(按升序排序)。
//    输入描述：
//    第一行一个整数 n，表示单链表的节点数量。
//    第二行 n 个整数 val 表示单链表的各个节点。
//    输出描述：
//    在给出的函数内返回给定链表的头指针。

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }


    public static Node selectionSort(Node head) {
        Node tail = null; // sorted part tail
        Node cur = head; // unsorted part head
        Node smallPre = null; // previous node of the smallest node
        Node small = null; // smallest node
        while (cur != null) {
            small = cur;
            smallPre = getSmallestPreNode(cur);
            if (smallPre != null) {
                small = smallPre.next;
                smallPre.next = small.next;
            }
            cur = cur == small ? cur.next : cur;
            if (tail == null) {
                head = small;
            } else {
                tail.next = small;
            }
            tail = small;
        }
        return head;
    }

    public static Node getSmallestPreNode(Node head) {
        Node smallPre = null;
        Node small = head;
        Node pre = head;
        Node cur = head.next;
        while (cur != null) {
            if (cur.value < small.value) {
                smallPre = pre;
                small = cur;
            }
            pre = cur;
            cur = cur.next;
        }
        return smallPre;
    }


    // 其實按照Array的方式操作就可以了
    public static Node selectionSort2(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node cur = head;
        Node node;
        Node min;
        int temp;
        while (cur != null) {
            // node指針先記下當前節點cur的下一個節點，並把min指針先指向cur
            node = cur.next;
            min = cur;

            // node指針往後找出鏈表剩下部分最小節點，然後讓min指向最小節點
            while (node != null) {
                if (node.value < min.value) {
                    min = node;
                }
                node = node.next;
            }

            // 最小節點跟當前節點進行交換
            temp = cur.value;
            cur.value = min.value;
            min.value = temp;

            // cur排序完成，往後移動
            cur = cur.next;
        }
        return head;
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
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(1);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(2);
        head.next = new Node(1);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(3);
        head.next.next = new Node(2);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(2);
        head.next = new Node(1);
        head.next.next = new Node(3);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(2);
        head.next = new Node(3);
        head.next.next = new Node(1);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(3);
        head.next = new Node(1);
        head.next.next = new Node(2);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(3);
        head.next = new Node(2);
        head.next.next = new Node(1);
        head = selectionSort(head);
        printLinkedList(head);

        head = new Node(3);
        head.next = new Node(1);
        head.next.next = new Node(4);
        head.next.next.next = new Node(2);
        head = selectionSort(head);
        printLinkedList(head);

    }
}
