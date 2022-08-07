package TeacherZuoCodingInterviewGuide.ch09;

import java.util.HashSet;

public class Q07_PerfectRectangle {
//    CD61 能否完美地拼成矩形
//    描述
//    每條邊不是平行於X軸就是平行於Y軸的矩形，可以用左下角和右上角的點來表示。比如{1, 2, 3, 4}，表示的圖形如下
//
//    給定一個N行4列的二維數組matrix，表示N個每條邊不是平行於X軸就是平行於Y軸的矩形。
//    想知道所有的矩形能否組成一個大的完美矩形。
//    完美矩形是指拼出的整體圖案是矩形，既不缺任何塊兒，也沒有重合部分
//[要求]
//    時間覆雜度為O(n)，額外空間覆雜度為O(n)

    public boolean isRectangleCover(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int mostLeft = Integer.MAX_VALUE;
        int mostRight = Integer.MIN_VALUE;
        int mostDown = Integer.MAX_VALUE;
        int mostUp = Integer.MIN_VALUE;
        HashSet<String> set = new HashSet<String>();
        int area = 0;
        for (int[] rect : matrix) {
            mostLeft = Math.min(rect[0], mostLeft);  // 比較左下角x，找出目前最左
            mostDown = Math.min(rect[1], mostDown);  // 比較左下角y，找出目前最下
            mostRight = Math.max(rect[2], mostRight);  // 比較右下角x，找出目前最右
            mostUp = Math.max(rect[3], mostUp);  // 比較右下角y，找出目前最上
            area += (rect[2] - rect[0]) * (rect[3] - rect[1]);  // 累積面積加上當前面積

            // 把當前的四點座標加進set中
            // 根據題目定義，每一點只可能出現兩次，除了最外側點之外（最外側四點只會出現一次）
            String leftDown = rect[0] + "_" + rect[1];
            String leftUp = rect[0] + "_" + rect[3];
            String rightDown = rect[2] + "_" + rect[1];
            String rightUp = rect[2] + "_" + rect[3];
            if (!set.add(leftDown)) set.remove(leftDown);
            if (!set.add(leftUp)) set.remove(leftUp);
            if (!set.add(rightUp)) set.remove(rightUp);
            if (!set.add(rightDown)) set.remove(rightDown);
        }

        // 如果最後set裡面不是僅僅包含最終的大矩形的4個頂點，那麼一定不能完美拼成矩形，返回false
        if (!set.contains(mostLeft + "_" + mostDown)
                || !set.contains(mostLeft + "_" + mostUp)
                || !set.contains(mostRight + "_" + mostDown)
                || !set.contains(mostRight + "_" + mostUp)
                || set.size() != 4) {
            return false;
        }

        // 這個地方一定不能直接返回true，
        // 除了滿足上面的條件，
        // 所有的小矩形的面積加起來等於最終的大矩形的面積時，才可以完美拼成矩形
        return area == (mostRight - mostLeft) * (mostUp - mostDown);
    }
}
