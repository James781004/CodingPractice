package LeetcodeMaster.BinaryTree;

import java.util.*;

public class Q03_LevelOrder {
//    102.二叉樹的層序遍歷
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0102.%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E5%B1%82%E5%BA%8F%E9%81%8D%E5%8E%86.md
//
//    給你一個二叉樹，請你返回其按 層序遍歷 得到的節點值。 （即逐層地，從左到右訪問所有節點）。

    public List<List<Integer>> resList = new ArrayList<List<Integer>>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        //checkDfs(root,0);
        checkBfs(root);

        return resList;
    }

    private void checkDfs(TreeNode root, int deep) {
        if (root == null) return;
        deep++;
        if (resList.size() < deep) {
            // 當層級增加時，list的Item也增加，利用list的索引值進行層級界定
            List<Integer> item = new ArrayList<Integer>();
            resList.add(item);
        }
        resList.get(deep - 1).add(root.value);

        checkDfs(root.left, deep);
        checkDfs(root.right, deep);
    }

    private void checkBfs(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode cur;
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> itemList = new ArrayList<Integer>();
            int len = queue.size();

            while (len > 0) {
                cur = queue.poll();
                itemList.add(cur.value);

                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
                len--;
            }
            resList.add(itemList);
        }
    }


    // LC107
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        Deque<TreeNode> que = new LinkedList<>();

        if (root == null) {
            return list;
        }

        que.offerLast(root);
        while (!que.isEmpty()) {
            List<Integer> levelList = new ArrayList<>();
            int levelSize = que.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode peek = que.peekFirst();
                levelList.add(que.pollFirst().value);

                if (peek.left != null) {
                    que.offerLast(peek.left);
                }
                if (peek.right != null) {
                    que.offerLast(peek.right);
                }
            }
            list.add(levelList);
        }
        
        List<List<Integer>> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }

        return result;

    }
}
