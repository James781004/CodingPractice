package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.LinkedList;
import java.util.Queue;

public class NearestExit {

    // https://leetcode.cn/problems/nearest-exit-from-entrance-in-maze/solutions/2938271/javapython3cbfsxi-dai-e-wai-xin-xi-de-zu-xmnf/
    private static final int directions[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // 轉移的四個方向

    public int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length, n = maze[0].length;
        maze[entrance[0]][entrance[1]] = '+';       // 將起點標記為牆，表示不可訪問
        int start = entrance[0] * n + entrance[1];  // 將起點坐標轉為一個int編號
        Queue<int[]> qu = new LinkedList<>();       // 隊列存儲某個位置的坐標編號和到達這個坐標的步數
        qu.offer(new int[]{start, 0});              // 初始化隊列，將起點入隊
        while (!qu.isEmpty()) {
            int[] front = qu.poll();                                  // 獲取隊首節點信息
            int r = front[0] / n, c = front[0] % n, d = front[1];     // 將坐標編號轉為坐標(r,c)
            for (int[] direct : directions) {
                int nextR = r + direct[0], nextC = c + direct[1];     // 往四個方向轉移，即下一步到達的坐標
                if (nextR == -1 || nextR == m || nextC == -1 || nextC == n || maze[nextR][nextC] == '+')
                    continue;    // 跳過不合法的坐標
                if (nextR == 0 || nextR == m - 1 || nextC == 0 || nextC == n - 1) {
                    return d + 1;                                       // 合法坐標且下一步到達出口，返回結果
                }
                int nextPos = nextR * n + nextC;    // 生成下一個坐標的編號
                qu.offer(new int[]{nextPos, d + 1});  // 將下一個坐標信息加入隊列
                maze[nextR][nextC] = '+';           // 入隊即為已訪問，將這個坐標標為牆
            }
        }
        return -1;                                  // 不能到達出口，返回-1
    }


}
