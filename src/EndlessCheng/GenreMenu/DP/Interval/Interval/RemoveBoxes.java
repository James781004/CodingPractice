package EndlessCheng.GenreMenu.DP.Interval.Interval;

public class RemoveBoxes {

    // https://leetcode.cn/problems/remove-boxes/solutions/134870/guan-fang-fang-fa-2ji-yi-hua-sou-suo-dong-hua-tu-j/
    public int removeBoxes(int[] boxes) {
        dp = new int[boxes.length][boxes.length][boxes.length];
        return caculate(boxes, 0, boxes.length - 1, 0);
    }

    int[][][] dp;


    int caculate(int[] boxes, int l, int r, int k) {
        //遞歸邊界
        if (l > r)
            return 0;

        //記憶中搜索
        if (dp[l][r][k] != 0)
            return dp[l][r][k];

        //記憶中沒有就開始遞歸
        //兩種策略，一種是直接將後面加上現在的所有都消除
        dp[l][r][k] = caculate(boxes, l, r - 1, 0) + (k + 1) * (k + 1);
        //一種是將前面的和現在一樣的元素疊在在一起再消除，也即將中間的雜元素全部消除
        for (int i = l; i < r; i++) {
            if (boxes[i] == boxes[r]) {
                //找到一樣的元素就開始策略2
                dp[l][r][k] = Math.max(dp[l][r][k], caculate(boxes, l, i, k + 1) + caculate(boxes, i + 1, r - 1, 0));
            }
        }
        return dp[l][r][k];
    }

}
