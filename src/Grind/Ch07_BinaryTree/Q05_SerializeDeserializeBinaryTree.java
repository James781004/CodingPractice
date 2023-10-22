package Grind.Ch07_BinaryTree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Q05_SerializeDeserializeBinaryTree {
    // https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/solutions/438446/bfshe-dfsliang-chong-fang-shi-jie-jue-by-sdwwld-3/
    public class CodecBFS {
        // 把樹轉化為字符串（使用BFS遍歷）
        public String serialize(TreeNode root) {
            // 邊界判斷，如果為空就返回一個字符串"#"
            if (root == null)
                return "#";
            // 創建一個隊列
            Queue<TreeNode> queue = new LinkedList<>();
            StringBuilder res = new StringBuilder();
            // 把根節點加入到隊列中
            queue.add(root);
            while (!queue.isEmpty()) {
                // 節點出隊
                TreeNode node = queue.poll();
                // 如果節點為空，添加一個字符"#"作為空的節點
                if (node == null) {
                    res.append("#,");
                    continue;
                }
                // 如果節點不為空，把當前節點的值加入到字符串中，
                // 注意節點之間都是以逗號","分隔的，在下面把字符
                // 串還原二叉樹的時候也是以逗號","把字符串進行拆分
                res.append(node.val + ",");
                // 左子節點加入到隊列中（左子節點有可能為空）
                queue.add(node.left);
                // 右子節點加入到隊列中（右子節點有可能為空）
                queue.add(node.right);
            }
            return res.toString();
        }

        // 把字符串還原為二叉樹
        public TreeNode deserialize(String data) {
            // 如果是"#"，就表示一個空的節點
            if (data == "#") return null;
            Queue<TreeNode> queue = new LinkedList<>();
            // 因為上面每個節點之間是以逗號","分隔的，所以這裡
            // 也要以逗號","來進行拆分
            String[] values = data.split(",");
            // 上面使用的是BFS，所以第一個值就是根節點的值，這裡創建根節點
            TreeNode root = new TreeNode(Integer.parseInt(values[0]));
            queue.add(root);
            int i = 1;
            while (!queue.isEmpty()) {
                // 隊列中節點出棧
                TreeNode parent = queue.poll();
                // 因為在BFS中左右子節點是成對出現的，所以這裡挨著的兩個值一個是
                // 左子節點的值一個是右子節點的值，當前值如果是"#"就表示這個子節點
                // 是空的，如果不是"#"就表示不是空的
                if (!"#".equals(values[i])) {
                    TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                    parent.left = left;
                    queue.add(left);
                }
                i++;

                // 上面如果不為空就是左子節點的值，這裡是右子節點的值，注意這裡有個i++，
                if (!"#".equals(values[i])) {
                    TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                    parent.right = right;
                    queue.add(right);
                }
                i++;
            }
            return root;
        }
    }


    public class CodecDFS {

        // 把樹轉化為字符串（使用DFS遍歷，也是前序遍歷，順序是：根節點→左子樹→右子樹）
        public String serialize(TreeNode root) {
            // 邊界判斷，如果為空就返回一個字符串"#"
            if (root == null)
                return "#";
            return root.val + "," + serialize(root.left) + "," + serialize(root.right);
        }

        // 把字符串還原為二叉樹
        public TreeNode deserialize(String data) {
            // 把字符串data以逗號","拆分，拆分之後存儲到隊列中
            Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
            return helper(queue);
        }

        private TreeNode helper(Queue<String> queue) {
            // 出隊
            String sVal = queue.poll();
            // 如果是"#"表示空節點
            if ("#".equals(sVal))
                return null;
            // 否則創建當前節點
            TreeNode root = new TreeNode(Integer.valueOf(sVal));
            // 分別創建左子樹和右子樹
            root.left = helper(queue);
            root.right = helper(queue);
            return root;
        }
    }
}
