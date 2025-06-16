package EndlessCheng;

import java.util.List;

public class Node {

    public int val;
    public Node left;
    public Node right;
    public Node next;
    public List<Node> children;

    Node() {
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }


    public Node(int val, Node left, Node right, Node next) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.next = next;
    }
}
