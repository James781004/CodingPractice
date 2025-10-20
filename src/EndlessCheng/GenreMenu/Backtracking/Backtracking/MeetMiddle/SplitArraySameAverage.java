package EndlessCheng.GenreMenu.Backtracking.Backtracking.MeetMiddle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SplitArraySameAverage {

    // https://leetcode.cn/problems/split-array-with-same-average/solutions/1970783/by-tizzi-73ov/
    int sum, n, m;
    Map<Integer, Boolean> mp = new HashMap<>();

    public boolean splitArraySameAverage(int[] nums) {
        sum = Arrays.stream(nums).sum();
        n = nums.length;
        m = n / 2;
        if (n == 1) return false;
        for (int i = 0; i < n; i++) nums[i] = nums[i] * n - sum;
        //從前部分數組中求出所有子集和
        for (int i = 1; i < (1 << m); i++) {
            int st = 0, cnt = 0;
            for (int j = 0; j < m; j++) if (((1 << j) & i) != 0) st += nums[j];
            mp.put(st, true);
            if (st == 0) return true;
        }
        //從後部分求解
        sum = (1 << (n - m)) - 1; //判斷不能全選
        for (int i = 1; i < (1 << (n - m)); i++) {
            int st = 0, cnt = 0;
            for (int j = 0; j < (n - m); j++) if (((1 << j) & i) != 0) st += nums[j + m];
            if (st == 0 || mp.containsKey(-st) && i != sum) return true;
        }
        return false;
    }


}
