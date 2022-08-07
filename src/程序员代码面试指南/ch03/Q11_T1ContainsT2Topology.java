package 程序员代码面试指南.ch03;

public class Q11_T1ContainsT2Topology {
    // 題目
    // 劍指 Offer 26. 樹的子結構
    //
    // 給定彼此獨立的兩棵樹頭節點分別為 t1 和 t2，判斷 t1 樹是否包含 t2 樹全部的拓撲結構。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

//    題解
//    思路如下：
//
//    如果 t1 中某棵子樹頭節點的值與 t2 頭節點的值一樣，則從這兩個頭節點開始匹配。
//    匹配的每一步，都讓 t1 上的節點跟著 t2 上的節點的先序遍歷移動，
//    每移動一步，都檢查 t1 的當前節點是否與 t2 當前節點的值一樣。
//
//    因此，如果 t1 的節點數為 N，t2 的節點數為 M，則該方法的時間覆雜度為 O(N×M)。

    // 前序遍歷，判斷是否"嚴格"子樹或"包含"子樹
    public static boolean contains(Node t1, Node t2) {
        if (t2 == null) return true;
        if (t1 == null) return false;
        return check(t1, t2) || contains(t1.left, t2) || contains(t1.right, t2);
    }

    // 判斷是否"嚴格"子樹，即h、t2都是頭結點的情況
    public static boolean check(Node h, Node t2) {
        if (t2 == null) return true;
        if (h == null || h.value != t2.value) return false;
        return check(h.left, t2.left) && check(h.right, t2.right);
    }

    public static void main(String[] args) {
        Node t1 = new Node(1);
        t1.left = new Node(2);
        t1.right = new Node(3);
        t1.left.left = new Node(4);
        t1.left.right = new Node(5);
        t1.right.left = new Node(6);
        t1.right.right = new Node(7);
        t1.left.left.left = new Node(8);
        t1.left.left.right = new Node(9);
        t1.left.right.left = new Node(10);

        Node t2 = new Node(2);
        t2.left = new Node(4);
        t2.left.left = new Node(8);
        t2.right = new Node(5);

        System.out.println(contains(t1, t2));
    }
}
