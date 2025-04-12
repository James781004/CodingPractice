package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.Arrays;

public class MaximumWhiteTiles {

    // https://leetcode.cn/problems/maximum-white-tiles-covered-by-a-carpet/solutions/1496434/by-endlesscheng-kdy9/
    public int maximumWhiteTiles(int[][] tiles, int carpetLen) {
        Arrays.sort(tiles, (a, b) -> a[0] - b[0]); // 按左端點排序
        int ans = 0, cover = 0, left = 0;
        for (var t : tiles) { // 枚舉毯子的擺放位置
            int tl = t[0], tr = t[1];
            cover += tr - tl + 1; // 記錄被覆蓋的瓷磚塊中一共有幾塊瓷磚
            for (; tiles[left][1] + carpetLen - 1 < tr; ++left)
                cover -= tiles[left][1] - tiles[left][0] + 1;
            ans = Math.max(ans, cover - Math.max(tr - carpetLen + 1 - tiles[left][0], 0)); // 0 表示毯子左端點不在瓷磚內的情況
        }
        return ans;
    }


}
