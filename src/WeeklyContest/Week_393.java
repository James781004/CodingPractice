package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_393 {
    // https://leetcode.cn/problems/latest-time-you-can-obtain-after-replacing-characters/solutions/2739292/fen-qing-kuang-tao-lun-javacgopython3-by-zkal/
    public String findLatestTime(String s) {
        StringBuilder ans = new StringBuilder();
        if (s.charAt(0) == '?') {
            if (s.charAt(1) == '?') {
                ans.append("11");
            } else if (s.charAt(1) >= '2') {
                ans.append("0").append(s.charAt(1));
            } else {
                ans.append("1").append(s.charAt(1));
            }
        } else {
            ans.append(s.charAt(0));
            if (s.charAt(1) == '?') {
                if (s.charAt(0) == '1') {
                    ans.append("1");
                } else {
                    ans.append("9");
                }
            } else {
                ans.append(s.charAt(1));
            }
        }
        ans.append(":");
        ans = s.charAt(3) == '?' ? ans.append("5") : ans.append(s.charAt(3));
        ans = s.charAt(4) == '?' ? ans.append("9") : ans.append(s.charAt(4));
        return ans.toString();
    }


    // https://leetcode.cn/problems/maximum-prime-difference/solutions/2739327/pan-duan-zhi-shu-pythonjavacgo-by-endles-24gc/
    public int maximumPrimeDifference(int[] nums) {
        int i = 0;
        while (!isPrime(nums[i])) {
            i++;
        }
        int j = nums.length - 1;
        while (!isPrime(nums[j])) {
            j--;
        }
        return j - i;
    }

    private boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return n >= 2;
    }


    // https://leetcode.cn/problems/kth-smallest-amount-with-single-denomination-combination/solutions/2739205/er-fen-da-an-rong-chi-yuan-li-pythonjava-v24i/
    public long findKthSmallest(int[] coins, int k) {
        int mx = Integer.MAX_VALUE;
        for (int x : coins) {
            mx = Math.min(mx, x);
        }
        long left = k - 1, right = (long) mx * k;
        while (left + 1 < right) {
            long mid = (left + right) / 2;
            if (check(mid, coins, k)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(long m, int[] coins, int k) {
        long cnt = 0;
        next:
        for (int i = 1; i < (1 << coins.length); i++) { // 枚舉所有非空子集
            long lcmRes = 1; // 計算子集 LCM
            for (int j = 0; j < coins.length; j++) {
                if ((i >> j & 1) == 1) {
                    lcmRes = lcm(lcmRes, coins[j]);
                    if (lcmRes > m) { // 太大了
                        continue next;
                    }
                }
            }
            cnt += Integer.bitCount(i) % 2 == 1 ? m / lcmRes : -m / lcmRes;
        }
        return cnt >= k;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/solutions/2739258/ji-yi-hua-sou-suo-jian-ji-xie-fa-by-endl-728z/
    public int minimumValueSum(int[] nums, int[] andValues) {
        Map<Long, Integer> memo = new HashMap<>();
        int ans = dfs(0, 0, -1, nums, andValues, memo);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int j, int and, int[] nums, int[] andValues, Map<Long, Integer> memo) {
        int n = nums.length;
        int m = andValues.length;
        if (m - j > n - i) { // 剩余元素不足
            return Integer.MAX_VALUE / 2;
        }
        if (j == m) { // 分了 m 段
            return i == n ? 0 : Integer.MAX_VALUE / 2;
        }
        and &= nums[i];
        if (and < andValues[j]) { // 剪枝：無法等於 andValues[j]
            return Integer.MAX_VALUE / 2;
        }
        long mask = (long) i << 36 | (long) j << 32 | and; // 三個狀態壓縮成一個 long
        if (memo.containsKey(mask)) {
            return memo.get(mask);
        }
        int res = dfs(i + 1, j, and, nums, andValues, memo); // 不劃分
        if (and == andValues[j]) { // 劃分，nums[i] 是這一段的最後一個數
            res = Math.min(res, dfs(i + 1, j + 1, -1, nums, andValues, memo) + nums[i]);
        }
        memo.put(mask, res);
        return res;
    }
}


