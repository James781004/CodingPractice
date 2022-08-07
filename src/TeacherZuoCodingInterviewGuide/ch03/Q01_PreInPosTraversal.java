package TeacherZuoCodingInterviewGuide.ch03;

import java.util.Stack;

public class Q01_PreInPosTraversal {
    //    描述
//    分别按照二叉树先序，中序和后序打印所有的节点。
//    输入描述：
//    第一行输入两个整数 n 和 root，n 表示二叉树的总节点个数，root 表示二叉树的根节点。
//
//    以下 n 行每行三个整数 fa，lch，rch，表示 fa 的左儿子为 lch，右儿子为 rch。(如果 lch 为 0 则表示 fa 没有左儿子，rch同理)
//    输出描述：
//    输出三行，分别表示二叉树的先序，中序和后序。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 遞歸部分
    public static void preOrderRecur(Node head) {
        if (head == null) return;
        System.out.println(head.value + " ");
        preOrderRecur(head.left);
        preOrderRecur(head.right);
    }

    public static void inOrderRecur(Node head) {
        if (head == null) return;
        inOrderRecur(head.left);
        System.out.println(head.value + " ");
        inOrderRecur(head.right);
    }

    public static void posOrderRecur(Node head) {
        if (head == null) return;
        posOrderRecur(head.left);
        posOrderRecur(head.right);
        System.out.println(head.value + " ");
    }


    // 迭代部分
    public static void preOrderUnRecur(Node head) {
        System.out.print("pre-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()) {
                // 順序為頭左右，所以頭必須先出來
                head = stack.pop();
                System.out.print(head.value + " ");

                // stack的特性是LIFO，左要先pop的話就先push右邊進去
                if (head.right != null) {
                    stack.push(head.right);
                }

                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }

        System.out.println();
    }

    public static void inOrderUnRecur(Node head) {
        System.out.print("in-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                // 順序為左頭右，頭必須先push，之後指針先往左
                if (head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    // 左樹已經走到底，就可以先處理stack
                    // stack頂部的一定是左樹最左節點，然後依次往頭節點移動
                    // 接下來就找右樹，指針往右走
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }

        System.out.println();
    }

    public static void posOrderUnRecur1(Node head) {
        System.out.print("pos-order: ");
        if (head != null) {

            // 後序順序為左右頭，這邊策略是使用兩個stack
            // 利用stack1以頭右左順序彈出處理，stack2再把整體逆序即可
            Stack<Node> stack1 = new Stack<>();
            Stack<Node> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()) {
                head = stack1.pop(); // 先彈出頭節點，stack2接著儲存下來
                stack2.push(head);
                if (head.left != null) {
                    stack1.push(head.left);  // stack1先壓左再壓右，下一個先彈出的就是右節點
                }
                if (head.right != null) {
                    stack1.push(head.right);
                }
            }

            while (!stack2.isEmpty()) { // stack2再把整體彈出(逆序)即可
                System.out.print(stack2.pop().value + " ");
            }
        }

        System.out.println();
    }

    public static void posOrderUnRecur2(Node h) {
        System.out.print("pos-order: ");
        if (h != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(h);
            Node c = null;
            while (!stack.isEmpty()) {
                c = stack.peek(); // 先看當前棧頂節點狀況
                if (c.left != null && h != c.left && h != c.right) { // stack先壓左再壓右
                    stack.push(c.left);
                } else if (c.right != null && h != c.right) { // 左邊全部壓完才考慮壓右
                    stack.push(c.right);
                } else {
                    // 根節點和左邊節點已經壓到底，下一個先彈出的就是整體左子樹最左節點
                    // 然後指針h移上去表示當前子樹的頭節點，然後開始處理右節點(else if部分)，壓入左樹的右部分
                    // 如果連右邊都沒得壓，左子樹就可以依據左右頭順序後序處理
                    // 左子樹處理完畢右子樹比照辦理，指針h不斷移上去表示當前子樹的頭節點
                    // 最後彈出棧底的整體頭節點即可
                    System.out.print(stack.pop().value + " ");
                    h = c;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);

        // recursive
        System.out.println("==============recursive==============");
        System.out.print("pre-order: ");
        preOrderRecur(head);
        System.out.println();
        System.out.print("in-order: ");
        inOrderRecur(head);
        System.out.println();
        System.out.print("pos-order: ");
        posOrderRecur(head);
        System.out.println();

        // unrecursive
        System.out.println("============unrecursive=============");
        preOrderUnRecur(head);
        inOrderUnRecur(head);
        posOrderUnRecur1(head);
        posOrderUnRecur2(head);

    }
}
