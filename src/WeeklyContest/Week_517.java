package WeeklyContest;

import java.util.*;

public class Week_517 {

    // https://leetcode.cn/problems/count-integers-appearing-in-a-single-block/solutions/4019841/jian-dan-ti-jian-dan-zuo-on-zuo-fa-pytho-q0ng/
    public int countSpecialIntegers(int[] nums) {
        Map<Integer, List<Integer>> pos = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            pos.computeIfAbsent(nums[i], v -> new ArrayList<>()).add(i);
        }

        int ans = 0;
        for (List<Integer> p : pos.values()) {
            if (p.getLast() - p.getFirst() + 1 == p.size()) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/sum-of-decoded-numbers/solutions/4019836/bu-yong-zi-fu-chuan-de-zuo-fa-pythonjava-kara/
    public static final int MOD = 1_000_000_007;

    public int sumDecoded(long[] nums) {
        long ans = 0;
        for (long x : nums) {
            long d = x / 10;
            // 計算 d 的十進制長度
            int lengthD = 0;
            for (long v = d; v > 0; v /= 10) {
                lengthD++;
            }
            long pow10 = (long) Math.pow(10, lengthD - x % 10);
            // 根據 pow10 求出 x = d/pow10 和 y = d%pow10
            ans += pow(d / pow10, d % pow10);
        }
        return (int) (ans % MOD);
    }

    private long pow(long x, long n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }


    // https://leetcode.cn/problems/minimum-operations-to-form-subset-sum-i/solutions/4019830/fen-zu-bei-bao-pythonjavacgo-by-endlessc-o7ao/
    public int minOperations(int[] nums, int sum) {
        int[] f = new int[sum + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2); // 避免加法溢出
        f[0] = 0;

        for (int x : nums) {
            int w = 32 - Integer.numberOfLeadingZeros(x); // x 的二進制長度
            for (int i = sum; i > 0; i--) {
                // 回想一下，0-1 背包是選或不選，狀態轉移方程為 f[i] = min(f[i], f[i-物品體積] + 物品價值)
                // 本題是分組背包，要枚舉選哪個物品（枚舉乘了 a 次或者除了 a 次）
                for (int a = 0; (x << a) <= i; a++) {
                    // 物品體積為 x<<a，價值為 a
                    f[i] = Math.min(f[i], f[i - (x << a)] + a);
                }
                // 從小到大枚舉 x>>a，方便在 x>>a > i 時跳出循環
                for (int a = w - 1; a > 0 && (x >> a) <= i; a--) {
                    // 物品體積為 x>>a，價值為 a
                    f[i] = Math.min(f[i], f[i - (x >> a)] + a);
                }
            }
        }

        return f[sum] == Integer.MAX_VALUE / 2 ? -1 : f[sum];
    }


    // https://leetcode.cn/problems/minimum-operations-to-form-subset-sum-ii/solutions/4019828/fen-zu-bei-bao-by-endlesscheng-eaz3/
    public int minOperationsII(int[] nums, int sum) {
        int[] f = new int[sum + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;

        for (int x : nums) {
            for (int i = sum; i > 0; i--) {
                // 回想一下，0-1 背包是選或不選，狀態轉移方程為 f[i] = min(f[i], f[i-物品體積] + 物品價值)
                // 本題是分組背包，要枚舉選哪個物品（枚舉除法操作次數為 a，乘法操作次數為 b）
                for (int a = 0; (x >> a) > 0; a++) {
                    for (int b = 0; (x >> a << b) <= i; b++) {
                        // 物品體積為 x>>a<<b，價值為 a+b
                        f[i] = Math.min(f[i], f[i - (x >> a << b)] + a + b);
                    }
                }
            }
        }

        return f[sum] == Integer.MAX_VALUE / 2 ? -1 : f[sum];
    }

}










