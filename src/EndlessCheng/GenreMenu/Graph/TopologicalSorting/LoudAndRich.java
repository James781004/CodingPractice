package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

public class LoudAndRich {

    // https://leetcode.cn/problems/loud-and-rich/solutions/1158774/tong-ge-lai-shua-ti-la-yi-ti-liang-jie-t-gses/
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        // 拓撲排序：取入度為0的先入隊，減少它下游節點的入度，如果為0了也入隊，直到隊列中沒有元素為止

        int n = quiet.length;

        // 先整理入度表和鄰接表
        int[] inDegree = new int[n];

        boolean[][] g = new boolean[n][n];

        for (int[] r : richer) {
            inDegree[r[1]]++;
            g[r[0]][r[1]] = true;
        }

        // 初始化ans各位為自己
        // 題目說的是：在所有擁有的錢肯定不少於 person x 的人中，person y 是最安靜的人
        // 注意這裡的不少於，說明可以是自己
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = i;
        }

        // 拓撲排序開始
        int[] queue = new int[n];
        int in = 0, out = 0;
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue[in++] = i;
            }
        }

        while (out < in) {
            int p = queue[out++];
            // q是p的下游，也就是p比q有錢
            for (int q = 0; q < g[p].length; q++) {
                if (g[p][q]) {
                    // 如果p的安靜值比q小，更新p的安靜值對應的那個人
                    // 注意這裡p的安靜值，並不是原始的quiet數組中的quiet[p]
                    // 而是已經更新後的安靜值，所以，應該取quiet[ans[p]]
                    // 這裡沒有改變原來數組的內容，而是通過ans[p]間接引用的，細細品一下
                    if (quiet[ans[p]] < quiet[ans[q]]) {
                        ans[q] = ans[p];
                    }

                    if (--inDegree[q] == 0) {
                        queue[in++] = q;
                    }
                }
            }
        }

        return ans;
    }


}
