package GuChengAlgorithm.Ch03_Tree;

import java.util.*;

public class Q01_Structures {
    // https://docs.google.com/presentation/d/1Vk_z4_BNAvDS9NGinN9kJfvLLXw4BCLB1-FtZA2O5M8/edit#slide=id.gc5f91e4f31_1_6
    public String serialize(TreeNode root) {
        if (root == null) return "#";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }


    public TreeNode deserialize(String data) {
        Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeHelper(q);
    }

    private TreeNode deserializeHelper(Queue<String> q) {
        String s = q.poll();
        if (s.equals("#")) return null;
        TreeNode root = new TreeNode(Integer.valueOf(s));
        root.left = deserializeHelper(q);
        root.right = deserializeHelper(q);
        return root;
    }


    // https://docs.google.com/presentation/d/1Vk_z4_BNAvDS9NGinN9kJfvLLXw4BCLB1-FtZA2O5M8/edit#slide=id.gc5f91e4f31_2_13
    public String serializeNaryTree(Node root) {
        List<String> res = new ArrayList<>();
        serializeNaryTreeDfs(root, res);
        return String.join(",", res);
    }

    private void serializeNaryTreeDfs(Node root, List<String> res) {
        if (root == null) return;
        res.add(String.valueOf(root.val));
        res.add(String.valueOf(root.children.size()));
        for (Node child : root.children) serializeNaryTreeDfs(child, res);
    }

    public Node deserializeNaryTree(String data) {
        if (data.equals("")) return null;
        Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeNaryTreeDfs(q);
    }

    private Node deserializeNaryTreeDfs(Queue<String> q) {
        Node root = new Node(Integer.valueOf(q.poll()));
        int size = Integer.valueOf(q.poll());
        for (int i = 0; i < size; i++) {
            root.children.add(deserializeNaryTreeDfs(q));
        }
        return root;
    }


    // https://docs.google.com/presentation/d/1Vk_z4_BNAvDS9NGinN9kJfvLLXw4BCLB1-FtZA2O5M8/edit#slide=id.gc5f91e4f31_2_24
    public String serializeBST(TreeNode root) {
        if (root == null) return "";
        String res = String.valueOf(root.val);
        if (root.left != null) res += "," + serializeBST(root.left);
        if (root.right != null) res += "," + serializeBST(root.right);
        return res;
    }

    public TreeNode deserializeBST(String data) {
        if (data == null || data.length() == 0) return null;
        Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeBST(q, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode deserializeBST(Queue<String> q, int lower, int upper) {
        if (q.isEmpty()) return null;
        String s = q.peek();
        int val = Integer.parseInt(s);
        if (val < lower || val > upper) return null;
        q.poll();
        TreeNode root = new TreeNode(val);
        root.left = deserializeBST(q, lower, val);
        root.right = deserializeBST(q, val, upper);
        return root;
    }


    // https://docs.google.com/presentation/d/1Vk_z4_BNAvDS9NGinN9kJfvLLXw4BCLB1-FtZA2O5M8/edit#slide=id.gc5f91e4f31_2_40
    public TreeNode deserializeBST2(String data) {
        if (data == null || data.length() == 0) return null;
        int[] arr = Arrays.stream(data.split(",")).mapToInt(Integer::valueOf).toArray();
        TreeNode root = bstFromPreorder(arr, Integer.MAX_VALUE);
        return root;
    }

    int i = 0;

    private TreeNode bstFromPreorder(int[] A, int bound) {
        if (i == A.length || A[i] > bound) return null;
        TreeNode root = new TreeNode(A[i++]);
        root.left = bstFromPreorder(A, root.val);
        root.right = bstFromPreorder(A, bound);
        return root;
    }


    // https://docs.google.com/presentation/d/1Vk_z4_BNAvDS9NGinN9kJfvLLXw4BCLB1-FtZA2O5M8/edit#slide=id.gc346d2fea6_0_0
    int[] preorder;
    int[] inorder;
    int preOrderIndex;
    Map<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;
        this.preOrderIndex = 0;
        int N = preorder.length;
        for (int i = 0; i < N; i++) {
            map.put(inorder[i], i);
        }
        return buildHelper(0, N - 1);
    }

    private TreeNode buildHelper(int inStart, int inEnd) {
        if (inStart > inEnd) return null;
        TreeNode root = new TreeNode(preorder[preOrderIndex++]);
        int index = map.get(root.val);
        root.left = buildHelper(inStart, index - 1);
        root.right = buildHelper(index + 1, inEnd);
        return root;
    }


    // https://docs.google.com/presentation/d/1Vk_z4_BNAvDS9NGinN9kJfvLLXw4BCLB1-FtZA2O5M8/edit#slide=id.gc346d2fea6_0_11
//    int[] inorder;
    int[] postorder;
//    int preOrderIndex;
//    Map<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        this.inorder = inorder;
        this.postorder = postorder;
        int N = inorder.length;
        for (int i = 0; i < N; i++) {
            map.put(inorder[i], i);
        }
        return buildHelper2(0, N - 1, 0, N - 1);
    }

    private TreeNode buildHelper2(int inStart, int inEnd, int postStart, int postEnd) {
        if (inStart > inEnd) return null;
        TreeNode root = new TreeNode(postorder[postEnd]);
        int inIndex = map.get(root.val);
        int rightTreeSize = inEnd = inIndex;
        root.left = buildHelper2(inStart, inIndex - 1, postStart, postEnd - rightTreeSize - 1);
        root.right = buildHelper2(inIndex + 1, inEnd, postEnd - rightTreeSize, postEnd - 1);
        return root;
    }


    // https://docs.google.com/presentation/d/1Vk_z4_BNAvDS9NGinN9kJfvLLXw4BCLB1-FtZA2O5M8/edit#slide=id.gc346d2fea6_0_21
    Map<Integer, Integer> postMap = new HashMap<>();
    int[] pre;
    int[] post;
    int preStart = 0;

    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        this.pre = pre;
        this.post = post;
        int N = pre.length;
        for (int i = 0; i < post.length; i++) {
            postMap.put(post[i], i);
        }

        return constructFromPrePostHelper(0, N - 1, 0, N - 1);
    }

    private TreeNode constructFromPrePostHelper(int preLeft, int preRight, int postLeft, int postRight) {
        if (preLeft > preRight || postLeft > postRight) return null;
        if (preLeft == preRight) return new TreeNode(pre[preLeft]);
        TreeNode root = new TreeNode(pre[preLeft]);
        int index = postMap.get(pre[preLeft + 1]);  // 找到分割點：左子樹的頭
        int leftTreeSize = index - postLeft;  // 左子樹size
        root.left = constructFromPrePostHelper(preLeft + 1, preLeft + 1 + leftTreeSize,
                postLeft, postLeft + leftTreeSize);
        root.right = constructFromPrePostHelper(preLeft + leftTreeSize + 2, preRight,
                postLeft + leftTreeSize + 1, postRight - 1);
        return root;
    }


    // https://docs.google.com/presentation/d/1Vk_z4_BNAvDS9NGinN9kJfvLLXw4BCLB1-FtZA2O5M8/edit#slide=id.gc346d2fea6_0_30
    public TreeNode treeToDoubleList(TreeNode root) {
        TreeNode[] head = flatten(root);
        if (head[0] != null) {
            head[0].left = head[1];
            head[1].right = head[0];
        }
        return head[0];
    }

    private TreeNode[] flatten(TreeNode root) {
        if (root == null) return new TreeNode[]{null, null};
        TreeNode[] left = flatten(root.left);
        TreeNode[] right = flatten(root.right);
        TreeNode pre = root, next = root;
        if (left[1] != null) {
            left[1].right = root;
            root.left = left[1];
            pre = left[0];
        }

        if (right[0] != null) {
            root.right = right[0];
            right[0].left = root;
            next = right[1];
        }

        return new TreeNode[]{pre, next};
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }


    class Node {
        int val;
        List<Node> children;

        public Node(int v) {
            val = v;
        }
    }
}
