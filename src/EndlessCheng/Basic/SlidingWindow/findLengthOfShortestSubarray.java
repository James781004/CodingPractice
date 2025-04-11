package EndlessCheng.Basic.SlidingWindow;

public class findLengthOfShortestSubarray {

    // https://leetcode.cn/problems/shortest-subarray-to-be-removed-to-make-array-sorted/solutions/2189149/dong-hua-yi-xie-jiu-cuo-liang-chong-xie-iijwz/
    public int findLengthOfShortestSubarray(int[] arr) {
        int n = arr.length, right = n - 1;
        while (right > 0 && arr[right - 1] <= arr[right])
            --right;
        if (right == 0) return 0; // arr 已經是非遞減數組
        // 此時 arr[right-1] > arr[right]
        int ans = right; // 刪除 0 到 right-1
        for (int left = 0; left == 0 || arr[left - 1] <= arr[left]; ++left) {
            while (right < n && arr[right] < arr[left])
                ++right;
            // 此時 arr[left] <= arr[right]，從 left+1 到 right-1 可以刪除
            ans = Math.min(ans, right - left - 1);
        }
        return ans;
    }


}
