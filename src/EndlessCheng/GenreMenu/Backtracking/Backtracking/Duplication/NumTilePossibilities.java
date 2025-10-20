package EndlessCheng.GenreMenu.Backtracking.Backtracking.Duplication;

import java.util.Arrays;

public class NumTilePossibilities {

    // https://leetcode.cn/problems/letter-tile-possibilities/solutions/3733298/huo-zi-yin-shua-qiu-fei-kong-zi-ji-de-bu-s7ar/
    int res = 0;
    int[] used;

    public int numTilePossibilities(String tiles) {
        used = new int[tiles.length()];
        char[] c = tiles.toCharArray();
        Arrays.sort(c);// 使用used去重一定記得先排序
        dfs(c);
        return res;
    }

    // 求排列數，在每個結點都可以收集結果
    void dfs(char[] c) {
        for (int i = 0; i < c.length; i++) {
            // 樹層去重，前一位沒被選上且後一位與前一位相同
            if (i > 0 && used[i - 1] == 0 && c[i] == c[i - 1]) continue;
            // 自己不能被選兩次
            if (used[i] == 1) continue;
            res++;
            used[i] = 1;
            dfs(c);
            used[i] = 0;
        }
    }


}
