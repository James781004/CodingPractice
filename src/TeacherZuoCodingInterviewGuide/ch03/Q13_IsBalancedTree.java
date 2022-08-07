package TeacherZuoCodingInterviewGuide.ch03;

public class Q13_IsBalancedTree {
//    題目
//    平衡二叉樹的性質為：要麼是一棵空樹，要麼任何一個節點的左右子樹高度差的絕對值不超過 1。
//
//    給定一棵二叉樹的頭節點 head，判斷這棵二叉樹是否為平衡二叉樹。
//
//    要求：如果二叉樹的節點數為 N，則要求時間覆雜度為 O(N)。

//    樹形dp套路
//    https://blog.csdn.net/sinat_42483341/article/details/112000461

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 題解
    // 平衡二叉樹的標準是：對任何子樹來說，左子樹和右子樹的高度差都不超過1。
    // 本題解法的整體過程為 樹形 dp 套路。
    //
    // 首先，樹形dp 套路的前提是滿足的。依次考查每個節點為頭節點的子樹，如果都是平衡二叉樹，那麼整體就是平衡二叉樹。
    //
    // 樹形 dp 套路第一步
    // 以某個節點X 為頭節點的子樹中，分析答案有哪些可能性，並且這種分析是以X 的左子樹、X 的右子樹和X 整棵樹的角度來考慮可能性的。
    //
    // 可能性一：如果 X 的左子樹不是平衡的，則以 X 為頭節點的樹就是不平衡的。
    // 可能性二：如果 X 的右子樹不是平衡的，則以 X 為頭節點的樹就是不平衡的。
    // 可能性三：如果 X 的左子樹和右子樹高度差超過 1，則以 X 為頭節點的樹就是不平衡的。
    // 可能性四：如果上面可能性都沒中，那麼以 X 為頭節點的樹是平衡的。

    // 樹形 dp 套路第二步
    // 根據第一步的可能性分析，列出所有需要的信息。
    // 左子樹和右子樹都需要知道各自是否平衡，以及高度這兩個信息。
    //
    // 樹形 dp 套路第三步
    // 根據第二步信息匯總。定義信息如 ReturnType 類所示。
    //
    // 樹形 dp 套路第四步
    // 設計遞歸函數。遞歸函數是處理以X 為頭節點的情況下的答案，
    // 包括設計遞歸的 base case，默認直接得到左樹和右樹所有的信息，以及把可能性做整合，
    // 並且也返回第三步的信息結構這四個小步驟。
    // 本題的遞歸實現請看以下的 process 方法，主函數是以下的 isBalanced 方法。

    public static boolean isBalanced(Node head) {
        return process(head).isBalanced;
    }

    public static class ReturnType {
        public boolean isBalanced;
        public int height;

        public ReturnType(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static ReturnType process(Node head) {
        if (head == null) return new ReturnType(true, 0);
        ReturnType leftData = process(head.left);
        ReturnType rightData = process(head.right);

        // 當前頭節點高度：左子樹高度和右子樹高度較大的那一個 + 本身1個節點
        int height = Math.max(leftData.height, rightData.height) + 1;

        // 平衡樹條件：左子樹平衡 && 右子樹平衡 && 左右子樹高度相差不超過1
        boolean isBalanced = leftData.isBalanced && rightData.isBalanced
                && Math.abs(leftData.height - rightData.height) <= 1;
        return new ReturnType(isBalanced, height);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println(isBalanced(head));

    }
}
