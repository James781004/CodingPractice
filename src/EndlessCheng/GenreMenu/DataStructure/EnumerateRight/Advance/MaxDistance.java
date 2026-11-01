package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class MaxDistance {

    // https://leetcode.cn/problems/two-furthest-houses-with-different-colors/solutions/1113521/on-zuo-fa-by-endlesscheng-an8b/
    public int maxDistance(int[] colors) {
        int n = colors.length;
        int c = colors[0];
        if (c != colors[n - 1]) {
            return n - 1;
        }

        // 找最右邊的顏色不等於 c 的房子
        // 題目保證至少有兩棟顏色不同的房子
        int r = n - 2;
        while (colors[r] == c) {
            r--;
        }

        // 找最左邊的顏色不等於 c 的房子
        int l = 1;
        while (colors[l] == c) {
            l++;
        }

        return Math.max(r, n - 1 - l);
    }

}
