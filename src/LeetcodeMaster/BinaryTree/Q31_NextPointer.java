package LeetcodeMaster.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class Q31_NextPointer {
//    116. 填充每個節點的下一個右側節點指針
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0116.%E5%A1%AB%E5%85%85%E6%AF%8F%E4%B8%AA%E8%8A%82%E7%82%B9%E7%9A%84%E4%B8%8B%E4%B8%80%E4%B8%AA%E5%8F%B3%E4%BE%A7%E8%8A%82%E7%82%B9%E6%8C%87%E9%92%88.md
//
//    給定一個 完美二叉樹 ，其所有葉子節點都在同一層，每個父節點都有兩個子節點。二叉樹定義如下：
//
//    struct Node {
//        int val;
//        Node *left;
//        Node *right;
//        Node *next;
//    }
//    填充它的每個 next 指針，讓這個指針指向其下一個右側節點。如果找不到下一個右側節點，則將 next 指針設置為 NULL。
//
//    初始狀態下，所有 next 指針都被設置為 NULL。
//
//    進階：
//
//    你只能使用常量級額外空間。
//    使用遞歸解題也符合要求，本題中遞歸程序占用的棧空間不算做額外的空間覆雜度。


    class Node {
        public int value;
        public Node left;
        public Node right;
        public Node next;

        public TreeNode(int value) {
            this.value = value;
        }
    }


    // 遞歸法
    public Node connect(Node root) {
        traversal(root);
        return root;
    }

    private void traversal(Node cur) {
        if (cur == null) return;
        if (cur.left != null) cur.left.next = cur.right; // 操作1
        if (cur.right != null) {
            if (cur.next != null) cur.right.next = cur.next.left; //操作2
            else cur.right.next = null;
        }
        traversal(cur.left);  // 左
        traversal(cur.right); //右
    }


    public Node connect2(Node root) {
        if (root == null) return root;
        Queue<Node> que = new LinkedList<Node>();
        que.offer(root);
        Node nodePre = null;
        Node node = null;
        while (!que.isEmpty()) {
            int size = que.size();
            for (int i = 0; i < size; i++) { // 開始每一層的遍歷
                if (i == 0) {
                    nodePre = que.peek(); // 記錄一層的頭結點
                    que.poll();
                    node = nodePre;
                } else {
                    node = que.peek();
                    que.poll();
                    nodePre.next = node; // 本層前一個節點next指向本節點
                    nodePre = nodePre.next;
                }
                if (node.left != null) que.offer(node.left);
                if (node.right != null) que.offer(node.right);
            }
            nodePre.next = null; // 本層最後一個節點指向null
        }
        return root;
    }
}
