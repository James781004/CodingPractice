package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.HashMap;
import java.util.Map;

public class Q10_CountSubArrays {
    // https://docs.google.com/presentation/d/11xGL50up6czpCa-0L-G07Sc-CTHiCgAj3HY5Ohu3jgU/edit#slide=id.g1a310abb1e5_0_60
    class CountSubArraysZeroes {
        int MOD = (int) 1e7;

        public int count(int[] nums) {
            int sum = 0;
            int N = nums.length;

            // dp[i][j]:
            // dp[i][0] = number of subarrays that end at position i and have equal amount of 0 and 1
            // dp[i][1] = number of subarrays that end at position i and have more 1s than 0
            int[][] dp = new int[N + 1][2];
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, 1);
            int res = 0;

            for (int i = 1; i <= N; i++) {
                int num = nums[i - 1];
                sum += (num == 1 ? 1 : -1);  // 將0轉換成-1處理
                dp[i][0] = map.getOrDefault(sum, 0);
                if (num == 1) {
                    // If nums[i] == 1 then dp[i][1] = dp[i-1][0] + dp[i-1][1] + 1
                    // 如果新數字是1，那麼目前合格的數目=我們之前是1的都可以繼承，
                    // 之前10數目正好相同的也都可以加入，再加上我們自己單獨的一個1
                    dp[i][1] = (dp[i - 1][0] + dp[i - 1][1] + 1) % MOD;
                } else {
                    // If nums[i] == 0 then dp[i][1] = dp[i-1][1] - dp[i][0]
                    // 如果新數字是0，那麼目前合格的數目=我們之前10數目正好的都舍棄，
                    // 因為0更多了，之前數目中1正好比0多的期中一部分我們可以繼承，那就是多大於1的部分，
                    // 如果之前數目正好1比0多一個，因為多了一個0就不符合條件了，我們要減去
                    dp[i][1] = (dp[i - 1][1] - dp[i][0] + MOD) % MOD;
                }

                map.put(sum, 1 + map.getOrDefault(sum, 0));
                res = (res + dp[i][1]) % MOD;
            }

            return res;
        }
    }


    // https://docs.google.com/presentation/d/11xGL50up6czpCa-0L-G07Sc-CTHiCgAj3HY5Ohu3jgU/edit#slide=id.g1a310abb1e5_0_81
    // For any subarray ending at A[j] with shorter length, they have score less than k.
    // There are j - i + 1 subarrays in total, so we update result res += j - i + 1
    public long countSubArrays(int[] nums, long k) {
        long sum = 0, res = 0;
        for (int i = 0, j = 0; i < nums.length; i++) {
            sum += nums[i];
            while (sum * (i - j + 1) >= k) sum -= nums[j++];
            res += i - j + 1;
        }
        return res;
    }


    // https://docs.google.com/presentation/d/11xGL50up6czpCa-0L-G07Sc-CTHiCgAj3HY5Ohu3jgU/edit#slide=id.g1a310abb1e5_0_91
    public int numSubArrayProductLessThanK(int[] nums, int k) {
        if (k <= 0) return 0;
        int res = 0, left = 0, product = 1;
        for (int right = 0; right < nums.length; right++) {
            product *= nums[right];
            while (product >= k) product /= nums[left++];
            res += (right - left + 1);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/11xGL50up6czpCa-0L-G07Sc-CTHiCgAj3HY5Ohu3jgU/edit#slide=id.g1b50a83f277_0_73
    // 牆壁作為一個分界線，牆壁以及右側是invalid，需要在牆的左邊進行搜索。只有當大小都出現在牆的左側，才可以進行匹配。
    // [min, x,x,x,max,y,y,y,牆]
    // count(y) + 1 = 4就是我們的valid subarray
    public long countSubArraysBounds(int[] nums, int minK, int maxK) {
        int N = nums.length;
        int mi = N + 1, ma = N + 1, wall = N;
        long ret = 0;
        for (int i = N - 1; i >= 0; i--) {
            if (nums[i] < minK) wall = i;
            if (nums[i] > maxK) wall = i;
            if (nums[i] == minK) mi = i;
            if (nums[i] == maxK) ma = i;
            if (wall < Math.max(mi, ma)) continue;
            ret += wall - Math.max(mi, ma);
        }
        return ret;
    }


    // https://docs.google.com/presentation/d/11xGL50up6czpCa-0L-G07Sc-CTHiCgAj3HY5Ohu3jgU/edit#slide=id.g1b50a83f277_0_0
    public int countSubArraysMedianK(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        cnt.put(0, 1);
        int res = 0, balance = 0;
        boolean found = false;
        for (int num : nums) {
            if (num < k) {
                balance--;
            } else if (num > k) {
                balance++;
            } else {
                found = true;
            }

            // 必須包括起始的數字，所以目標出現後右側的不再遞增
            if (found) {
                res += cnt.getOrDefault(balance, 0) + cnt.getOrDefault(balance - 1, 0);
            } else {
                cnt.put(balance, cnt.getOrDefault(balance, 0) + 1);
            }
        }
        return res;
    }


    // https://docs.google.com/presentation/d/11xGL50up6czpCa-0L-G07Sc-CTHiCgAj3HY5Ohu3jgU/edit#slide=id.g1b50a83f277_0_9
    public int countNumSubArrays(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private int atMost(int[] nums, int k) {
        int res = 0, left = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            k -= nums[i] % 2;  // 只有奇數才會k-1
            while (k < 0) k += nums[left++] % 2;
            res += i - left + 1;
        }
        return res;
    }


    // https://docs.google.com/presentation/d/11xGL50up6czpCa-0L-G07Sc-CTHiCgAj3HY5Ohu3jgU/edit#slide=id.g1b50a83f277_0_16
    class SubArraysWithDistinct {
        public int subArraysWithDistinct(int[] A, int K) {
            return atMost(A, K) - atMost(A, K - 1);
        }

        private int atMost(int[] A, int K) {
            int res = 0, left = 0, n = A.length;
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                if (map.getOrDefault(A[i], 0) == 0) K--;  // 發現一個不在window裡的數字，還需要不同的數字K-1
                map.put(A[i], map.getOrDefault(A[i], 0) + 1);
                while (K < 0) {
                    map.put(A[left], map.get(A[left]) - 1);
                    if (map.get(A[left]) == 0) K++;
                    left++;
                }
                res += i - left + 1;
            }
            return res;
        }
    }


    // https://docs.google.com/presentation/d/11xGL50up6czpCa-0L-G07Sc-CTHiCgAj3HY5Ohu3jgU/edit#slide=id.g1b50a83f277_0_88
    public long countStrictlyIncreaseSubArrays(int[] nums) {
        long ans = 0;

        // j: valid left bound of window
        // i: valid right bound of window
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (i > 0 && nums[i - 1] >= nums[i]) {
                j = i;  // set j to i, the new head
            }
            ans += i - j + 1;
        }

        return ans;
    }
}

