package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.ArrayDeque;
import java.util.Deque;

public class MinPushBox {

    // https://leetcode.cn/problems/minimum-moves-to-move-a-box-to-their-target-location/solutions/2261099/python3javacgotypescript-yi-ti-yi-jie-sh-xgcz/
    private int m;
    private int n;
    private char[][] grid;

    public int minPushBox(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        this.grid = grid;
        int si = 0, sj = 0, bi = 0, bj = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 'S') {
                    si = i;
                    sj = j;
                } else if (grid[i][j] == 'B') {
                    bi = i;
                    bj = j;
                }
            }
        }
        int[] dirs = {-1, 0, 1, 0, -1};
        Deque<int[]> q = new ArrayDeque<>();
        boolean[][] vis = new boolean[m * n][m * n];
        q.offer(new int[]{f(si, sj), f(bi, bj), 0}); // 表示玩家位於 (si, sj)，箱子位於 (bi, bj)，並且已經進行了 d 次推動
        vis[f(si, sj)][f(bi, bj)] = true;
        while (!q.isEmpty()) {
            var p = q.poll();
            int d = p[2];
            bi = p[1] / n;
            bj = p[1] % n;
            if (grid[bi][bj] == 'T') {
                return d; // 箱子已經被推到目標位置，此時將 d 作為答案返回
            }
            si = p[0] / n;
            sj = p[0] % n;
            for (int k = 0; k < 4; ++k) {
                int sx = si + dirs[k], sy = sj + dirs[k + 1];
                if (!check(sx, sy)) {
                    continue;
                }

                // 如果 (sx, sy) 是一個合法的位置，
                // 判斷此時 (sx, sy) 是否與箱子的位置 (bi, bj) 相同
                if (sx == bi && sy == bj) {
                    // 如果相同，說明當前玩家到達了箱子的位置，並且推動箱子往前走了一步。
                    // 箱子新的位置為 (bx, by)
                    int bx = bi + dirs[k], by = bj + dirs[k + 1];
                    if (!check(bx, by) || vis[f(sx, sy)][f(bx, by)]) {
                        continue;
                    }

                    // 如果 (bx, by) 是一個合法的位置，且沒有被訪問過
                    // 加入隊列 q 的末尾
                    vis[f(sx, sy)][f(bx, by)] = true;
                    q.offer(new int[]{f(sx, sy), f(bx, by), d + 1});
                } else if (!vis[f(sx, sy)][f(bi, bj)]) {
                    // 如果不同，說明當前玩家沒有推動箱子，
                    // 那麼只需要判斷狀態 (sx, sy) (bi, bj) 是否被訪問過，
                    // 如果沒有被訪問過，加入隊列 q 的頭部
                    vis[f(sx, sy)][f(bi, bj)] = true;
                    q.offerFirst(new int[]{f(sx, sy), f(bi, bj), d});
                }
            }
        }
        return -1;
    }

    private int f(int i, int j) {
        return i * n + j;
    }

    private boolean check(int i, int j) {
        return i >= 0 && i < m && j >= 0 && j < n && grid[i][j] != '#';
    }


}
