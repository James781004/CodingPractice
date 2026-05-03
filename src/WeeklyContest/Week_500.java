package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_500 {

    // https://leetcode.cn/problems/count-indices-with-opposite-parity/solutions/3962727/on-zuo-fa-mei-ju-zuo-wei-hu-you-pythonja-oeia/
    public int[] countOppositeParity(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int[] cnt = new int[2];
        for (int i = n - 1; i >= 0; i--) {
            int x = nums[i] & 1; // x 的奇偶性
            ans[i] = cnt[x ^ 1]; // 查詢右側奇偶性不等於 x（即 x^1）的元素個數
            cnt[x]++;
        }
        return ans;
    }


    // https://leetcode.cn/problems/sum-of-primes-between-number-and-its-reverse/solutions/3962736/yu-chu-li-qian-zhui-he-olog-n-shi-jian-p-6tlu/
    static class Solution {
        private static final int MX = 1001;
        private static final int[] isPrime = new int[MX];
        private static boolean initialized = false;

        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            Arrays.fill(isPrime, 2, MX, 1);

            // 用篩法（例如埃氏篩）預處理每個數是不是質數，然後把非質數視作 0
            for (int i = 2; i * i < MX; i++) {
                if (isPrime[i] > 0) {
                    for (int j = i * i; j < MX; j += i) {
                        isPrime[j] = 0;
                    }
                }
            }

            // 原地計算 isPrime 的質數前綴和
            for (int i = 1; i < MX; i++) {
                isPrime[i] = isPrime[i - 1] + (isPrime[i] > 0 ? i : 0);
            }
        }

        public int sumOfPrimesInRange(int n) {
            int r = 0;
            for (int x = n; x > 0; x /= 10) {
                r = r * 10 + x % 10;
            }
            return isPrime[Math.max(n, r)] - isPrime[Math.min(n, r) - 1];
        }
    }


    // https://leetcode.cn/problems/minimum-cost-to-move-between-indices/solutions/3962725/tan-xin-qian-zhui-he-pythonjavacgo-by-en-0fe7/
    public int[] minCost(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] sumL = new int[n]; // sumL[i] 等於從 i 移動到 0 的代價和
        int[] sumR = new int[n]; // sumR[i] 等於從 0 移動到 i 的代價和
        for (int i = 1, cost; i < n; i++) {
            // 往左走 i -> i-1
            if (i < n - 1 && nums[i] - nums[i - 1] > nums[i + 1] - nums[i]) { // closest(i) = i+1
                cost = nums[i] - nums[i - 1]; // 只能用方式一往左走
            } else {
                cost = 1;
            }
            sumL[i] = sumL[i - 1] + cost;

            // 往右走 i-1 -> i
            if (i > 1 && nums[i - 1] - nums[i - 2] <= nums[i] - nums[i - 1]) { // closest(i-1) = i-2
                cost = nums[i] - nums[i - 1]; // 只能用方式一往右走
            } else {
                cost = 1;
            }
            sumR[i] = sumR[i - 1] + cost;
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            if (l < r) {
                // cost(0 -> r) - cost(0 -> l) = cost(l -> r)
                ans[i] = sumR[r] - sumR[l];
            } else {
                // cost(l -> 0) - cost(r -> 0) = cost(l -> r)
                ans[i] = sumL[l] - sumL[r];
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximize-fixed-points-after-deletions/solutions/3962724/zui-chang-di-zeng-zi-xu-lie-pythonjavacg-0uln/
    public int maxFixedPoints(int[] nums) {
        List<int[]> a = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (i >= x) {
                a.add(new int[]{x, i - x});
            }
        }
        return maxEnvelopes(a);
    }

    // 354. 俄羅斯套娃信封問題
    private int maxEnvelopes(List<int[]> envelopes) {
        envelopes.sort((a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

        List<Integer> g = new ArrayList<>();
        for (int[] e : envelopes) {
            int h = e[1];
            int j = upperBound(g, h); // 允許 LIS 相鄰元素相等
            if (j < g.size()) {
                g.set(j, h);
            } else {
                g.add(h);
            }
        }
        return g.size();
    }

    private int upperBound(List<Integer> g, int target) {
        int left = -1, right = g.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[right] > target
            // nums[left] <= target
            int mid = left + (right - left) / 2;
            if (g.get(mid) > target) {
                right = mid; // 范圍縮小到 (left, mid)
            } else {
                left = mid; // 范圍縮小到 (mid, right)
            }
        }
        return right;
    }


}










