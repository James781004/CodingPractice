package EndlessCheng.GenreMenu.Backtracking.NormalTree.TopDown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumOfMinutes {

    // https://leetcode.cn/problems/time-needed-to-inform-all-employees/solutions/2251986/shen-ru-li-jie-di-gui-zi-ding-xiang-xia-ps0mm/
    private int ans;

    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            if (manager[i] >= 0) {
                g[manager[i]].add(i); // 建樹
            }
        }
        dfs(g, informTime, headID, 0); // 從根節點 headID 開始遞歸
        return ans;
    }

    private void dfs(List<Integer>[] g, int[] informTime, int x, int pathSum) {
        pathSum += informTime[x]; // 累加遞歸路徑上的 informTime[x]
        ans = Math.max(ans, pathSum); // 更新答案的最大值
        for (int y : g[x]) { // 遍歷 x 的兒子 y（如果沒有兒子就不會進入循環）
            dfs(g, informTime, y, pathSum); // 繼續遞歸
        }
    }


}
