package TeacherZuoCodingInterviewGuide.ch02;

import java.util.HashMap;

public class Q09_CopyListWithRandom {
//    题目
//    一种特殊的链表节点类描述如下:
//
//    public static class Node {
//        public int value;
//        public Node next;
//        public Node rand;
//
//        public Node(int data) {
//            this.value = data;
//        }
//    }
//    复制
//    next指针和正常单链表中next指针的意义 一 样，都指向下一个节点，
//    rand指针是Node类中新增的指针，这个指针可能指向链表中的任意一个节点，也可能指向null。
//    给定一个由 Node节点类型组成的无环单链表的头节点head，
//    请实现一个 函数完成 这个链表中所有结构的复制，并返回复制的新链表的头节点。
//
//    进阶要求
//    不使用额外的数据结构，只用有限几个变量，
//    且在时间复杂度为 O(N) 内完成原问题要实现的函数

    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node copyListWithRand1(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }

        return map.get(head);
    }

    public static Node copyListWithRand2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;

        // copy node and link to every node
        while (cur != null) {
            next = cur.next;  // keep the next node
            cur.next = new Node(cur.value); // copy node
            cur.next.next = next; // cur -> copy -> next
            cur = next; // move cur pointer to next
        }

        // set copy node rand
        cur = head;
        Node curCopy = null;
        while (cur != null) {
            next = cur.next.next; // keep the next node: cur -> copy -> (next)
            curCopy = cur.next; // keep the copied node: cur -> (copy) -> next
            curCopy.rand = cur.rand != null ? cur.rand.next : null; // connect the rand pointer
            cur = next; // move cur pointer to next
        }

        // split
        Node res = head.next;
        cur = head;
        while (cur != null) {
            next = cur.next.next; // cur -> copy -> (next)
            curCopy = cur.next; // cur -> (copy) -> next
            cur.next = next; // rebuild the old list
            curCopy.next = next != null ? next.next : null;  // rebuild the new list
            cur = next; // move cur pointer to next
        }

        return res;
    }

    public static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

    }
}
