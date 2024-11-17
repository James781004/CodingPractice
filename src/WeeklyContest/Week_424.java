package WeeklyContest;

public class Week_424 {

    // https://leetcode.cn/problems/make-array-elements-equal-to-zero/solutions/2991257/javabao-li-jie-fa-by-hong-shan-qiu-tel-asjb/
    public int countValidSelections(int[] nums) {
        int res = 0;
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < nums.length; i++) {
            sum1 = 0;
            sum2 = 0;
            if (nums[i] == 0) {
                for (int j = 0; j < i; j++) {
                    sum1 += nums[j];
                }
                for (int j = i + 1; j < nums.length; j++) {
                    sum2 += nums[j];
                }
                if (sum1 == sum2) {
                    res += 2;
                }
                if (sum1 + 1 == sum2 || sum2 + 1 == sum1) {
                    res += 1;
                }
            }
        }
        return res;
    }


    // https://leetcode.cn/problems/zero-array-transformation-i/solutions/2991216/chai-fen-shu-zu-qiu-jie-by-chu-mou-mou-rq0l/
    public boolean isZeroArray(int[] nums, int[][] queries) {
        int nLen = nums.length;
        int[] diff = new int[nLen + 1];

        // 差分維護每個元素被幾個區間覆蓋
        for (int[] qus : queries) {
            diff[qus[0]]++;
            diff[qus[1] + 1]--;
        }
        int sum = 0;

        // 枚舉每個元素，求區間覆蓋數
        for (int i = 0; i < nLen; i++) {
            sum += diff[i];
            if (sum < nums[i]) {
                return false;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/zero-array-transformation-ii/solutions/2991160/er-fen-chai-fen-shu-zu-da-an-you-dan-dia-2x0z/
    public int minZeroArray(int[] nums, int[][] queries) {
        // 還有初始就可以的情況。即nums數組里都是0，
        // 因為 1 <= nums.length <= 1e5，所以可以也可以轉化成總和為 0 來判斷。
        long sum = 0L;
        for (int i : nums) sum += i;
        if (sum == 0L) return 0;

        // 二分
        int l = 0;
        int r = queries.length - 1;
        while (l + 1 < r) {
            int m = (l + r) >>> 1;
            if (check(m, nums, queries)) r = m; // 合法，那能縮更小，則取左半邊
            else l = m; // 非法，那要更大的，則取右半邊
        }

        // 最後還要判斷二分出來的結果 是不是可行，可行則k為下標r再加一；不可行則為-1
        return check(r, nums, queries) ? r + 1 : -1;
    }

    // 二分check函數，用了 逆向思維，正向加的 差分數組。
    public boolean check(int m, int[] nums, int[][] queries) {
        int n = nums.length;
        // 差分，把 q[0] 到 q[1] 下標對應的元素都加q[2]。
        int[] arr = new int[n + 1];
        for (int i = 0; i <= m; i++) {
            int[] q = queries[i];
            arr[q[0]] += q[2];
            arr[q[1] + 1] -= q[2];
        }

        // 差分的前綴和。
        int pre = arr[0];
        if (pre < nums[0]) return false;
        for (int i = 1; i < n; i++) {
            pre += arr[i];
            if (pre < nums[i]) return false;
        }
        return true;
    }

    // https://leetcode.cn/problems/minimize-the-maximum-adjacent-element-difference/solutions/2991784/on-tan-xin-pythonjavacgo-by-endlesscheng-1bxe/
    public int minDifference(int[] nums) {
        int n = nums.length;
        // 和空位相鄰的最小數字 minL 和最大數字 maxR
        int minL = Integer.MAX_VALUE;
        int maxR = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != -1 && (i > 0 && nums[i - 1] == -1 || i < n - 1 && nums[i + 1] == -1)) {
                minL = Math.min(minL, nums[i]);
                maxR = Math.max(maxR, nums[i]);
            }
        }

        int ans = 0;
        int preI = -1;
        for (int i = 0; i < n; i++) {
            if (nums[i] == -1) {
                continue;
            }
            if (preI >= 0) {
                if (i - preI == 1) {
                    ans = Math.max(ans, Math.abs(nums[i] - nums[preI]));
                } else {
                    int l = Math.min(nums[preI], nums[i]);
                    int r = Math.max(nums[preI], nums[i]);
                    int d = (Math.min(r - minL, maxR - l) + 1) / 2;
                    if (i - preI > 2) {
                        d = Math.min(d, (maxR - minL + 2) / 3); // d 不能超過上界
                    }
                    ans = Math.max(ans, d);
                }
            }
            preI = i;
        }
        return ans;
    }


}






