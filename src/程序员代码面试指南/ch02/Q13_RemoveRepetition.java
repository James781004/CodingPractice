package 程序员代码面试指南.ch02;

import java.util.HashSet;

public class Q13_RemoveRepetition {
    // 移除鏈表中的重複節點問題

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void removeRep1(Node head) {
        if (head == null) return;

        // 使用HashSet可以直接解決問題
        HashSet<Integer> set = new HashSet<>();
        set.add(head.value);

        Node pre = head;
        Node cur = head.next;
        while (cur != null) {
            if (set.contains(cur.value)) {
                pre.next = cur.next;
            } else {
                set.add(cur.value);
                pre = cur;
            }
            cur = cur.next;
        }
    }

    public static void removeRep2(Node head) {
        if (head == null) return;

        // 從頭節點開始記錄當下節點value，遇到相等就刪掉
        Node cur = head;
        Node pre = null, next = null;
        while (cur != null) {
            pre = cur;
            next = cur.next;
            while (next != null) {
                if (cur.value == next.value) {
                    pre.next = next.next;
                } else {
                    pre = next;
                }
                next = next.next;
            }
            cur = cur.next;
        }
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
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(3);
        head.next.next.next.next = new Node(4);
        head.next.next.next.next.next = new Node(4);
        head.next.next.next.next.next.next = new Node(2);
        head.next.next.next.next.next.next.next = new Node(1);
        head.next.next.next.next.next.next.next.next = new Node(1);
        removeRep1(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(1);
        head.next.next = new Node(3);
        head.next.next.next = new Node(3);
        head.next.next.next.next = new Node(4);
        head.next.next.next.next.next = new Node(4);
        head.next.next.next.next.next.next = new Node(2);
        head.next.next.next.next.next.next.next = new Node(1);
        head.next.next.next.next.next.next.next.next = new Node(1);
        removeRep2(head);
        printLinkedList(head);

    }

}
