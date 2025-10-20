package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountCombinations {

    // https://leetcode.cn/problems/number-of-valid-move-combinations-on-chessboard/solutions/1075478/go-mo-ni-by-endlesscheng-kjpt/
    // 每種棋子的移動方向
    private static final Map<Character, int[][]> PIECE_DIRS = Map.of(
            'r', new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}, // 車
            'b', new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}}, // 象
            'q', new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}} // 皇後
    );

    public int countCombinations(String[] pieces, int[][] positions) {
        int n = pieces.length;
        // 預處理所有合法移動
        Move[][] allMoves = new Move[n][];
        for (int i = 0; i < n; i++) {
            allMoves[i] = generateMoves(positions[i][0], positions[i][1], PIECE_DIRS.get(pieces[i].charAt(0)));
        }

        Move[] path = new Move[n]; // 注意 path 的長度是固定的
        return dfs(0, n, allMoves, path);
    }

    // 起點 (x0,y0)，移動方向 (dx,dy)，移動次數 step
    private record Move(int x0, int y0, int dx, int dy, int step) {
    }

    // 計算位於 (x0,y0) 的棋子在 dirs 這些方向上的所有合法移動
    private Move[] generateMoves(int x0, int y0, int[][] dirs) {
        final int SIZE = 8;
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(x0, y0, 0, 0, 0)); // 原地不動
        for (int[] d : dirs) {
            // 往 d 方向走 1,2,3,... 步
            int x = x0 + d[0], y = y0 + d[1];
            for (int step = 1; 0 < x && x <= SIZE && 0 < y && y <= SIZE; step++) {
                moves.add(new Move(x0, y0, d[0], d[1], step));
                x += d[0];
                y += d[1];
            }
        }
        return moves.toArray(Move[]::new);
    }

    // 判斷兩個移動是否合法，即不存在同一時刻兩個棋子重疊的情況
    private boolean isValid(Move m1, Move m2) {
        int x1 = m1.x0, y1 = m1.y0; // 初始位置
        int x2 = m2.x0, y2 = m2.y0;
        for (int i = 0; i < Math.max(m1.step, m2.step); i++) {
            // 每一秒走一步
            if (i < m1.step) {
                x1 += m1.dx;
                y1 += m1.dy;
            }
            if (i < m2.step) {
                x2 += m2.dx;
                y2 += m2.dy;
            }
            if (x1 == x2 && y1 == y2) { // 重疊
                return false;
            }
        }
        return true;
    }

    private int dfs(int i, int n, Move[][] allMoves, Move[] path) {
        if (i == n) {
            return 1;
        }
        int res = 0;
        outer:
        // 枚舉當前棋子的所有合法移動
        for (Move move1 : allMoves[i]) {
            // 判斷合法移動 move1 是否有效
            for (int j = 0; j < i; j++) {
                if (!isValid(move1, path[j])) {
                    continue outer; // 無效，枚舉下一個 move1
                }
            }
            path[i] = move1; // 直接覆蓋，無需恢復現場
            res += dfs(i + 1, n, allMoves, path); // 枚舉後續棋子的所有合法移動組合
        }
        return res;
    }


}
