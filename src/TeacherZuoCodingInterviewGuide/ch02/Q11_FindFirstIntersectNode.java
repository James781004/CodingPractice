package TeacherZuoCodingInterviewGuide.ch02;

public class Q11_FindFirstIntersectNode {
    //    【題目】在本題中，單鏈表可能有環，也可能無環。
//    給定兩個單鏈表的頭節點head1和head2，這兩個鏈表可能相交，也可能不相交。
//    請實現一個函數，如果兩個鏈表相交，則返回相交的第一個節點；如果不相交，則返回null。
//
//    【要求】如果鏈表1的長度為N，鏈表2的長度為M，時間復雜度請達到O(N+M)，額外空間復雜度請達到O(1)

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) return null;
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        // 判斷單鏈表是否成環
        // 只有都成環或者都不成環的狀況才有可能相交
        // 兩個鏈表都不成環，那麼就直接判斷最後一個節點，如果最後一個節點相同，那麼就肯定相交了
        if (loop1 != null && loop2 != null) {
            return noLoop(head1, head2);
        }

        // 判斷兩個環是否相交
        // 兩個鏈表都成環，在入環之前相交
        // 兩個鏈表都成環，在分別成環之後相交，那麽第一個相交點可以看作各自的入環點
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }

        return null;
    }

    private static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        // 用快慢指針找環入口
        // 快指針走到底後回到鏈表頭，然後快慢指針一起往後找到第一個相同點就是環入口
        Node n1 = head.next; // n1 -> slow
        Node n2 = head.next.next; // n2 -> fast
        while (n1 != n2) {
            if (n2.next == null || n2.next.next == null) {
                return null; // 快指針走到底了，表示沒有環
            }
            n2 = n2.next.next;
            n1 = n1.next;
        }

        n2 = head; // n2 -> walk again from head
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }

    private static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;

        // 計算長度差值
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }

        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }

        // 兩鏈表尾部不相等，表示不可能有相交
        if (cur1 != cur2) {
            return null;
        }

        // 較長的鏈表頭部作為起點
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);

        // 起點走完差值n，兩鏈表就到同樣長度了
        // 兩鏈表指標往後走到相同節點即為交點
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }

        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    private static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;
        if (loop1 == loop2) {
            // loop1和loop2相等，表示存在共同環入口
            // 所以相交點必定在在共同環入口或入口之前
            // 步驟就如同前面無環鏈表的狀況：
            // 1. 計算長度差值
            // 2. 較長的鏈表頭部作為起點
            // 3. 較長的鏈表起點走完差值n，兩鏈表就到同樣長度了
            // 4. 兩鏈表指標往後走到相同節點即為交點

            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }

            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }

            return cur1;
        } else {
            cur1 = loop1.next;

            // 入環點不同，就直接走環
            // 如果回到起點前遇到另一個入環點，表示有相交，返回哪一個入環點都可以
            // 如果走回起點還是沒遇到另一個入環點，表示沒相交，返回空值
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }

            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }
}
