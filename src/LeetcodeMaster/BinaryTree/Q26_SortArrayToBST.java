package LeetcodeMaster.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class Q26_SortArrayToBST {
//    108.將有序數組轉換為二叉搜索樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0108.%E5%B0%86%E6%9C%89%E5%BA%8F%E6%95%B0%E7%BB%84%E8%BD%AC%E6%8D%A2%E4%B8%BA%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91.md
//
//    將一個按照升序排列的有序數組，轉換為一棵高度平衡二叉搜索樹。
//
//    本題中，一個高度平衡二叉樹是指一個二叉樹每個節點 的左右兩個子樹的高度差的絕對值不超過 1。


    // 左閉右開
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length);
    }

    public TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if (left >= right) return null;
        if (right - left == 1) return new TreeNode(nums[left]);
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, left, mid);
        root.right = sortedArrayToBST(nums, mid + 1, right);
        return root;
    }

    // 左閉右閉
    public TreeNode sortedArrayToBST2(int[] nums) {
        TreeNode root = traversal(nums, 0, nums.length - 1);
        return root;
    }

    // 左閉右閉區間[left, right)
    private TreeNode traversal(int[] nums, int left, int right) {
        if (left > right) return null;

        int mid = left + ((right - left) >> 1);
        TreeNode root = new TreeNode(nums[mid]);
        root.left = traversal(nums, left, mid - 1);
        root.right = traversal(nums, mid + 1, right);
        return root;
    }


    public TreeNode sortedArrayToBSTQueue(int[] nums) {
        if (nums.length == 0) return null;

        //根節點初始化
        TreeNode root = new TreeNode(-1);
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> leftQueue = new LinkedList<>();
        Queue<Integer> rightQueue = new LinkedList<>();

        // 根節點入隊列
        nodeQueue.offer(root);
        // 0為左區間下標初始位置
        leftQueue.offer(0);
        // nums.size() - 1為右區間下標初始位置
        rightQueue.offer(nums.length - 1);

        while (!nodeQueue.isEmpty()) {
            TreeNode currNode = nodeQueue.poll();
            int left = leftQueue.poll();
            int right = rightQueue.poll();
            int mid = left + ((right - left) >> 1);

            // 將mid對應的元素給中間節點
            currNode.value = nums[mid];

            // 處理左區間
            if (left <= mid - 1) {
                currNode.left = new TreeNode(-1);
                nodeQueue.offer(currNode.left);
                leftQueue.offer(left);
                rightQueue.offer(mid - 1);
            }

            // 處理右區間
            if (right >= mid + 1) {
                currNode.right = new TreeNode(-1);
                nodeQueue.offer(currNode.right);
                leftQueue.offer(mid + 1);
                rightQueue.offer(right);
            }
        }
        return root;
    }
}
