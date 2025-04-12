package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Shortest;

public class FindLengthOfShortestSubarray {

    // https://leetcode.cn/problems/shortest-subarray-to-be-removed-to-make-array-sorted/solutions/2189149/dong-hua-yi-xie-jiu-cuo-liang-chong-xie-iijwz/
    // 寫法一：枚舉左端點，移動右端點
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


    // 寫法二：枚舉右端點，移動左端點
    public int findLengthOfShortestSubarray2(int[] arr) {
        int n = arr.length, right = n - 1;
        while (right > 0 && arr[right - 1] <= arr[right])
            --right;
        if (right == 0) return 0; // arr 已經是非遞減數組
        // 此時 arr[right-1] > arr[right]
        int ans = right; // 刪除 0 到 right-1
        for (int left = 0; ; ++right) // 枚舉 right
            while (right == n || arr[left] <= arr[right]) {
                // 中間 left+1 到 right-1 可以刪除
                ans = Math.min(ans, right - left - 1);
                if (arr[left] > arr[left + 1]) return ans;
                ++left;
            }
    }


}
