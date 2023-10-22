package Grind.Ch05_Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Q13_GraphValidTree {
    // https://aaronice.gitbook.io/lintcode/union_find/graph_valid_tree
    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) return false;

        // 初始化Union Find的father map，讓每一個節點的初始parent指向自己（自己跟自己是一個Group）
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        // 查找兩個節點的parent，如果相同，說明形成了環（Cycle），那麼這便不符合樹（Tree）的定義
        for (int[] edge : edges) {
            int x = find(edge[0], parent);
            int y = find(edge[1], parent);

            if (x == y) return false;
            parent[x] = y; // parent 如果不相同，則將其中一個節點設為另一個的parent，繼續循環
        }

        return true;
    }

    int find(int node, int[] parent) {
        if (parent[node] == -1) return node; // -1 表示本節點為父節點
        return parent[node] = find(parent[node], parent);
    }


    // DFS 解法
    public boolean validTreeDFS(int n, int[][] edges) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            map.put(i, list);
        }

        // 輸入的edge list轉化為adjacency list，便於基於vertex的搜索
        for (int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];

        if (!helper(0, -1, map, visited)) return false;

        for (boolean b : visited) {
            if (!b) return false;
        }

        return true;
    }

    public boolean helper(int curr, int parent, HashMap<Integer, ArrayList<Integer>> map, boolean[] visited) {
        if (visited[curr]) return false; // 如果出現二次訪問，就說明了有cycle

        visited[curr] = true; // 訪問過的節點設為 true

        // 訪問當前節點鄰居，選擇curr != parent才進行下一層的遞歸
        for (int i : map.get(curr)) {
            if (i != parent && !helper(i, curr, map, visited)) {
                return false;
            }
        }

        return true;
    }


    // BFS 解法
    public boolean validTreeBFS(int n, int[][] edges) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            map.put(i, list);
        }

        // 輸入的edge list轉化為adjacency list，便於基於vertex的搜索
        for (int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int top = queue.poll();
            if (visited[top]) return false; // 如果遇到元素被二次訪問就說明有cycle

            visited[top] = true; // 訪問過的節點設為 true

            // 只在neighbor沒有被遍歷過時才放入queue中
            for (int i : map.get(top)) {
                // only putting new node into the queue
                if (!visited[i]) queue.offer(i);
            }
        }

        // 確認每一個元素都被遍歷到，才是valid tree（沒有落單的節點）
        for (boolean b : visited) {
            if (!b) return false;
        }

        return true;
    }
}
