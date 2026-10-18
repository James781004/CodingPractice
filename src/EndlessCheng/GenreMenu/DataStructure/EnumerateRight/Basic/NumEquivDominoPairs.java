package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

public class NumEquivDominoPairs {

    // https://leetcode.cn/problems/number-of-equivalent-domino-pairs/solutions/3661647/mei-ju-you-wei-hu-zuo-pythonjavaccgojsru-me3c/
    public int numEquivDominoPairs(int[][] dominoes) {
        int ans = 0;
        int[][] cnt = new int[10][10];
        for (int[] d : dominoes) {
            int a = Math.min(d[0], d[1]);
            int b = Math.max(d[0], d[1]); // 保證 a <= b
            ans += cnt[a][b]++;
        }
        return ans;
    }

}
