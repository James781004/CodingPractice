package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

import java.util.ArrayList;
import java.util.List;

public class IntervalIntersection {

    // https://leetcode.cn/problems/interval-list-intersections/solutions/1061065/java-shuang-zhi-zhen-by-feilue-b0qt/
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> res = new ArrayList();
        int p1 = 0, p2 = 0;
        int n1 = firstList.length, n2 = secondList.length;

        while (p1 < n1 && p2 < n2) {
            int[] arr1 = firstList[p1];
            int[] arr2 = secondList[p2];

            // 可能重合區域
            int L = Math.max(arr1[0], arr2[0]);
            int R = Math.min(arr1[1], arr2[1]);

            // 若不相交，則 right1<left2 或 right2<left1，
            // 那麼求得的重合區域 max(left1,left2) 的值會比 min(right1,right2) 大，
            // 可以通過比較兩個值來判斷是否重合
            if (L <= R) res.add(new int[]{L, R});

            // 每次優先移動當前區間尾段較小的指針
            if (arr1[1] < arr2[1]) p1++;
            else p2++;
        }

        return res.toArray(new int[res.size()][]);
    }


}
