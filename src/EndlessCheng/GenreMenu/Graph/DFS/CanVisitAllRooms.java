package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.List;

public class CanVisitAllRooms {

    // https://leetcode.cn/problems/keys-and-rooms/solutions/1678758/dai-ma-sui-xiang-lu-841-yao-chi-he-fang-8mlei/
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        List<Boolean> visited = new ArrayList<>() {{
            for (int i = 0; i < rooms.size(); i++) {
                add(false);
            }
        }};
        dfs(0, rooms, visited);
        // 檢查是否都訪問到了
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

}
