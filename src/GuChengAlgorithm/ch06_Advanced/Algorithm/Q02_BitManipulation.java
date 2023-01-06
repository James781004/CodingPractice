package GuChengAlgorithm.ch06_Advanced.Algorithm;

import java.util.HashMap;
import java.util.Map;

public class Q02_BitManipulation {
    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_10
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int n : nums) res ^= n;
        return res;
    }


    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_18
    public int singleNumberII(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) map.put(n, map.getOrDefault(n, 0) + 1);
        for (int k : map.keySet()) if (map.get(k) == 1) return k;
        return -1;
    }

    public int singleNumberII2(int[] nums) {
        int res = 0;
        int[] map = new int[32];
        for (int i = 0; i < nums.length; i++) {
            saveIntoMap(nums[i], map);  // 每個數字轉成二進制，然後放入32位map
        }

        for (int i = 0; i < 32; i++) {
            map[i] = map[i] % 3;  // map每一位取模之後上下的就是多出來的數
        }

        for (int i = 0; i < 32; i++) {
            res |= map[i] << i;  // 重組數字，| 或運算符 bit 的兩個數值對應的值只要 1 個為 1，則結果值相應的 bit 就是 1，否則為 0。
        }

        return res;
    }

    private void saveIntoMap(int num, int[] map) {
        for (int i = 0; i < 32; i++) {
            map[i] += (num >> i) & 1; // & 與運算符: 兩個數值對應的值都是 1，則結果值相應的 bit 就是 1，否則為 0。
        }
    }


    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_25
    public int[] singleNumberIII(int[] nums) {
        int xor = 0;  // 只留下兩個不同數字的異或
        for (int i = 0; i < nums.length; i++) {
            xor ^= nums[i];  // ^ 異或運算符: 如果數值相同則為 0，數值不同則為 1。
        }

        // 這邊找出兩數在某一位的不同，根據這位可以將兩者分開
        int lastDigit = xor & (-xor);  // 直接用補碼
//        int lastDigit = xor & (-xor + 1);  // 補碼 = 反碼 + 1

        int group1 = 0, group2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((lastDigit & nums[i]) == 0) group1 ^= nums[i];
            else group2 ^= nums[i];
        }

        return new int[]{group1, group2};
    }


    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_67
    public int getSum(int a, int b) {
        return b == 0 ? a : getSum((a ^ b), (a & b) << 1);
    }

    public int getSum2(int a, int b) {
        // 3 + 5 = 8
        // 011 + 101 = 1000
        while (b != a) {
            int carry = a & b;  // 101 & 011 = 001  ===> 把a, b相同位置的1保留下來處理進位
            a = a ^ b;          // s = 101 ^ 011 = 110  ===> 把a, b不同位置的1保留下來不進位，直接加法計算
            b = carry << 1;     // b = 00 << 1 = 0  ===> 相同位置有1證明需要進位，進位左移一位下一次loop處理
        }
        return a;  // a = 11 = 3
    }


    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_75
    public int missingNumber(int[] nums) {
        int miss = 0;
        for (int n : nums) miss ^= n;
        for (int i = 1; i <= nums.length; i++) {
            miss ^= i;
        }
        return miss;
    }

    public int missingNumber2(int[] nums) {
        int expectedSum = nums.length * (nums.length + 1) / 2;
        int actualSum = 0;
        for (int n : nums) actualSum += n;
        return expectedSum - actualSum;
    }


    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_82
    public int hammingWeight(int n) {
        int count = 0, mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) count++;
            mask <<= 1;  // mask不斷左移，和每一位&看看，如果結果不是0表示該位是1(1 & 0 == 0, 1 & 1 == 1)，count++
        }
        return count;
    }

    public int hammingWeight2(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n &= (n - 1);  // 最右邊一位1改為0
        }
        return count;
    }

    public int hammingWeight3(int n) {
        return Integer.bitCount(n);
    }


    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_91
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            res[i] = res[i / 2] + (i % 2);  // (x / 2) is (x >>= 1) and (x % 2) is (x & 1)
        }
        return res;
    }

    public int[] countBits2(int num) {
        int[] ans = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            ans[i] = ans[i & (i - 1)] + 1;
        }
        return ans;
    }


    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_99
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    public boolean isPowerOfTwo2(int n) {
        if (n <= 0) return false;
        while (n % 2 == 0) n /= 2;
        return n == 1;
    }


    // https://docs.google.com/presentation/d/1_1mhjlmIwTHKV_rRpCo3YLI3Kc3FU8i4dbqhqghK6s8/edit#slide=id.ga4b64814d9_1_107
    public boolean isPowerOfFour(int num) {
        return (num > 0) && (num & (num - 1)) == 0 && ((num & 0xaaaaaaaa) == 0);
    }
}
