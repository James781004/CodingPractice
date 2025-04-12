package EndlessCheng.GenreMenu.Grid.DFS;

import java.util.ArrayList;
import java.util.Arrays;

public class PondSizes {

    // https://leetcode.cn/problems/pond-sizes-lcci/solutions/2316704/mo-ban-wang-ge-tu-dfsfu-ti-dan-by-endles-p0n1/
    public int[] pondSizes(int[][] land) {
        int m = land.length, n = land[0].length;
        var ans = new ArrayList<Integer>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (land[i][j] == 0) // 從沒有訪問過的 0 出發
                    ans.add(dfs(land, i, j));

        // 簡潔寫法，但是慢
        // return ans.stream().sorted().mapToInt(i -> i).toArray();

        // 更快的寫法
        var size = new int[ans.size()];
        int i = 0;
        for (int x : ans)
            size[i++] = x;
        Arrays.sort(size);
        return size;
    }

    private int dfs(int[][] land, int x, int y) {
        if (x < 0 || x >= land.length || y < 0 || y >= land[x].length || land[x][y] != 0)
            return 0;
        land[x][y] = 1; // 標記 (x,y) 被訪問，避免重復訪問
        int cnt0 = 1;
        // 訪問八方向的 0
        for (int i = x - 1; i <= x + 1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                cnt0 += dfs(land, i, j);
        return cnt0;
    }


}
