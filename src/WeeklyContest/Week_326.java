package WeeklyContest;

import java.util.HashSet;
import java.util.Set;

public class Week_326 {
    // https://www.bilibili.com/video/BV1H8411E7hn
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2520.Count%20the%20Digits%20That%20Divide%20a%20Number/README.md
    public int countDigits(int num) {
        int res = 0;
        for (int x = num; x > 0; x /= 10) {
            if (num % (x % 10) == 0) res++;
        }
        return res;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2521.Distinct%20Prime%20Factors%20of%20Product%20of%20Array/README.md
    // 哈希表 + 質因數分解
    public int distinctPrimeFactors(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            // 對於數組中的每個元素，先對其進行質因數分解，然後將分解出的質因數加入哈希表中
            for (int i = 2; i <= n / i; i++) {
                if (n % i == 0) {
                    set.add(i);
                    while (n % i == 0) {
                        n /= i;
                    }
                }
            }
            if (n > 1) set.add(n);
        }
        return set.size();
    }


    // https://leetcode.cn/problems/partition-string-into-substrings-with-values-at-most-k/solution/bian-li-by-endlesscheng-7ojw/
    public int minimumPartition(String s, int k) {
        int n = s.length(), ans = 1;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            int cur = s.charAt(i) - '0';
            if (cur > k) return -1;
            sum = sum * 10 + cur;
            if (sum > k) {
                ans++;
                sum %= 10;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/closest-prime-numbers-in-range/solution/yu-chu-li-zhi-shu-mei-ju-by-endlesscheng-uw2b/
    private int[] getPrimes() {
        int MX = (int) 1e6;
        int[] primes = new int[78500];
        boolean[] np = new boolean[MX];
        int pi = 0;
        for (int i = 2; i <= MX; i++) {
            if (!np[i]) {
                primes[pi++] = i;
                for (int j = i; j <= MX / i; j++) { // 避免溢出的寫法
                    np[i * j] = true;
                }
            }
        }

        primes[pi++] = MX + 1;
        primes[pi++] = MX + 1; // 保證下面下標不會越界

        return primes;
    }


    public int[] closestPrimes(int left, int right) {
        int[] primes = getPrimes();
        int p = -1, q = -1;
        for (int i = lowerBound(primes, left); primes[i + 1] <= right; i++) {
            if (p < 0 || primes[i + 1] - primes[i] < q - p) {
                p = primes[i];
                q = primes[i + 1];
            }
        }
        return new int[]{p, q};
    }

    // 見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[] nums, int target) {
        int left = -1, right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }

}
