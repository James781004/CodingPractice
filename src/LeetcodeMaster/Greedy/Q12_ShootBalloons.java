package LeetcodeMaster.Greedy;

import java.util.Arrays;

public class Q12_ShootBalloons {
//    452. 用最少數量的箭引爆氣球
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0452.%E7%94%A8%E6%9C%80%E5%B0%91%E6%95%B0%E9%87%8F%E7%9A%84%E7%AE%AD%E5%BC%95%E7%88%86%E6%B0%94%E7%90%83.md
//
//    在二維空間中有許多球形的氣球。對於每個氣球，提供的輸入是水平方向上，氣球直徑的開始和結束坐標。
//    由於它是水平的，所以縱坐標並不重要，因此只要知道開始和結束的橫坐標就足夠了。開始坐標總是小於結束坐標。
//
//    一支弓箭可以沿著 x 軸從不同點完全垂直地射出。在坐標 x 處射出一支箭，
//    若有一個氣球的直徑的開始和結束坐標為 xstart，xend， 且滿足  xstart ≤ x ≤ xend，則該氣球會被引爆。
//    可以射出的弓箭的數量沒有限制。
//    弓箭一旦被射出之後，可以無限地前進。我們想找到使得所有氣球全部被引爆，所需的弓箭的最小數量。
//
//    給你一個數組 points ，其中 points [i] = [xstart,xend] ，返回引爆所有氣球所必須射出的最小弓箭數。
//
//    示例 1：
//
//    輸入：points = [[10,16],[2,8],[1,6],[7,12]]
//    輸出：2
//    解釋：對於該樣例，x = 6 可以射爆 [2,8],[1,6] 兩個氣球，以及 x = 11 射爆另外兩個氣球
//    示例 2：
//
//    輸入：points = [[1,2],[3,4],[5,6],[7,8]]
//    輸出：4
//    示例 3：
//
//    輸入：points = [[1,2],[2,3],[3,4],[4,5]]
//    輸出：2
//    示例 4：
//
//    輸入：points = [[1,2]]
//    輸出：1
//    示例 5：
//
//    輸入：points = [[2,3],[2,3]]
//    輸出：1
//    提示：
//
//            0 <= points.length <= 10^4
//    points[i].length == 2
//            -2^31 <= xstart < xend <= 2^31 - 1

    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) return 0;
        // 用x[0] - y[0] 會大於2147483647 造成整型溢出
        Arrays.sort(points, (x, y) -> Integer.compare(x[0], y[0]));
        // count = 1 因為最少需要一個箭來射擊第一個氣球
        int count = 1;
        // 重疊氣球的最小右邊界
        int leftmostRightBound = points[0][1];
        for (int i = 1; i < points.length; i++) {
            // 如果下一個氣球的左邊界大於最小右邊界
            if (points[i][0] > leftmostRightBound) {
                // 增加一次射擊
                count++;
                leftmostRightBound = points[i][1];
                // 不然就更新最小右邊界
            } else {
                leftmostRightBound = Math.min(leftmostRightBound, points[i][1]);
            }
        }

        return count;
    }
}
