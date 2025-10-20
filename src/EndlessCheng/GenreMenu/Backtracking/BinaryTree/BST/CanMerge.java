package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BST;

import EndlessCheng.TreeNode;

import java.util.*;

public class CanMerge {

    // https://leetcode.cn/problems/merge-bsts-to-create-single-bst/solutions/870998/javajian-dan-shi-xian-by-dreampigsty-2-301k/
    Map<Integer, TreeNode> rootMap = new HashMap<>();
    List<Integer> nodeValList = new ArrayList<>();

    public TreeNode canMerge(List<TreeNode> trees) {
        //特殊情況判斷
        if (trees.isEmpty()) {
            return null;
        }
        if (trees.size() == 1) {
            return trees.get(0);
        }

        // 將根節點的值作為key，節點作為value放入rootMap
        // 葉子節點的值放入leafSet
        Set<Integer> leafSet = new HashSet<>();
        for (TreeNode node : trees) {
            int root = node.val;
            // 有節點重復則不存在解，直接返回null
            if (rootMap.containsKey(root)) {
                return null;
            }
            rootMap.put(root, node);
            TreeNode left = node.left;
            if (left != null) {
                // 有節點重復則不存在解，直接返回null
                if (leafSet.contains(left.val)) {
                    return null;
                }
                leafSet.add(left.val);
            }
            TreeNode right = node.right;
            if (right != null) {
                // 有節點重復則不存在解，直接返回null
                if (leafSet.contains(right.val)) {
                    return null;
                }
                leafSet.add(right.val);
            }
        }
        // 尋找根節點
        TreeNode rootNode = null;
        for (int root : rootMap.keySet()) {
            if (!leafSet.contains(root)) {
                rootNode = rootMap.get(root);
                break;
            }
        }
        // 不存在根節點，返回null
        if (rootNode == null) {
            return null;
        }
        rootMap.remove(rootNode.val);
        // 拼接rootNode，並判斷所有節點都在rootNode下
        dfs(rootNode);
        if (!rootMap.isEmpty()) {
            return null;
        }

        // 中序遍歷，並判斷rootNode為有效的二叉搜索樹
        dfs2(rootNode);
        for (int i = 1; i < nodeValList.size(); i++) {
            if (nodeValList.get(i) <= nodeValList.get(i - 1)) {
                return null;
            }
        }
        return rootNode;
    }

    public void dfs(TreeNode rootNode) {
        if (rootNode.left != null && rootMap.containsKey(rootNode.left.val)) {
            rootNode.left = rootMap.get(rootNode.left.val);
            rootMap.remove(rootNode.left.val);
            dfs(rootNode.left);
        }
        if (rootNode.right != null && rootMap.containsKey(rootNode.right.val)) {
            rootNode.right = rootMap.get(rootNode.right.val);
            rootMap.remove(rootNode.right.val);
            dfs(rootNode.right);
        }
    }

    public void dfs2(TreeNode treeNode) {
        if (treeNode.left != null) {
            dfs2(treeNode.left);
        }
        nodeValList.add(treeNode.val);
        if (treeNode.right != null) {
            dfs2(treeNode.right);
        }
    }


}
