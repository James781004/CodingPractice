package EndlessCheng.GenreMenu.Grid.DFS;

import java.util.ArrayList;
import java.util.List;

public class BallGame {

    // https://leetcode.cn/problems/EXvqDp/solutions/1847059/mei-ju-by-endlesscheng-5wzf/
    public int[][] ballGame(int num, String[] plate) {
        int m = plate.length, n = plate[0].length();
        char[][] temp = new char[m][n];
        for (int i = 0; i < m; i++)
            temp[i] = plate[i].toCharArray();
        List<int[]> validPos = new ArrayList<>();

        // 枚舉左右兩條邊
        for (int i = 1; i < m - 1; i++) {
            if (temp[i][0] == '.') {
                if (dfs(temp, i, 0, 0, num))
                    validPos.add(new int[]{i, 0});
            }
            if (temp[i][n - 1] == '.') {
                if (dfs(temp, i, n - 1, 2, num))
                    validPos.add(new int[]{i, n - 1});
            }
        }

        // 枚舉上下兩條邊
        for (int j = 1; j < n - 1; j++) {
            if (temp[0][j] == '.') {
                if (dfs(temp, 0, j, 1, num))
                    validPos.add(new int[]{0, j});
            }
            if (temp[m - 1][j] == '.') {
                if (dfs(temp, m - 1, j, 3, num))
                    validPos.add(new int[]{m - 1, j});
            }
        }
        int s = validPos.size();
        int[][] res = new int[s][2];
        for (int i = 0; i < s; i++) {
            res[i] = validPos.get(i);
        }
        return res;
    }


    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int curDir = 0;

    public boolean dfs(char[][] plate, int x, int y, int curDir, int num) {
        int m = plate.length, n = plate[0].length;
        if (x < 0 || x >= m || y < 0 || y >= n)
            return false;
        if (plate[x][y] == 'O')
            return true;
        if (num == 0) { // 無剩餘步數
            return false;
        }
        if (plate[x][y] == 'W') {
            curDir = (curDir + 3) % 4;  // 逆時針
        } else if (plate[x][y] == 'E') {
            curDir = (curDir + 1) % 4;  // 順時針
        }
        int[] dir = dirs[curDir];
        if (dfs(plate, x + dir[0], y + dir[1], curDir, num - 1))
            return true;
        return false;
    }

}
