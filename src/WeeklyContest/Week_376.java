package WeeklyContest;

import java.util.Arrays;

public class Week_376 {
    // https://leetcode.cn/problems/find-missing-and-repeated-values/solutions/2569378/2965-zhao-chu-que-shi-he-zhong-fu-de-shu-emid/
    public int[] findMissingAndRepeatedValues(int[][] grid) {
        int n = grid.length;
        int maxNum = n * n;
        int[] counts = new int[maxNum + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                counts[grid[i][j]]++;
            }
        }
        int[] ans = new int[2];
        for (int i = 1; i <= maxNum; i++) {
            if (counts[i] > 1) {
                ans[0] = i;
            } else if (counts[i] == 0) {
                ans[1] = i;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/divide-array-into-arrays-with-max-difference/solutions/2569341/pai-xu-hou-qie-fen-pythonjavacgo-by-endl-lewc/
    // 既然元素的順序並不重要，那麼把數組排序後，從小到大切分即可。
    public int[][] divideArray(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int[][] ans = new int[n / 3][3];
        for (int i = 2; i < n; i += 3) {
            if (nums[i] - nums[i - 2] > k) {
                return new int[][]{};
            }
            ans[i / 3] = new int[]{nums[i - 2], nums[i - 1], nums[i]};
        }
        return ans;
    }

    // https://leetcode.cn/problems/minimum-cost-to-make-array-equalindromic/solutions/2569308/yu-chu-li-hui-wen-shu-zhong-wei-shu-tan-7j0zy/
    private static final int[] pal = new int[109999];

    static {
        // 嚴格按順序從小到大生成所有回文數（不用字符串轉換）
        int palIdx = 0;
        for (int base = 1; base <= 10000; base *= 10) {
            // 生成奇數長度回文數
            for (int i = base; i < base * 10; i++) {
                int x = i;
                for (int t = i / 10; t > 0; t /= 10) {
                    x = x * 10 + t % 10;
                }
                pal[palIdx++] = x;
            }
            // 生成偶數長度回文數
            if (base <= 1000) {
                for (int i = base; i < base * 10; i++) {
                    int x = i;
                    for (int t = i; t > 0; t /= 10) {
                        x = x * 10 + t % 10;
                    }
                    pal[palIdx++] = x;
                }
            }
        }
        pal[palIdx++] = 1_000_000_001; // 哨兵，防止下面代碼中的 i 下標越界
    }

    public long minimumCost(int[] nums) {
        // 注：排序只是為了找中位數，如果用快速選擇算法，可以做到 O(n)
        Arrays.sort(nums);
        int n = nums.length;

        // 二分找中位數右側最近的回文數
        int i = lowerBound(nums[(n - 1) / 2]);

        // 回文數在中位數范圍內
        if (pal[i] <= nums[n / 2]) {
            return cost(nums, i); // 直接變成 pal[i]
        }

        // 枚舉離中位數最近的兩個回文數 pal[i-1] 和 pal[i]
        return Math.min(cost(nums, i - 1), cost(nums, i));
    }

    // 返回 nums 中的所有數變成 pal[i] 的總代價
    private long cost(int[] nums, int i) {
        int target = pal[i];
        long sum = 0;
        for (int x : nums) {
            sum += Math.abs(x - target);
        }
        return sum;
    }

    // 開區間寫法
    private int lowerBound(int target) {
        int left = -1, right = pal.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // pal[left] < target
            // pal[right] >= target
            int mid = left + (right - left) / 2;
            if (pal[mid] < target)
                left = mid; // 范圍縮小到 (mid, right)
            else
                right = mid; // 范圍縮小到 (left, mid)
        }
        return right; // 或者 left+1
    }
    

    // https://leetcode.cn/problems/apply-operations-to-maximize-frequency-score/solutions/2569301/hua-dong-chuang-kou-zhong-wei-shu-tan-xi-nuvr/
    public int maxFrequencyScore(int[] nums, long k) {
        Arrays.sort(nums);

        int n = nums.length;
        long[] s = new long[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
        }

        int ans = 0, left = 0;
        for (int i = 0; i < n; i++) {
            while (distanceSum(s, nums, left, (left + i) / 2, i) > k) {
                left++;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }

    // 把 nums[l] 到 nums[r] 都變成 nums[i]
    long distanceSum(long[] s, int[] nums, int l, int i, int r) {
        long left = (long) nums[i] * (i - l) - (s[i] - s[l]);
        long right = s[r + 1] - s[i + 1] - (long) nums[i] * (r - i);
        return left + right;
    }
}
