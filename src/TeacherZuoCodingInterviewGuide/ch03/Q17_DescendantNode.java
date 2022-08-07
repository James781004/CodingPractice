package TeacherZuoCodingInterviewGuide.ch03;

public class Q17_DescendantNode {
    //    描述
    //    二叉樹中一個節點的後繼節點指的是，二叉樹的中序遍歷的序列中的下一個節點。

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;  // 用來記錄父節點

        public Node(int data) {
            this.value = data;
        }
    }

    // 目標是尋找中序遍歷的序列中的下一個節點
    // 1. 可以先找右子樹的最左節點
    // 2. 如果右子樹不存在，則往上尋找parent:
    // 2.1 當前節點是parent的左節點，那parent就是目標(中序遍歷左根右)
    // 2.2 當前節點是parent的右節點，就繼續往上找parent直到2.1達成(根節點的前面必定是左子樹的最右節點)
    public static Node getNextNode(Node node) {
        if (node == null) return null;
        if (node.right != null) {
            return getLeftMost(node.right);
        } else {
            Node parent = node.parent;
            while (parent != null && parent.left != node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    private static Node getLeftMost(Node node) {
        if (node == null) return null;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getNextNode(test));
    }
}
