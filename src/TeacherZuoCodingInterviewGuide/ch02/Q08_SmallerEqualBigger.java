package TeacherZuoCodingInterviewGuide.ch02;

public class Q08_SmallerEqualBigger {
//    描述
//    给定一个链表，再给定一个整数 pivot，请将链表调整为左部分都是值小于 pivot 的节点，中间部分都是值等于 pivot 的节点， 右边部分都是大于 pivot 的节点。
//    除此之外，对调整后的节点顺序没有更多要求。
//    输入描述：
//    第一行两个整数 n 和 pivot，n 表示链表的长度。
//
//    第二行 n 个整数 ai 表示链表的节点。
//    输出描述：
//    请在给定的函数内返回链表的头指针。
//    示例1
//    输入：
//            5 3
//            9 0 4 5 1
//    输出：
//            1 0 4 5 9
//    备注：
//            10000001≤n≤1000000
//            −1000000≤ai,pivot≤1000000

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return head;
        }
        Node cur = head;
        int i = 0;
        while (cur != null) { // 計算一共有多少節點
            i++;
            cur = cur.next;
        }
        Node[] nodeArr = new Node[i];
        i = 0;
        cur = head;
        for (i = 0; i != nodeArr.length; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        arrPartition(nodeArr, pivot); // 荷蘭國旗問題
        for (i = 1; i != nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        return nodeArr[0];
    }

    public static void arrPartition(Node[] nodeArr, int pivot) {
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big) {
            if (nodeArr[index].value < pivot) {
                swap(nodeArr, ++small, index++);
            } else if (nodeArr[index].value == pivot) {
                index++;
            } else {
                swap(nodeArr, --big, index);
            }
        }
    }

    private static void swap(Node[] nodeArr, int a, int b) {
        Node tmp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = tmp;
    }

    public static Node listPartition2(Node head, int pivot) {
        Node smallHead = null; // small head
        Node smallTail = null; // small tail
        Node equalHead = null; // equal head
        Node equalTail = null; // equal tail
        Node bigHead = null; // big head
        Node bigTail = null; // big tail
        Node next = null; // save next node
        // every node distributed to three lists
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (smallHead == null) { // first node of the list
                    smallHead = head;
                    smallTail = head;
                } else {
                    smallTail.next = head; // add new node from the tail
                    smallTail = head;
                }
            } else if (head.value == pivot) {
                if (equalHead == null) {
                    equalHead = head;
                    equalTail = head;
                } else {
                    equalTail.next = head;
                    equalTail = head;
                }
            } else {
                if (bigHead == null) {
                    bigHead = head;
                    bigTail = head;
                } else {
                    bigTail.next = head;
                    bigTail = head;
                }
            }
            head = next;
        }
        // small and equal reconnect
        if (smallTail != null) {
            smallTail.next = equalHead;
            equalTail = equalTail == null ? smallTail : equalTail;
        }
        // all reconnect
        if (equalTail != null) {
            equalTail.next = bigHead;
        }
        return smallHead != null ? smallHead : equalHead != null ? equalHead : bigHead;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }
}
