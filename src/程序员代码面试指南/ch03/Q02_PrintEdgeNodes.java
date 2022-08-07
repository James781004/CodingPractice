package 程序员代码面试指南.ch03;

public class Q02_PrintEdgeNodes {
    // CD162 打印二叉树的边界节点
    //    描述
//    给定一颗二叉树的根节点 root，按照如下两种标准分别实现二叉树的边界节点的逆时针打印。
//    标准一：
//            1，根节点为边界节点。
//            2，叶节点为边界节点。
//            3，如果节点在其所在的层中是最左的或最右的，那么该节点也是边界节点。
//    标准二：
//            1，根节点为边界节点。
//            2，叶节点为边界节点。
//            3，树左边界延伸下去的路径为边界节点。
//            4，树右边界延伸下去的路径为边界节点。
//    ps:具体请对照样例
//    输入描述：
//    第一行输入两个整数 n 和 root，n 表示二叉树的总节点个数，root 表示二叉树的根节点。
//    以下 n 行每行三个整数 fa，lch，rch，表示 fa 的左儿子为 lch，右儿子为 rch。(如果 lch 为 0 则表示 fa 没有左儿子，rch同理)
//    输出描述：
//    输出两行整数分别表示按两种标准的边界节点。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void printEdge1(Node head) {
        if (head == null) return;
        int height = getHeight(head, 0);
        Node[][] edgeMap = new Node[height][2];  // 第一維代表當前層數，第二維代表最左和最右兩點
        setEdgeMap(head, 0, edgeMap);

        // 從上到下打印第i層的最左節點
        for (int i = 0; i != edgeMap.length; i++) {
            System.out.print(edgeMap[i][0].value + " ");
        }

        // 打印葉子節點,並且該葉子節點不是當前層的最左節點同時也不是當前層的最右節點
        printLeafNotInMap(head, 0, edgeMap);

        // 從下到上打印第i層的最右節點; 注意不打印根節點所在的層
        for (int i = edgeMap.length - 1; i != -1; i--) {

            // 細節: 打印右邊界時要注意檢查, 當前節點是不是也是左邊界!
            // 是的話就不能打印了, 因為在打印左邊界時已經打印了, 再打印就重覆了
            if (edgeMap[i][0] != edgeMap[i][1]) {
                System.out.print(edgeMap[i][1].value + " ");
            }
        }
        System.out.println();
    }


    private static int getHeight(Node head, int i) {
        if (head == null) return i;
        return Math.max(getHeight(head.left, i + 1), getHeight(head.right, i + 1));
    }

    private static void setEdgeMap(Node head, int i, Node[][] edgeMap) {
        if (head == null) return;

        // 核心:前序遍歷中, 第i層的情況: 第一次碰到的就是最左的節點,
        // 最後一次碰到的是最右節點; 也就是說每到第i層, 都要更新最右節點, 最左節點不動
        // 尋找最右和最右節點的思想很重要! 需要掌握; 這個思想建立在二叉樹的前序遍歷基礎上

        // 遍歷順序接下來會先走左邊，第一次碰到的就是最左節點，所以edgeMap[i][0]可能會先有最左邊界的值
        // 如果已經有值就不改動，否則記錄當下節點
        // 同一層左邊走完走右邊時，edgeMap[i][1]會不斷被更新，直到最後一次最右節點進來為止
        edgeMap[i][0] = edgeMap[i][0] == null ? head : edgeMap[i][0];
        edgeMap[i][1] = head;


        setEdgeMap(head.left, i + 1, edgeMap);
        setEdgeMap(head.right, i + 1, edgeMap);
    }

    public static void printLeafNotInMap(Node h, int level, Node[][] edgeMap) {
        if (h == null) {
            return;
        }

        // 左樹為空，右樹為空，且不在map裡面的節點直接印出來
        // 基本上都是不在最後一層的葉節點（沒有左右子樹的節點）
        if (h.left == null && h.right == null && h != edgeMap[level][0] && h != edgeMap[level][1]) {
            System.out.print(h.value + " ");
        }
        printLeafNotInMap(h.left, level + 1, edgeMap);
        printLeafNotInMap(h.right, level + 1, edgeMap);
    }

    public static void printEdge2(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");

        // 當head有左孩子和右孩子時; 進入到if內部後, 就不會再發生printEdge2的遞歸調用了
        if (head.left != null && head.right != null) {
            printLeftEdge(head.left, true);
            printRightEdge(head.right, true);
        } else {
            // 當head孩子不全時, 新條件新遞歸
            printEdge2(head.left != null ? head.left : head.right);
        }
        System.out.println();
    }

    // 根左右; 從上往下打印; 打印的條件:要麽print==true, 要麽root.right沒有兄弟節點
    public static void printLeftEdge(Node h, boolean print) {
        if (h == null) {
            return;
        }

        // 打印左邊界的狀況
        // 前次判斷print的結果，或者當前節點是不是左右都是空（葉節點）
        if (print || (h.left == null && h.right == null)) {
            System.out.print(h.value + " ");
        }

        // 左樹就直接打印
        printLeftEdge(h.left, print);

        // 右樹則要先判斷左樹狀況
        // 如果左樹為空，表示此右樹同時也是左樹
        // 這種情況就可以打印
        printLeftEdge(h.right, print && h.left == null ? true : false);
    }

    //左右根; 從下往上打印; 打印的條件:要麽print==true, 要麽root.left沒有兄弟節點
    public static void printRightEdge(Node h, boolean print) {
        if (h == null) {
            return;
        }

        // 左樹要先判斷右樹狀況
        // 如果右樹為空，表示此左樹同時也是右樹
        // 這種情況就可以打印
        printRightEdge(h.left, print && h.right == null ? true : false);

        // 右樹就直接打印
        printRightEdge(h.right, print);

        // 打印右邊界的狀況
        // 前次判斷print的結果，或者當前節點是不是左右都是空（葉節點）
        if (print || (h.left == null && h.right == null)) {
            System.out.print(h.value + " ");
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.right = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.right.left = new Node(7);
        head.left.right.right = new Node(8);
        head.right.left.left = new Node(9);
        head.right.left.right = new Node(10);
        head.left.right.right.right = new Node(11);
        head.right.left.left.left = new Node(12);
        head.left.right.right.right.left = new Node(13);
        head.left.right.right.right.right = new Node(14);
        head.right.left.left.left.left = new Node(15);
        head.right.left.left.left.right = new Node(16);

        printEdge1(head);
        printEdge2(head);

    }

}
