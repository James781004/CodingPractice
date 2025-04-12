package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.Arrays;

public class AnswerQueries {

    // https://leetcode.cn/problems/longest-subsequence-with-limited-sum/solutions/1781111/fei-bao-li-zuo-fa-qian-zhui-he-er-fen-by-ny4m/
    public int[] answerQueries(int[] nums, int[] queries) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1]; // 原地求前綴和
        }
        for (int i = 0; i < queries.length; i++) {
            queries[i] = upperBound(nums, queries[i]); // 復用 queries 作為答案
        }
        return queries;
    }

    // 返回 nums 中第一個大於 target 的數的下標（注意是大於，不是大於等於）
    // 如果這樣的數不存在，則返回 nums.length
    private int upperBound(int[] nums, int target) {
        int left = -1, right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] <= target
            // nums[right] > target
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid; // 范圍縮小到 (left, mid)
            } else {
                left = mid; // 范圍縮小到 (mid, right)
            }
        }
        return right;
    }


}
