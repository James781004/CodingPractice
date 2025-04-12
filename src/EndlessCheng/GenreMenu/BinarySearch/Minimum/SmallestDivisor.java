package EndlessCheng.GenreMenu.BinarySearch.Minimum;

public class SmallestDivisor {

    // https://leetcode.cn/problems/find-the-smallest-divisor-given-a-threshold/solutions/2989469/mo-ban-er-fen-da-an-qiu-zui-xiao-pythonj-ukwe/
    public int smallestDivisor(int[] nums, int threshold) {
        int left = 0;
        int right = 0;
        for (int x : nums) {
            right = Math.max(right, x);
        }
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(nums, mid, threshold)) { // mid 越大，sum 越小
                right = mid; // right 左移嘗試讓 mid 更小，sum 更大
            } else {
                left = mid; // left 右移嘗試讓 mid 更大，sum 更小
            }
        }
        return right;
    }

    private boolean check(int[] nums, int m, int threshold) {
        int sum = 0;
        for (int x : nums) {
            sum += (x + m - 1) / m; // 每個數除以 m 再上取整
            if (sum > threshold) { // sum 過大，提前退出循環
                return false;
            }
        }
        return true;
    }


}
