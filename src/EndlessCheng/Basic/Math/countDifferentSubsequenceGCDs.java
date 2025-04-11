package EndlessCheng.Basic.Math;

public class countDifferentSubsequenceGCDs {

    // https://leetcode.cn/problems/number-of-different-subsequences-gcds/solutions/2061079/ji-bai-100mei-ju-gcdxun-huan-you-hua-pyt-get7/
    public int countDifferentSubsequenceGCDs(int[] nums) {
        int ans = 0, mx = 0;
        for (int x : nums) mx = Math.max(mx, x);
        var has = new boolean[mx + 1];
        for (int x : nums) has[x] = true;
        for (int i = 1; i <= mx; ++i) {
            int g = 0; // 0 和任何數 x 的最大公約數都是 x
            for (int j = i; j <= mx && g != i; j += i) // 枚舉 i 的倍數 j
                if (has[j]) // 如果 j 在 nums 中
                    g = gcd(g, j); // 更新最大公約數
            if (g == i) ++ans; // 找到一個答案
        }
        return ans;
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


}
