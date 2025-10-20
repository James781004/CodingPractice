package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeToList;

public class ConnectII {

    // https://leetcode.cn/problems/populating-next-right-pointers-in-each-node-ii/solutions/2510360/san-chong-fang-fa-dfsbfsbfslian-biao-fu-1bmqp/
    public Node connect(Node root) {
        Node dummy = new Node();
        Node cur = root;
        while (cur != null) {
            dummy.next = null;
            Node nxt = dummy; // 下一層的鏈表
            while (cur != null) { // 遍歷當前層的鏈表
                if (cur.left != null) {
                    nxt.next = cur.left; // 下一層的相鄰節點連起來
                    nxt = cur.left;
                }
                if (cur.right != null) {
                    nxt.next = cur.right; // 下一層的相鄰節點連起來
                    nxt = cur.right;
                }
                cur = cur.next; // 當前層鏈表的下一個節點
            }
            cur = dummy.next; // 下一層鏈表的頭節點 (即上面 while 循環中第一個與 nxt 連接的節點)
        }
        return root;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    ;
}
