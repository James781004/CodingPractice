package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.*;

public class CanMouseWin {

    // https://leetcode.cn/problems/cat-and-mouse-ii/solutions/3070697/ni-xiang-si-wei-tuo-bu-xu-dpfu-yong-913-t99rl/
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // 左右上下

    public boolean canMouseWin(String[] grid, int catJump, int mouseJump) {
        int m = grid.length, n = grid[0].length();
        // 鼠和貓分別建圖
        List<Integer>[] gMouse = new ArrayList[m * n];
        List<Integer>[] gCat = new ArrayList[m * n];
        Arrays.setAll(gMouse, i -> new ArrayList<>());
        Arrays.setAll(gCat, i -> new ArrayList<>());
        int mx = 0, my = 0, cx = 0, cy = 0, fx = 0, fy = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = grid[i].charAt(j);
                if (c == '#') { // 牆
                    continue;
                }
                if (c == 'M') { // 鼠的位置
                    mx = i;
                    my = j;
                } else if (c == 'C') { // 貓的位置
                    cx = i;
                    cy = j;
                } else if (c == 'F') { // 食物（洞）的位置
                    fx = i;
                    fy = j;
                }
                int v = i * n + j; // 二維坐標 (i,j) 映射為一維坐標 v
                for (int[] dir : DIRS) { // 枚舉左右上下四個方向
                    for (int k = 0; k <= mouseJump; k++) { // 枚舉跳躍長度
                        int x = i + k * dir[0], y = j + k * dir[1];
                        if (x < 0 || x >= m || y < 0 || y >= n || grid[x].charAt(y) == '#') { // 出界或者遇到牆
                            break;
                        }
                        gMouse[v].add(x * n + y); // 連邊
                    }
                    for (int k = 0; k <= catJump; k++) { // 枚舉跳躍長度
                        int x = i + k * dir[0], y = j + k * dir[1];
                        if (x < 0 || x >= m || y < 0 || y >= n || grid[x].charAt(y) == '#') { // 出界或者遇到牆
                            break;
                        }
                        gCat[v].add(x * n + y); // 連邊
                    }
                }
            }
        }

        // 判斷是否鼠贏
        return catMouseGame(gMouse, gCat, mx * n + my, cx * n + cy, fx * n + fy) == 1;
    }

    // 913. 貓和老鼠
    private int catMouseGame(List<Integer>[] gMouse, List<Integer>[] gCat, int mouseStart, int catStart, int hole) {
        int n = gMouse.length;
        int[][][] deg = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                deg[i][j][0] = gMouse[i].size();
                deg[i][j][1] = gCat[j].size();
            }
        }

        int[][][] winner = new int[n][n][2];
        Queue<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            winner[hole][i][1] = 1; // 鼠到達洞中（此時輪到貓移動），鼠獲勝
            winner[i][hole][0] = 2; // 貓到達洞中（此時輪到鼠移動），貓獲勝
            winner[i][i][0] = winner[i][i][1] = 2; // 貓和鼠出現在同一個節點，無論輪到誰移動，都是貓獲勝
            q.offer(new int[]{hole, i, 1}); // 從終點開始倒推
            q.offer(new int[]{i, hole, 0});
            q.offer(new int[]{i, i, 0});
            q.offer(new int[]{i, i, 1});
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int mouse = cur[0], cat = cur[1], turn = cur[2];
            int win = winner[mouse][cat][turn]; // 最終誰贏了
            for (int[] pre : getPreStates(mouse, cat, turn, gMouse, gCat, winner)) {
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

        // 鼠在節點 mouseStart，貓在節點 catStart，當前輪到鼠移動
        return winner[mouseStart][catStart][0]; // 返回最終誰贏了（或者平局）
    }

    // 獲取 (mouse, cat, turn) 的上個狀態（值尚未確定）
    private List<int[]> getPreStates(int mouse, int cat, int turn, List<Integer>[] gMouse, List<Integer>[] gCat, int[][][] winner) {
        List<int[]> preStates = new ArrayList<>();
        if (turn == 0) { // 當前輪到鼠移動
            for (int preCat : gCat[cat]) { // 上一輪貓的位置
                if (winner[mouse][preCat][1] == 0) { // 貓無法移動到洞中
                    preStates.add(new int[]{mouse, preCat});
                }
            }
        } else { // 當前輪到貓移動
            for (int preMouse : gMouse[mouse]) { // 上一輪鼠的位置
                if (winner[preMouse][cat][0] == 0) {
                    preStates.add(new int[]{preMouse, cat});
                }
            }
        }
        return preStates;
    }


}
