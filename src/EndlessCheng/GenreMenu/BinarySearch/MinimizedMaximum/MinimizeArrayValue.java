package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

public class MinimizeArrayValue {

    // https://leetcode.cn/problems/minimize-maximum-of-array/solutions/1895244/liang-chong-zuo-fa-er-fen-da-an-fen-lei-qhee6/
    public int minimizeArrayValue(int[] nums) {
        int left = -1;
        int right = 0;
        for (int x : nums) {
            right = Math.max(right, x);
        }

        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check(nums, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    // 猜測一個上界 limit，要求操作後所有元素均不超過 limit
    private boolean check(int[] nums, int limit) {
        long extra = 0;

        // 從後往前模擬
        // 如果 nums[i]>limit，那麼應當去掉多余的 extra=nums[i]−limit 加到 nums[i−1] 上，
        // 最後如果 nums[0]≤limit，則二分判定成功。
        for (int i = nums.length - 1; i > 0; i--) {
            extra = Math.max(nums[i] + extra - limit, 0);
        }
        return nums[0] + extra <= limit;
    }


    public int minimizeArrayValue2(int[] nums) {
        long ans = 0;
        long s = 0;
        for (int i = 0; i < nums.length; i++) {
            s += nums[i];
            ans = Math.max(ans, (s + i) / (i + 1)); // 上取整的計算
        }
        return (int) ans;
    }

}
