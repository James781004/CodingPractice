package Grind.Ch05_Graph;

import java.util.*;

public class Q16_MinimumKnightMoves {
    // https://wentao-shao.gitbook.io/leetcode/graph-search/1197.minimum-knight-moves
    // https://www.cnblogs.com/cnoodle/p/12820573.html

    private final int[][] DIRECTIONS = new int[][]{{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

    public int minKnightMoves(int x, int y) {
        // 無論 [x, y] 在哪個象限，只要他與（0，0）的距離相等，實際在哪個象限都是等價的。
        // 所以一開始我們計算方便，可以把 input 的這個 [x, y] 坐標取絕對值
        // 把問題限制在第一象限
        x = Math.abs(x);
        y = Math.abs(y);

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});

        // 記錄遍歷過的點
        Set<String> visited = new HashSet<>();
        visited.add("0,0");

        int result = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.remove();
                int curX = cur[0];
                int curY = cur[1];
                if (curX == x && curY == y) {
                    return result;
                }

                for (int[] d : DIRECTIONS) {
                    int newX = curX + d[0];
                    int newY = curY + d[1];
                    // If you remove this condition newX >= -1 && newY >= -1) this solution would give TLE.
                    // 已經把目標座標都轉換成絕對值，所以應該往第一象限走
                    // 往第三象限就沒有意義，必須過濾 newX newY 都 >= -1 的情況
                    if (!visited.contains(newX + "," + newY) && newX >= -1 && newY >= -1) {
                        queue.add(new int[]{newX, newY});
                        visited.add(newX + "," + newY);
                    }
                }
            }
            result++;
        }
        return -1;
    }


    public int minKnightMovesDFS(int x, int y) {
        Map<String, Integer> map = new HashMap<>();
        // base case
        map.put("0,0", 0);
        map.put("1,0", 3);
        map.put("1,1", 2);
        map.put("2,0", 2);
        return dfs(x, y, map);
    }

    private int dfs(int x, int y, Map<String, Integer> map) {
        // Sysmetrical of axis
        x = Math.abs(x);
        y = Math.abs(y);
        // Sysmetrical of diagonal
        if (x < y) {
            int temp = x;
            x = y;
            y = temp;
        }

        String s = x + "," + y;
        if (map.containsKey(s)) return map.get(s);  // 記憶化搜索
        int temp = Math.min(dfs(x - 2, y - 1, map),
                dfs(x - 1, y - 2, map)) + 1;
        map.put(s, temp); // 記憶化搜索
        return temp;
    }


}
