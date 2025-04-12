package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayDeque;
import java.util.Queue;

public class ContainVirus {

    // https://leetcode.cn/problems/contain-virus/solutions/1673595/ge-chi-bing-du-by-leetcode-solution-vn9m/
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int containVirus(int[][] isInfected) {
        int m = isInfected.length, n = isInfected[0].length, ans = 0, maxArea, maxColor = 0, maxWall = 0, color;
        while (true) {
            maxArea = 0;
            color = 2;
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (isInfected[i][j] == 1) {
                        Queue<Integer> queue = new ArrayDeque<>();
                        // 初版情況下只會出現:-1 隔離區 0 正常區 1 病毒區，因此color需要從2開始，處理的時候只需要考慮後二者即可
                        // 改造後2表示隔離區，color從3開始，-color表示color遍歷時遇到的正常區
                        int wall = 0, count = 0;
                        isInfected[i][j] = ++color;
                        queue.offer(i * 100 + j);
                        while (!queue.isEmpty()) {
                            int tmp = queue.poll();
                            int x = tmp / 100, y = tmp % 100;
                            for (int k = 0; k < 4; ++k) {
                                int xx = x + dirs[k][0], yy = y + dirs[k][1];
                                if (xx < 0 || xx >= m || yy < 0 || yy >= n)
                                    continue;
                                if (isInfected[xx][yy] == 1) {
                                    queue.offer(xx * 100 + yy);
                                    isInfected[xx][yy] = color;
                                } else if (isInfected[xx][yy] <= 0) {
                                    wall++;
                                    if (isInfected[xx][yy] != -color) {
                                        isInfected[xx][yy] = -color;
                                        count++;
                                    }
                                }
                            }
                        }
                        if (count > maxArea) {
                            maxArea = count;
                            maxColor = color;
                            maxWall = wall;
                        }
                    }
                }
            }
            if (maxArea == 0)
                break;
            ans += maxWall;
            if (color == 3)
                break;
            // 注意這裡是需要將被涂色的區域還原的，否則可能影響bfs
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (isInfected[i][j] > 2) {
                        isInfected[i][j] = (isInfected[i][j] == maxColor) ? 2 : 1;
                    }
                    if (isInfected[i][j] < 0)
                        isInfected[i][j] = 0;
                }
            }
            // 擴張病毒區，第一輪將新擴展的區域標記為-1，防止連續擴張，第二輪再將-1復位為1
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (isInfected[i][j] == 1) {
                        for (int k = 0; k < 4; ++k) {
                            int x = i + dirs[k][0], y = j + dirs[k][1];
                            if (x >= 0 && x < m && y >= 0 && y < n && isInfected[x][y] == 0)
                                isInfected[x][y] = -1;
                        }
                    }
                }
            }
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (isInfected[i][j] == -1)
                        isInfected[i][j] = 1;
                }
            }
        }
        return ans;
    }


}
