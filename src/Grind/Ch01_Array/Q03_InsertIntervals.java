package Grind.Ch01_Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q03_InsertIntervals {
    // https://leetcode.com/problems/insert-interval/
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // 另外建array，避免改動原本輸入資料
        int[][] merged = new int[intervals.length + 1][];
        int c = 0;
        for (int[] i : intervals) {
            merged[c++] = i;
        }
        merged[intervals.length] = newInterval;
        Arrays.sort(merged, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> l1 = new ArrayList<>();
        for (int[] arr : merged) {
            if (l1.size() == 0) {
                l1.add(arr);
            } else {
                int[] prevArr = l1.get(l1.size() - 1);
                if (arr[0] <= prevArr[1]) {
                    prevArr[1] = Math.max(prevArr[1], arr[1]);  // 這樣就可以更新尾部，不用重新插入
                } else {
                    l1.add(arr);
                }
            }
        }
        return l1.toArray(new int[l1.size()][]);
    }
}
