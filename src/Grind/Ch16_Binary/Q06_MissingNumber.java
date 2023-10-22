package Grind.Ch16_Binary;

public class Q06_MissingNumber {
    // https://leetcode.cn/problems/missing-number/solutions/1086820/tong-ge-lai-shua-ti-la-yi-ti-liang-jie-q-5tc2/
    // 異或的性質：x ^ x = 0，x ^ 0 = x。
    // 而給定的數組中只缺失了一個數，所以可以把 0 到 n 中所有的數都補充上，
    // 那麼，只有一個數出現了一次，把所有這些數進行異或最後的結果就是那個要尋找的數。
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans ^= nums[i] ^ i;
        }
        return ans ^ n;
    }

    public int missingNumber2(int[] nums) {
        int n = nums.length;
        int sum = n * (n + 1) / 2; // 先求出 0 到 n 的數字之和
        for (int num : nums) {
            sum -= num; // 再減去數組中出現的數
        }
        return sum; // 最後剩余的就是沒出現的那個數
    }
}
