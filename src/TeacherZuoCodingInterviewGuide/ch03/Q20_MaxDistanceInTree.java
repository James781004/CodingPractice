package TeacherZuoCodingInterviewGuide.ch03;

public class Q20_MaxDistanceInTree {
//    描述
//    從二叉樹的節點 A 出發，可以向上或者向下走，但沿途的節點只能經過一次，當到達節點 B 時，路徑上的節點數叫作 A 到 B 的距離。
//    現在給出一棵二叉樹，求整棵樹上每對節點之間的最大距離。
//    輸入描述：
//    第一行輸入兩個整數 n 和 root，n 表示二叉樹的總節點個數，root 表示二叉樹的根節點。
//    以下 n 行每行三個整數 fa，lch，rch，表示 fa 的左兒子為 lch，右兒子為 rch。(如果 lch 為 0 則表示 fa 沒有左兒子，rch同理)
//    最後一行為節點 o1 和 o2。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int getMaxDistance(Node head) {
        return process(head).maxDistance;
    }

    public static class ReturnType {
        public int maxDistance; // 當前節點最遠距離
        public int height; // 當前節點高度（也就是從頭走到最深處的節點數）

        public ReturnType(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static ReturnType process(Node head) {
        if (head == null) return new ReturnType(0, 0);

        // 默認左右子樹返回相同資訊
        ReturnType leftData = process(head.left);
        ReturnType rightData = process(head.right);

        // 當前節點最遠距離: 左子樹最遠距離, 右子樹最遠距離, 1+左樹高度+右樹高度, 三者做比較
        int maxDistance = Math.max(leftData.height + rightData.height + 1,
                Math.max(leftData.maxDistance, rightData.maxDistance));

        // 當前節點高度: 左右子樹較大高度 + 1個當前節點
        int height = 1 + Math.max(leftData.height, rightData.height);

        return new ReturnType(maxDistance, height);
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.left = new Node(2);
        head1.right = new Node(3);
        head1.left.left = new Node(4);
        head1.left.right = new Node(5);
        head1.right.left = new Node(6);
        head1.right.right = new Node(7);
        head1.left.left.left = new Node(8);
        head1.right.left.right = new Node(9);
        System.out.println(getMaxDistance(head1));

        Node head2 = new Node(1);
        head2.left = new Node(2);
        head2.right = new Node(3);
        head2.right.left = new Node(4);
        head2.right.right = new Node(5);
        head2.right.left.left = new Node(6);
        head2.right.right.right = new Node(7);
        head2.right.left.left.left = new Node(8);
        head2.right.right.right.right = new Node(9);
        System.out.println(getMaxDistance(head2));

    }
}
