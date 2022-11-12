package FuckingAlgorithm.Backtrcking;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Q07_BFS_Framework {

    class Node {
        int val;
        Node[] adj;

        public Node(int v) {
            val = v;
        }
    }

    // 計算從起點 start 到終點 target 的最近距離
    int BFS(Node start, Node target) {
        Queue<Node> q = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        q.offer(start); // 將起點加入隊列
        visited.add(start);
        int step = 0; // 記錄擴散的步數

        while (!q.isEmpty()) {
            int sz = q.size();

            /* 將當前隊列中的所有節點向四周擴散 */
            for (int i = 0; i < sz; i++) {
                Node cur = q.poll();

                /* 劃重點：這裡判斷是否到達終點 */
                if (cur == target) return step;

                /* 將 cur 的相鄰節點加入隊列 */
                for (Node x : cur.adj) {
                    if (!visited.contains(x)) {
                        q.offer(x);
                        visited.add(x);
                    }
                }
            }

            /* 劃重點：更新步數在這裡 */
            step++;
        }
        return step;
    }


//    https://leetcode.cn/problems/minimum-depth-of-binary-tree/
//    111. 二叉樹的最小深度
//    給定一個二叉樹，找出其最小深度。
//
//    最小深度是從根節點到最近葉子節點的最短路徑上的節點數量。
//
//    說明：葉子節點是指沒有子節點的節點。

    class TreeNode {
        int val;
        TreeNode left, right;

        public TreeNode(int v) {
            val = v;
        }
    }

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        // root 本身就是一層，depth 初始化為 1
        int depth = 1;

        while (!q.isEmpty()) {
            int sz = q.size();
            /* 遍歷當前層的節點 */
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();

                /* 判斷是否到達葉子結點 */
                if (cur.left == null && cur.right == null) return depth;

                /* 將下一層節點加入隊列 */
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            /* 這裡增加步數 */
            depth++;
        }
        return depth;
    }


//    https://leetcode.cn/problems/open-the-lock/
//    752. 打開轉盤鎖
//    你有一個帶有四個圓形撥輪的轉盤鎖。每個撥輪都有10個數字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
//    每個撥輪可以自由旋轉：例如把 '9' 變為 '0'，'0' 變為 '9' 。每次旋轉都只能旋轉一個撥輪的一位數字。
//
//    鎖的初始數字為 '0000' ，一個代表四個撥輪的數字的字符串。
//
//    列表 deadends 包含了一組死亡數字，一旦撥輪的數字和列表裡的任何一個元素相同，這個鎖將會被永久鎖定，無法再被旋轉。
//
//    字符串 target 代表可以解鎖的數字，你需要給出解鎖需要的最小旋轉次數，如果無論如何不能解鎖，返回 -1 。

    public int openLock(String[] deadends, String target) {
        // 記錄需要跳過的死亡密碼
        Set<String> deads = new HashSet<>();
        for (String s : deadends) deads.add(s);
        // 記錄已經窮舉過的密碼，防止走回頭路
        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        // 從起點開始啟動廣度優先搜索
        int step = 0;
        q.offer("0000");
        visited.add("0000");

        while (!q.isEmpty()) {
            int sz = q.size();
            /* 將當前隊列中的所有節點向周圍擴散 */
            for (int i = 0; i < sz; i++) {
                String cur = q.poll();

                /* 判斷是否到達終點 */
                if (deads.contains(cur))
                    continue;
                if (cur.equals(target))
                    return step;

                /* 將一個節點的未遍歷相鄰節點加入隊列 */
                for (int j = 0; j < 4; j++) {
                    String up = plusOne(cur, j);
                    if (!visited.contains(up)) {
                        q.offer(up);
                        visited.add(up);
                    }
                    String down = minusOne(cur, j);
                    if (!visited.contains(down)) {
                        q.offer(down);
                        visited.add(down);
                    }
                }
            }
            /* 在這裡增加步數 */
            step++;
        }
        // 如果窮舉完都沒找到目標密碼，那就是找不到了
        return -1;
    }

    // 將 s[j] 向上撥動一次
    String plusOne(String s, int j) {
        char[] ch = s.toCharArray();
        if (ch[j] == '9')
            ch[j] = '0';
        else
            ch[j] += 1;
        return new String(ch);
    }

    // 將 s[i] 向下撥動一次
    String minusOne(String s, int j) {
        char[] ch = s.toCharArray();
        if (ch[j] == '0')
            ch[j] = '9';
        else
            ch[j] -= 1;
        return new String(ch);
    }


    // 雙向 BFS 算法
    int openLock2(String[] deadends, String target) {
        Set<String> deads = new HashSet<>();
        for (String s : deadends) deads.add(s);
        // 用集合不用隊列，可以快速判斷元素是否存在
        Set<String> q1 = new HashSet<>();
        Set<String> q2 = new HashSet<>();
        Set<String> visited = new HashSet<>();

        int step = 0;
        q1.add("0000");
        q2.add(target);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            // 哈希集合在遍歷的過程中不能修改，用 temp 存儲擴散結果
            Set<String> temp = new HashSet<>();

            /* 將 q1 中的所有節點向周圍擴散 */
            for (String cur : q1) {
                /* 判斷是否到達終點 */
                if (deads.contains(cur))
                    continue;
                if (q2.contains(cur))
                    return step;

                visited.add(cur);

                /* 將一個節點的未遍歷相鄰節點加入集合 */
                for (int j = 0; j < 4; j++) {
                    String up = plusOne(cur, j);
                    if (!visited.contains(up))
                        temp.add(up);
                    String down = minusOne(cur, j);
                    if (!visited.contains(down))
                        temp.add(down);
                }
            }
            /* 在這裡增加步數 */
            step++;
            // temp 相當於 q1
            // 這裡交換 q1 q2，下一輪 while 就是擴散 q2
            q1 = q2;
            q2 = temp;
        }
        return -1;
    }
}
