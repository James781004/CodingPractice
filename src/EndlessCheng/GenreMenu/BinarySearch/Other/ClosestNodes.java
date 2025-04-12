package EndlessCheng.GenreMenu.BinarySearch.Other;

import EndlessCheng.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class ClosestNodes {

    // https://leetcode.cn/problems/closest-nodes-queries-in-a-binary-search-tree/solutions/2651916/zhong-xu-bian-li-er-fen-cha-zhao-pythonj-4ic0/
    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        List<Integer> arr = new ArrayList<>();
        dfs(root, arr);

        int n = arr.size();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = arr.get(i); // 轉成數組，效率更高
        }

        List<List<Integer>> ans = new ArrayList<>(queries.size()); // 預分配空間
        for (int q : queries) {
            int j = lowerBound(a, q); // 二分查找大於等於 q 的最小下標
            int mx = j == n ? -1 : a[j];
            if (j == n || a[j] != q) { // a[j]>q, a[j-1]<q
                j--;
            }
            int mn = j < 0 ? -1 : a[j];
            ans.add(List.of(mn, mx));
        }
        return ans;
    }

    private void dfs(TreeNode node, List<Integer> a) {
        if (node == null) {
            return;
        }
        dfs(node.left, a);
        a.add(node.val);
        dfs(node.right, a);
    }

    private int lowerBound(int[] a, int target) {
        int left = -1, right = a.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            int mid = (left + right) >>> 1; // 比 /2 快
            if (a[mid] >= target) {
                right = mid; // 范圍縮小到 (left, mid)
            } else {
                left = mid; // 范圍縮小到 (mid, right)
            }
        }
        return right;
    }


}
