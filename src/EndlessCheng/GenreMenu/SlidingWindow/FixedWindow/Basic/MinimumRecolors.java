package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

public class MinimumRecolors {

    // https://leetcode.cn/problems/minimum-recolors-to-get-k-consecutive-black-blocks/solutions/1763639/on-hua-dong-chuang-kou-by-endlesscheng-s4fx/
    public int minimumRecolors(String blocks, int k) {
        int n = blocks.length();
        char[] chars = blocks.toCharArray();
        int res = n;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (chars[i] == 'W') {
                sum++;
            }
            if (i < k - 1) {
                continue;
            }
            res = Math.min(res, sum);
            if (chars[i - k + 1] == 'W') {
                sum--;
            }
        }
        return res;
    }


}
