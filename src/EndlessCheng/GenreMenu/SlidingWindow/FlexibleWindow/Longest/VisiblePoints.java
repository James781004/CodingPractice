package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisiblePoints {

    // https://leetcode.cn/problems/maximum-number-of-visible-points/solutions/1160434/tong-ge-lai-shua-ti-la-tu-jie-pai-xu-hua-wajo/
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        // 算出每一個坐標相對於location位置與x軸的夾角（弧度制），扔到List中排序
        // 然後對於每一個點，使用二分或滑動窗口找出小於這個點+angle（轉成弧度制）的最大坐標點
        // 兩者之間的下標差就是從這個點出發，輻射angle角度的點數量
        // 注意，與location點相同的點的特殊處理

        int same = 0;
        List<Double> list = new ArrayList<>();
        int x = location.get(0), y = location.get(1);
        for (List<Integer> point : points) {
            int a = point.get(0), b = point.get(1);
            if (a == x && b == y) {
                // 與location同點
                same++;
            } else {
                // 計算角度（弧度制）
                list.add(Math.atan2(b - y, a - x));
            }
        }

        // 排序
        Collections.sort(list);

        // 把前面所有的數添加一遍到後面，類似於於循環數組的使用
        int size = list.size();
        for (int i = 0; i < size; i++) {
            // 加 360度，然後范圍相當於變成了 [-pi, 3*pi]
            list.add(list.get(i) + 2 * Math.PI);
        }

        double angleDegree = angle * Math.PI / 180;
        int max = 0;
        int i = 0, j = 0;
        while (i < size) {
            // 滑動窗口，簡單點，list是有序的，使用二分查找也是可以的
            while (j < 2 * size && list.get(j) - list.get(i) <= angleDegree) {
                j++;
            }
            max = Math.max(max, j - i);
            i++;
        }

        return max + same;
    }


}
