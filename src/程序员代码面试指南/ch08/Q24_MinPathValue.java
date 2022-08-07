package 程序员代码面试指南.ch08;

import java.util.LinkedList;
import java.util.Queue;

public class Q24_MinPathValue {
//    CD38 求最短通路值
//    描述
//    用一個整形矩陣matrix表示一個網格，1代表有路，0代表無路，
//    每一個位置只要不越界，都有上下左右四個方向，求從最左上角到右下角的最短通路值
//    例如，matrix為：
//            1 0 1 1 1
//            1 0 1 0 1
//            1 1 1 0 1
//            0 0 0 0 1
//    通路只有一條，由12個1構成，所以返回12
//[要求]
//    時間覆雜度為O(nm)，空間覆雜度為O(nm)

    // 使用BFS，找尋當前位置上下左右相對位置座標放進隊列，
    // 下個循環隊列頭元素再繼續相同動作處理，直到隊列為空，或者抵達終點停止
    public static int minPathValue(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0 || m[0][0] != 1
                || m[m.length - 1][m[0].length - 1] != 1) {
            return 0;
        }
        int res = 0;
        int[][] map = new int[m.length][m[0].length]; // 存放從(0,0)走到(i,j)的最短累加和
        map[0][0] = 1;
        Queue<Integer> rQ = new LinkedList<>(); // 行座標
        Queue<Integer> cQ = new LinkedList<>(); // 列座標
        rQ.add(0);
        cQ.add(0);
        int r = 0;
        int c = 0;

        while (!rQ.isEmpty()) {

            // 不斷彈出行列座標(r,c)
            r = rQ.poll();
            c = cQ.poll();

            // 如果已經到右下角就直接返回
            if (r == m.length - 1 && c == m[0].length - 1) {
                return map[r][c];
            }

            // 把座標上下左右位置都加進隊列
            walkTo(map[r][c], r - 1, c, m, map, rQ, cQ); // up
            walkTo(map[r][c], r + 1, c, m, map, rQ, cQ); // down
            walkTo(map[r][c], r, c - 1, m, map, rQ, cQ); // left
            walkTo(map[r][c], r, c + 1, m, map, rQ, cQ); // right
        }

        return res;
    }

    public static void walkTo(int pre, int toR, int toC, int[][] m,
                              int[][] map, Queue<Integer> rQ, Queue<Integer> cQ) {

        // 邊界條件:
        // 超過矩陣邊緣的(toR < 0 || toR == m.length || toC < 0 || toC == m[0].length)
        // 矩陣內不能走的(m[toR][toC] != 1)
        // 矩陣內已經走過的(map[toR][toC] != 0)
        // 直接return
        if (toR < 0 || toR == m.length || toC < 0 || toC == m[0].length
                || m[toR][toC] != 1 || map[toR][toC] != 0) {
            return;
        }
        map[toR][toC] = pre + 1; // 經過當前座標，累計次數為map裡面紀錄前面次數+1
        rQ.add(toR); // 座標加進隊列
        cQ.add(toC); // 座標加進隊列
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 0, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1}};
        System.out.println(minPathValue(matrix));

    }

}
