package FuckingAlgorithm.Backtrcking;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Q08_BFS_SlidingPuzzle {
//    https://leetcode.cn/problems/sliding-puzzle/
//    773. 滑動謎題
//    在一個 2 x 3 的板上（board）有 5 塊磚瓦，用數字 1~5 來表示,
//    以及一塊空缺用 0 來表示。一次 移動 定義為選擇 0 與一個相鄰的數字（上下左右）進行交換.
//
//    最終當板 board 的結果是 [[1,2,3],[4,5,0]] 謎板被解開。
//
//    給出一個謎板的初始狀態 board ，返回最少可以通過多少次移動解開謎板，如果不能解開謎板，則返回 -1 。

    public int slidingPuzzle(int[][] board) {
        int m = 2, n = 3;
        StringBuilder sb = new StringBuilder();
        String target = "123450";
        // 將 2x3 的數組轉化成字符串作為 BFS 的起點
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]);
            }
        }
        String start = sb.toString();

        // 記錄一維字符串的相鄰索引
        int[][] neighbor = new int[][]{
                {1, 3},
                {0, 4, 2},
                {1, 5},
                {0, 4},
                {3, 1, 5},
                {4, 2}
        };

        /******* BFS 算法框架開始 *******/
        Queue<String> q = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        // 從起點開始 BFS 搜索
        q.offer(start);
        visited.add(start);

        int step = 0;
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                String cur = q.poll();
                // 判斷是否達到目標局面
                if (target.equals(cur)) {
                    return step;
                }
                // 找到數字 0 的索引
                int idx = 0;
                for (; cur.charAt(idx) != '0'; idx++) ;
                // 將數字 0 和相鄰的數字交換位置
                for (int adj : neighbor[idx]) {
                    String new_board = swap(cur.toCharArray(), adj, idx);
                    // 防止走回頭路
                    if (!visited.contains(new_board)) {
                        q.offer(new_board);
                        visited.add(new_board);
                    }
                }
            }
            step++;
        }
        /******* BFS 算法框架結束 *******/
        return -1;
    }

    private String swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }


    // 雙端回溯
    public int slidingPuzzle2(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]);
            }
        }
        String start = sb.toString();
        String target = "123450";

        //0 在各個位置的相鄰節點的索引
        int[][] neighbor = new int[][]{
                {1, 3},
                {0, 4, 2},
                {1, 5},
                {0, 4},
                {3, 1, 5},
                {4, 2}
        };

        Queue<String> q1 = new LinkedList<>();
        Queue<String> q2 = new LinkedList<>();
        //存儲相同字符串是否被拜訪過
        HashSet<String> visited = new HashSet<>();
        q1.offer(start);
        q2.offer(target);
        visited.add(start);

        int step = 0;
        while (!q1.isEmpty() && !q2.isEmpty()) {
            Queue<String> temp = new LinkedList<>();
            int size = q1.size();
            for (int i = 0; i < size; i++) {
                String cur = q1.poll();

                // 需要在內部檢查 q2 中是否存在 cur
                if (q2.contains(cur)) {
                    return step;
                }
                
                visited.add(cur);

                int index = 0;
                for (; cur.charAt(index) != '0'; index++) ;//找到0的索引位置
                //0 和 其相鄰的數字交換位置
                for (int adj : neighbor[index]) {
                    String new_board = swap(cur.toCharArray(), adj, index);
                    if (!visited.contains(new_board)) {
                        temp.offer(new_board);
                    }
                }
            }
            step++;
            q1 = q2;
            q2 = temp;
        }
        return -1;
    }
}
