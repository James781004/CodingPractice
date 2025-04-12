package EndlessCheng.GenreMenu.Graph.Other;

import java.util.Arrays;

public class DistanceLimitedPathsExist {

    // https://leetcode.cn/problems/checking-existence-of-edge-length-limited-paths/solutions/3028854/bi-ji-bing-cha-ji-chi-xian-cha-xun-by-hu-qlr5/
    class Solution {
        // 設定最大節點數為100001
        static int MAXN = 100001;
        // 存儲所有查詢問題，每個問題包括兩個節點、一個距離限制和一個原始索引
        static int[][] questions = new int[MAXN][4];
        // 並查集數組，用於存儲每個節點的父節點
        static int[] father = new int[MAXN];

        // 主方法，判斷是否存在距離受限的路徑
        public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
            int m = queries.length; // 查詢數量
            build(n, m, queries); // 初始化並查集和questions數組
            // 按查詢的距離限制升序排序
            Arrays.sort(questions, 0, m, (a, b) -> a[2] - b[2]);
            // 按邊的權重升序排序
            Arrays.sort(edgeList, (a, b) -> a[2] - b[2]);
            boolean[] ans = new boolean[m]; // 存儲每個查詢的結果
            for (int i = 0, j = 0; i < m; i++) {
                // 不斷合並權重小於當前查詢距離限制的邊
                for (; j < edgeList.length && edgeList[j][2] < questions[i][2]; j++) {
                    union(edgeList[j][0], edgeList[j][1]);
                }
                // 判斷當前查詢的兩個節點是否在同一集合中
                if (isSameSet(questions[i][0], questions[i][1])) {
                    ans[questions[i][3]] = true;
                }
            }
            return ans;
        }

        // 初始化並查集和questions數組
        void build(int n, int m, int[][] queries) {
            for (int i = 0; i < n; i++) {
                father[i] = i; // 每個節點的初始父節點是自己
            }
            for (int i = 0; i < m; i++) {
                // 將查詢信息存儲到questions數組中，同時記錄原始索引
                questions[i][0] = queries[i][0];
                questions[i][1] = queries[i][1];
                questions[i][2] = queries[i][2];
                questions[i][3] = i;
            }
        }

        // 查找節點i的根節點（路徑壓縮）
        int find(int i) {
            if (i != father[i]) {
                father[i] = find(father[i]); // 路徑壓縮，直接連接到根節點
            }
            return father[i];
        }

        // 判斷兩個節點是否在同一集合中
        boolean isSameSet(int x, int y) {
            return find(x) == find(y);
        }

        // 合並兩個節點所在的集合
        void union(int x, int y) {
            father[find(x)] = find(y); // 將x的根節點連接到y的根節點
        }
    }


}
