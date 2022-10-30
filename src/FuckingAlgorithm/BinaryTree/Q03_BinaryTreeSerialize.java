package FuckingAlgorithm.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class Q03_BinaryTreeSerialize {

//    https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/
//    297. 二叉樹的序列化與反序列化
//    序列化是將一個數據結構或者對象轉換為連續的比特位的操作，進而可以將轉換後的數據存儲在一個文件或者內存中，同時也可以通過網絡傳輸到另一個計算機環境，采取相反方式重構得到原數據。
//
//    請設計一個算法來實現二叉樹的序列化與反序列化。這裡不限定你的序列 / 反序列化算法執行邏輯，你只需要保證一個二叉樹可以被序列化為一個字符串並且將這個字符串反序列化為原始的樹結構。
//
//    提示: 輸入輸出格式與 LeetCode 目前使用的方式一致，詳情請參閱 LeetCode 序列化二叉樹的格式。你並非必須采取這種方式，你也可以采用其他的方法解決這個問題。


    class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }

    class CodecPreOrder {
        // Encodes a tree to a single string.
        String SEP = ",";
        String NULL = "#";

        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();
        }

        /* 輔助函數，將二叉樹存入 StringBuilder */
        private void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append(NULL).append(SEP);
                return;
            }

            /****** 前序遍歷位置 ******/
            sb.append(root.val).append(SEP);
            /***********************/

            serialize(root.left, sb);
            serialize(root.right, sb);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            // 將字符串轉化成列表
            LinkedList<String> nodes = new LinkedList<>();
            for (String s : data.split(SEP)) {
                nodes.addLast(s);
            }
            return deserialize(nodes);
        }

        /* 輔助函數，通過 nodes 列表構造二叉樹 */
        private TreeNode deserialize(LinkedList<String> nodes) {
            if (nodes.isEmpty()) return null;

            /****** 前序遍歷位置 ******/
            // 列表最左側就是根節點
            String first = nodes.removeFirst();
            if (first.equals(NULL)) return null;
            TreeNode root = new TreeNode(Integer.parseInt(first));
            /***********************/

            root.left = deserialize(nodes);
            root.right = deserialize(nodes);

            return root;
        }

    }

    class CodecPostOrder {
        String SEP = ",";
        String NULL = "#";

        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();
        }

        private void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append(NULL).append(SEP);
                return;
            }

            serialize(root.left);
            serialize(root.right);

            /****** 後序遍歷位置 ******/
            sb.append(root.val).append(SEP);
            /***********************/
        }

        public TreeNode deserialize(String data) {
            // 將字符串轉化成列表
            LinkedList<String> nodes = new LinkedList<>();
            for (String s : data.split(SEP)) {
                nodes.addLast(s);
            }
            return deserialize(nodes);
        }

        private TreeNode deserialize(LinkedList<String> nodes) {
            if (nodes.isEmpty()) return null;

            // 從後往前取出元素
            String last = nodes.removeLast();
            if (last.equals(NULL)) return null;
            TreeNode root = new TreeNode(Integer.parseInt(last));

            // 從後往前在 nodes 列表中取元素，一定要先構造右子樹，後構造左子樹
            root.right = deserialize(nodes);
            root.left = deserialize(nodes);

            return root;
        }

    }

    class CodecInOrder {
        // 中序遍歷的方式行不通，因為無法實現反序列化方法 deserialize。
        // 要想實現反序列方法，首先要構造 root 節點。
        // 前序遍歷得到的 nodes 列表中，第一個元素是 root 節點的值；後序遍歷得到的 nodes 列表中，最後一個元素是 root 節點的值。
        // 中序遍歷的代碼，root 的值被夾在兩棵子樹的中間，也就是在 nodes 列表的中間，
        // 不知道確切的索引位置，所以無法找到 root 節點，也就無法進行反序列化。
    }

    class CodecLevelOrder {
        String SEP = ",";
        String NULL = "#";

        public String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder sb = new StringBuilder();

            // 初始化隊列，將 root 加入隊列
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            while (!q.isEmpty()) {
                TreeNode cur = q.poll();

                /* 層級遍歷代碼位置 */
                if (cur == null) {
                    sb.append(NULL).append(SEP);
                    continue;
                }
                sb.append(cur.val).append(SEP);
                /*****************/

                q.offer(cur.left);
                q.offer(cur.right);
            }

            return sb.toString();
        }


        public TreeNode deserialize(String data) {
            if (data.isEmpty()) return null;
            String[] nodes = data.split(SEP);

            // 第一個元素就是 root 的值
            TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));

            // 隊列 q 記錄父節點，將 root 加入隊列
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            for (int i = 1; i < nodes.length; ) {
                // 隊列中存的都是父節點
                TreeNode parent = q.poll();

                // 父節點對應的左側子節點的值
                String left = nodes[i++];
                if (!left.equals(NULL)) {
                    parent.left = new TreeNode(Integer.parseInt(left));
                    q.offer(parent.left);
                } else {
                    parent.left = null;
                }

                // 父節點對應的右側子節點的值
                String right = nodes[i++];
                if (!right.equals(NULL)) {
                    parent.right = new TreeNode(Integer.parseInt(right));
                    q.offer(parent.right);
                } else {
                    parent.right = null;
                }
            }
            return root;
        }

    }
}
