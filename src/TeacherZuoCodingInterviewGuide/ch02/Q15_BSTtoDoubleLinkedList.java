package TeacherZuoCodingInterviewGuide.ch02;

import java.util.LinkedList;
import java.util.Queue;

public class Q15_BSTtoDoubleLinkedList {
    //    描述
//    对二叉树的节点来说，有本身的值域，有指向左孩子节点和右孩子节点的两个指针；
//    对双向链表的节点来说，有本身的值域，有指向上一个节点和下一个节点的指针。
//    在结构上，两种结构有相似性，现在有一棵搜索二叉树，请将其转换成一个有序的双向链表。
//    输入描述：
//    第一行一个数字 n 表示二叉树的总结点数。
//    以下 n 行每行三个整数 fa lch rch，表示节点 fa 的左儿子节点为 lch，右儿子节点为 rch。(若 lch 为 0 则表示 fa 没有左儿子，rch同理)
//
//    第一行的 fa 为根节点。
//
//    ps:节点的标号就是节点的值。
//    输出描述：
//    在给定的函数中返回双向链表的头指针。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node convert1(Node head) {
        Queue<Node> queue = new LinkedList<>();
        inOrderToQueue(head, queue); // BST中序排列後會是一個遞增數組，這裡用queue保存
        if (queue.isEmpty()) {
            return head;
        }

        head = queue.poll(); // 找到頭節點
        Node pre = head; // pre指針先指向頭節點
        pre.left = null; // pre現在是指向頭節點，頭節點左側為空
        Node cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll(); // cur是queue接下來彈出的第一個節點元素
            pre.right = cur; // 把pre, cur左右相連
            cur.left = pre;
            pre = cur; // pre向後移動到cur
        }
        pre.right = null; // pre現在指向鏈表尾部，尾節點右側為空
        return head;
    }

    private static void inOrderToQueue(Node head, Queue<Node> queue) {
        if (head == null) return;
        inOrderToQueue(head.left, queue);
        queue.add(head);
        inOrderToQueue(head.right, queue);
    }


    // 樹形dp解法，首先準備一個包裝所有必須資訊的子樹結構ReturnType
    public static class ReturnType {
        public Node start;
        public Node end;

        public ReturnType(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }

    public static Node convert2(Node head) {
        if (head == null) return null;
        return process(head).start;
    }

    private static ReturnType process(Node head) {
        if (head == null) return new ReturnType(null, null);

        // ReturnType定義了左右兩端的start, end範圍值
        // 所以進入遞歸流程後，這邊默認已經取得子樹的資訊
        // 接下來如果需要左右兩端的資料，就直接向左子樹或右子樹要
        ReturnType leftList = process(head.left);
        ReturnType rightList = process(head.right);

        // 連接左邊end以及head
        if (leftList.end != null) {
            leftList.end.right = head;
        }
        head.left = leftList.end;

        // 連接右邊end以及head
        head.right = rightList.start;
        if (rightList.start != null) {
            rightList.start.left = head;
        }

        // 連接好左右邊，目前鏈表變長了，需要重新定義start, end範圍值
        Node start = leftList.start != null ? leftList.start : head;
        Node end = rightList.end != null ? rightList.end : head;

        return new ReturnType(start, end);
    }

    public static void printBSTInOrder(Node head) {
        System.out.print("BST in-order: ");
        if (head != null) {
            inOrderPrint(head);
        }
        System.out.println();
    }

    public static void inOrderPrint(Node head) {
        if (head == null) {
            return;
        }
        inOrderPrint(head.left);
        System.out.print(head.value + " ");
        inOrderPrint(head.right);
    }

    public static void printDoubleLinkedList(Node head) {
        System.out.print("Double Linked List: ");
        Node end = null;
        while (head != null) {
            System.out.print(head.value + " ");
            end = head;
            head = head.right;
        }
        System.out.print("| ");
        while (end != null) {
            System.out.print(end.value + " ");
            end = end.left;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(2);
        head.right = new Node(9);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.left.right.right = new Node(4);
        head.right.left = new Node(7);
        head.right.right = new Node(10);
        head.left.left = new Node(1);
        head.right.left.left = new Node(6);
        head.right.left.right = new Node(8);

        printBSTInOrder(head);
        head = convert1(head);
        printDoubleLinkedList(head);

        head = new Node(5);
        head.left = new Node(2);
        head.right = new Node(9);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.left.right.right = new Node(4);
        head.right.left = new Node(7);
        head.right.right = new Node(10);
        head.left.left = new Node(1);
        head.right.left.left = new Node(6);
        head.right.left.right = new Node(8);

        printBSTInOrder(head);
        head = convert2(head);
        printDoubleLinkedList(head);

    }
}
