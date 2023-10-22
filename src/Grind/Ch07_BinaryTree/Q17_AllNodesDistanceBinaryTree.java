package Grind.Ch07_BinaryTree;

import java.util.*;

public class Q17_AllNodesDistanceBinaryTree {
    // https://leetcode.cn/problems/all-nodes-distance-k-in-binary-tree/solutions/439437/di-gui-fa-jie-jue-er-cha-shu-zhong-sou-suo-ju-chi-/
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new ArrayList<>();
        Map<TreeNode, TreeNode> familyMap = new HashMap<>();
        // 遞歸獲取父子節點映射關系
        map(root, familyMap);
        boolean[] visited = new boolean[501];
        // 遞歸獲取距離指定節點指定距離的所有節點值
        search(target, K, res, visited, familyMap);
        return res;
    }

    private void map(TreeNode root, Map<TreeNode, TreeNode> familyMap) {
        if (root == null) return;
        if (root.left != null) familyMap.put(root.left, root); // <左節點, 父節點>
        if (root.right != null) familyMap.put(root.right, root); // <右節點, 父節點>
        map(root.left, familyMap); // 繼續往下層
        map(root.right, familyMap);
    }

    private void search(TreeNode target, int K, List<Integer> res, boolean[] visited, Map<TreeNode, TreeNode> familyMap) {
        if (target == null || K < 0 || visited[target.val]) return;
        if (K == 0 && !visited[target.val]) {
            res.add(target.val);
            visited[target.val] = true;
            return;
        }
        visited[target.val] = true;

        // 除了下一層搜尋左右子樹之外，也要移動回到父節點去找另一側的可能性
        search(target.left, K - 1, res, visited, familyMap);
        search(target.right, K - 1, res, visited, familyMap);
        search(familyMap.get(target), K - 1, res, visited, familyMap);
    }


    // https://leetcode.cn/problems/all-nodes-distance-k-in-binary-tree/solutions/901032/java-xiang-xi-de-xin-lu-li-cheng-li-yong-1irv/
    public List<Integer> distanceKBFS(TreeNode root, TreeNode target, int k) {
        Deque<TreeNode> deque = new ArrayDeque<>();
        HashMap<TreeNode, TreeNode> map = new HashMap<>();

        // BFS 建立父子節點映射圖
        deque.addFirst(root);
        while (!deque.isEmpty()) {
            TreeNode node = deque.pollLast();
            if (node.left != null) {
                map.put(node.left, node);
                deque.addFirst(node.left);
            }
            if (node.right != null) {
                map.put(node.right, node);
                deque.addFirst(node.right);
            }
        }

        // 從 target 開始使用 BFS 進行遍歷
        HashSet<TreeNode> vis = new HashSet<>();
        deque.addFirst(target);
        vis.add(target); // 設置vis保存已經訪問過的節點，防止重複訪問
        int step = 0;
        List<Integer> ans = new ArrayList<>();
        if (step == k) ans.add(deque.pollLast().val);
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.pollLast();

                // 每一趟遍歷就將每一個節點的父節點以及左右孩子節點（如果有的話）加入下一趟遍歷的隊列中
                if (map.containsKey(node) && !vis.contains(map.get(node))) {
                    deque.addFirst(map.get(node));
                    vis.add(map.get(node));
                }

                if (node.left != null && !vis.contains(node.left)) {
                    deque.addFirst(node.left);
                    vis.add(node.left);
                }

                if (node.right != null && !vis.contains(node.right)) {
                    deque.addFirst(node.right);
                    vis.add(node.right);
                }
            }

            // 一趟遍歷結束之後，判斷當前步數是否等於k，如果等於k，就可以直接將隊列中的節點值直接作為結果返回
            // 如果不等於k，就開始下一趟遍歷。
            step++;
            if (step == k) {
                while (!deque.isEmpty()) {
                    ans.add(deque.pollLast().val);
                }
            }
        }
        return ans;
    }
}