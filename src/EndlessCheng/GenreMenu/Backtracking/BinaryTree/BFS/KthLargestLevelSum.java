package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.*;

public class KthLargestLevelSum {

    // https://leetcode.cn/problems/kth-largest-sum-in-a-binary-tree/solutions/2650642/bfspai-xu-kuai-su-xuan-ze-pythonjavacgoj-kl5c/
    public long kthLargestLevelSum(TreeNode root, int k) {
        List<Long> a = new ArrayList<>();
        List<TreeNode> q = List.of(root);
        while (!q.isEmpty()) {
            long sum = 0; // 利用 BFS，可以得到二叉樹每一層的節點值之和
            List<TreeNode> tmp = q;
            q = new ArrayList<>();
            for (TreeNode node : tmp) {
                sum += node.val;
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            a.add(sum); // BFS 的同時，把每一層的節點值之和保存到一個列表 a 中，把 a 排序後就可以得到第 k 大
        }
        int n = a.size();
        if (k > n) {
            return -1;
        }
        Collections.sort(a);
        return a.get(n - k);
    }


    // TopK問題，用優先隊列，時間復雜度應該可以降到O(nlogk)
    public long kthLargestLevelSumPQ(TreeNode root, int k) {
        // 小頂堆
        PriorityQueue<Long> pq = new PriorityQueue<>(k);
        // BFS
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()) {
            long sum = 0L;
            for (int i = que.size(); i > 0; i--) {
                TreeNode cur = que.poll();
                sum += cur.val;
                if (cur.left != null) {
                    que.offer(cur.left);
                }
                if (cur.right != null) {
                    que.offer(cur.right);
                }
            }
            if (pq.size() < k || sum > pq.peek()) {
                if (pq.size() == k) {
                    pq.poll();
                }
                pq.offer(sum);
            }
        }
        return pq.size() == k ? pq.peek() : -1;
    }


}
