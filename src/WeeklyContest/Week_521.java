package WeeklyContest;

import java.util.*;

public class Week_521 {

    // https://leetcode.cn/problems/rearrange-array-by-removing-distinct-values/solutions/4035358/liang-chong-fang-fa-bao-li-mo-ni-an-chu-1gogb/
    public int[] rearrangeArray(int[] nums) {
        int n = nums.length;
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int[] cnt = new int[mx + 1];
        List<Integer>[] levels = new ArrayList[n];
        Arrays.setAll(levels, v -> new ArrayList<>());

        for (int x : nums) {
            int c = cnt[x];
            levels[c].add(x);
            cnt[x]++;
        }

        int[] ans = new int[n];
        int k = 0;
        // 從下到上遍歷每一層的積木
        for (List<Integer> level : levels) {
            level.sort(null);
            for (int x : level) {
                ans[k++] = x;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-equal-adjacent-pairs-after-at-most-one-replacement/solutions/4035315/tong-ji-xiang-lin-bu-deng-de-shu-dui-ge-ukk7j/
    public int maxEqualAdjacentPairs(int[] nums) {
        int base = 0;
        Map<Long, Integer> cnt = new HashMap<>();

        for (int i = 1; i < nums.length; i++) {
            int x = nums[i - 1];
            int y = nums[i];
            if (x == y) {
                base++;
            } else {
                // 把 (x,y) 和 (y,x) 都統一為 (x,y)
                if (x > y) {
                    int tmp = x; // 交換 x 和 y
                    x = y;
                    y = tmp;
                }
                // 統計相鄰且不相等的數對個數
                long key = (long) x << 32 | y;
                cnt.merge(key, 1, Integer::sum); // cnt[key]++
            }
        }

        int maxCnt = cnt.isEmpty() ? 0 : Collections.max(cnt.values());
        return base + maxCnt;
    }


    // https://leetcode.cn/problems/longest-subarray-with-restricted-pair-sums/solutions/4035333/hua-dong-chuang-kou-wei-hu-liang-shu-zhi-qdhn/
    public int maxSubarray(int[] nums) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int[] cntS = new int[mx * 2 + 1];
        int[] cntD = new int[mx + 1];
        int left = 0;
        int ans = 0;

        // 枚舉有效子數組的右端點為 i，那麼左端點 left 最小是多少？
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];

            // x 進入窗口前，先判斷：
            // 如果窗口中有兩數之和等於 x，或者兩數之差等於 x，那麼必須縮小窗口
            while (cntS[x] > 0 || cntD[x] > 0) {
                int y = nums[left];
                left++;
                for (int j = left; j < i; j++) {
                    int z = nums[j];
                    cntS[y + z]--;
                    cntD[Math.abs(y - z)]--;
                }
            }

            // 用子數組 [left, i] 的長度更新答案的最大值
            ans = Math.max(ans, i - left + 1);

            // 元素 x 進入窗口
            for (int j = left; j < i; j++) {
                int y = nums[j];
                cntS[x + y]++;
                cntD[Math.abs(x - y)]++;
            }
        }

        return ans;
    }


    // https://leetcode.cn/problems/maximize-meeting-earnings-with-idle-gaps/solutions/4035319/dong-tai-gui-hua-shi-zi-bian-xing-python-ycfm/
    public long maxEarnings(int[][] meetings) {
        // 按結束時間從小到大排序
        Arrays.sort(meetings, (a, b) -> a[1] - b[1]);
        int end0 = meetings[0][1];

        // preMax[i+1] = [0,i] 中的 f[j] - end[j] 的前綴最大值
        int n = meetings.length;
        long[] preMax = new long[n + 1];
        preMax[0] = Long.MIN_VALUE;
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int[] m = meetings[i];
            int start = m[0], end = m[1], revenue = m[2];

            long f = revenue;
            if (start >= end0) { // 左邊有會議
                int j = search(meetings, i, start);
                f += preMax[j + 1] + start;
            }
            ans = Math.max(ans, f);

            preMax[i + 1] = Math.max(preMax[i], f - end);
        }

        return ans;
    }

    // 返回滿足 end[j] <= upper 的最大 j
    private int search(int[][] meetings, int right, int upper) {
        int left = -1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (meetings[mid][1] <= upper) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

}










