package TeacherZuoCodingInterviewGuide.ch02;

import java.util.Stack;

public class Q10_AddTwoLinkedLists {
    //    描述
//    假设链表中每一个节点的值都在 0 - 9 之间，那么链表整体就可以代表一个整数。
//    给定两个这种链表，请生成代表两个整数相加值的结果链表。
//    例如：链表 1 为 9->3->7，链表 2 为 6->3，最后生成新的结果链表为 1->0->0->0。
//    输入描述：
//    第一行两个整数 n 和 m，分别表示两个链表的长度。
//
//    第二行 n 个整数 ai 表示第一个链表的节点。
//
//    第三行 m 个整数 bi 表示第二个链表的节点。
//    输出描述：
//    输出一行整数表示结果链表。
//    示例1
//    输入：
//            3 2
//            9 3 7
//            6 3
//    输出：
//            1 0 0 0
//    备注：
//            10000001≤n,m≤1000000
//            0≤ ai,bi ≤9
//    ai, bi != 0

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node addLists1(Node head1, Node head2) {
        Stack<Integer> stack1 = new Stack<>();
        while (head1 != null) {
            stack1.push(head1.value);
            head1 = head1.next;
        }

        Stack<Integer> stack2 = new Stack<>();
        while (head2 != null) {
            stack2.push(head2.value);
            head2 = head1.next;
        }

        int carry = 0;
        int sum = 0;
        int num1 = 0, num2 = 0;
        Node node = null;
        Node pre = null;

        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            num1 = stack1.isEmpty() ? 0 : stack1.pop();
            num2 = stack2.isEmpty() ? 0 : stack2.pop();
            sum = num1 + num2 + carry;
            carry = sum / 10; // 進位值

            pre = node; // pre指針向前移動到當前node位置
            node = new Node(sum % 10); // 建立新節點，值是sum % 10的結果
            node.next = pre;  // 頭插法加入新節點
        }

        if (carry == 1) { // 最後還有進位1，再做一次頭插法
            pre = node;
            node = new Node(1);
            node.next = pre;
        }

        return node;
    }

    public static Node addLists2(Node head1, Node head2) {
        head1 = reverseList(head1); // 反轉鏈表
        head2 = reverseList(head2);
        int carry = 0;
        int n1 = 0;
        int n2 = 0;
        int sum = 0;
        Node c1 = head1;
        Node c2 = head2;
        Node node = null;
        Node pre = null;
        while (c1 != null || c2 != null) {
            n1 = c1 != null ? c1.value : 0;
            n2 = c2 != null ? c2.value : 0;
            sum = n1 + n2 + carry;
            pre = node; // pre指針向前移動到當前node位置
            node = new Node(sum % 10); // 建立新節點，值是sum % 10的結果
            node.next = pre; // 頭插法加入新節點
            carry = sum / 10; // 進位值
            c1 = c1 != null ? c1.next : null; // 兩鏈表向後找節點
            c2 = c2 != null ? c2.next : null;
        }

        if (carry == 1) { // 最後還有進位1，再做一次頭插法
            pre = node;
            node = new Node(1);
            node.next = pre;
        }

        reverseList(head1); // 反轉鏈表復原
        reverseList(head2);
        return node;
    }

    private static Node reverseList(Node head1) {
        Node pre = null;
        Node next = null;
        while (head1 != null) {
            next = head1.next;
            head1.next = pre;
            pre = head1;
            head1 = next;
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
        Node head1 = new Node(9);
        head1.next = new Node(9);
        head1.next.next = new Node(9);

        Node head2 = new Node(1);

        printLinkedList(head1);
        printLinkedList(head2);
        printLinkedList(addLists1(head1, head2));
        printLinkedList(addLists2(head1, head2));

    }
}
