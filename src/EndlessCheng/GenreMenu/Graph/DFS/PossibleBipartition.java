package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.List;

public class PossibleBipartition {

    // https://leetcode.cn/problems/possible-bipartition/solutions/1895359/zhua-wa-mou-si-tu-jie-leetcode-by-muse-7-ac1o/
    public boolean possibleBipartition(int n, int[][] dislikes) {
        List<Integer>[] matrix = new ArrayList[n + 1];
        for (int i = 1; i < matrix.length; i++) matrix[i] = new ArrayList(n + 1);
        for (int[] item : dislikes) { // 預處理：創建每個人的仇人表
            matrix[item[0]].add(item[1]);
            matrix[item[1]].add(item[0]);
        }
        int[] record = new int[n + 1]; // 記錄分組情況
        for (int i = 1; i < matrix.length; i++)
            // i未分組，把i分到a組，如果分到a組會沖突：false
            if (record[i] == 0 && !dfs(matrix, record, i, 1)) return false;
        return true;
    }

    private boolean dfs(List<Integer>[] matrix, int[] record, int index, int group) {
        record[index] = group; //把 index 分到 group 組裡
        for (int i = 0; i < matrix[index].size(); i++) { // 仇人表裡的每一個元素都不可以同組
            int num = matrix[index].get(i); // num 已分組，且與 index 同在 group 組裡：false
            if (record[num] == group) return false;
            if (record[num] == 0 && !dfs(matrix, record, num, group * -1)) return false;
        }
        return true;
    }


}
