package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.*;

public class CutOffTree {

    // https://leetcode.cn/problems/cut-off-trees-for-golf-event/solutions/1512295/by-fuxuemingzhu-dtet/
    int m, n;
    int[][] f;
    int[][] DIR = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int cutOffTree(List<List<Integer>> forest) {
        // 思路：每次從樹中選擇一個最低的，看從當前位置（初始位置為 (0,0)）到該最低樹位置的距離
        m = forest.size();
        n = forest.get(0).size();
        f = new int[m][n];
        List<int[]> tree2Cut = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                f[i][j] = forest.get(i).get(j);
                if (f[i][j] > 1) {
                    tree2Cut.add(new int[]{f[i][j], i, j});
                }
            }
        }
        // 按照樹的高度從低到高排序
        Collections.sort(tree2Cut, (o1, o2) -> o1[0] - o2[0]);

        if (f[0][0] == 0) return -1;
        int xPrev = 0, yPrev = 0;
        int ans = 0;
        // 依次取出當前高度最低的樹
        for (int[] curr : tree2Cut) {
            int distance = bfs(xPrev, yPrev, curr[1], curr[2]); // 求從當前位置（起始位置為(0, 0)）到該最低樹的距離
            if (distance == -1) return -1;
            ans += distance;
            // 將當前樹的位置更新為下一次的起始位置
            xPrev = curr[1];
            yPrev = curr[2];
        }

        return ans;
    }

    private boolean inForest(int x, int y) {
        return x >= 0 && x < m && y >= 0 && y < n;
    }

    // 求從 (xStart, yStart) 到 (xEnd, yEnd) 的最短距離（BFS 第一次到達即為最短距離）
    private int bfs(int xStart, int yStart, int xEnd, int yEnd) {
        if (xStart == xEnd && yStart == yEnd) return 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{xStart, yStart});
        boolean[][] visited = new boolean[m][n];
        visited[xStart][yStart] = true;
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            steps++;
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int x = curr[0], y = curr[1];

                for (int[] dir : DIR) {
                    int xNew = x + dir[0], yNew = y + dir[1];
                    if (xNew == xEnd && yNew == yEnd) return steps;
                    // 確保新加入的必須是樹或者是空地
                    if (inForest(xNew, yNew) && !visited[xNew][yNew] && f[xNew][yNew] > 0) {
                        queue.offer(new int[]{xNew, yNew});
                        visited[xNew][yNew] = true;
                    }
                }
            }
        }
        // 遍歷完也沒到達 (xEnd, yEnd)，返回 -1
        return -1;
    }


}
