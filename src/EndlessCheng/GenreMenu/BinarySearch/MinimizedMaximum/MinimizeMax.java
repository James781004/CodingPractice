package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

import java.util.Arrays;

public class MinimizeMax {

    // https://leetcode.cn/problems/minimize-the-maximum-difference-of-pairs/solutions/2216315/er-fen-da-an-tan-xin-by-endlesscheng-dlxv/
    public int minimizeMax(int[] nums, int p) {
        Arrays.sort(nums);
        int n = nums.length, left = -1, right = nums[n - 1] - nums[0]; // 開區間
        while (left + 1 < right) { // 開區間
            int mid = (left + right) >>> 1, cnt = 0;
            for (int i = 0; i < n - 1; i++)
                if (nums[i + 1] - nums[i] <= mid) { // 都選
                    ++cnt;
                    ++i;
                }
            if (cnt >= p) right = mid;
            else left = mid;
        }
        return right;
    }


}
