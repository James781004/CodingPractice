package EndlessCheng.GenreMenu.DP.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CatMouseGame {

    // https://leetcode.cn/problems/cat-and-mouse/solutions/3070461/ni-xiang-si-wei-tuo-bu-xu-dppythonjavacg-wp8k/
    private static final int HOLE = 0;

    public int catMouseGame(int[][] g) {
        int n = g.length;
        int[][][] deg = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                deg[i][j][0] = g[i].length;
                deg[i][j][1] = g[j].length;
            }
            // 對於貓來說，所有連到洞的邊都不能走
            for (int j : g[HOLE]) {
                deg[i][j][1]--;
            }
        }

        int[][][] winner = new int[n][n][2];
        Queue<int[]> q = new ArrayDeque<>();
        for (int i = 1; i < n; i++) {
            winner[HOLE][i][1] = 1; // 鼠到達洞中（此時輪到貓移動），鼠獲勝
            winner[i][i][0] = winner[i][i][1] = 2; // 貓和鼠出現在同一個節點，無論輪到誰移動，都是貓獲勝
            q.offer(new int[]{HOLE, i, 1}); // 從終點開始倒推
            q.offer(new int[]{i, i, 0});
            q.offer(new int[]{i, i, 1});
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int mouse = cur[0], cat = cur[1], turn = cur[2];
            int win = winner[mouse][cat][turn]; // 最終誰贏了
            for (int[] pre : getPreStates(mouse, cat, turn, g, winner)) {
                int preMouse = pre[0], preCat = pre[1], preTurn = turn ^ 1;
                // 情況一：如果上一回合鼠從 pre 移動到 cur，最終鼠贏，那麼標記 pre 狀態的 winner = 鼠
                // 情況二：如果上一回合貓從 pre 移動到 cur，最終貓贏，那麼標記 pre 狀態的 winner = 貓
                // 情況三：如果上一回合鼠從 pre 移動到 cur，最終貓贏，那麼待定，直到我們發現從 pre 出發能到達的狀態都是貓贏，那麼標記 pre 狀態的 winner = 貓
                // 情況四：如果上一回合貓從 pre 移動到 cur，最終鼠贏，那麼待定，直到我們發現從 pre 出發能到達的狀態都是鼠贏，那麼標記 pre 狀態的 winner = 鼠
                if (preTurn == win - 1 || --deg[preMouse][preCat][preTurn] == 0) {
                    winner[preMouse][preCat][preTurn] = win;
                    q.offer(new int[]{preMouse, preCat, preTurn}); // 繼續倒推
                }
            }
        }

        // 鼠在節點 1，貓在節點 2，當前輪到鼠移動
        return winner[1][2][0]; // 返回最終誰贏了（或者平局）
    }

    // 獲取 (mouse, cat, turn) 的上個狀態（值尚未確定）
    private List<int[]> getPreStates(int mouse, int cat, int turn, int[][] g, int[][][] winner) {
        List<int[]> preStates = new ArrayList<>();
        if (turn == 0) { // 當前輪到鼠移動
            for (int preCat : g[cat]) { // 上一輪貓的位置
                if (preCat != HOLE && winner[mouse][preCat][1] == 0) { // 貓無法移動到洞中
                    preStates.add(new int[]{mouse, preCat});
                }
            }
        } else { // 當前輪到貓移動
            for (int preMouse : g[mouse]) { // 上一輪鼠的位置
                if (winner[preMouse][cat][0] == 0) {
                    preStates.add(new int[]{preMouse, cat});
                }
            }
        }
        return preStates;
    }

}
