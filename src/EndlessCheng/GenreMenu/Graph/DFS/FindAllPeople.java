package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.*;

public class FindAllPeople {

    // https://leetcode.cn/problems/find-all-people-with-secret/solutions/2846243/fei-chang-you-ya-qing-xi-de-dfs-dai-ma-f-83zz/
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        // 將 meetings 按時間排序
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[2], b[2]));

        // 根據時間先後，為每個時刻構建一個鄰接表,置於列表中。
        // 考慮到本就是稀疏圖並再次拆分，非常分散，故每個鄰接表都用 hash map。
        List<Map<Integer, List<Integer>>> multiAdj = new ArrayList<>();
        for (int i = 0; i < meetings.length; i++) {
            if (i == 0 || meetings[i][2] > meetings[i - 1][2])
                multiAdj.add(new HashMap<>());
            Map<Integer, List<Integer>> adj = multiAdj.get(multiAdj.size() - 1);
            int u = meetings[i][0], v = meetings[i][1];
            adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            adj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }

        boolean[] visited = new boolean[n];
        visited[0] = true;
        visited[firstPerson] = true;

        for (Map<Integer, List<Integer>> adj : multiAdj) {
            // 選取訪問過的點 dfs 向外擴散，此處切忌遍歷 0～n-1
            for (int p : adj.keySet()) {
                if (visited[p])
                    dfs(p, adj, visited);
            }
        }

        // 篩選訪問過的點構成結果，即所有知曉秘密的專家
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (visited[i]) result.add(i);
        }
        return result;
    }


    private void dfs(int u, Map<Integer, List<Integer>> adj, boolean[] visited) {
        if (adj.get(u).isEmpty()) return;

        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                dfs(v, adj, visited);
            }
        }
    }

}
