package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.*;

public class MinimumOperations {

    // https://leetcode.cn/problems/minimum-number-of-operations-to-sort-a-binary-tree-by-level/solutions/1965422/by-endlesscheng-97i9/
    public int minimumOperations(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        int ans = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int[] arr = new int[size], temp = new int[size];
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                arr[i] = temp[i] = cur.val;
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            Map<Integer, Integer> map = new HashMap<>();
            Arrays.sort(temp); // 離散化按嚴格遞增順序排序
            for (int i = 0; i < arr.length; i++) map.put(temp[i], i); // map 保存嚴格遞增順序下標的位置
            for (int i = 0; i < arr.length; i++) {
                while (arr[i] != temp[i]) { // 發現非嚴格遞增順序，進行交換並計算次數
                    int j = map.get(arr[i]);
                    int t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                    ans++;
                }
            }
        }
        return ans;
    }

}
