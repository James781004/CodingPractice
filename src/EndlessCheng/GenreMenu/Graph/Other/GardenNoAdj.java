package EndlessCheng.GenreMenu.Graph.Other;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GardenNoAdj {

    // https://leetcode.cn/problems/flower-planting-with-no-adjacent/solutions/6840/jian-dan-de-ran-se-wen-ti-bu-xu-yao-kao-lu-hui-su-/
    public int[] gardenNoAdj(int N, int[][] paths) {
        /* 這是一道簡單題，限制每個節點的度為3，同時提供四種顏色，因此不需要回溯 */
        /* 初始化節點，使用map保存節點與其臨界點的關系 */
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) {
            graph.put(i, new HashSet<>());
        }
        /* 初始化路徑信息 */
        for (int[] path : paths) {
            int a = path[0] - 1;
            int b = path[1] - 1;
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            boolean[] used = new boolean[5];
            /* 查看當前節點的所有鄰接點的色彩 */
            for (int adj : graph.get(i)) {
                used[res[adj]] = true;
            }
            /* 為當前節點染色 */
            for (int j = 1; j <= 4; j++) {
                if (!used[j]) {
                    res[i] = j;
                }
            }
        }
        return res;
    }


}
