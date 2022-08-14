package LeetcodeMaster.TwoPointer;

public class Q08_CycleList {
//142.環形鏈表II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0142.%E7%8E%AF%E5%BD%A2%E9%93%BE%E8%A1%A8II.md
//
//    題意： 給定一個鏈表，返回鏈表開始入環的第一個節點。 如果鏈表無環，則返回 null。
//
//    為了表示給定鏈表中的環，使用整數 pos 來表示鏈表尾連接到鏈表中的位置（索引從 0 開始）。 如果 pos 是 -1，則在該鏈表中沒有環。
//
//    說明：不允許修改給定的鏈表。


    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public Node detectCycle(Node head) {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) { // 相遇表示有環
                slow = head; // slow回到頭節點循環利用
                // 兩個指針，從頭結點和相遇結點，各走一步，直到相遇，相遇點即為環入口
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

}
