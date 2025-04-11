package EndlessCheng.Basic.BinaryTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class numOfMinutes {

    // https://leetcode.cn/problems/time-needed-to-inform-all-employees/solutions/2251986/shen-ru-li-jie-di-gui-zi-ding-xiang-xia-ps0mm/
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        List<Integer> g[] = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 0; i < n; ++i)
            if (manager[i] >= 0)
                g[manager[i]].add(i); // 建樹
        return dfs(g, informTime, headID); // 從根節點 headID 開始遞歸
    }

    private int dfs(List<Integer>[] g, int[] informTime, int x) {
        int maxPathSum = 0;
        for (int y : g[x]) // 遍歷 x 的兒子 y（如果沒有兒子就不會進入循環）
            maxPathSum = Math.max(maxPathSum, dfs(g, informTime, y));
        // 這和 104 題代碼中的 return max(lDepth, rDepth) + 1; 是一個意思
        return maxPathSum + informTime[x];
    }


}
