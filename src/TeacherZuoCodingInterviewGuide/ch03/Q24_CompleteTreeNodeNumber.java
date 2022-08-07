package TeacherZuoCodingInterviewGuide.ch03;

import java.util.Deque;
import java.util.LinkedList;

public class Q24_CompleteTreeNodeNumber {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int nodeNum(Node head) {
        if (head == null) {
            return 0;
        }
        return bs(head, 1, mostLeftLevel(head, 1));
    }

    // 用高度探查方式的時間覆雜度為O(h^2)。
    //
    // 當我們拿到一個完全二叉樹時，其高度等於其左邊界的長度。
    //
    // 當其左子樹的高度和右子樹的高度相同時，表示它是一個滿二叉樹
    // 當其左子樹的高度大於右子樹的高度時，那麽其右子樹是滿二叉樹，繼續探查其左子樹。
    public static int bs(Node node, int l, int h) {
        if (l == h) {
            return 1;
        }
        if (mostLeftLevel(node.right, l + 1) == h) {
            return (1 << (h - l)) + bs(node.right, l + 1, h);
        } else {
            return (1 << (h - l - 1)) + bs(node.left, l + 1, h);
        }
    }

    public static int mostLeftLevel(Node node, int level) {
        while (node != null) {
            level++;
            node = node.left;
        }
        return level - 1;
    }


    /**
     * 直接遞歸版本,沒有利用到完全二叉樹的特性 * * @param root * @return
     */
    public int countNodes1(Node root) {
        if (root == null) {
            return 0;
        }
        return countNodes1(root.left) + countNodes1(root.right) + 1;
    }

    /**
     * 叠代版本,沒有利用到完全二叉樹的特性
     * * * @param root
     * * @return
     */
    public int countNodes2(Node root) {
        if (root == null) {
            return 0;
        }

        int nodeNum = 0;
        Deque<Node> deque = new LinkedList<>();
        deque.addLast(root);
        while (!deque.isEmpty()) {
            nodeNum++;
            Node node = deque.removeFirst();
            if (node.left != null) {
                deque.addLast(node.left);
            }
            if (node.right != null) {
                deque.addLast(node.right);
            }
        }

        return nodeNum;
    }

    /**
     * 遞歸版本,利用完全二叉樹的特性進行計算
     * * @param root
     * * @return
     * 對於完全二叉樹中的一個節點，如果它的左子樹的深度等於右子樹的深度，那麼該節點是一顆完全二叉樹，
     * 可以使用公式 num = 2^h - 1直接計算該子樹的節點個數，
     * 可以減少一部分的遞歸時間
     */
    public static int countNodes3(Node root) {
        if (root == null) {
            return 0;
        }

        Node l = root, r = root;
        // 記錄左、右子樹的高度
        int hl = 0, hr = 0;
        while (l != null) {
            l = l.left;
            hl++;
        }
        while (r != null) {
            r = r.right;
            hr++;
        }
        // 如果左右子樹的高度相同，則是一棵滿二叉樹
        if (hl == hr) {
            return (int) Math.pow(2, hl) - 1;
        }
        // 如果左右高度不同，則按照普通二叉樹的邏輯計算
        return 1 + countNodes3(root.left) + countNodes3(root.right);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        System.out.println(nodeNum(head));
        System.out.println(countNodes3(head));
    }

}
