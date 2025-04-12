package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.List;

public class AllPathsSourceTarget {

    // https://leetcode.cn/problems/all-paths-from-source-to-target/solutions/1666224/by-carlsun-2-66pf/
    // 存儲所有的路徑結果
    List<List<Integer>> results = new ArrayList<>();
    // 存儲當前搜索路徑上的節點
    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        // 始終從0開始，所以總是需要把節點0加入
        path.add(0);
        // 從節點0開始進行深度優先搜索
        dfs(graph, 0);
        // 返回所有找到的路徑
        return results;
    }

    // 深度搜索方法，參數是要遍歷的圖和當前節點編號
    public void dfs(int[][] graph, int n) {
        // 如果遍歷到最後一個節點，則停止遍歷
        if (n == graph.length - 1) {
            // 達到目標節點，保存此條路徑並結束搜索
            // 使用new ArrayList<>(path)創建path的一個副本，以避免後續對path的修改影響已經添加的結果
            results.add(new ArrayList<>(path));
            return;
        }

        // 遍歷當前節點所有關聯的節點
        for (int i = 0; i < graph[n].length; i++) {
            // 將當前節點保存在本次搜索路徑中
            path.add(graph[n][i]);
            // 繼續遍歷與當前節點關聯的節點
            dfs(graph, graph[n][i]);
            // 回溯：當遞歸返回時，移除最後訪問的節點，以便嘗試其他可能的路徑
            // 注意：ArrayList的remove()方法如果傳入整數會被作為下標，因此這裡使用path.size()-1來嚴格控制刪除最後一個元素
            path.remove(path.size() - 1);
        }
        return;
    }


}
