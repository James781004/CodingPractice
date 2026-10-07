package EndlessCheng.GenreMenu.DP.StateCompression.SumOverSubsets;

public class CountEffective {

    // https://leetcode.cn/problems/number-of-effective-subsequences/solutions/3839695/zheng-nan-ze-fan-rong-chi-yuan-li-sos-dp-abir/
    static class Solution {
        private static final int MOD = 1_000_000_007;
        private static final int MAX_N = 100_001;
        private static final int[] pow2 = new int[MAX_N];
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            // 預處理 2 的冪
            pow2[0] = 1;
            for (int i = 1; i < MAX_N; i++) {
                pow2[i] = pow2[i - 1] * 2 % MOD;
            }
        }

        public int countEffective(int[] nums) {
            int or = 0;
            boolean same = true;
            for (int x : nums) {
                or |= x;
                if (x != nums[0]) {
                    same = false;
                }
            }
            // 優化：如果 nums 只有一種數字，那麼非空子序列的按位或都是 or，只有空子序列的按位或比 or 小
            if (same) {
                return 1;
            }

            int w = 32 - Integer.numberOfLeadingZeros(or);
            int[] f = new int[1 << w];
            for (int x : nums) {
                f[x]++;
            }
            for (int i = 0; i < w; i++) {
                if ((or >> i & 1) == 0) { // 優化：or 中是 0 但 s 中是 1 的 f[s] 後面容斥用不到，無需計算
                    continue;
                }
                for (int s = 0; s < (1 << w); s++) {
                    s |= 1 << i;
                    f[s] += f[s ^ (1 << i)];
                }
            }
            // 計算完畢後，f[s] 表示 nums 中的是 s 的子集的元素個數

            long ans = pow2[nums.length]; // 所有子序列的個數
            // 枚舉 or 的所有子集（包括空集）
            int sub = or;
            do {
                int sign = Integer.bitCount(or ^ sub) % 2 > 0 ? -1 : 1;
                ans -= sign * pow2[f[sub]];
                sub = (sub - 1) & or;
            } while (sub != or);
            return (int) ((ans % MOD + MOD) % MOD);
        }
    }


}
