package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CanFinish {

    // https://leetcode.cn/problems/course-schedule/solutions/2992884/san-se-biao-ji-fa-pythonjavacgojsrust-by-pll7/
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] g = new ArrayList[numCourses];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] p : prerequisites) {
            g[p[1]].add(p[0]);
        }

        int[] colors = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (colors[i] == 0 && dfs(i, g, colors)) {
                return false; // 有環
            }
        }
        return true; // 沒有環
    }

    private boolean dfs(int x, List<Integer>[] g, int[] colors) {
        colors[x] = 1; // x 正在訪問中
        for (int y : g[x]) {
            if (colors[y] == 1 || colors[y] == 0 && dfs(y, g, colors)) {
                return true; // 找到了環
            }
        }
        colors[x] = 2; // x 完全訪問完畢
        return false; // 沒有找到環
    }


}
