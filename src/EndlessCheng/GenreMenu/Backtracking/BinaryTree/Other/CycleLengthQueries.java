package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

public class CycleLengthQueries {

    // https://leetcode.cn/problems/cycle-length-queries-in-a-tree/solutions/2024527/zui-jin-gong-gong-zu-xian-pythonjavacgo-v8ata/
    public int[] cycleLengthQueries(int n, int[][] queries) {
        var m = queries.length;
        var ans = new int[m];
        for (var i = 0; i < m; ++i) {
            int res = 1, a = queries[i][0], b = queries[i][1];
            while (a != b) {
                if (a > b) a /= 2;
                else b /= 2;
                ++res;
            }
            ans[i] = res;
        }
        return ans;
    }

}
