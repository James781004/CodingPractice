package LeetcodeMaster.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q02_VisitAllRooms {
//    841.鑰匙和房間
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0841.%E9%92%A5%E5%8C%99%E5%92%8C%E6%88%BF%E9%97%B4.md
//
//    有 N 個房間，開始時你位於 0 號房間。每個房間有不同的號碼：0，1，2，...，N-1，並且房間里可能有一些鑰匙能使你進入下一個房間。
//
//    在形式上，對於每個房間 i 都有一個鑰匙列表 rooms[i]，每個鑰匙 rooms[i][j] 由 [0,1，...，N-1] 中的一個整數表示，其中 N = rooms.length。
//    鑰匙 rooms[i][j] = v 可以打開編號為 v 的房間。
//
//    最初，除 0 號房間外的其余所有房間都被鎖住。
//
//    你可以自由地在房間之間來回走動。
//
//    如果能進入每個房間返回 true，否則返回 false。
//
//    示例 1：
//
//    輸入: [[1],[2],[3],[]]
//    輸出: true
//    解釋: 我們從 0 號房間開始，拿到鑰匙 1。 之後我們去 1 號房間，拿到鑰匙 2。 然後我們去 2 號房間，拿到鑰匙 3。
//    最後我們去了 3 號房間。 由於我們能夠進入每個房間，我們返回 true。
//    示例 2：
//
//    輸入：[[1,3],[3,0,1],[2],[0]]
//    輸出：false
//    解釋：我們不能進入 2 號房間。

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        List<Boolean> visited = new ArrayList<Boolean>() {{
            for (int i = 0; i < rooms.size(); i++) {
                add(false);
            }
        }};
        dfs(0, rooms, visited);
        //檢查是否都訪問到了
        for (boolean flag : visited) {
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int key, List<List<Integer>> rooms, List<Boolean> visited) {
        if (visited.get(key)) {
            return;
        }
        visited.set(key, true);
        for (int k : rooms.get(key)) {
            // 深度優先搜索遍歷
            dfs(k, rooms, visited);
        }
    }

    public boolean canVisitAllRoomsBFS(List<List<Integer>> rooms) {
        boolean[] visited = new boolean[rooms.size()];
        visited[0] = true; //  0 號房間開始
        Queue<Integer> que = new LinkedList<>();
        que.offer(0); //  0 號房間開始

        // 廣度優先搜索的過程
        while (!que.isEmpty()) {
            int key = que.poll();
            List<Integer> keys = rooms.get(key);
            for (int k : keys) {
                que.offer(k);
                visited[k] = true;
            }
        }

        // 檢查房間是不是都遍歷過了
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) return false;
        }
        return true;
    }
}
