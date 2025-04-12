package EndlessCheng.GenreMenu.BinarySearch.Kth;

import java.util.HashMap;

public class MedianOfUniquenessArray {

    // https://leetcode.cn/problems/find-the-median-of-the-uniqueness-array/solutions/2759114/er-fen-da-an-hua-dong-chuang-kou-pythonj-ykg9/
    public int medianOfUniquenessArray(int[] nums) {
        int n = nums.length;
        long k = ((long) n * (n + 1) / 2 + 1) / 2;
        int left = 0;
        int right = n;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check(nums, mid, k)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(int[] nums, int upper, long k) {
        long cnt = 0;
        int l = 0;
        HashMap<Integer, Integer> freq = new HashMap<>();
        for (int r = 0; r < nums.length; r++) {
            freq.merge(nums[r], 1, Integer::sum); // 移入右端點
            while (freq.size() > upper) { // 窗口內元素過多
                int out = nums[l++];
                if (freq.merge(out, -1, Integer::sum) == 0) { // 移出左端點
                    freq.remove(out);
                }
            }
            cnt += r - l + 1; // 右端點固定為 r 時，有 r-l+1 個合法左端點
            if (cnt >= k) {
                return true;
            }
        }
        return false;
    }


}
