package EndlessCheng.GenreMenu.Backtracking.LinkedList.Other;


public class CopyRandomList {

    // https://leetcode.cn/problems/copy-list-with-random-pointer/solutions/2993775/bu-yong-ha-xi-biao-de-zuo-fa-pythonjavac-nzdo/
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        // 復制每個節點，把新節點直接插到原節點的後面
        for (Node cur = head; cur != null; cur = cur.next.next) {
            cur.next = new Node(cur.val, cur.next);
        }

        // 遍歷交錯鏈表中的原鏈表節點
        for (Node cur = head; cur != null; cur = cur.next.next) {
            if (cur.random != null) {
                // 要復制的 random 是 cur.random 的下一個節點
                cur.next.random = cur.random.next;
            }
        }

        // 把交錯鏈表分離成兩個鏈表
        Node newHead = head.next;
        Node cur = head;
        for (; cur.next.next != null; cur = cur.next) {
            Node copy = cur.next;
            cur.next = copy.next; // 恢復原節點的 next
            copy.next = copy.next.next; // 設置新節點的 next
        }
        cur.next = null; // 恢復原節點的 next
        return newHead;
    }


    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
            this.random = null;
        }
    }

}
