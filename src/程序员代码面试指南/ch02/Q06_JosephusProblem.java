package 程序员代码面试指南.ch02;

public class Q06_JosephusProblem {
//    描述
//    据说著名犹太历史学家 Josephus 有过以下故事：在罗马人占领乔塔帕特后，39 个犹太人与 Josephus 及他的朋友躲到一个洞中，
//    39 个犹太人决定宁愿死也不要被敌人抓到，于是决定了一种自杀方式，41 个人排成一个圆圈，由第 1 个人开始报数，报数到 3 的人就自杀，
//    然后再由下一个人重新报 1，报数到 3 的人再自杀，这样依次下去，直到剩下最后一个人时，那个人可以自由选择自己的命运。这就是著名的约瑟夫问题。
//    现在请用单向环形链表得出最终存活的人的编号。
//
//    输入描述：
//    一行两个整数 n 和 m， n 表示环形链表的长度， m 表示每次报数到 m 就自杀。
//    输出描述：
//    输出最后存活下来的人编号(编号从1开始到n)
//    示例1
//    输入：
//            5 2
//    输出：
//            3
//    备注：
//             100001≤n,m≤10000

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 循環報數，報到 m 的人自殺
     */
    public static Node josephusKill1(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node last = head;
        while (last.next != head) {
            last = last.next;
        }
        int count = 0;
        while (head != last) { // 直到僅剩一個為止
            if (++count == m) { // 正好數到 m
                last.next = head.next; // 刪除 head 所指向的節點
                count = 0; // 歸零，下一個重新計數
            } else { // 繼續往後數
                last = last.next;
            }
            head = last.next; // head 在前面走，last 在後面跟著
        }
        return head;
    }

    /**
     * 循環報數，報到 m 的人自殺
     */
    public static Node josephusKill2(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node cur = head.next;
        int tmp = 1; // tmp -> list size
        while (cur != head) {
            tmp++;
            cur = cur.next;
        }
        tmp = getLive(tmp, m); // tmp -> service node position
        while (--tmp != 0) {
            head = head.next;
        }
        head.next = head;
        return head;
    }

    /**
     * 計算最終剩余的節點編號
     * 注：根據數學推理，殺死節點之前的老編號=（殺死節點之後的新編號+m-1）%i+1
     *
     * @param i 圈中剩余節點數量
     * @param m 報數為 m 的自殺
     * @return 最終生存的節點
     */
    public static int getLive(int i, int m) {
        if (i == 1) {
            return 1;
        }
        return (getLive(i - 1, m) + m - 1) % i + 1;
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
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = head1;
        printCircularList(head1);
        head1 = josephusKill1(head1, 3);
        printCircularList(head1);

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = head2;
        printCircularList(head2);
        head2 = josephusKill2(head2, 3);
        printCircularList(head2);

    }
}
