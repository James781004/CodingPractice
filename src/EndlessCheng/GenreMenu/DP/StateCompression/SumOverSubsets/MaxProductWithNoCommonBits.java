package EndlessCheng.GenreMenu.DP.StateCompression.SumOverSubsets;

public class MaxProductWithNoCommonBits {

    // https://leetcode.cn/problems/maximum-product-of-two-integers-with-no-common-bits/solutions/3768219/mo-ban-gao-wei-qian-zhui-he-sos-dppython-78fz/
    public long maxProduct(int[] nums) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int w = 32 - Integer.numberOfLeadingZeros(mx); // mx 的二進制長度
        int u = 1 << w;
        int[] f = new int[u];
        for (int x : nums) {
            f[x] = x; // 初始值
        }

        for (int i = 0; i < w; i++) { // 枚舉 s 中的元素 i
            for (int s = 0; s < u; s++) { // 從小到大枚舉集合 s
                s |= 1 << i; // 快速跳到第 i 位是 1 的 s
                f[s] = Math.max(f[s], f[s ^ (1 << i)]); // 從 s 的子集 s \ {i} 轉移過來
            }
        }

        long ans = 0;
        for (int x : nums) {
            ans = Math.max(ans, (long) x * f[(u - 1) ^ x]);
        }
        return ans;
    }


}
