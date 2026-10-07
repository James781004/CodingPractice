package EndlessCheng.GenreMenu.DP.StateCompression.ContourLine;

import java.util.Arrays;

public class GetSchemeCount {

    // https://leetcode.cn/problems/1ybDKD/solutions/2240380/zhuang-ya-dp-bao-sou-xia-yi-ge-zhuang-ta-9dad/
    // {末尾添加空後的狀態, 末尾添加B後的狀態，末尾添加R後的狀態}
    static final int[][] trans = {
            // 空
            {1, 2},
            // 只有一個 B
            {3, 6},
            // 只有一個 R
            {5, 4},
            // 連續多個 B
            {3, -1},
            // 連續多個 R
            {-1, 4},
            // BR 交替，且以 B 結尾
            {-1, 6},
            // BR 交替，且以 R 結尾
            {5, -1},
    };
    static int n, m;
    static String[] board;
    static long cache[][];

    public long getSchemeCount(int _n, int _m, String[] _board) {
        if (_n < _m) {
            return getSchemeCount(_m, _n, rotate(_board));
        }
        n = _n;
        m = _m;
        board = _board;
        cache = new long[n + 1][n * (int) Math.pow(8, m) + 1];
        for (long[] c : cache) {
            Arrays.fill(c, -1);
        }
        return dfs(0, 0);
    }


    static private long dfs(int r, int mask) {
        if (cache[r][mask] != -1) {
            return cache[r][mask];
        }
        if (r == n) {
            return 1;
        }
        return cache[r][mask] = dfs(r, 0, 0, mask);
    }

    static private long dfs(int r, int c, int row_mask, int col_mask) {
        if (c == m) {
            return dfs(r + 1, col_mask);
        }
        char b = board[r].charAt(c);
        if (b == 'B') {
            return nxt(r, c, row_mask, col_mask, 0);
        }
        if (b == 'R') {
            return nxt(r, c, row_mask, col_mask, 1);
        }
        if (b == '.') {
            return dfs(r, c + 1, row_mask, col_mask);
        }
        return nxt(r, c, row_mask, col_mask, 0) + nxt(r, c, row_mask, col_mask, 1) + dfs(r, c + 1, row_mask, col_mask);

    }

    static private long nxt(int r, int c, int row_mask, int col_mask, int color) {
        int rm = trans[row_mask][color];
        if (rm < 0) {
            return 0;
        }
        int c3 = c * 3;
        int cm = trans[(col_mask >> c3) & 7][color];
        if (cm < 0) {
            return 0;
        }
        cm = col_mask & ~(7 << c3) | (cm << c3);
        return dfs(r, c + 1, rm, cm);
    }

    // 行列轉換
    static String[] rotate(String[] board) {
        int n = board.length, m = board[0].length();
        String[] newBoard = new String[m];
        for (int i = 0; i < m; ++i) {
            char[] row = new char[n];
            for (int j = 0; j < n; ++j) {
                row[j] = board[j].charAt(i);
            }
            newBoard[i] = new String(row);
        }
        return newBoard;
    }

}
